package usc.dcis.tea

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import usc.dcis.teacherattendancesystem.scheduleDatabase.*

import android.arch.persistence.room.Room
import android.support.annotation.NonNull
import com.google.firebase.firestore.Source
import java.util.*
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.util.concurrent.CountDownLatch


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

        //region ADD MULTIPLE DATA
        fun AddMultipleSchedules(db: FirebaseFirestore, scheds: List<ScheduleDB>) {
            val batch = db.batch()

            for(sched in scheds){
                batch.set(db.collection("scheduleDB").document(sched.courseID.toString()), sched)
            }

            batch.commit().addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Log.d(TAG, "Multiple schedules successfully added")
                }else {
                    Log.d(TAG, "Error: ${task.exception}")
                }
            }
        }

        fun AddMultipleRoomAssignments(db: FirebaseFirestore, rooms: List<RoomAssignment>) {
            val batch = db.batch()

            for(room in rooms){
                batch.set(db.collection("roomAssignment").document(room.roomID.toString()), room)
            }

            batch.commit().addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Log.d(TAG, "Multiple rooms successfully added")
                }else {
                    Log.d(TAG, "Error: ${task.exception}")
                }
            }
        }

        fun AddMultipleStatuses(db: FirebaseFirestore, statuses: List<Status>) {
            val batch = db.batch()

            for(status in statuses){
                batch.set(db.collection("status").document(status.statusId.toString()), status)
            }

            batch.commit().addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Log.d(TAG, "Multiple statuses successfully added")
                }else {
                    Log.d(TAG, "Error: ${task.exception}")
                }
            }
        }

        fun AddMultipleUsers(db: FirebaseFirestore, users: List<UserDB>) {
            val batch = db.batch()

            for(user in users){
                batch.set(db.collection("userDB").document(user.userID.toString()), user)
            }

            batch.commit().addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Log.d(TAG, "Multiple users successfully added")
                }else {
                    Log.d(TAG, "Error: ${task.exception}")
                }
            }
        }

        fun UpdateStatus(db: FirebaseFirestore, status: Status){
            FirebaseFirestore.getInstance().collection("status").document(status.statusId.toString())
                .set(status)
                .addOnSuccessListener { Log.d(TAG, "Status successfully updated") }
                .addOnFailureListener { task -> Log.d(TAG, "Status failed to update: ${task.message}") }
        }
        fun UpdateRoomAssignments(db: FirebaseFirestore, roomAssignment: RoomAssignment ){
            FirebaseFirestore.getInstance().collection("roomAssignment").document(roomAssignment.roomID.toString())
                .set(roomAssignment)
                .addOnSuccessListener { Log.d(TAG, "RoomAssignment successfully updated") }
                .addOnFailureListener { task -> Log.d(TAG, "Status failed to update: ${task.message}") }
        }
        //endregion
    }
}

class DebugFirebase {

    companion object {
        const val TAG = "FIREBASE"

        @SuppressLint("SimpleDateFormat")
        fun displayRoomAssignment(db: FirebaseFirestore){
            val sdf = java.text.SimpleDateFormat("hh:mm a")
            sdf.timeZone = TimeZone.getTimeZone("GMT+8")

            db.collection("roomAssignment")
                .whereEqualTo("dayAssigned", "W")
                .get()
                .addOnCompleteListener { task ->
                    val snapshot = task.result

                    if(!snapshot?.isEmpty!!){
                        for(snap in snapshot){
                            val roomIDToCheck = snap["roomID"] as Number
                            val startTimestamp = snap.getTimestamp("startTime")
                            val endTimestamp = snap.getTimestamp("endTime")
                            val startTime = startTimestamp?.toDate()
                            val endTime = endTimestamp?.toDate()

                            Log.d(TAG, "RoomID: ${roomIDToCheck.toInt()}, CourseCode: ${snap["courseCode"]}, GroupNumber: ${snap["groupNumber"]}" +
                                    "\nTime: ${sdf.format(startTime)} - ${sdf.format(endTime)}" +
                                    "\nRoomNumber: ${snap["roomNumber"]}" +
                                    "\nDay: ${snap["dayAssigned"]}")
                        }
                    }else {
                        Log.d(TAG, "RoomAssignment is empty.")
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