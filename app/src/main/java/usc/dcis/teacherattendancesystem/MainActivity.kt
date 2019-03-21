package usc.dcis.teacherattendancesystem

import android.content.Intent
import kotlinx.android.synthetic.main.activity_main.*
import usc.dcis.teacherattendancesystem.scheduleDatabase.ScheduleDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import android.widget.Toast
import android.util.Log
import usc.dcis.teacherattendancesystem.menu.Menu
import usc.dcis.teacherattendancesystem.scheduleDatabase.*
import java.util.*
import com.google.firebase.FirebaseApp
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import usc.dcis.tea.ScheduleFirebase
import usc.dcis.tea.ScheduleFirebaseDebug
import usc.dcis.teacherattendancesystem.DateManager.Companion.getCurrentDay
import com.google.firebase.firestore.FirebaseFirestoreSettings

class MainActivity : AppCompatActivity() {

    var firestore = FirebaseFirestore.getInstance()
    val settings = FirebaseFirestoreSettings.Builder()
        .setTimestampsInSnapshotsEnabled(true)
        .build()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)
        firestore.firestoreSettings = settings

        //val loginBtn = findViewById<Button>(R.id.loginBtn)
        //testDatabase()
        //testUserDatabase()

    }

    fun check(view : View){
        Toast.makeText(this, "Attempting to login", Toast.LENGTH_SHORT).show()

        try{
            firestore.collection("userDB").document(username.text.toString())
                .get()
                .addOnCompleteListener { task ->
                    if(task.isComplete){
                        var userSnapshot = task.result

                        if(userSnapshot.exists() && userSnapshot != null){
                            if(password.text.isNotBlank() && userSnapshot["password"].toString() == password.text.toString()){
                                var userDB = UserDB(userSnapshot["userID"].toString().toInt(),
                                    userSnapshot["idNumber"].toString().toInt(), userSnapshot["password"].toString(),
                                    userSnapshot["type"].toString())

                                Log.d("FIREBASE", "ID Number => ${userSnapshot["idNumber"]}")
                                Log.d("FIREBASE", "ID Number: ${userDB?.idNumber}")

                                Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_SHORT).show()

                                if(userDB.type.equals("student")){
                                    val activity = Intent(this, SchedListStudent::class.java)
                                    startActivity(activity)
                                }else if(userDB.type.equals("teacher")){
                                    val activity = Intent(this, SchedListTeacher::class.java)
                                    startActivity(activity)
                                }else if(userDB.type.equals("dean")){
                                    val activity = Intent(this, Menu::class.java)
                                    startActivity(activity)
                                }
                            }else {
                                Toast.makeText(this, "Incorrect password.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else {
                        Log.e("FIREBASE", "Error: ${task.exception?.message}")
                        Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }catch(e: Exception){
            Toast.makeText(this, "Fill up empty fields.", Toast.LENGTH_SHORT).show()
            Log.w("FIREBASE", "Error: ${e.message}")
        }


        /*
        val db = ScheduleDatabase.getInstance(this)
        var user = db.scheduleDAO

        val userCtr = user.getUserCountOnLogin(username.text.toString().toInt(), password.text.toString())

        if(userCtr != 0){
            var userLists = user.getUserOnLogin(username.text.toString().toInt(), password.text.toString())

            Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_SHORT).show()

            //ScheduleDatabase.destroyInstance()

            if(userLists.type.equals("student")){
                val activity = Intent(this, SchedListStudent::class.java)
                startActivity(activity)
            }else if(userLists.type.equals("teacher")){
                val activity = Intent(this, SchedListTeacher::class.java)
                startActivity(activity)
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
        */
    }

    fun testUserDatabase(){
        val db = ScheduleDatabase.getInstance(this)
        var userList = db.scheduleDAO

        /*
        userList.insertUser(UserDB(0, 3, "3", "student"))
        userList.insertUser(UserDB(0, 2, "2", "teacher"))
        userList.insertUser(UserDB(0, 1, "1", "dean"))
        */

        for(users in userList.getAllUsers()){
            Log.d("USER", "userID: ${users.userID}")
            Log.d("USER", "idNUmber: ${users.idNumber}")
            Log.d("USER", "Password: ${users.password}")
            Log.d("USER", "Type: ${users.type}")
        }

        ScheduleDatabase.destroyInstance()
    }

    // For testing database. Subject to change.
    private fun testDatabase() {

        Log.d("DEBUG: ", "Inside testDatabase function")

        //var db = FirebaseFirestore.getInstance()
        /*val settings = FirebaseFirestoreSettings.Builder()
            .setTimestampsInSnapshotsEnabled(true)
            .build()*/
        //db.firestoreSettings = settings

       /* var user = HashMap<String, Any>()
        user.put("id_number", 4)
        user.put("password", 4)
        user.put("type", "student")
        user.put("userID", 4)*/

        /*
        firestore.collection("userDB").document("1")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var snapshot = task.result

                    if (snapshot.exists() && snapshot != null) {
                        var type = snapshot.getString("type")
                        Log.d("FIREBASE", "Type: ${type}")
                    }
                } else {
                    Log.e("FIREBASE", "Error: ${task.exception?.message}")
                }

            }
        */

        //ScheduleFirebase.GetIDAndPassword(db, 1, "1")
        //var testUser = ScheduleFirebase.GetIDAndPassword(db, 1, "1")

        //ScheduleFirebaseDebug.printUserDB(db)
        val db = ScheduleDatabase.getInstance(this)
        var scheduleListTest = db.scheduleDAO

        /*

        var sdf = java.text.SimpleDateFormat("h:m a")
        var sdfDate = java.text.SimpleDateFormat("yyyy-MM-dd")

        scheduleListTest.insert(ScheduleDB(0, 3, 1, "IT5001", "Ms. Polinar"))
        scheduleListTest.insert(ScheduleDB(0, 3, 1, "IT1101", "Ms. Cantara"))
        scheduleListTest.insert(ScheduleDB(0, 3, 1, "MATH25", "Ms. Punzalan"))
        scheduleListTest.insert(ScheduleDB(0, 2, 1, "NIPPONGO1", "Ms. Watanabe"))
        scheduleListTest.insert(ScheduleDB(0, 3, 1, "NIPPONGO1", "Ms. Watanabe"))
       */

       /* ScheduleDebug.printAllSchedules(scheduleListTest)
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, 1, "LB485TC", sdf.parse("10:30 AM"),
                sdf.parse("12:00 PM"), "M"
            )
        )
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, 1, "LB448TC", sdf.parse("10:30 AM"),
                sdf.parse("12:00 PM"), "W"
            )
        )
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, 1, "LB485TC", sdf.parse("10:30 AM"),
                sdf.parse("12:00 PM"), "F"
            )
        )
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, 2, "LB484TC", sdf.parse("1:30 PM"),
                sdf.parse("5:00 PM"), "M"
            )
        )
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, 2, "LB483TC", sdf.parse("1:30 PM"),
                sdf.parse("5:00 PM"), "W"
            )
        )
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, 2, "LB484TC", sdf.parse("1:30 PM"),
                sdf.parse("5:00 PM"), "F"
            )
        )
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, 3, "LB306TC", sdf.parse("5:30 PM"),
                sdf.parse("6:30 PM"), "M"
            )
        )
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, 3, "LB306TC", sdf.parse("5:30 PM"),
                sdf.parse("6:30 PM"), "W"
            )
        )
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, 3, "LB306TC", sdf.parse("5:30 PM"),
                sdf.parse("6:30 PM"), "F"
            )
        )
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, 4, "LB404TC", sdf.parse("1:30 PM"),
                sdf.parse("5:00 PM"), "T"
            )
        )
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, 4, "LB404TC", sdf.parse("1:30 PM"),
                sdf.parse("5:00 PM"), "TH"
            )
        )
        */
        ScheduleDebug.printAllRoomAssignments(scheduleListTest)


        //ScheduleDebug.printAllUserSchedules(scheduleListTest, 2)



        // For dynamically creating today's room assignment statuses. KEEP THIS UNCOMMENTED SO SCHEDLIST WON'T CRASH
        var today = scheduleListTest.getAllRoomAssignmentsByDay(DateManager.getCurrentDay())

        for (sched in today) {
            val statusCheck = scheduleListTest.getStatusCountByRoomIdAndDate(DateManager.getCurrentDate(), sched.roomID)

            if (statusCheck == 0) {
                scheduleListTest.insertStatus(Status(0, sched.roomID, DateManager.getCurrentDate(), "Absent"))
            }
        }


        ScheduleDebug.printAllStatus(scheduleListTest)
        ScheduleDebug.printAllRoomAssignmentsByDay(scheduleListTest, getCurrentDay())

    }


}
