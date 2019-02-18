package usc.dcis.teacherattendancesystem

import android.arch.persistence.room.*


@Dao
interface ScheduleDAO {
    @Insert
    fun insert(vararg schedules: scheduleDB)

    @Update
    fun update(vararg schedules: scheduleDB)


    @Query("SELECT * FROM schedule")
    fun getAllSchedules(): List<scheduleDB>

    @Query("SELECT * FROM schedule WHERE days = :selectedDays AND courseCode = :courseCode")
    fun getSelectedDays(selectedDays: String, courseCode: String): scheduleDB

    @Query("SELECT * FROM schedule WHERE days = :startTime AND courseCode = :courseCode")
    fun getSelectedStartTime(startTime: String, courseCode: String): scheduleDB

    @Query("SELECT * FROM schedule WHERE days = :endTime AND courseCode = :courseCode")
    fun getSelectedEndTime(endTime: String, courseCode: String): scheduleDB
}