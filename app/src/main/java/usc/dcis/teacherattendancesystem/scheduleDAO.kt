package usc.dcis.teacherattendancesystem

import android.arch.persistence.room.*
import java.sql.Date


@Dao
interface ScheduleDAO {
    // ALL SCHEDULEDB QUERIES
    @Insert
    fun insert(vararg schedules: scheduleDB)

    @Update
    fun update(vararg schedules: scheduleDB)

    @Query("SELECT * FROM Schedules")
    fun getAllSchedules(): List<scheduleDB>

    @Query("SELECT * FROM Schedules WHERE courseID = :courseId")
    fun getSchedule(courseId: Int): scheduleDB

    // ALL ROOM ASSIGNMENT QUERIES
    @Insert
    fun insertRoomAssignment(vararg roomAssignment: RoomAssignment)

    @Update
    fun updateRoomAssignment(vararg roomAssignment: RoomAssignment)

    // ALL STATUS QUERIES
    @Insert
    fun insertStatus(vararg status: Status)

    @Update
    fun updateStatus(vararg status: Status)

    @Query("SELECT * FROM Statuses WHERE roomID = :statusId")
    fun getAllStatusByStatusId(statusId: Int): List<Status>

    @Query("SELECT * FROM Statuses WHERE roomID = :roomId")
    fun getAllStatusByRoomId(roomId: Int): List<Status>

    @Query("SELECT * FROM Statuses WHERE status = :status")
    fun getAllStatusByStatus(status: String): List<Status>
}