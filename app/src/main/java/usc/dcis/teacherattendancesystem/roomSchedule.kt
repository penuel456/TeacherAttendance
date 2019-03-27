package usc.dcis.teacherattendancesystem

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_room_schedule.*
import usc.dcis.teacherattendancesystem.scheduleDatabase.ScheduleDAO
import usc.dcis.teacherattendancesystem.scheduleDatabase.ScheduleDatabase

class roomSchedule : AppCompatActivity() {



    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_schedule)
        setSupportActionBar(toolbar)

        val db = ScheduleDatabase.getInstance(this)
        val dao = db.scheduleDAO
        val sdf = java.text.SimpleDateFormat("KK:m")

        var roomTitle: String = intent.getStringExtra("RoomTxt")

        roomNumTxt.text = roomTitle

        val roomNumber = dao.getAllRoomAssignmentsByRoomNumber(roomTitle)

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
        val hourSdf = java.text.SimpleDateFormat("hh:mm a")

        for(room in roomNumber){
            val dayAssigned = room.dayAssigned
            if(dayAssigned.equals("M")){
                monDAY.text = dayAssigned
                monSubject.text = room.courseCode
                monSched.text =
                    "${hourSdf.format(room.startTime)} - ${hourSdf.format(room.endTime)}"
                monTeacher.text = getTeacherName(dao, room.courseCode, room.groupNumber)

            }else if(dayAssigned.equals("T")){
                tueDAY.text = dayAssigned
                tueSubject.text = room.courseCode
                tueSched.text =
                    "${hourSdf.format(room.startTime)} - ${hourSdf.format(room.endTime)}"
                tueTeacher.text = getTeacherName(dao, room.courseCode, room.groupNumber)
            }else if(dayAssigned.equals("W")){
                wedDAY.text = dayAssigned
                wedSubject.text =  room.courseCode
                wedSched.text =
                    "${hourSdf.format(room.startTime)} - ${hourSdf.format(room.endTime)}"
                wedTeacher.text = getTeacherName(dao, room.courseCode, room.groupNumber)
            }else if(dayAssigned.equals("TH")){
                thuDAY.text = dayAssigned
                thuSubject.text = room.courseCode
                thuSched.text =
                    "${hourSdf.format(room.startTime)} - ${hourSdf.format(room.endTime)}"
                thuTeacher.text = getTeacherName(dao, room.courseCode, room.groupNumber)
            }else if(dayAssigned.equals("F")){
                friDAY.text = dayAssigned
                friSubject.text = room.courseCode
                friSched.text =
                    "${hourSdf.format(room.startTime)} - ${hourSdf.format(room.endTime)}"
                friTeacher.text = getTeacherName(dao, room.courseCode, room.groupNumber)
            }
        }



        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

    }

    private fun getTeacherName(dao: ScheduleDAO, courseCode: String, groupNumber: Int): String{
        return dao.getTeacherFromSchedule(dao.getScheduleByCourseCodeAndGroupNumber(courseCode, groupNumber)!!.teacherId).name
    }

}
