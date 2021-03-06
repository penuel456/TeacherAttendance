package usc.dcis.teacherattendancesystem.scheduleDatabase

import android.arch.persistence.room.*
import android.support.annotation.NonNull
import java.lang.reflect.Constructor
import java.util.*

@Entity(tableName = "Users")
data class UserDB(
    @PrimaryKey var userID: Int,
    var idNumber: Int = 0,
    var name: String,
    var password: String? = null,
    var type: String? = null
)

class UsersWithSchedules {
    @Embedded
    var user: UserDB? = null
    @Relation(parentColumn = "userID", entityColumn = "userID")
    var schedules: List<ScheduleDB>? = emptyList()
}


@Entity(tableName = "Schedules")
data class ScheduleDB(
    @PrimaryKey var courseID: Int = 0,
    var userID: Int = 0,
    var teacherId: Int? = null,
    var groupNumber: Int = 0,
    var courseCode: String = "",
    var department: String = "",
    var courseName: String = ""
)

// ROOM ASSIGNMENTS
// ScheduleDB.courseID = RoomAssignment.courseID
// AND
// ScheduleDB.courseCode = RoomAssignment.courseCode
@Entity(tableName = "Room_Assignments")
data class RoomAssignment(
    @PrimaryKey(autoGenerate = true) var roomID: Int = 0,
    // FOREIGN KEYS
    var courseCode: String = "",
    var groupNumber: Int = 0,

    var roomNumber: String = "",
    var startTime: Date? = null,
    var endTime: Date? = null,

    // M, T, W, TH, F, SAT, SUN
   var dayAssigned: String? = null
)

@Entity(tableName = "Statuses")
data class Status(

    @PrimaryKey(autoGenerate = true) var statusId: Int = 0,
    // FOREIGN KEY ON ROOM ASSIGNMENT
    var roomID: Int = 0,

    // FOR KNOWING WHICH DAY IS PRESENT OR ABSENT
    @ColumnInfo(name = "date") var date: Date? = null,
    var status: String = "Absent",
    var remarks: String = "None"
)