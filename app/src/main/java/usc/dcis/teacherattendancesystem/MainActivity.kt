package usc.dcis.teacherattendancesystem

import android.content.Context
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
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val loginBtn = findViewById<Button>(R.id.loginBtn)
        testDatabase()
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


        userList.insertUser(userDB(0, 3, "3", "student"))
        userList.insertUser(userDB(0, 2, "2", "teacher"))
        userList.insertUser(userDB(0, 1, "1", "dean"))


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
        var sdfDate = java.text.SimpleDateFormat("yyyy-MM-dd")


        /*scheduleListTest.insert(scheduleDB(0, "IT5001", "Ms. Polinar"))
        scheduleListTest.insert(scheduleDB(0, "IT1101", "Ms. Cantara"))
        scheduleListTest.insert(scheduleDB(0, "MATH25", "Ms. Punzalan"))
        scheduleListTest.insert(scheduleDB(0, "NIPPONGO1", "Ms. Watanabe"))
        */

        printAllSchedules(scheduleListTest)

        /*
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 1, "LB485TC", sdf.parse("10:30 AM"),
            sdf.parse("12:00 PM"), "M"))
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 1, "LB448TC", sdf.parse("10:30 AM"),
            sdf.parse("12:00 PM"), "W"))
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 1, "LB485TC", sdf.parse("10:30 AM"),
            sdf.parse("12:00 PM"), "F"))
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 2, "LB484TC", sdf.parse("1:30 PM"),
            sdf.parse("5:00 PM"), "M"))
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 2, "LB483TC", sdf.parse("1:30 PM"),
            sdf.parse("5:00 PM"), "W"))
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 2, "LB484TC", sdf.parse("1:30 PM"),
            sdf.parse("5:00 PM"), "F"))
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 3, "LB306TC", sdf.parse("5:30 PM"),
            sdf.parse("6:30 PM"), "M"))
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 3, "LB306TC", sdf.parse("5:30 PM"),
            sdf.parse("6:30 PM"), "W"))
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 3, "LB306TC", sdf.parse("5:30 PM"),
            sdf.parse("6:30 PM"), "F"))
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 4, "LB404TC", sdf.parse("1:30 PM"),
            sdf.parse("5:00 PM"), "T"))
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 4, "LB404TC", sdf.parse("1:30 PM"),
            sdf.parse("5:00 PM"), "TH"))
        */


        //printAllRoomAssignments(scheduleListTest)

        // For dynamically creating today's room assignment statuses. KEEP THIS UNCOMMENTED SO SCHEDLIST WON'T CRASH
        var today = scheduleListTest.getAllRoomAssignmentsByDay(getCurrentDay())

        for(sched in today) {
            val statusCheck = scheduleListTest.getStatusCountByRoomIdAndDate(getCurrentDate(), sched.roomID)

            if(statusCheck == 0){
                scheduleListTest.insertStatus(Status(0, sched.roomID, getCurrentDate(), "Absent"))
            }
        }


        printAllStatus(scheduleListTest)
        printAllRoomAssignmentsByDay(scheduleListTest, getCurrentDay())
    }

    companion object DebugSchedule {
        // PRINTING EVERYTHING
        fun printAllStatus(dao: ScheduleDAO){
            for(status in dao.getAllStatus()){
                Log.d("DEBUG STATUS: ", status.toString())
            }
        }

        fun printAllRoomAssignments(dao: ScheduleDAO){
            for(rooms in dao.getAllRoomAssignments()){
                Log.d("DEBUG ROOM: ", rooms.toString())
            }
        }

        fun printAllSchedules(dao: ScheduleDAO){
            for(sched in dao.getAllSchedules()){
                Log.d("DEBUG SCHED: ", sched.toString())
            }
        }

        // PRINTING ROOM ASSIGNMENT BY DAY
        fun printAllRoomAssignmentsByDay(dao: ScheduleDAO, currentDay: String){
            for(rooms in dao.getAllRoomAssignmentsByDay(currentDay)){
                Log.d("DEBUG ROOMDAY: ", rooms.toString())
            }
        }
    }

    fun getCurrentDate(): Date {
        val indiaTime = GregorianCalendar(TimeZone.getTimeZone("Asia/Singapore"))
        val year = indiaTime.get(Calendar.YEAR)
        val month = indiaTime.get(Calendar.MONTH) + 1
        val day = indiaTime.get(Calendar.DAY_OF_MONTH)

        return java.text.SimpleDateFormat("yyyy-MM-dd").parse("$year-$month-$day")
    }

    fun getCurrentDay(): String {
        val indiaTime = GregorianCalendar(TimeZone.getTimeZone("Asia/Singapore"))
        val day = indiaTime.get(Calendar.DAY_OF_WEEK)

        Log.d("TODAY: ", getDayString(day))

        return getDayString(day)
    }

    fun getDayString(today: Int): String{
        val day = arrayListOf("SUN", "M", "T", "W", "TH", "F", "SAT")

        for((ndx, day) in day.withIndex()){
            if(ndx == (today - 1)) return day
        }

        return "NONE"
    }
}
