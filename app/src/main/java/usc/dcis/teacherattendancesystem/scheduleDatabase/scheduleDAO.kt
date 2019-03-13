package usc.dcis.teacherattendancesystem.scheduleDatabase

import android.arch.persistence.room.*
import java.util.*


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

    @Update (onConflict = OnConflictStrategy.REPLACE)
    fun updateRoomAssignment(vararg roomAssignment: RoomAssignment)

    @Query("SELECT * FROM Room_Assignments")
    fun getAllRoomAssignments(): List<RoomAssignment>

    @Query("SELECT * FROM Room_Assignments WHERE courseID = :courseId")
    fun getAllRoomAssignmentsByCourseId(courseId: Int): List<RoomAssignment>

    @Query("SELECT * FROM Room_Assignments WHERE dayAssigned = :dayAssigned ORDER BY startTime DESC")
    fun getAllRoomAssignmentsByDay(dayAssigned: String): List<RoomAssignment>

    @Query("SELECT * FROM Room_Assignments WHERE roomNumber = :roomNumber")
    fun getAllRoomAssignmentsByRoomNumber(roomNumber: String) : List<RoomAssignment>

    @Query("SELECT * FROM Room_Assignments WHERE roomNumber = :roomNumber AND dayAssigned = :dayAssigned")
    fun getAllRoomAssignmentsByRoomNumberAndDay(roomNumber: String, dayAssigned: String) : List<RoomAssignment>


    @Query("SELECT * FROM Room_Assignments WHERE roomID = :roomId")
    fun getRoomAssignmentByRoomId(roomId: Int): RoomAssignment

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

    @Query("SELECT * FROM Statuses WHERE date = :date")
    fun getStatusByDate(date: Date): List<Status>

    @Query("SELECT * FROM Statuses WHERE date = :date AND roomID = :roomID LIMIT 1")
    fun getStatusByRoomIdAndDate(date: Date, roomID: Int): Status

    @Query("DELETE FROM Schedules")
    fun deleteAllSchedules()

    @Query("DELETE FROM Room_Assignments")
    fun deleteAllRoomAssignments()

    @Query("DELETE FROM Statuses")
    fun deleteAllStatus()

    @Query("SELECT COUNT(*) FROM Schedules")
    fun getScheduleCount(): Int

    @Query("SELECT COUNT(*) FROM Room_Assignments")
    fun getRoomAssignmentsCount(): Int

    @Query("SELECT COUNT(*) FROM Room_Assignments WHERE courseID = :courseId")
    fun getRoomAssignmentCountBycourseId(courseId: Int): Int

    @Query("UPDATE Room_Assignments SET roomNumber = :roomNumber, startTime =:startTime, endTime =:endTime, dayAssigned =:day WHERE roomID =:roomId")
    fun updateRoomAssignmentsByRoomId(roomId: Int, roomNumber: String, startTime: Date, endTime :Date, day: String )

}