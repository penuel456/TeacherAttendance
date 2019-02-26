package usc.dcis.teacherattendancesystem

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.arch.persistence.room.Room
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.util.Log
import kotlinx.android.synthetic.main.menu_fragment_schedlistteacher.*
import java.sql.Date


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val loginBtn = findViewById<Button>(R.id.loginBtn)
        //testDatabase()

    }

    fun check(view : View){
        //val username = findViewById<EditText>(R.id.username)
        //val password = findViewById<EditText>(R.id.password)

        // check username and password first


        val userLists = MutableList(5) { userList() }

        val status = if (!username.text.toString().isBlank() && username.text.toString().toInt().equals(userLists[0].idnumber)
            && password.text.toString().equals(userLists[0].password)) "Logged in Successfully"
        else if (username.text.toString().isBlank() || password.text.toString().isBlank()) "Please enter an input in the empty fields."
        else "Incorrect username/password."

        val text = Toast.makeText(this, status, Toast.LENGTH_SHORT)
        text.show()

        //testDatabase()

        // Create an Intent to start the second activity
        if (!username.text.toString().isBlank() && username.text.toString().toInt().equals(userLists[0].idnumber) && password.text.toString().equals(userLists[0].password)) {
            val menuActivity = Intent(this, Menu::class.java)

            // Start the new activity.
            startActivity(menuActivity)
        }
    }

    private fun userList() = object {
        val idnumber: Int = 15102593
        val password: String = "test123"
    }

    // For testing database. Subject to change.
     fun testDatabase(){
        /*
        Log.d("DEBUG: ", "Inside testDatabase function")

        val db = Room.databaseBuilder(this, AppDatabase::class.java, "db-scheduleList").allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        var scheduleListTest = db.scheduleDAO
        var sdf = java.text.SimpleDateFormat("h:m a")

        for(schedules in scheduleListTest.getAllSchedules()){
            Log.d("DB:", "CourseID: ${schedules.courseID}")
            Log.d("DB:", "CourseCode: ${schedules.courseCode}")
            Log.d("DB:", "Teacher: ${schedules.teacher}")
        }

        //var scheduleDb = scheduleDB(0, "ENGL3", "Ms. Charity Tecson")
        //scheduleListTest.insert(scheduleDb)

        //scheduleListTest.deleteAllRoomAssignments()
        val rooms = arrayListOf("LB483TC", "LB485TC")
        val startTimes = arrayListOf("6:30 PM", "5:30 PM")
        val endTimes = arrayListOf("8:30 PM", "8:30 PM")
        val days = arrayListOf("M", "W")
        Log.d("DEBUG: ", "Declared schedule variables")

        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 1, rooms[0], sdf.parse(startTimes[0]),
            sdf.parse(endTimes[0]), days[0]))
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 1, rooms[1], sdf.parse(startTimes[1]),
            sdf.parse(endTimes[1]), days[1]))

        for(roomAssignment in scheduleListTest.getAllRoomAssignments()){
            Log.d("DB:", "CourseID: ${roomAssignment.courseID}")
            Log.d("DB:", "Room ID: ${roomAssignment.roomID}")
            Log.d("DB:", "Room Number: ${roomAssignment.roomNumber}")
            Log.d("DB:", "Start Time: ${sdf.format(roomAssignment.startTime)}")
            Log.d("DB:", "End Time: ${sdf.format(roomAssignment.endTime)}")
            Log.d("DB:", "Day: ${roomAssignment.dayAssigned}")
        }

        var sdf = java.text.SimpleDateFormat("m:s")

        var dateString = "10:30"
        var mDate  = sdf.parse(dateString)

        scheduleListTest.insertRoomAssignment(RoomAssignment(1, 1, "LBB305TC", mDate, mDate, "M"))
        Log.d("DEBUG: ", "Inserted room assignment")

        sdf = java.text.SimpleDateFormat("yyyy-MM-dd")
        dateString = "2019-03-28"
        mDate = sdf.parse(dateString)

        scheduleListTest.insertStatus(Status(0, 1, mDate, "Present"))
        Log.d("DEBUG: ", "Insert status")


        Log.d("DEBUG: ", sdf.format(mDate))
        */

        //val statusPrintTest = scheduleListTest.getAllStatusByRoomId(1)

        //Log.d("DEBUG: ", "StatusID: ${statusPrintTest[0].statusId}")
        //Log.d("DEBUG: ", "Date: ${statusPrintTest[0].date}")
        //Log.d("DEBUG: ", "Status: ${statusPrintTest[0].status}")
    }
}
