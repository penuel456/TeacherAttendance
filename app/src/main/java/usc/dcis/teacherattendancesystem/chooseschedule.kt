package usc.dcis.teacherattendancesystem

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.android.synthetic.main.activity_choooseschedule.*
import kotlinx.android.synthetic.main.content_chooseschedule.view.*
import kotlinx.android.synthetic.main.menu_fragment_schedlistteacher.view.*
import usc.dcis.teacherattendancesystem.scheduleDatabase.RoomAssignment
import usc.dcis.teacherattendancesystem.scheduleDatabase.ScheduleDatabase
import java.lang.StringBuilder
import java.util.*

class chooseschedule : AppCompatActivity() {

    var roomIdToPass: Int = 0

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choooseschedule)
        setSupportActionBar(toolbar)
        val sdf = java.text.SimpleDateFormat("hh:mm a")
        sdf.timeZone = TimeZone.getTimeZone("GMT+8")

        val db = ScheduleDatabase.getInstance(this)
        val scheduleDao = db.scheduleDAO
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
                            }

                        }



                    }
                }
            }
        //end
        val roomAssignmentCount = scheduleDao.getRoomAssignmentsCount()

        val chooseSched = findViewById<Spinner>(R.id.choosesched)


        val schedList: MutableList<String> = mutableListOf()
        val RoomID: MutableList<Int> = mutableListOf()
        //val sb = StringBuilder()
        //region INSERTING ROOM ASSIGNMENT TO LOCAL DATABASE

        for(i in 0 until roomAssignmentCount) {
            val daysList = scheduleDao.getAllRoomAssignments()
            schedList.add("${sdf.format(daysList[i].startTime)} - ${sdf.format(daysList[i].endTime)} ${daysList[i].dayAssigned} ${daysList[i].roomNumber}")
            RoomID.add(daysList[i].roomID)

        }


       // val schedList = "${roomAssList[0].startTime} - ${roomAssList[0].endTime}"
       /* val schedList = arrayOf("7:00 AM - 9:30 AM (M) LBB446", "12:00 PM - 5:00 PM (Sat) LBB466", "1:00 PM - 4:30 PM (TTH) LBB485")*/
        val schedAdapter  = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            schedList.toTypedArray() // Array
        )
        RoomID.toTypedArray()
        schedAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        chooseSched.adapter = schedAdapter
        chooseSched.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                // Display the selected item text on text view

                // date.text = buildingName
                roomIdToPass = RoomID[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
            }
        }

    }

    fun goToEditSchedActivity(view : View){
        val activity = Intent(this, activity_editschedule::class.java)
        Log.d("Room ID","$roomIdToPass")
        activity.putExtra("RoomID", roomIdToPass)
        startActivity(activity)
    }

}
