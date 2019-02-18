package usc.dcis.teacherattendancesystem

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "Schedule")
class scheduleDB {
    var teacher: String = ""
    var room: String = ""
    var startTime: MutableList<String>? = null
    var endTime: MutableList<String>? = null
    var days: MutableList<String>? = null
    var status: MutableList<String>? = null

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

    fun getroom(): String {
        return room
    }

    fun setroom(courseCode : String) {
        this.room = room
    }

    fun getStarttime(): MutableList<String>? {
        return startTime
    }

    fun setStarttime(startTime: MutableList<String>?) {
        this.startTime = startTime
    }

    fun getEndtime(): MutableList<String>? {
        return endTime
    }

    fun setEndtime(endTime: MutableList<String>?){
        this.endTime = endTime
    }

    fun getdays(): MutableList<String>? {
        return days
    }

    fun setdays(days: MutableList<String>?){
        this.days = days
    }

    fun getSingleDay(selectedDay: Int): String {
        return this!!.days!![selectedDay]
    }

    fun getstatus(): MutableList<String>? {
        return status
    }

    fun setstatus(days: MutableList<String>?){
        this.status = status
    }

    fun getSingleStatus(selectedStatus: Int): String {
        return this!!.status!![selectedStatus]
    }
}