package usc.dcis.teacherattendancesystem

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sched_list_teacher.*
import kotlinx.android.synthetic.main.activity_sched_list_teacher.view.*

import kotlinx.android.synthetic.main.sched_list_student.view.*
import usc.dcis.tea.ScheduleFirebase
import usc.dcis.teacherattendancesystem.DateManager.Companion.getDayString
import usc.dcis.teacherattendancesystem.scheduleDatabase.*


import java.text.SimpleDateFormat
import java.util.*

class SchedListTeacher : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sched_list_teacher)
        debugPrintAllRoomAssignments()
        getSchedule()
    }

    @SuppressLint("SimpleDateFormat")
    private fun createSchedules(){
        val db = ScheduleDatabase.getInstance(this)
        val sdf = java.text.SimpleDateFormat("hh:mm a")
        val scheduleDao = db.scheduleDAO
        val cal = Calendar.getInstance()

        Toast.makeText(this, "Please wait while we're getting your schedules.", Toast.LENGTH_SHORT).show()

        FirebaseFirestore.getInstance().collection("scheduleDB")
            .whereEqualTo("userID", 2)
            .get()
            .addOnCompleteListener { task ->
                val scheduleSnapshot = task.result

                if(!scheduleSnapshot?.isEmpty!!){
                    for(sched in scheduleSnapshot){
                        scheduleDao.insert(sched.toObject(ScheduleDB::class.java))
                        //Log.d("FIREBASE", "$sched added")
                    }

                    //region INSERTING ROOM ASSIGNMENT TO LOCAL DATABASE
                    FirebaseFirestore.getInstance().collection("roomAssignment")
                        .get()
                        .addOnCompleteListener { task ->
                            val roomSnapshot = task.result


                            if(!roomSnapshot?.isEmpty!!){
                                for(room in roomSnapshot){
                                    val groupNumber = room["groupNumber"] as Number
                                    if(scheduleDao.
                                            getScheduleCountByCourseCodeAndGroupNumber
                                                (groupNumber.toInt(), room["courseCode"].toString()) != 0){
                                        val startTimestamp = room.getTimestamp("startTime")
                                        val endTimestamp = room.getTimestamp("endTime")
                                        val startTime = startTimestamp?.toDate()
                                        val endTime = endTimestamp?.toDate()
                                        val roomIDToCheck = room["roomID"] as Number


                                        if(scheduleDao.getRoomAssignmentCountByRoomID(roomIDToCheck.toInt()) == 0){
                                            scheduleDao.insertRoomAssignment(
                                                RoomAssignment(roomID = room["roomID"].toString().toInt(),
                                                    courseCode = room["courseCode"].toString(),
                                                    groupNumber = room["groupNumber"].toString().toInt(),
                                                    startTime = sdf.parse(sdf.format(startTime)),
                                                    endTime = sdf.parse(sdf.format(endTime)),
                                                    dayAssigned = room["dayAssigned"].toString(),
                                                    roomNumber = room["roomNumber"].toString())
                                            )

                                            //region INSERTING STATUS BASED ON TODAY'S ROOM ASSIGNMENT
                                            val today = scheduleDao.getAllRoomAssignmentsByDay(DateManager.getCurrentDay())

                                            for (sched in today) {
                                                val statusCheck = scheduleDao.getStatusCountByRoomIdAndDate(DateManager.getCurrentDate(), sched.roomID)

                                                if (statusCheck == 0) {
                                                    scheduleDao.insertStatus(Status(0, sched.roomID, DateManager.getCurrentDate(), "Absent"))
                                                    ScheduleFirebase.AddStatus(FirebaseFirestore.getInstance(),
                                                        scheduleDao.getStatusByRoomIdAndDate(DateManager.getCurrentDate(), sched.roomID))
                                                }
                                            }
                                            //endregion
                                        }

                                    }



                                }
                            }
                        }
                    //endregion


                }

            }

    }

    private fun getSchedule(){
        val db = ScheduleDatabase.getInstance(this)
        val scheduleDao = db.scheduleDAO
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_WEEK)

        val scheduleList = scheduleDao.getAllSchedules()
        val roomAssignmentList = scheduleDao.getAllRoomAssignmentsByDay(getDayString(day))
        Log.d("TODAY:", getDayString(day))

        if(roomAssignmentList.isNotEmpty()){
            getOnGoingAndUpNext(scheduleDao, roomAssignmentList)
        }else {
            Log.d("NOTICE", "RoomAssignmentList is empty. Do something here (should say 'no schedule for today')")
            displayNoSchedule()
        }
        ScheduleDatabase.destroyInstance()
    }

    private fun displayNoSchedule(){
        Schedule_teacher_layout.visibility = View.INVISIBLE
        noSchedNotif2.visibility = View.VISIBLE
    }

    private fun getOnGoingAndUpNext(scheduleDao: ScheduleDAO, roomAssignmentList: List<RoomAssignment>){
        var sdf = SimpleDateFormat("hh:mm a")
        var isThereOnGoing = false
        var isThereUpNext = false

        val sdfTime = DateManager.getCurrentTime()
        val sdfDate = DateManager.getCurrentDate()


        for(rooms in roomAssignmentList){
            Log.d("TODAYROOM: ", rooms.toString())
            val currentSched = scheduleDao.getScheduleByCourseCodeAndGroupNumber(rooms.courseCode, rooms.groupNumber)
            if(sdfTime.after(rooms?.endTime)){
                Log.d("TIMEDEBUG:", "Schedule ${rooms?.roomID} is FINISHED in ${rooms?.roomNumber}")
            }else if(sdfTime.before(rooms.startTime)){
                Log.d("TIMEDEBUG:", "Schedule ${rooms?.roomID} is ABOUT TO GO in ${rooms?.roomNumber}")
                Schedule_teacher_layout.studNextCourseCode.text = currentSched?.courseCode
                Schedule_teacher_layout.studNextTeacher.text = scheduleDao.getTeacherFromSchedule(currentSched?.teacherId)?.name
                Schedule_teacher_layout.studNextBuilding.text = rooms?.roomNumber
                Schedule_teacher_layout.studNextStartTime.text = sdf.format(rooms?.startTime)
                Schedule_teacher_layout.studNextEndTime.text = sdf.format(rooms?.endTime)
                isThereUpNext = true
            }else if(sdfTime.after(rooms.startTime) && sdfTime.before(rooms.endTime)){
                Log.d("TIMEDEBUG:", "Schedule ${rooms?.roomID} is CURRENTLY in ${rooms?.roomNumber}")
                Schedule_teacher_layout.teachCourseCode.text = currentSched?.courseCode
                Schedule_teacher_layout.teachTeacher.text = scheduleDao.getTeacherFromSchedule(currentSched?.teacherId)?.name
                Schedule_teacher_layout.teachBuilding.text = rooms?.roomNumber
                Schedule_teacher_layout.teachStartTime.text = sdf.format(rooms?.startTime)
                Schedule_teacher_layout.teachEndTime.text = sdf.format(rooms?.endTime)

                /* For status, it's supposed to get from the database that the teacher inputted. */
               Schedule_teacher_layout.studStatus.text = scheduleDao.getStatusByRoomIdAndDate(sdfDate, rooms.roomID)?.status
                isThereOnGoing = true
            }

            if(isThereOnGoing && isThereUpNext) break
        }

    }

    fun refreshSchedule() {
        getSchedule()
    }

    private fun debugPrintAllRoomAssignments(){
        val db = ScheduleDatabase.getInstance(this)
        var scheduleDao = db.scheduleDAO

        var roomAssignmentList = scheduleDao.getAllRoomAssignmentsByDay("W")
        var sdf = java.text.SimpleDateFormat("h:m a")

        for(rooms in roomAssignmentList){
            Log.d("ROOMASSN", "RoomID: ${rooms.roomID}")
            //Log.d("ROOMASSN", "CourseID: ${rooms.courseID}")
            //Log.d("ROOMASSN", "CourseCode: ${scheduleDao.getSchedule(rooms.courseID).courseCode}")
            Log.d("ROOMASSN", "StartTime: ${sdf.format(rooms.startTime)}")
            Log.d("ROOMASSN", "EndTime: ${sdf.format(rooms.endTime)}")
            Log.d("ROOMASSN", "DayAssigned: ${rooms.dayAssigned}")
        }

        ScheduleDatabase.destroyInstance()
    }

    /*fun check_stat(){
        Log.d("Click", "Senpai clicked me")
        val Text = view?.status!!.selectedItem.toString()
        //status.text = Text
        Log.d("Click", Text)
        if(Text.equals("Absent")) {
            Log.d("Click", "I'm here")
            reason.visibility = View.VISIBLE
        }else{
            reason.visibility = View.INVISIBLE
        }
    }*/

    fun editSched(view: View){
        val chooseSched = Intent(this, chooseschedule::class.java)
        startActivity(chooseSched)
    }
}
