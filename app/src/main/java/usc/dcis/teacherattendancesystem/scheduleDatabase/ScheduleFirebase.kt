package usc.dcis.tea

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import usc.dcis.teacherattendancesystem.scheduleDatabase.*

import android.arch.persistence.room.Room
import com.google.firebase.firestore.Source
import java.util.*

class ScheduleFirebase {

    companion object {
        const val TAG = "FIREBASE"
        //region ADD DATA
        fun AddSchedule(db: FirebaseFirestore, sched: ScheduleDB) {
            db.collection("scheduleDB").document(sched.courseID.toString())
                .set(sched)
                .addOnSuccessListener { Log.d(TAG, "ScheduleDB document successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing ScheduleDB document", e) }
        }

        fun AddRoomAssignment(db: FirebaseFirestore, room: RoomAssignment) {
            db.collection("roomAssignment").document(room.roomID.toString())
                .set(room)
                .addOnSuccessListener { Log.d(TAG, "RoomAssignment document successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing RoomAssignment document", e) }
        }

        fun AddStatus(db: FirebaseFirestore, status: Status) {
            db.collection("status").document(status.statusId.toString())
                .set(status)
                .addOnSuccessListener { Log.d(TAG, "Status document successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing Status document", e) }
        }

        fun AddUser(db: FirebaseFirestore, user: UserDB) {
            db.collection("userDB").document(user.userID.toString())
                .set(user)
                .addOnSuccessListener { Log.d(TAG, "UserDB document successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing UserDB document", e) }
        }
        //endregion

        //region UPDATE DATA
        fun UpdateSchedule(db: FirebaseFirestore, sched: ScheduleDB){
            db.collection("cities").document(sched.courseID.toString())
                .set(sched)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated/written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        }

        fun UpdateRoomAssignment(db: FirebaseFirestore, room: RoomAssignment){
            db.collection("cities").document(room.roomID.toString())
                .set(room)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated/written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        }

        fun UpdateStatus(db: FirebaseFirestore, status: Status){
            db.collection("cities").document(status.statusId.toString())
                .set(status)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated/written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        }

        fun UpdateUser(db: FirebaseFirestore, user: UserDB){
            db.collection("cities").document(user.userID.toString())
                .set(user)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated/written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        }
        //endregion

        //region GET ALL DATA
        fun GetAllSchedule(db: FirebaseFirestore): List<ScheduleDB>? {
            var scheduleData: List<ScheduleDB>? = null
            var ctr = 0

            db.collection("scheduleDB")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        scheduleData!![ctr].courseCode = document.data.getValue("courseCode").toString()
                        scheduleData!![ctr].courseID = document.data.getValue("courseID") as Int
                        scheduleData!![ctr].teacher = document.data.getValue("teacher").toString()
                        scheduleData!![ctr].userID = document.data.getValue("userID") as Int
                        scheduleData!![ctr].groupNumber = document.data.getValue("groupNumber") as Int
                        ctr++
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting schedule documents: ", exception)
                }

            if(scheduleData!!.count() > 0){
                return scheduleData
            }else {
                return null
            }
        }

        fun GetAllRoomAssignment(db: FirebaseFirestore): List<RoomAssignment>? {
            var roomAssignmentData: List<RoomAssignment>? = null
            var ctr = 0

            db.collection("roomAssignment")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        roomAssignmentData!![ctr].courseID = document.data.getValue("courseID") as Int
                        roomAssignmentData!![ctr].roomID = document.data.getValue("courseID") as Int
                        roomAssignmentData!![ctr].roomNumber = document.data.getValue("roomNumber").toString()
                        roomAssignmentData!![ctr].startTime = document.data.getValue("startTime") as Date
                        roomAssignmentData!![ctr].endTime = document.data.getValue("endTime") as Date
                        roomAssignmentData!![ctr].dayAssigned = document.data.getValue("dayAssigned").toString()
                        ctr++
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting schedule documents: ", exception)
                }

            if(roomAssignmentData!!.count() > 0){
                return roomAssignmentData
            }else {
                return null
            }
        }

        fun GetAllStatus(db: FirebaseFirestore): List<Status>? {
            var statusData: List<Status>? = null
            var ctr = 0

            db.collection("status")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        statusData!![ctr].statusId = document.data.getValue("statusID") as Int
                        statusData!![ctr].roomID = document.data.getValue("roomID") as Int
                        statusData!![ctr].date = document.data.getValue("date") as Date
                        statusData!![ctr].status = document.data.getValue("status").toString()
                        statusData!![ctr].remarks = document.data.getValue("remarks").toString()
                        ctr++
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting schedule documents: ", exception)
                }

            if(statusData!!.count() > 0){
                return statusData
            }else {
                return null
            }
        }

        fun GetAllUser(db: FirebaseFirestore): List<UserDB>? {
            var userData: List<UserDB>? = null
            var ctr = 0

            db.collection("userDB")
                .get()
                .addOnCompleteListener { task ->

                    for (document in task.result) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        userData!![ctr].userID = document.data.getValue("userID") as Int
                        userData!![ctr].idNumber = document.data.getValue("id_number") as Int
                        userData!![ctr].password = document.data.getValue("password").toString()
                        userData!![ctr].password = document.data.getValue("type").toString()
                        ctr++
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting schedule documents: ", exception)
                }

            if(userData!!.count() > 0){
                return userData
            }else {
                return null
            }

        }
        //endregion

        //region GET SPECIFIC DATA
        fun GetIDAndPassword(db: FirebaseFirestore, idNumber: Number, password: String): UserDB? {
            var userData: UserDB? = null
            Log.i("FIREBASE", "ACCESSING GetIDAndPassword METHOD")

            db.collection("userDB")
                .whereEqualTo("id_number", "$idNumber")
                .whereEqualTo("password", "$password")
                .get(Source.CACHE)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        for(document in task.result){
                            Log.d(TAG, "${document.id} => ${document.data}")
                            /*
                            userData?.idNumber = document.data.getValue("id_number") as Int
                            userData?.userID = document.data.getValue("userID") as Int
                            userData?.password = document.data.getValue("password").toString()
                            userData?.type = document.data.getValue("type").toString()
                            */
                        }
                    }else {
                        Log.e("FIREBASE", "Error getting documents.", task.exception)
                    }
                }

            return userData
        }

        //fun GetScheduleBy
        //endregion
    }
}

class ScheduleFirebaseDebug {
    companion object {
        fun printRoomAssignment(db: FirebaseFirestore){
            db.collection("roomAssignment")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            Log.d("FIREBASE", document.id + " => " + document.data)
                        }
                    } else {
                        Log.w("FIREBASE", "Error getting documents.", task.exception)
                    }
                }
        }

        fun printStatus(db: FirebaseFirestore){
            db.collection("status")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            Log.d("FIREBASE", document.id + " => " + document.data)
                        }
                    } else {
                        Log.w("FIREBASE", "Error getting documents.", task.exception)
                    }
                }
        }

        fun printScheduleDB(db: FirebaseFirestore){
            db.collection("scheduleDB")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            Log.d("FIREBASE", document.id + " => " + document.data)
                        }
                    } else {
                        Log.w("FIREBASE", "Error getting documents.", task.exception)
                    }
                }
        }

        fun printUserDB(db: FirebaseFirestore){
            db.collection("userDB")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            Log.d("FIREBASE", document.id + " => " + document.data)
                        }
                    } else {
                        Log.w("FIREBASE", "Error getting documents.", task.exception)
                    }
                }
        }
    }
}

/********************************* FIREBASE QUERY INSTRUCTIONS ************************************************

 ****To create or overwrite a single document, use the set() method:

val city = HashMap<String, Any>()
city["name"] = "Los Angeles"
city["state"] = "CA"
city["country"] = "USA"

db.collection("cities").document("LA")
.set(city)
.addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
.addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

 ****If the document does not exist, it will be created. If the document does exist, its contents will be
overwritten with the newly provided data, unless you specify that the data should be merged into the
existing document, as follows:

// Update one field, creating the document if it does not already exist.
val data = HashMap<String, Any>()
data["capital"] = true

 ****When you use set() to create a document, you must specify an ID for the document to create. For example:

db.collection("cities").document("BJ")
.set(data, SetOptions.merge())

db.collection("cities").document("new-city-id").set(data)

 ****But sometimes there isn't a meaningful ID for the document, and it's more convenient to let Cloud Firestore
auto-generate an ID for you. You can do this by calling add():

// Add a new document with a generated id.
val data = HashMap<String, Any>()
data["name"] = "Tokyo"
data["country"] = "Japan"

db.collection("cities")
.add(data)
.addOnSuccessListener { documentReference ->
Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
}
.addOnFailureListener { e ->
Log.w(TAG, "Error adding document", e)
}

 ****To update some fields of a document without overwriting the entire document, use the update() method:

val washingtonRef = db.collection("cities").document("DC")

// Set the "isCapital" field of the city 'DC'
washingtonRef.update("capital", true)
.addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
.addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }

 ****If your document contains nested objects, you can use "dot notation" to reference nested fields within
 * the document when you call update():

// Assume the document contains:
// {
//   name: "Frank",
//   favorites: { food: "Pizza", color: "Blue", subject: "recess" }
//   age: 12
// }
//
// To update age and favorite color:
db.collection("users").document("frank")
.update(
"age", 13,
"favorites.color", "Red"
)

 ****You can also retrieve multiple documents with one request by querying documents in a collection.
 * For example, you can use where() to query for all of the documents that meet a certain condition,
 * then use get() to retrieve the results:

db.collection("cities")
.whereEqualTo("capital", true)
.get()
.addOnSuccessListener { documents ->
for (document in documents) {
Log.d(TAG, "${document.id} => ${document.data}")
}
}
.addOnFailureListener { exception ->
Log.w(TAG, "Error getting documents: ", exception)
}

The behavior for java.util.Date objects stored in Firestore is going to change AND YOUR APP MAY BREAK.
To hide this warning and ensure your app does not break, you need to add the following code to your app
before calling any other Cloud Firestore methods:

FirebaseFirestore firestore = FirebaseFirestore.getInstance();
FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
.setTimestampsInSnapshotsEnabled(true)
.build();
firestore.setFirestoreSettings(settings);

With this change, timestamps stored in Cloud Firestore will be read back as com.google.firebase.
Timestamp objects instead of as system java.util.Date objects. So you will also need to update code expecting
a java.util.Date to instead expect a Timestamp. For example:

// Old:
java.util.Date date = snapshot.getDate("created_at");
// New:
Timestamp timestamp = snapshot.getTimestamp("created_at");
java.util.Date date = timestamp.toDate();

Please audit all existing usages of java.util.Date when you enable the new behavior. In a future release,
the behavior will be changed to the new behavior, so if you do not follow these steps, YOUR APP MAY BREAK.


 ***********************************************************************************************************/