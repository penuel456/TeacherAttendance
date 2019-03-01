package usc.dcis.teacherattendancesystem

import android.arch.persistence.room.PrimaryKey
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


        var userLists = userList(1, "1", "dean")

        if (!username.text.toString().isBlank() && username.text.toString().toInt().equals(userLists.idnumber)
            && password.text.toString().equals(userLists.password)) {
            "Logged in Successfully"
            val text = Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_SHORT).show()


            if(userLists.type.equals("student")){
                val activity = Intent(this, SchedListStudent::class.java)
                startActivity(activity)
            }else if(userLists.type.equals("teacher")){
                // Activity for teachers
            }else if(userLists.type.equals("dean")){
                val activity = Intent(this, Menu::class.java)
                startActivity(activity)
            }
        }
        else if (username.text.toString().isBlank() || password.text.toString().isBlank()){
            val text = Toast.makeText(this, "Please enter an input in the empty fields.", Toast.LENGTH_SHORT).show()
        }
        else {
            val text = Toast.makeText(this, "Incorrect username/password", Toast.LENGTH_SHORT).show()
        }
    }

    data class userList (
        val idnumber: Int,
        val password: String,
        val type: String
    )

    // For testing database. Subject to change.
     fun testDatabase(){

        Log.d("DEBUG: ", "Inside testDatabase function")

        val db = Room.databaseBuilder(this, AppDatabase::class.java, "db-scheduleList").allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        var scheduleListTest = db.scheduleDAO
        var sdf = java.text.SimpleDateFormat("h:m a")

        //var scheduleDb = scheduleDB(0, "MATH25", "Ms. Punzalan")
        //scheduleListTest.insert(scheduleDb)

        for(schedules in scheduleListTest.getAllSchedules()){
            Log.d("SCHED:", "CourseID: ${schedules.courseID}")
            Log.d("SCHED:", "CourseCode: ${schedules.courseCode}")
            Log.d("SCHED:", "Teacher: ${schedules.teacher}")
        }

        //scheduleListTest.deleteAllRoomAssignments()
        val rooms = arrayListOf("LB306TC", "LB305TC")
        val startTimes = arrayListOf("3:30 PM", "2:30 PM")
        val endTimes = arrayListOf("5:30 PM", "5:30 PM")
        val days = arrayListOf("T", "TH")
        Log.d("DEBUG: ", "Declared schedule variables")

        /*
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 1, rooms[0], sdf.parse(startTimes[0]),
            sdf.parse(endTimes[0]), days[0]))
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 1, rooms[1], sdf.parse(startTimes[1]),
            sdf.parse(endTimes[1]), days[1]))
        Log.d("DEBUG: ", "Inserted room assignment")
        */

        for(roomAssignment in scheduleListTest.getAllRoomAssignments()){
            Log.d("ROOMASSN:", "CourseID: ${roomAssignment.courseID}")
            Log.d("ROOMASSN:", "Room ID: ${roomAssignment.roomID}")
            Log.d("ROOMASSN:", "Room Number: ${roomAssignment.roomNumber}")
            Log.d("ROOMASSN:", "Start Time: ${sdf.format(roomAssignment.startTime)}")
            Log.d("ROOMASSN:", "End Time: ${sdf.format(roomAssignment.endTime)}")
            Log.d("ROOMASSN:", "Day: ${roomAssignment.dayAssigned}")
        }

        var dateString = "10:30"

        sdf = java.text.SimpleDateFormat("yyyy-MM-dd")
        dateString = "2019-02-28"
        var mDate = sdf.parse(dateString)

        /*
        scheduleListTest.insertStatus(Status(0, 5, sdf.parse("2019-02-28"), "Present"))
        scheduleListTest.insertStatus(Status(0, 6, sdf.parse("2019-03-05"), "Present"))
        Log.d("DEBUG: ", "Insert status")
        */

        //Log.d("DEBUG: ", sdf.format(mDate))


        //val statusPrintTest = scheduleListTest.getAllStatusByRoomId(1)

        //Log.d("DEBUG: ", "StatusID: ${statusPrintTest[0].statusId}")
        //Log.d("DEBUG: ", "Date: ${statusPrintTest[0].date}")
        //Log.d("DEBUG: ", "Status: ${statusPrintTest[0].status}")
    }
}
