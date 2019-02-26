package usc.dcis.teacherattendancesystem

import android.arch.persistence.room.*


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

    @Query("SELECT * FROM Room_Assignments")
    fun getAllRoomAssignments(): List<RoomAssignment>

    @Query("SELECT * FROM Room_Assignments WHERE courseID = :courseId")
    fun getAllRoomAssignmentsByCourseId(courseId: Int): List<RoomAssignment>

    @Query("SELECT * FROM Room_Assignments WHERE dayAssigned = :dayAssigned")
    fun getAllRoomAssignmentsByDay(dayAssigned: String): List<RoomAssignment>

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

    @Query("DELETE FROM Schedules")
    fun deleteAllSchedules()

    @Query("DELETE FROM Room_Assignments")
    fun deleteAllRoomAssignments()

    @Query("DELETE FROM Statuses")
    fun deleteAllStatus()

}