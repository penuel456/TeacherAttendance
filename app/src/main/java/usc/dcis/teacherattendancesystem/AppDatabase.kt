package usc.dcis.teacherattendancesystem

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database


@Database(entities = [scheduleDB::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val scheduleDAO: ScheduleDAO
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