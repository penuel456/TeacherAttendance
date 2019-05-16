package usc.dcis.teacherattendancesystem


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sched_list_teacher.*
import kotlinx.android.synthetic.main.activity_sched_list_teacher.view.*
import kotlinx.android.synthetic.main.sched_list_student.view.*
import usc.dcis.tea.ScheduleFirebase
import usc.dcis.teacherattendancesystem.DateManager.Companion.getDayString
import usc.dcis.teacherattendancesystem.scheduleDatabase.*
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.util.*

class SchedListTeacher : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sched_list_teacher)
        val statusSpinner = findViewById<Spinner>(R.id.status)
        val teacherStatus = arrayOf("Present", "Absent")
        val statusSpinner_adapter = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            teacherStatus // Array
        )
        statusSpinner.adapter = statusSpinner_adapter
        statusSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                // Display the selected item text on text view
                val statusValue = parent.getItemAtPosition(position).toString()
                // date.text = buildingName
                when (statusValue){
                    "Present" -> {
                        reason.visibility = View.GONE
                    }
                    "Absent" -> reason.visibility = View.VISIBLE
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
            }
        }
        createSchedules()
    }

    @Suppress("NAME_SHADOWING")
    @SuppressLint("SimpleDateFormat")
    private fun createSchedules(){
        val db = ScheduleDatabase.getInstance(this)

        val scheduleDao = db.scheduleDAO

        Toast.makeText(this, "Please wait while we're getting your schedules.", Toast.LENGTH_SHORT).show()

        FirebaseFirestore.getInstance().collection("scheduleDB")
            .whereEqualTo("userID", 2)
            .get()
            .addOnCompleteListener { task ->
                Log.d("FIREBASE", "Getting schedules")
                val scheduleSnapshot = task.result

                if(!scheduleSnapshot?.isEmpty!!){
                    for(sched in scheduleSnapshot){
                        val groupNumber = parseInt(sched["groupNumber"].toString())
                        Log.d("FIREBASE", "GRP #$groupNumber")

                        //region CHECKS IF SCHEDULE IN LOCAL DATABASE EXISTS
                        if(scheduleDao.getScheduleCountByCourseCodeAndGroupNumber(courseCode = sched["courseCode"].toString(),
                                groupNumber = groupNumber) == 0){
                            scheduleDao.insert(sched.toObject(ScheduleDB::class.java))
                        }
                        //endregion
                    }

                    //region INSERTING ROOM ASSIGNMENT TO LOCAL DATABASE
                    FirebaseFirestore.getInstance().collection("roomAssignment")
                        .get()
                        .addOnCompleteListener { task ->
                            val roomSnapshot = task.result

                            if(!roomSnapshot?.isEmpty!!){
                                for(room in roomSnapshot){
                                    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
                                    sdf.timeZone = TimeZone.getTimeZone("Asia/Singapore")

                                    val groupNumber = parseInt(room["groupNumber"].toString())

                                    if(scheduleDao.
                                            getScheduleCountByCourseCodeAndGroupNumber
                                                (groupNumber, room["courseCode"].toString()) != 0){
                                        val startTimestamp = room.getTimestamp("startTime")
                                        val endTimestamp = room.getTimestamp("endTime")
                                        val startTime = sdf.format(startTimestamp?.toDate())
                                        val endTime = sdf.format(endTimestamp?.toDate())
                                        val roomIDToCheck = parseInt(room["roomID"].toString())

                                        //region CHECKS IF ROOM ASSIGNMENT IN LOCAL DATABASE EXISTS
                                        if(scheduleDao.getRoomAssignmentCountByRoomID(roomIDToCheck) == 0){
                                            scheduleDao.insertRoomAssignment(
                                                RoomAssignment(roomID = room["roomID"].toString().toInt(),
                                                    courseCode = room["courseCode"].toString(),
                                                    groupNumber = room["groupNumber"].toString().toInt(),
                                                    startTime = sdf.parse(startTime),
                                                    endTime = sdf.parse(endTime),
                                                    dayAssigned = room["dayAssigned"].toString(),
                                                    roomNumber = room["roomNumber"].toString())
                                            )
                                        }
                                        //endregion
                                    }
                                }

                                FirebaseFirestore.getInstance().collection("status")
                                    .whereEqualTo("date", DateManager.getCurrentDate())
                                    .get()
                                    .addOnCompleteListener { task ->
                                        if(task.isComplete){
                                            val statusSnapshot = task.result
                                            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                                            sdf.timeZone = TimeZone.getTimeZone("GMT+8")

                                            for(status in statusSnapshot!!){
                                                val dateTimestamp = status.getTimestamp("date")
                                                val statusDate = sdf.format(dateTimestamp?.toDate())
                                                Log.d("STATUSDATE", statusDate)
                                                val roomID = parseInt(status["roomID"].toString())
                                                val statusID = parseInt(status["statusId"].toString())

                                                scheduleDao.insertStatus(
                                                    Status(
                                                        statusId = statusID,
                                                        roomID = roomID,
                                                        date = sdf.parse(statusDate),
                                                        status = status["status"].toString(),
                                                        remarks = status["remarks"].toString()
                                                    )
                                                )
                                            }

                                            //region INSERTING STATUS BASED ON TODAY'S ROOM ASSIGNMENT
                                            ScheduleDebug.printAllRoomAssignmentsByDay(scheduleDao, DateManager.getCurrentDay())

                                            val rooms = scheduleDao.getAllRoomAssignmentsByDay(
                                                dayAssigned = DateManager.getCurrentDay()
                                            )

                                            for (room in rooms) {
                                                val statusCheck = scheduleDao.getStatusCountByRoomId(roomID = room.roomID)
                                                Log.d("STATUSCHECK", "Status Check is $statusCheck")

                                                //val status =

                                                if (statusCheck == 0) {

                                                    scheduleDao.insertStatus(Status(0, room.roomID, DateManager.getCurrentDate(), "Absent"))


                                                    val status = scheduleDao.getStatusByRoomIdAndDate(
                                                        date = DateManager.getCurrentDate(),
                                                        roomID = room.roomID
                                                    )


                                                    Log.d("STATUS", "ADDING IN FIREBASE: $status")

                                                    ScheduleFirebase.AddStatus(db = FirebaseFirestore.getInstance(), status = status)
                                                }
                                            }
                                            //endregion
                                        }

                                        getSchedule()
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

        val roomAssignmentList = scheduleDao.getAllRoomAssignmentsByDay(getDayString(day))
        Log.d("TODAY:", getDayString(day))

        if(roomAssignmentList.isNotEmpty()){
            displaySchedule()
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
    private fun displaySchedule(){
        Schedule_teacher_layout.visibility = View.VISIBLE
        noSchedNotif2.visibility = View.INVISIBLE
    }


    @SuppressLint("SimpleDateFormat")
    private fun getOnGoingAndUpNext(dao: ScheduleDAO, roomAssignmentList: List<RoomAssignment>){
        val sdf = SimpleDateFormat("hh:mm a")
        sdf.timeZone = TimeZone.getTimeZone("GMT+8")
        var isThereOnGoing = false
        var isThereUpNext = false

        val sdfTime = DateManager.getCurrentTime()
        val sdfDate = DateManager.getCurrentDate()

        for(rooms in roomAssignmentList){
            val currentSched = dao.getScheduleByCourseCodeAndGroupNumber(rooms.courseCode, rooms.groupNumber)
            val start = sdf.parse(sdf.format(rooms.startTime))
            val end = sdf.parse(sdf.format(rooms.endTime))

            Log.d("START", start.toString())
            Log.d("END", end.toString())
            when {
                sdfTime.after(end) -> Log.d("TIMEDEBUG:", "Schedule ${rooms.roomID} is FINISHED in ${rooms.roomNumber}. " +
                        "EndTime is ${sdf.format(rooms.endTime)}")
                sdfTime.before(start) -> {
                    Log.d("TIMEDEBUG:", "Schedule ${rooms.roomID} is ABOUT TO GO in ${rooms.roomNumber}. " +
                            "StartTime is ${sdf.format(rooms.startTime)}")
                    Schedule_teacher_layout.studNextCourseCode.text = currentSched?.courseCode
                    Schedule_teacher_layout.studNextBuilding.text = rooms.roomNumber
                    Schedule_teacher_layout.studNextStartTime.text = sdf.format(rooms.startTime)
                    Schedule_teacher_layout.studNextEndTime.text = sdf.format(rooms.endTime)
                    isThereUpNext = true
                }
                sdfTime.after(start) -> {
                    Log.d("TIMEDEBUG:", "Schedule ${rooms.roomID} is CURRENTLY in ${rooms.roomNumber}. " +
                            "EndTime is ${sdf.format(rooms.endTime)}")
                    currentRoomID = rooms.roomID
                    Schedule_teacher_layout.teachCourseCode.text = currentSched?.courseCode
                    Schedule_teacher_layout.teachBuilding.text = rooms.roomNumber
                    Schedule_teacher_layout.teachStartTime.text = sdf.format(rooms.startTime)
                    Schedule_teacher_layout.teachEndTime.text = sdf.format(rooms.endTime)

                    /* For status, it's supposed to get from the database that the teacher inputted. */
                    Schedule_teacher_layout?.studStatus?.text = dao.getStatusByRoomIdAndDate(sdfDate, rooms.roomID)?.status
                    isThereOnGoing = true
                }
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

    /*
    fun check_stat(view: View){
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
    }
    */


    fun submitStatus(view: View){
        val dao = ScheduleDatabase.getInstance(this).scheduleDAO
        val statusSpinner = findViewById<Spinner>(R.id.status)

        Log.d("SUBMIT", "$currentRoomID ${DateManager.getCurrentDate()} ${statusSpinner.selectedItem}")

        dao.updateStatusState(roomID = currentRoomID, date = DateManager.getCurrentDate(),
            status = view.status?.selectedItem.toString())
        val currentStatus = dao.getStatusByRoomIdAndDate(date = DateManager.getCurrentDate(), roomID = currentRoomID)

        Log.d("STATUS TO SUBMIT:", currentStatus.toString())
        //ScheduleFirebase.UpdateStatus(db = FirebaseFirestore.getInstance(), status = currentStatus)


        Toast.makeText(this, "Status updated.", Toast.LENGTH_SHORT).show()
    }

    fun editSched(view: View){
        val chooseSched = Intent(this, chooseschedule::class.java)
        startActivity(chooseSched)
    }

    companion object {
        var currentRoomID: Int = 0
        @SuppressLint("SimpleDateFormat")
        private fun debugPrintAllRoomAssignments(schedListTeacher: SchedListTeacher){
            val db = ScheduleDatabase.getInstance(schedListTeacher)
            val scheduleDao = db.scheduleDAO
    
            val roomAssignmentList = scheduleDao.getAllRoomAssignments()
            val sdf = SimpleDateFormat("hh:mm a")
    
            for(rooms in roomAssignmentList){
                Log.d("ROOMASSN", "\nRoomID: ${rooms.roomID}")
                Log.d("ROOMASSN", "CourseCode: ${rooms.courseCode}")
                Log.d("ROOMASSN", "StartTime: ${sdf.format(rooms.startTime)}")
                Log.d("ROOMASSN", "EndTime: ${sdf.format(rooms.endTime)}")
                Log.d("ROOMASSN", "DayAssigned: ${rooms.dayAssigned}")
            }
    
            ScheduleDatabase.destroyInstance()
        }
    }
}
