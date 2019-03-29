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
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.*
import com.google.firebase.firestore.FirebaseFirestoreSettings

class MainActivity : AppCompatActivity() {


    private var firestore = FirebaseFirestore.getInstance()
    private val settings = FirebaseFirestoreSettings.Builder()
        .setTimestampsInSnapshotsEnabled(true)
        .build()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        FirebaseApp.initializeApp(this)
        firestore.firestoreSettings = settings


        //val loginBtn = findViewById<Button>(R.id.loginBtn)
        //testDatabase()
        deleteOldDatabase()
        testUserDatabase()


    }



    fun check(view : View){
        Toast.makeText(this, "Attempting to login", Toast.LENGTH_SHORT).show()


        try{
            firestore.collection("userDB").whereEqualTo("idNumber", username.text.toString().toInt())
                .whereEqualTo("password", password.text.toString())
                .limit(1)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isComplete) {
                        var userSnapshot = task.result

                        if (!userSnapshot?.isEmpty!!) {
                            for (user in userSnapshot) {
                                var userDB = UserDB(
                                    user["userID"].toString().toInt(),
                                    user["idNumber"].toString().toInt(),
                                    user["name"].toString(),
                                    user["password"].toString(),
                                    user["type"].toString()
                                )

                                Log.d("FIREBASE", "ID Number => ${user["idNumber"]} Type => ${user["type"]}")
                                Log.d("FIREBASE", "ID Number: ${userDB?.idNumber} Type: ${userDB?.type}")

                                Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_SHORT).show()

                                when {
                                    userDB.type.equals("student") -> {
                                        val activity = Intent(this, SchedListStudent::class.java)
                                        startActivity(activity)
                                    }
                                    userDB.type.equals("teacher") -> {
                                        val activity = Intent(this, SchedListTeacher::class.java)
                                        startActivity(activity)
                                    }
                                    userDB.type.equals("dean") -> {
                                        val activity = Intent(this, Menu::class.java)
                                        startActivity(activity)
                                    }
                                }
                            }

                        }else {
                            Toast.makeText(this, "Username does not exist", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.e("FIREBASE", "Error: ${task.exception?.message}")
                        Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }catch(e: Exception){
            Toast.makeText(this, "Fill up empty fields.", Toast.LENGTH_SHORT).show()
            Log.w("FIREBASE", "Error: ${e.message}")
        }


        //region OLD LOGIN
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
        //endregion
    }

    fun testUserDatabase(){
        val TAG = "USER"
        val db = ScheduleDatabase.getInstance(this)
        val userList = db.scheduleDAO


        //userList.insertUser'(UserDB(0, 3, "Nico Nico", "student", "student"))

        /*firestore.collection("userDB").document("2")
            .get()
            .addOnCompleteListener { task ->
                val user = task.result

                userList.insertUser(UserDB(userID = user?.get("userID").toString().toInt(), idNumber = user?.get("idNumber").toString().toInt(),
                    name = user?.get("name") as String, password = null, type = user["type"] as String)
                )

                Log.d("FIREBASE", "User ${user["name"]} added.")

            }*/

        //if u want to insert all into the local db
       /* firestore.collection("userDB")
            .get()
            .addOnCompleteListener { task ->
                val users = task.result

                for(user in users!!){
                    userList.insertUser(user.toObject(UserDB::class.java))
                }


            }*/

        //userList.insertUser(UserDB(0, 2, "Ms. Cantara", "teacher", "teacher"))
        //userList.insertUser(UserDB(0, 1, "Dean", "dean", "dean"))


        //ScheduleFirebase.AddMultipleUsers(firestore, userList.getAllUsers())

        for(users in userList.getAllUsers()){
            Log.d(TAG, "${users.userID} => $users")
        }

        //ScheduleDatabase.destroyInstance()
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



        var sdf = java.text.SimpleDateFormat("h:m a")
        var sdfDate = java.text.SimpleDateFormat("yyyy-MM-dd")

        //region ALL INSERTION
        /*
        scheduleListTest.insert(ScheduleDB(0, 3, 2, 1, "IT5001"))
        scheduleListTest.insert(ScheduleDB(0, 3, 2, 1, "IT1101"))
        scheduleListTest.insert(ScheduleDB(0, 3, null, 1, "MATH25"))
        scheduleListTest.insert(ScheduleDB(0, 2, null, 1, "NIPPONGO1"))
        scheduleListTest.insert(ScheduleDB(0, 3, null, 1, "NIPPONGO1"))
        */
        /*
        ScheduleDebug.printAllSchedules(scheduleListTest)
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, "IT5001", 1, "LB485TC", sdf.parse("10:30 AM"),
                sdf.parse("12:00 PM"), "M"
            )
        )
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, "IT5001", 1, "LB448TC", sdf.parse("10:30 AM"),
                sdf.parse("12:00 PM"), "W"
            )
        )
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, "IT5001", 1, "LB485TC", sdf.parse("10:30 AM"),
                sdf.parse("12:00 PM"), "F"
            )
        )
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, "IT1101", 1, "LB484TC", sdf.parse("1:30 PM"),
                sdf.parse("5:00 PM"), "M"
            )
        )
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, "IT1101", 1, "LB483TC", sdf.parse("1:30 PM"),
                sdf.parse("5:00 PM"), "W"
            )
        )
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, "IT1101", 1, "LB484TC", sdf.parse("1:30 PM"),
                sdf.parse("5:00 PM"), "F"
            )
        )
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, "MATH25", 1, "LB306TC", sdf.parse("5:30 PM"),
                sdf.parse("6:30 PM"), "M"
            )
        )
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, "MATH25", 1, "LB306TC", sdf.parse("5:30 PM"),
                sdf.parse("6:30 PM"), "W"
            )
        )
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, "MATH25", 1, "LB306TC", sdf.parse("5:30 PM"),
                sdf.parse("6:30 PM"), "F"
            )
        )
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, "NIPPONGO1", 1, "LB404TC", sdf.parse("1:30 PM"),
                sdf.parse("5:00 PM"), "T"
            )
        )
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, "NIPPONGO1", 1, "LB404TC", sdf.parse("1:30 PM"),
                sdf.parse("5:00 PM"), "TH"
            )
        )
        */
        //endregion

        //ScheduleDebug.printAllSchedules(scheduleListTest)

        //ScheduleDebug.printAllUserSchedules(scheduleListTest, 3)


        /*
        // For dynamically creating today's room assignment statuses. KEEP THIS UNCOMMENTED SO SCHEDLIST WON'T CRASH
        var today = scheduleListTest.getAllRoomAssignmentsByDay(DateManager.getCurrentDay())

        for (sched in today) {
            val statusCheck = scheduleListTest.getStatusCountByRoomIdAndDate(DateManager.getCurrentDate(), sched.roomID)

            if (statusCheck == 0) {
                scheduleListTest.insertStatus(Status(0, sched.roomID, DateManager.getCurrentDate(), "Absent"))
                ScheduleFirebase.AddStatus(FirebaseFirestore.getInstance(),
                    scheduleListTest.getStatusByRoomIdAndDate(DateManager.getCurrentDate(), sched.roomID))
            }
        }
        */

        //ScheduleDebug.printAllStatus(scheduleListTest)
        //ScheduleDebug.printAllRoomAssignmentsByDay(scheduleListTest, getCurrentDay())

        //ScheduleDatabase.destroyInstance()

    }

    private fun deleteOldDatabase() {
        val dao = ScheduleDatabase.getInstance(this).scheduleDAO

        dao.deleteAllSchedules()
        dao.deleteAllRoomAssignments()
        dao.deleteAllStatus()
        dao.deleteAllUsers()
    }

}
