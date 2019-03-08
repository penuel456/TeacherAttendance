package usc.dcis.teacherattendancesystem

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Log
import usc.dcis.teacherattendancesystem.menu.Menu
import usc.dcis.teacherattendancesystem.scheduleDatabase.*
import usc.dcis.teacherattendancesystem.userDatabase.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val loginBtn = findViewById<Button>(R.id.loginBtn)
        //testDatabase()
        //testUserDatabase()

    }

    fun check(view : View){
        //val username = findViewById<EditText>(R.id.username)
        //val password = findViewById<EditText>(R.id.password)

        val db = UserDatabase.getInstance(this)
        var user = db.userDAO

        val userCtr = user.getUserCountOnLogin(username.text.toString().toInt(), password.text.toString())

        if(userCtr != 0){
            var userLists = user.getUserOnLogin(username.text.toString().toInt(), password.text.toString())

            Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_SHORT).show()

            UserDatabase.destroyInstance()

            if(userLists.type.equals("student")){
                val activity = Intent(this, SchedListStudent::class.java)
                startActivity(activity)
            }else if(userLists.type.equals("teacher")){
                // Activity for teachers
            }else if(userLists.type.equals("dean")){
                val activity = Intent(this, Menu::class.java)
                startActivity(activity)
            }
        }else {
            if(username.text.toString().isBlank() || password.text.toString().isBlank()){
                Toast.makeText(this, "Please enter an input in the empty fields.", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this, "Incorrect username/password", Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun testUserDatabase(){
        val db = UserDatabase.getInstance(this)
        var userList = db.userDAO

        /*
        userList.insertUser(userDB(0, 3, "3", "student"))
        userList.insertUser(userDB(0, 2, "2", "teacher"))
        userList.insertUser(userDB(0, 1, "1", "dean"))
        */

        for(users in userList.getAllUsers()){
            Log.d("USER", "userID: ${users.userID}")
            Log.d("USER", "idNUmber: ${users.idNumber}")
            Log.d("USER", "Password: ${users.password}")
            Log.d("USER", "Type: ${users.type}")
        }

        UserDatabase.destroyInstance()
    }

    // For testing database. Subject to change.
     fun testDatabase(){

        Log.d("DEBUG: ", "Inside testDatabase function")

        val db = ScheduleDatabase.getInstance(this)
        var scheduleListTest = db.scheduleDAO
        var sdf = java.text.SimpleDateFormat("h:m a")

       /* var scheduleDb = scheduleDB(0, "NIPPONGO1", "Ms. Climaco Watanabe")
        scheduleListTest.insert(scheduleDb)*/

        //scheduleListTest.deleteAllSchedules()
        //scheduleListTest.deleteAllRoomAssignments()
        //scheduleListTest.deleteAllStatus()
        for(schedules in scheduleListTest.getAllSchedules()){
            Log.d("SCHED:", "CourseID: ${schedules.courseID}")
            Log.d("SCHED:", "CourseCode: ${schedules.courseCode}")
            Log.d("SCHED:", "Teacher: ${schedules.teacher}")
        }



        val rooms = arrayListOf("LB304TC", "LB304TC")
        val startTimes = arrayListOf("10:30 AM", "10:30 AM")
        val endTimes = arrayListOf("12:00 PM", "12:00 PM")
        val days = arrayListOf("T", "TH")
        Log.d("DEBUG: ", "Declared schedule variables")


       /* scheduleListTest.insertRoomAssignment(RoomAssignment(0, 8, rooms[0], sdf.parse(startTimes[0]),
            sdf.parse(endTimes[0]), days[0]))*/
        /*scheduleListTest.insertRoomAssignment(RoomAssignment(0, 5, rooms[1], sdf.parse(startTimes[1]),
            sdf.parse(endTimes[1]), days[1]))*/
        Log.d("DEBUG: ", "Inserted room assignment")


        for(roomAssignment in scheduleListTest.getAllRoomAssignments()){
            Log.d("ROOMASSN:", "CourseID: ${roomAssignment.courseID}")
            Log.d("ROOMASSN:", "Room ID: ${roomAssignment.roomID}")
            Log.d("ROOMASSN:", "Room Number: ${roomAssignment.roomNumber}")
            Log.d("ROOMASSN:", "Start Time: ${sdf.format(roomAssignment.startTime)}")
            Log.d("ROOMASSN:", "End Time: ${sdf.format(roomAssignment.endTime)}")
            Log.d("ROOMASSN:", "Day: ${roomAssignment.dayAssigned}")
        }

        var dateString = "2019-02-28"

        sdf = java.text.SimpleDateFormat("yyyy-MM-dd")
        var mDate = sdf.parse(dateString)


    scheduleListTest.insertStatus(Status(0, 5, sdf.parse("2019-03-06"), "Present"))
        //scheduleListTest.insertStatus(Status(0, 7, sdf.parse("2019-03-08"), "Present"))
        //Log.d("DEBUG: ", "Insert status")


        //Log.d("DEBUG: ", sdf.format(mDate))

        val statusPrintTest = scheduleListTest.getAllStatusByRoomId(5)

        Log.d("STATUS", "RoomID: ${scheduleListTest.getRoomAssignmentByRoomId(statusPrintTest[0].roomID).roomID}")
        Log.d("STATUS: ", "StatusID: ${statusPrintTest[0].statusId}")
        Log.d("STATUS: ", "Date: ${sdf.format(statusPrintTest[0].date)}")
        Log.d("STATUS: ", "Status: ${statusPrintTest[0].status}")

    }
}
