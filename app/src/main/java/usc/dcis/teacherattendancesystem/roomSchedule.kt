package usc.dcis.teacherattendancesystem

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.android.synthetic.main.activity_room_schedule.*
import kotlinx.android.synthetic.main.content_activity_editschedule.*
import usc.dcis.teacherattendancesystem.scheduleDatabase.ScheduleDatabase

class roomSchedule : AppCompatActivity() {



    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_schedule)
        setSupportActionBar(toolbar)

        val db = ScheduleDatabase.getInstance(this)
        val scheduleListTest = db.scheduleDAO
        val sdf = java.text.SimpleDateFormat("KK:m")

        val roomTitle: String = intent.getStringExtra("RoomTxt")

        roomNumTxt.text = roomTitle

        val roomNumber = scheduleListTest.getAllRoomAssignmentsByRoomNumber(roomTitle)

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
        for(i in roomNumber.indices){
            val dayAssigned = roomNumber[i].dayAssigned
            if(dayAssigned.equals("M")){
                monDAY.text = dayAssigned
                monSubject.text = roomNumber[i].courseCode
                monSched.text =
                    "${roomNumber[i].startTime!!.hours}:${roomNumber[i].startTime!!.minutes} - ${roomNumber[i].endTime!!.hours}:${roomNumber[i].endTime!!.minutes}"
                monTeacher.text = scheduleListTest.getTeacherFromSchedule(scheduleListTest.getScheduleByCourseCodeAndGroupNumber(roomNumber[i].courseCode, roomNumber[i].groupNumber)!!.teacherId).name

            }else if(dayAssigned.equals("T")){
                tueDAY.text = dayAssigned
                tueSubject.text = roomNumber[i].courseCode
                tueSched.text =
                    "${roomNumber[i].startTime!!.hours}:${roomNumber[i].startTime!!.minutes} - ${roomNumber[i].endTime!!.hours}:${roomNumber[i].endTime!!.minutes}"
                tueTeacher.text = scheduleListTest.getTeacherFromSchedule(scheduleListTest.getScheduleByCourseCodeAndGroupNumber(roomNumber[i].courseCode, roomNumber[i].groupNumber)!!.teacherId).name
            }else if(dayAssigned.equals("W")){
                wedDAY.text = dayAssigned
                wedSubject.text =  roomNumber[i].courseCode
                wedSched.text =
                    "${roomNumber[i].startTime!!.hours}:${roomNumber[i].startTime!!.minutes} - ${roomNumber[i].endTime!!.hours}:${roomNumber[i].endTime!!.minutes}"
                wedTeacher.text = scheduleListTest.getTeacherFromSchedule(scheduleListTest.getScheduleByCourseCodeAndGroupNumber(roomNumber[i].courseCode, roomNumber[i].groupNumber)!!.teacherId).name
            }else if(dayAssigned.equals("TH")){
                thuDAY.text = dayAssigned
                thuSubject.text = roomNumber[i].courseCode
                thuSched.text =
                    "${roomNumber[i].startTime!!.hours}:${roomNumber[i].startTime!!.minutes} - ${roomNumber[i].endTime!!.hours}:${roomNumber[i].endTime!!.minutes}"
                thuTeacher.text = scheduleListTest.getTeacherFromSchedule(scheduleListTest.getScheduleByCourseCodeAndGroupNumber(roomNumber[i].courseCode, roomNumber[i].groupNumber)!!.teacherId).name
            }else if(dayAssigned.equals("F")){
                friDAY.text = dayAssigned
                friSubject.text = roomNumber[i].courseCode
                friSched.text =
                    "${roomNumber[i].startTime!!.hours}:${roomNumber[i].startTime!!.minutes} - ${roomNumber[i].endTime!!.hours}:${roomNumber[i].endTime!!.minutes}"
                friTeacher.text = scheduleListTest.getTeacherFromSchedule(scheduleListTest.getScheduleByCourseCodeAndGroupNumber(roomNumber[i].courseCode, roomNumber[i].groupNumber)!!.teacherId).name
            }

        }



        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

    }

}
