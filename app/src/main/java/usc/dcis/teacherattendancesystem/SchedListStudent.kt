package usc.dcis.teacherattendancesystem

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.sched_list_student.*
import kotlinx.android.synthetic.main.sched_list_student.view.*
import usc.dcis.teacherattendancesystem.DateManager.Companion.getCurrentDate
import usc.dcis.teacherattendancesystem.DateManager.Companion.getCurrentTime
import usc.dcis.teacherattendancesystem.DateManager.Companion.getDayString
import usc.dcis.teacherattendancesystem.scheduleDatabase.*
import java.util.*


class SchedListStudent : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sched_list_student)

        //val loginBtn = findViewById<Button>(R.id.loginBtn)
        createSchedules()
        debugPrintAllRoomAssignments()
    }

    @Suppress("NAME_SHADOWING")
    @SuppressLint("SimpleDateFormat")
    private fun createSchedules(){
        var isScheduleComplete = false
        var isRoomAssignmentComplete = false
        var isUserComplete = false
        val db = ScheduleDatabase.getInstance(this)
        val sdf = java.text.SimpleDateFormat("hh:mm a")
        sdf.timeZone = TimeZone.getTimeZone("GMT+8")
        val scheduleDao = db.scheduleDAO

        Toast.makeText(this, "Please wait while we're getting your schedules.", Toast.LENGTH_SHORT).show()

        FirebaseFirestore.getInstance().collection("scheduleDB")
            .whereEqualTo("userID", 3)
            .get()
            .addOnCompleteListener { task ->
                val scheduleSnapshot = task.result

                if(!scheduleSnapshot?.isEmpty!!){
                    for(sched in scheduleSnapshot){
                        scheduleDao.insert(sched.toObject(ScheduleDB::class.java))
                        Log.d("FIREBASE", "${sched["courseCode"]} at group number ${sched["groupNumber"]} added")
                    }
                    isScheduleComplete = true

                    checkFirebaseState(isScheduleComplete, isRoomAssignmentComplete, isUserComplete)

                    //region INSERTING ROOM ASSIGNMENT TO LOCAL DATABASE
                    FirebaseFirestore.getInstance().collection("roomAssignment")
                        .whereEqualTo("dayAssigned", DateManager.getCurrentDay())
                        .get()
                        .addOnCompleteListener { task ->
                            val roomSnapshot = task.result

                            if(!roomSnapshot?.isEmpty!!){
                                for(room in roomSnapshot){
                                    val groupNumber = room["groupNumber"] as Number

                                    //CHECKS IF SCHEDULE DB HAS EXISTING ROOM ASSIGNMENT
                                    if(scheduleDao.
                                            getScheduleCountByCourseCodeAndGroupNumber
                                                (groupNumber = groupNumber.toInt(), courseCode = room["courseCode"].toString()) != 0){
                                        val startTimestamp = room.getTimestamp("startTime")
                                        val endTimestamp = room.getTimestamp("endTime")
                                        val startTime = startTimestamp?.toDate()
                                        val endTime = endTimestamp?.toDate()
                                        val roomIDToCheck = room["roomID"] as Number

                                        /*
                                        Log.d("FIREBASE", "RoomID: ${roomIDToCheck.toInt()}, CourseCode: ${room["courseCode"]}, " +
                                                "GroupNumber: ${room["groupNumber"]}" +
                                                "\nTime: ${sdf.format(startTime)} - ${sdf.format(endTime)}" +
                                                "\nRoomNumber: ${room["roomNumber"]}" +
                                                "\nDay: ${room["dayAssigned"]}")
                                        */

                                        //CHECKS IF ROOM ASSIGNMENT EXISTS IN LOCAL DATABASE
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

                                                Log.d("FIREBASE", "RoomAssignment ${room["courseCode"]} " +
                                                        " at group number ${room["groupNumber"]} added")
                                        }
                                    }
                                }
                            }


                            isRoomAssignmentComplete = true

                            checkFirebaseState(isScheduleComplete, isRoomAssignmentComplete, isUserComplete)

                        }
                    //endregion

                    //region INSERTING TEACHERS BASED ON SCHEDULE
                    FirebaseFirestore.getInstance().collection("userDB")
                        .get()
                        .addOnCompleteListener { task ->
                            val userSnapshot = task.result

                            for(user in userSnapshot!!){
                                val teacherIDToCheck = user["userID"] as Number
                                for(sched in scheduleDao.getAllSchedules()){
                                    if(sched.teacherId != null){
                                        if(sched.teacherId == teacherIDToCheck.toInt()){
                                            scheduleDao.insertUser(
                                                UserDB(userID = user["userID"].toString().toInt(), idNumber = user["idNumber"].toString().toInt(),
                                                    name = user["name"] as String, password = null, type = user["type"] as String)
                                            )

                                            Log.d("FIREBASE", "User ${user["name"]} added.")
                                        }
                                    }
                                }
                            }

                            isUserComplete = true

                            checkFirebaseState(isScheduleComplete, isRoomAssignmentComplete, isUserComplete)


                        }

                    //endregion
                }
                getSchedule()
            }
    }

    private fun checkFirebaseState(bool1: Boolean, bool2: Boolean, bool3: Boolean){
        if(bool1 && bool2 && bool3) getSchedule()
    }

    private fun getSchedule(){
        val db = ScheduleDatabase.getInstance(this)
        val dao = db.scheduleDAO
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_WEEK)

        val roomAssignmentList = dao.getAllRoomAssignmentsByDay(DateManager.getDayString(day))

        Log.d("TODAY:", getDayString(day))

        if(roomAssignmentList.isNotEmpty()){
            getOnGoingAndUpNext(dao, roomAssignmentList.toList())
        }else {
            Log.d("NOTICE", "RoomAssignmentList is empty. Do something here (should say 'no schedule for today')")
            displayNoSchedule()
        }
        ScheduleDatabase.destroyInstance()
    }

    private fun displayNoSchedule(){
        Schedule_layout.visibility = View.INVISIBLE
        noSchedNotif.visibility = View.VISIBLE
    }

    @SuppressLint("SimpleDateFormat")
    private fun getOnGoingAndUpNext(dao: ScheduleDAO, roomAssignmentList: List<RoomAssignment>){
        val sdf = java.text.SimpleDateFormat("hh:mm a")
        sdf.timeZone = TimeZone.getTimeZone("GMT+8")
        var isThereOnGoing = false
        var isThereUpNext = false

        val sdfTime = getCurrentTime()
        val sdfDate = getCurrentDate()

        for(rooms in roomAssignmentList){
            Log.d("TODAYROOM: ", rooms.toString())
            val currentSched = dao.getScheduleByCourseCodeAndGroupNumber(rooms.courseCode, rooms.groupNumber)
            if(sdfTime.after(rooms.endTime)){
                Log.d("TIMEDEBUG:", "Schedule ${rooms.roomID} is FINISHED in ${rooms.roomNumber}. " +
                        "EndTime is ${sdf.format(rooms.endTime)}")
            }else if(sdfTime.before(rooms.startTime)){
                Log.d("TIMEDEBUG:", "Schedule ${rooms.roomID} is ABOUT TO GO in ${rooms.roomNumber}. " +
                        "StartTime is ${sdf.format(rooms.startTime)}")
                Schedule_layout.studUpNextCourseCode.text = currentSched?.courseCode
                Schedule_layout.studUpNextTeacher.text = dao.getTeacherFromSchedule(currentSched?.teacherId)
                    ?.name
                Schedule_layout.studUpNextBuilding.text = rooms.roomNumber
                Schedule_layout.studUpNextStartTime.text = sdf.format(rooms.startTime)
                Schedule_layout.studUpNextEndTime.text = sdf.format(rooms.endTime)
                isThereUpNext = true
            }else if(sdfTime.after(rooms.startTime) && sdfTime.before(rooms.endTime)){
                Log.d("TIMEDEBUG:", "Schedule ${rooms.roomID} is CURRENTLY in ${rooms.roomNumber}. " +
                        "EndTime is ${sdf.format(rooms.endTime)}")
                Schedule_layout.studCourseCode.text = currentSched?.courseCode
                Schedule_layout.studTeacher.text = dao.getTeacherFromSchedule(currentSched?.teacherId)?.name
                Schedule_layout.studBuilding.text = rooms.roomNumber
                Schedule_layout.studStartTime.text = sdf.format(rooms.startTime)
                Schedule_layout.studEndTime.text = sdf.format(rooms.endTime)

                /* For status, it's supposed to get from the database that the teacher inputted. */
                Schedule_layout.studStatus.text = dao.getStatusByRoomIdAndDate(sdfDate, rooms?.roomID)?.status


                isThereOnGoing = true
            }

            if(isThereOnGoing && isThereUpNext) break
        }

        if(!isThereOnGoing && !isThereUpNext){
            displayNoSchedule()
        }

    }

    fun refreshSchedule(view: View) {
        getSchedule()
    }

    @SuppressLint("SimpleDateFormat")
    private fun debugPrintAllRoomAssignments(){
        val db = ScheduleDatabase.getInstance(this)
        val scheduleDao = db.scheduleDAO

        val roomAssignmentList = scheduleDao.getAllRoomAssignmentsByDay("W")
        val sdf = java.text.SimpleDateFormat("h:m a")

        for(rooms in roomAssignmentList){
            val schedDB = scheduleDao.getScheduleByCourseCodeAndGroupNumber(rooms.courseCode, rooms.groupNumber)
            val teacher: UserDB? = scheduleDao.getTeacherFromSchedule(schedDB?.teacherId)

            Log.d("ROOMASSN", "RoomID: ${rooms.roomID}")
            Log.d("ROOMASSN", "Teacher: ${teacher?.name}")
            Log.d("ROOMASSN", "GroupNumber: ${rooms.groupNumber}")
            Log.d("ROOMASSN", "CourseCode: ${rooms.courseCode}")
            Log.d("ROOMASSN", "StartTime: ${sdf.format(rooms.startTime)}")
            Log.d("ROOMASSN", "EndTime: ${sdf.format(rooms.endTime)}")
            Log.d("ROOMASSN", "DayAssigned: ${rooms.dayAssigned}")
        }

        ScheduleDatabase.destroyInstance()
    }
}

