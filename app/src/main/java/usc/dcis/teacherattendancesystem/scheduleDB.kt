package usc.dcis.teacherattendancesystem

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "Schedule")
class scheduleDB {
    var teacher: String = ""
    var room: ArrayList<String>? = null
    var startTime: ArrayList<String>? = null
    var endTime: ArrayList<String>? = null
    var days: ArrayList<String>? = null
    var status: ArrayList<String>? = null

    @PrimaryKey
    @NonNull
    var courseCode: String = ""

    @NonNull
    fun getCoursecode(): String {
        return courseCode
    }

    fun setCoursecode(courseCode : String) {
        this.courseCode = courseCode
    }

    fun getroom(): ArrayList<String>? {
        return room
    }

    fun setroom(room: ArrayList<String>?){
        this.room = room
    }

    fun getSingleRoom(selectedRoom: Int): String? {
        return room?.get(selectedRoom)
    }

    fun setSingleRoom(selectedRoom: Int, newRoom: String){
        this.room?.set(selectedRoom, newRoom)
    }

    fun getStarttime(): MutableList<String>? {
        return startTime
    }

    fun setStarttime(startTime: ArrayList<String>?) {
        this.startTime = startTime
    }

    fun getEndtime(): ArrayList<String>? {
        return endTime
    }

    fun setEndtime(endTime: ArrayList<String>?){
        this.endTime = endTime
    }

    fun getdays(): ArrayList<String>? {
        return days
    }

    fun setdays(days: ArrayList<String>?){
        this.days = days
    }

    fun getSingleDay(selectedDay: Int): String {
        return this!!.days!![selectedDay]
    }

    fun setSingleDay(selectedDay: Int, newDay: String){
        this.days?.set(selectedDay, newDay)
    }

    fun getstatus(): ArrayList<String>? {
        return status
    }

    fun setstatus(days: ArrayList<String>?){
        this.status = status
    }

    fun getSingleStatus(selectedStatus: Int): String {
        return this!!.status!![selectedStatus]
    }

    fun setSingleStatus(selectedStatus: Int, newStatus: String){
        this.status?.set(selectedStatus, newStatus)
    }
}