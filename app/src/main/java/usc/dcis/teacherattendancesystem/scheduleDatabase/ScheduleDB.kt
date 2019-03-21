package usc.dcis.teacherattendancesystem.scheduleDatabase

import android.arch.persistence.room.*
import java.lang.reflect.Constructor
import java.util.*

@Entity(tableName = "Users")
data class UserDB(
    @PrimaryKey(autoGenerate = true) var userID: Int,
    @ColumnInfo(name = "id_number") var idNumber: Int = 0,
    @ColumnInfo(name = "password") var password: String? = null,
    @ColumnInfo(name = "type") var type: String? = null
)

class UsersWithSchedules {
    @Embedded
    var user: UserDB? = null
    @Relation(parentColumn = "userID", entityColumn = "user_id")
    var schedules: List<ScheduleDB>? = emptyList()
}


@Entity(tableName = "Schedules")
data class ScheduleDB(
    @PrimaryKey(autoGenerate = true) var courseID: Int = 0,
    @ColumnInfo(name = "user_id") var userID: Int = 0,
    @ColumnInfo(name = "group_number") var groupNumber: Int = 0,
    @ColumnInfo(name = "course_code")var courseCode: String? = null,
    @ColumnInfo(name = "teacher") var teacher: String? = null
)

// ROOM ASSIGNMENTS
// ScheduleDB.courseID = RoomAssignment.courseID
// AND
// ScheduleDB.courseCode = RoomAssignment.courseCode
@Entity(tableName = "Room_Assignments", foreignKeys = arrayOf(ForeignKey(entity = ScheduleDB::class,
    parentColumns = arrayOf("courseCode"),
    childColumns = arrayOf("courseCode"),
    onDelete = ForeignKey.CASCADE,
    onUpdate = ForeignKey.CASCADE),
    ForeignKey(entity = ScheduleDB::class,
    parentColumns = arrayOf("groupNumber"),
    childColumns = arrayOf("groupNumber"),
    onDelete = ForeignKey.CASCADE,
    onUpdate = ForeignKey.CASCADE)))
data class RoomAssignment(
    @PrimaryKey(autoGenerate = true) var roomID: Int = 0,
    // FOREIGN KEYS
    var courseCode: String,
    var groupNumber: Int,

    var roomNumber: String,
    var startTime: Date,
    var endTime: Date,

    // M, T, W, TH, F, SAT, SUN
    var dayAssigned: String
)

@Entity(tableName = "Statuses", foreignKeys = arrayOf(ForeignKey(entity = RoomAssignment::class,
    parentColumns = arrayOf("roomID"),
    childColumns = arrayOf("roomID"),
    onDelete = ForeignKey.CASCADE,
    onUpdate = ForeignKey.CASCADE)))
data class Status(
    // FOREIGN KEY
    @PrimaryKey(autoGenerate = true) var statusId: Int,
    @ColumnInfo(name = "roomID") var roomID: Int,

    // FOR KNOWING WHICH DAY IS PRESENT OR ABSENT
    @ColumnInfo(name = "date") var date: Date,
    var status: String = "Absent",
    var remarks: String = "None"
)