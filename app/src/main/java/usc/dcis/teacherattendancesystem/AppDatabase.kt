package usc.dcis.teacherattendancesystem

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.TypeConverter
import android.arch.persistence.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@Database(entities = [scheduleDB::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val scheduleDAO: ScheduleDAO
}

class Converters {

    @TypeConverter
    fun fromString(value: String): ArrayList<String> {

        val listType = object : TypeToken<ArrayList<String>>() {

        }.getType()

        return Gson().fromJson(value, listType)

    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String>): String {

        val gson = Gson()

        return gson.toJson(list)

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