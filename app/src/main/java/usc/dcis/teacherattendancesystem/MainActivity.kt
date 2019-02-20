package usc.dcis.teacherattendancesystem

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.arch.persistence.room.Room
import android.util.Log



class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val loginBtn = findViewById<Button>(R.id.loginBtn)
        testDatabase()

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
        /*
        val rooms = arrayListOf("LB446TC", "LB468TC")
        val startTimes = arrayListOf("9:30", "10:30")
        val endTimes = arrayListOf("12:00", "12:00")
        val days = arrayListOf("M", "W", "F")
        Log.d("DEBUG: ", "Declared schedule variables")



        scheduleListTest.insert(scheduleDB(1, "IT 2201", "Mr. Dummy"))
        */
        Log.d("DEBUG::", "CourseCode: " + scheduleListTest.getSchedule(1).courseCode)
        Log.d("DEBUG::", "Teacher: " + scheduleListTest.getSchedule(1).teacher)
        Log.d("DEBUG::", "CourseID: " + scheduleListTest.getSchedule(1).courseID)
    }
}
