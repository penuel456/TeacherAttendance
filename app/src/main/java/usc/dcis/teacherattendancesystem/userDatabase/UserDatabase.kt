package usc.dcis.teacherattendancesystem.userDatabase

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.TypeConverters
import android.arch.persistence.room.Room
import android.content.Context
import usc.dcis.teacherattendancesystem.scheduleDatabase.Converters


@Database(entities = [userDB::class], version = 1)
@TypeConverters(Converters::class)
abstract class UserDatabase : RoomDatabase() {
    abstract val userDAO: UserDAO


    companion object {
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "db-userlist"
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return INSTANCE as UserDatabase
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}