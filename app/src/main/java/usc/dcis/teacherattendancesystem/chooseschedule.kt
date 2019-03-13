package usc.dcis.teacherattendancesystem

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

import kotlinx.android.synthetic.main.activity_choooseschedule.*
import kotlinx.android.synthetic.main.content_chooseschedule.view.*
import kotlinx.android.synthetic.main.menu_fragment_schedlistteacher.view.*
import usc.dcis.teacherattendancesystem.scheduleDatabase.ScheduleDatabase
import java.lang.StringBuilder

class chooseschedule : AppCompatActivity() {

    var roomIdToPass: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choooseschedule)
        setSupportActionBar(toolbar)
        val db = ScheduleDatabase.getInstance(this)
        var scheduleDao = db.scheduleDAO
        var roomAssignmentCount = scheduleDao.getRoomAssignmentsCount()

        val chooseSched = findViewById<Spinner>(R.id.choosesched)

        val schedule = scheduleDao.getAllSchedules()
        val allRoomAssign = scheduleDao.getAllRoomAssignments()
        val schedList: MutableList<String> = mutableListOf()
        val RoomID: MutableList<Int> = mutableListOf()
        val sb = StringBuilder()
        var sdf = java.text.SimpleDateFormat("hh:mm a")

        for(i in 0 until roomAssignmentCount) {
            //roomAssList[i] = allRoomAss[i]
            val daysList = scheduleDao.getAllRoomAssignmentsByCourseId(allRoomAssign[i].courseID)
           // var daysCount = scheduleDao.getRoomAssignmentCountBycourseId(allRoomAssign[i].courseID)
            /*for(x in 0 until daysCount){
                if(x < daysCount) {
                    sb.append(daysList[x].dayAssigned)
                }
            }*/


            //var combinedDays = sb.toString()
            //Log.d("COMBINEDDAYS", combinedDays)
            //schedList = "${sdf.format(daysList[i].startTime)} - ${sdf.format(daysList[i].endTime)} $combinedDays ${daysList[i].roomNumber}"
            schedList.add("${sdf.format(daysList[i].startTime)} - ${sdf.format(daysList[i].endTime)} ${daysList[i].dayAssigned} ${daysList[i].roomNumber}")
            RoomID.add(daysList[i].roomID)
            sb.clear()

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
                val Schedule ="${parent.getItemAtPosition(position).toString()}"
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
