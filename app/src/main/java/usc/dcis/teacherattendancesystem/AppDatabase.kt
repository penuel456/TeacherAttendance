package usc.dcis.teacherattendancesystem

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.TypeConverter
import android.arch.persistence.room.TypeConverters
import java.util.Date




@Database(entities = [scheduleDB::class, RoomAssignment::class, Status::class], version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val scheduleDAO: ScheduleDAO
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
        return (if (date == null) null else date!!.getTime())?.toLong()
    }
}

/*******************************************************************************************

When using the scheduleDB database, copy paste this:

AppDatabase database = Room.databaseBuilder(this, AppDatabase.class, "db-scheduleList")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build()

Then use it like this:

ScheduleDAO scheduleDAO = database.getContactDAO();

ScheduleDB schedule = new ScheduleDB();
schedule.setFirstName("Gurleen");
schedule.setLastName("Sethi");
schedule.setPhoneNumber("1234567890");

scheduleDAO.insert(contact);

contactDAO.update(contact);


 All in Java. Convert to Kotlin
 ******************************************************************************************/