package usc.dcis.teacherattendancesystem

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.android.synthetic.main.activity_room_schedule.*
import usc.dcis.teacherattendancesystem.scheduleDatabase.*
import java.util.*

class roomSchedule : AppCompatActivity() {



    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_schedule)
        setSupportActionBar(toolbar)

        val db = ScheduleDatabase.getInstance(this)
        val dao = db.scheduleDAO
        val sdf = java.text.SimpleDateFormat("KK:m")

        val roomTitle: String = intent.getStringExtra("RoomTxt")

        roomNumTxt.text = roomTitle

       // val roomNumber = dao.getAllRoomAssignmentsByRoomNumber(roomTitle)
        //ScheduleDebug.printAllRoomAssignments(dao)

        createSchedules()

        /*
        var firestore = FirebaseFirestore.getInstance()
        firestore.collection("roomAssignment").whereEqualTo("roomNumber", roomTitle)
            .get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    var documents = task.result

                    for(document in documents){
                        var day = document["dayAssigned"]
                        document["startTime"]
                        Log.d("FIREBASE", "Day: ${day}")
                    }
                }
            }
        */




    }

    fun displayRooms(dao: ScheduleDAO, roomNumber: List<RoomAssignment>){
        val hourSdf = java.text.SimpleDateFormat("hh:mm a")


        for(room in roomNumber){
            Log.d("MAO NI ANG TEACHERID: ",  "${dao.getScheduleByCourseCodeAndGroupNumber(room.courseCode, room.groupNumber)?.teacherId}")

            roomNumTxt.text = room.roomNumber + "( '" + room.dayAssigned + "' Schedule )"

            if(room.roomID.equals(roomNumber[0].roomID)){
                courseCode1.text = room.courseCode
                groupNumber1.text = room.groupNumber.toString()
                schedTime1.text =  "${hourSdf.format(room.startTime)} - ${hourSdf.format(room.endTime)}"
                courseTeacher1.text = getTeacherName(dao, room.courseCode, room.groupNumber)
            }else if(room.roomID.equals(roomNumber[1].roomID)){
                courseCode2.text = room.courseCode
                groupNumber2.text = room.groupNumber.toString()
                schedTime2.text =  "${hourSdf.format(room.startTime)} - ${hourSdf.format(room.endTime)}"
                courseTeacher2.text = getTeacherName(dao, room.courseCode, room.groupNumber)
            }

            /*val dayAssigned = room.dayAssigned
            when {
                dayAssigned.equals("M") -> {
                    monDAY.text = dayAssigned
                    monSubject.text = room.courseCode
                    monSched.text =
                            "${hourSdf.format(room.startTime)} - ${hourSdf.format(room.endTime)}"
                    monTeacher.text = getTeacherName(dao, room.courseCode, room.groupNumber)

                }
                dayAssigned.equals("T") -> {
                    tueDAY.text = dayAssigned
                    tueSubject.text = room.courseCode
                    tueSched.text =
                            "${hourSdf.format(room.startTime)} - ${hourSdf.format(room.endTime)}"
                    tueTeacher.text = getTeacherName(dao, room.courseCode, room.groupNumber)
                }
                dayAssigned.equals("W") -> {
                    wedDAY.text = dayAssigned
                    wedSubject.text =  room.courseCode
                    wedSched.text =
                            "${hourSdf.format(room.startTime)} - ${hourSdf.format(room.endTime)}"
                    wedTeacher.text = getTeacherName(dao, room.courseCode, room.groupNumber)
                }
                dayAssigned.equals("TH") -> {
                    thuDAY.text = dayAssigned
                    thuSubject.text = room.courseCode
                    thuSched.text =
                            "${hourSdf.format(room.startTime)} - ${hourSdf.format(room.endTime)}"
                    thuTeacher.text = getTeacherName(dao, room.courseCode, room.groupNumber)
                }
                dayAssigned.equals("F") -> {
                    friDAY.text = dayAssigned
                    friSubject.text = room.courseCode
                    friSched.text =
                            "${hourSdf.format(room.startTime)} - ${hourSdf.format(room.endTime)}"
                    friTeacher.text = getTeacherName(dao, room.courseCode, room.groupNumber)
                }
                dayAssigned.equals("SAT") -> {
                    saturDAY.text = dayAssigned
                    satSubject.text = room.courseCode
                    satSched.text =
                            "${hourSdf.format(room.startTime)} - ${hourSdf.format(room.endTime)}"
                    satTeacher.text = getTeacherName(dao, room.courseCode, room.groupNumber)
                }
            }*/
        }
    }

    @Suppress("NAME_SHADOWING")
    @SuppressLint("SimpleDateFormat")
    private fun createSchedules(){
        val db = ScheduleDatabase.getInstance(this)
        val sdf = java.text.SimpleDateFormat("hh:mm a")
        val scheduleDao = db.scheduleDAO

        Toast.makeText(this, "Please wait while we're getting the schedules.", Toast.LENGTH_SHORT).show()

        //region INSERTING ROOM ASSIGNMENT TO LOCAL DATABASE
        FirebaseFirestore.getInstance().collection("roomAssignment")
            .whereEqualTo("roomNumber", intent.getStringExtra("RoomTxt"))
            .get()
            .addOnCompleteListener { task ->
                val roomSnapshot = task.result
                Log.d("ROOM NUMBER: ", intent.getStringExtra("RoomTxt"))

                if(!roomSnapshot?.isEmpty!!){
                    for(room in roomSnapshot){
                        val groupNumber = room["groupNumber"] as Number

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
                            }

                            Log.d("FIREBASE", "$room added")
                            Log.d("FIREBASE: ", "NAKUHA ANG ROOMS")

                    }

                    FirebaseFirestore.getInstance().collection("scheduleDB")
                        .get()
                        .addOnCompleteListener { task ->
                            val scheduleSnapshot = task.result

                            if(!scheduleSnapshot?.isEmpty!!){
                                for(sched in scheduleSnapshot){
                                    val groupNumber = sched["groupNumber"] as Number
                                    if(scheduleDao.getRoomAssignmentCountByCourseCodeAndGroupNumber(
                                            courseCode = sched["courseCode"].toString(),
                                            groupNumber = groupNumber.toInt()) != 0){
                                        scheduleDao.insert(sched.toObject(ScheduleDB::class.java))
                                    }

                                    Log.d("FIREBASE", "$sched added")
                                }

                                Log.d("FIREBASE: ", "NAKUHA ANG SCHED")

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

                                        displayRooms(scheduleDao, scheduleDao.getAllRoomAssignmentsByRoomNumberAndDay(
                                            intent.getStringExtra("RoomTxt"),
                                            DateManager.getDayString(Calendar.getInstance().get(Calendar.DAY_OF_WEEK))))
                                    }
                                //endregion
                            }
                        }

                }

            }
        //endregion

    }

    private fun getTeacherName(dao: ScheduleDAO, courseCode: String, groupNumber: Int): String{
        return dao.getTeacherFromSchedule(dao.getScheduleByCourseCodeAndGroupNumber(courseCode, groupNumber)?.teacherId)?.name
    }

}
