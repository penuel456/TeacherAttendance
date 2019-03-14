package usc.dcis.teacherattendancesystem.scheduleDatabase

import android.arch.persistence.room.*
import java.util.*

@Entity(tableName = "Schedules")
data class scheduleDB(
    @PrimaryKey(autoGenerate = true) val courseID: Int = 0,
    @ColumnInfo(name = "course_code")var courseCode: String,
    @ColumnInfo(name = "teacher") var teacher: String
)


// ROOM ASSIGNMENTS
@Entity(tableName = "Room_Assignments", foreignKeys = arrayOf(ForeignKey(entity = scheduleDB::class,
    parentColumns = arrayOf("courseID"),
    childColumns = arrayOf("courseID"),
    onDelete = ForeignKey.CASCADE,
    onUpdate = ForeignKey.CASCADE)))
data class RoomAssignment(
    @PrimaryKey(autoGenerate = true) val roomID: Int = 0,
    // FOREIGN KEY
    val courseID: Int,

    var roomNumber: String,
    var startTime: Date,
    var endTime: Date,

    // MWF, TTH, etc
    var dayAssigned: String
)

@Entity(tableName = "Statuses", foreignKeys = arrayOf(ForeignKey(entity = RoomAssignment::class,
    parentColumns = arrayOf("roomID"),
    childColumns = arrayOf("roomID"),
    onDelete = ForeignKey.CASCADE,
    onUpdate = ForeignKey.CASCADE)))
data class Status(
    // FOREIGN KEY
    @PrimaryKey(autoGenerate = true) val statusId: Int,
    @ColumnInfo(name = "roomID") val roomID: Int,

    // FOR KNOWING WHICH DAY IS PRESENT OR ABSENT
    @ColumnInfo(name = "date") var date: Date,
    var status: String = "Absent"
)