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
        Log.d("DEBUG: ", "Inside testDatabase function")

        val db = Room.databaseBuilder(this, AppDatabase::class.java, "db-scheduleList").allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        var scheduleListTest = db.scheduleDAO

        //var scheduleDb = scheduleDB(0, "ENGL3", "Ms. Charity Tecson")

        /*
        val rooms = arrayListOf("LB446TC", "LB468TC")
        val startTimes = arrayListOf("9:30", "10:30")
        val endTimes = arrayListOf("12:00", "12:00")
        val days = arrayListOf("M", "W", "F")
        Log.d("DEBUG: ", "Declared schedule variables")
        */
        /*
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
