package usc.dcis.teacherattendancesystem.scheduleDatabase

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.TypeConverter
import android.arch.persistence.room.TypeConverters
import java.util.Date
import android.arch.persistence.room.Room
import android.content.Context


@Database(entities = [ScheduleDB::class, UserDB::class, RoomAssignment::class, Status::class], version = 15)
@TypeConverters(Converters::class)
abstract class ScheduleDatabase : RoomDatabase() {
    abstract val scheduleDAO: ScheduleDAO


    companion object {
        private var INSTANCE: ScheduleDatabase? = null

        fun getInstance(context: Context): ScheduleDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    ScheduleDatabase::class.java,
                    "db-scheduleList"
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return INSTANCE as ScheduleDatabase
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}

object Converters {
    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    @JvmStatic
    fun dateToTimestamp(date: Date?): Long? {
        return (if (date == null) null else date!!.time)?.toLong()
    }
}

/*******************************************************************************************

When using the ScheduleDB database, copy paste this:

val db = Room.databaseBuilder(this, ScheduleDatabase::class.java, "db-scheduleList").allowMainThreadQueries()
.fallbackToDestructiveMigration()
.build()

-----------------------------ADD A SCHEDULE LIKE THIS:

var scheduleListTest = db.scheduleDAO

var schedule = ScheduleDB
schedule.courseCode = "IT 5001"
schedule.teacher = "Mr. Teacher"

scheduleListTest.insert(schedule)

OR LIKE THIS:

var sched = scheduleListTest.insert(ScheduleDB(0, "IT 5001", "Mr. Teacher"))

------------------------------FOR ROOM ASSIGNMENTS, IT CONTAINS TIME:

var sdf = java.text.SimpleDateFormat("m:s")

var dateString = "10:30"
var mDate  = sdf.parse(dateString)

scheduleListTest.insertRoomAssignment(RoomAssignment(1, 1, "LBB305TC", mDate, mDate, "M"))

------------------------------SAME FOR STATUS, BUT THIS TIME, DATE:

sdf = java.text.SimpleDateFormat("yyyy-MM-dd")
dateString = "2019-03-28"
mDate = sdf.parse(dateString)

scheduleListTest.insertStatus(Status(0, 1, mDate, "Present"))

------------------------------SYNTAX:

 ScheduleDB(courseID: Int, courseCode: String, teacher: String)

 RoomAssignment(roomID: Int, courseID: Int, roomNumber: String, startTime: Date, endTime: Date, dayAssigned: String)
 **** Room assignment accounts for ONE DAY. In case of multiple class days,
  create a new RoomAssignment that refers to the same courseID ****

Status(statusID: Int, roomID: Int, statusDate: Date, status: String)


 ******************************************************************************************/