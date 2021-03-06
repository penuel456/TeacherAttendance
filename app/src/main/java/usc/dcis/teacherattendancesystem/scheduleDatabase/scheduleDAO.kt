package usc.dcis.teacherattendancesystem.scheduleDatabase

import android.arch.persistence.room.*
import java.util.*


@Dao
interface ScheduleDAO {
    /******************************* ALL USER QUERIES **********************************************/
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(vararg UserDB: UserDB)

    @Update (onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(vararg UserDB: UserDB)

    @Query("SELECT * FROM Users WHERE idNumber = :idNumber AND password = :password")
    fun getUserOnLogin(idNumber: Int, password: String): UserDB

    @Query("SELECT COUNT(*) FROM Users WHERE idNumber = :idNumber AND password = :password")
    fun getUserCountOnLogin(idNumber: Int, password: String): Int

    @Query("SELECT * FROM Users")
    fun getAllUsers(): List<UserDB>

    /******************************* ALL USER QUERIES **********************************************/

    /******************************* ALL SCHEDULEDB QUERIES ****************************************/
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg schedules: ScheduleDB)

    @Update (onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg schedules: ScheduleDB)

    @Query("SELECT * FROM Schedules")
    fun getAllSchedules(): List<ScheduleDB>

    @Query("SELECT * FROM Schedules WHERE courseID = :courseId")
    fun getSchedule(courseId: Int): ScheduleDB

    @Query("SELECT * FROM Schedules WHERE courseCode = :courseCode AND groupNumber = :groupNumber")
    fun getScheduleByCourseCodeAndGroupNumber(courseCode: String, groupNumber: Int): ScheduleDB?

    @Query("SELECT * FROM users WHERE userID = :teacherID")
    fun getTeacherFromSchedule(teacherID: Int?): UserDB

    @Query("SELECT * FROM users WHERE userID = :teacherID AND type = 'teacher'")
    fun getTeacherByType(teacherID: Int?): UserDB

    /******************************* ALL SCHEDULEDB QUERIES ****************************************/

    /******************************* ALL USERSWITHSCHEDULES QUERIES ********************************/

    @Transaction
    @Query("SELECT * FROM users WHERE userID = :userID")
    fun getAllUserSchedules(userID: Int): UsersWithSchedules

    /******************************* ALL USERSWITHSCHEDULES QUERIES ********************************/

    /******************************* ALL ROOM ASSIGNMENT QUERIES ***********************************/

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertRoomAssignment(vararg roomAssignment: RoomAssignment)

    @Update (onConflict = OnConflictStrategy.REPLACE)
    fun updateRoomAssignment(vararg roomAssignment: RoomAssignment)

    @Query("SELECT * FROM Room_Assignments")
    fun getAllRoomAssignments(): List<RoomAssignment>

    // Getting all Room Assignments by DAY
    @Query("SELECT * FROM Room_Assignments WHERE dayAssigned = :dayAssigned ORDER BY startTime DESC")
    fun getAllRoomAssignmentsByDay(dayAssigned: String): List<RoomAssignment>

    // Getting all Room Assignments by ROOM NUMBER
    @Query("SELECT * FROM Room_Assignments WHERE roomNumber = :roomNumber")
    fun getAllRoomAssignmentsByRoomNumber(roomNumber: String) : List<RoomAssignment>

    // Getting all Room Assignments by COURSE CODE and GROUP NUMBER
    @Query("SELECT * FROM Room_Assignments WHERE courseCode = :courseCode AND groupNumber = :groupNumber")
    fun getRoomAssignmentByCourseCodeAndGroupNumber(courseCode: String, groupNumber: Int): RoomAssignment

    // Getting all Room Assignments by COURSE CODE
    @Query("SELECT * FROM Room_Assignments WHERE courseCode = :courseCode")
    fun getAllRoomAssignmentsByCourseCode(courseCode: String): List<RoomAssignment>

    // Getting all Room Assignments by ROOM NUMBER and DAY
    @Query("SELECT * FROM Room_Assignments WHERE roomNumber = :roomNumber AND dayAssigned = :dayAssigned")
    fun getAllRoomAssignmentsByRoomNumberAndDay(roomNumber: String, dayAssigned: String) : List<RoomAssignment>

    // Getting all Room Assignments by BY ROOM ID
    @Query("SELECT * FROM Room_Assignments WHERE roomID = :roomId")
    fun getRoomAssignmentByRoomId(roomId: Int): RoomAssignment

    @Query("UPDATE Room_Assignments SET roomNumber = :roomNumber, startTime =:startTime, endTime =:endTime, dayAssigned =:day WHERE roomID =:roomId")
    fun updateRoomAssignmentsByRoomId(roomId: Int, roomNumber: String, startTime: Date, endTime :Date, day: String )

    /******************************* ALL ROOM ASSIGNMENT QUERIES ***********************************/

    /******************************* ALL STATUS QUERIES ********************************************/

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertStatus(vararg status: Status)

    @Update (onConflict = OnConflictStrategy.REPLACE)
    fun updateStatus(vararg status: Status)

    @Query("UPDATE Statuses SET status = :status WHERE roomID = :roomID AND date = :date")
    fun updateStatusState(roomID: Int, date: Date, status: String)

    @Query("UPDATE Statuses SET status = :status, remarks = :remarks WHERE roomID = :roomID AND date = :date")
    fun updateStatusStateRemarks(roomID: Int, date: Date, status: String, remarks: String)

    @Query("SELECT * FROM Statuses")
    fun getAllStatus(): List<Status>

    @Query("SELECT * FROM Statuses WHERE statusId = :statusId")
    fun getStatusByStatusId(statusId: Int): Status

    @Query("SELECT * FROM Statuses WHERE roomID = :roomId")
    fun getAllStatusByRoomId(roomId: Int): List<Status>

    @Query("SELECT * FROM Statuses WHERE status = :status")
    fun getAllStatusByStatus(status: String): List<Status>

    @Query("SELECT * FROM Statuses WHERE date = :date")
    fun getStatusByDate(date: Date): List<Status>

    @Query("SELECT COUNT(*) FROM Statuses WHERE date = :date AND roomID = :roomID")
    fun getStatusCountByRoomIdAndDate(date: Date, roomID: Int): Int

    @Query("SELECT COUNT(*) FROM Statuses WHERE roomID = :roomID")
    fun getStatusCountByRoomId(roomID: Int): Int

    @Query("SELECT * FROM Statuses WHERE date = :date AND roomID = :roomID LIMIT 1")
    fun getStatusByRoomIdAndDate(date: Date, roomID: Int): Status

    /******************************* ALL STATUS QUERIES ********************************************/

    /******************************* ALL DELETION QUERIES ******************************************/

    @Query("DELETE FROM Schedules")
    fun deleteAllSchedules()

    @Query("DELETE FROM Room_Assignments")
    fun deleteAllRoomAssignments()

    @Query("DELETE FROM Statuses")
    fun deleteAllStatus()

    @Query("DELETE FROM Users")
    fun deleteAllUsers()

    /******************************* ALL DELETION QUERIES ******************************************/

    /******************************* ALL COUNT QUERIES *********************************************/

    @Query("SELECT COUNT(*) FROM Schedules")
    fun getScheduleCount(): Int

    @Query("SELECT COUNT(*) FROM Schedules WHERE groupNumber = :groupNumber AND courseCode = :courseCode")
    fun getScheduleCountByCourseCodeAndGroupNumber(groupNumber: Int, courseCode: String): Int

    // Getting all Room Assignments by COURSE CODE and GROUP NUMBER
    @Query("SELECT COUNT(*) FROM Room_Assignments WHERE courseCode = :courseCode AND groupNumber = :groupNumber")
    fun getRoomAssignmentCountByCourseCodeAndGroupNumber(courseCode: String, groupNumber: Int): Int

    @Query("SELECT COUNT(*) FROM Room_Assignments")
    fun getRoomAssignmentsCount(): Int

    @Query("SELECT COUNT(*) FROM Room_Assignments WHERE roomID = :roomID")
    fun getRoomAssignmentCountByRoomID(roomID: Int): Int
    /******************************* ALL COUNT QUERIES *********************************************/

}