package usc.dcis.teacherattendancesystem

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_sched_list_teacher.*
import kotlinx.android.synthetic.main.sched_list_student.*
import kotlinx.android.synthetic.main.sched_list_student.view.*
import usc.dcis.teacherattendancesystem.scheduleDatabase.ScheduleDatabase
import usc.dcis.teacherattendancesystem.scheduleDatabase.RoomAssignment
import usc.dcis.teacherattendancesystem.scheduleDatabase.ScheduleDAO
import java.util.*


class SchedListStudent : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sched_list_student)

        //val loginBtn = findViewById<Button>(R.id.loginBtn)
        debugPrintAllRoomAssignments()
        getSchedule()

    }

    fun getSchedule(){
        val db = ScheduleDatabase.getInstance(this)
        var scheduleDao = db.scheduleDAO
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_WEEK)

        val scheduleList = scheduleDao.getAllSchedules()
        val roomAssignmentList = scheduleDao.getAllRoomAssignmentsByDay(getDayString(day))
        Log.d("TODAY:", getDayString(day))

        if(roomAssignmentList.isNotEmpty()){
            getOnGoingAndUpNext(scheduleDao, roomAssignmentList)
        }else {
            Log.d("NOTICE", "RoomAssignmentList is empty. Do something here (should say 'no schedule for today')")
            displayNoSchedule()
        }
        ScheduleDatabase.destroyInstance()
    }

    fun displayNoSchedule(){
        Schedule_teacher_layout.visibility = View.INVISIBLE
        noSchedNotif.visibility = View.VISIBLE
    }

    fun getCurrentTime(): Date{
        val indiaTime = GregorianCalendar(TimeZone.getTimeZone("Asia/Singapore"))
        var hour = indiaTime.get(Calendar.HOUR_OF_DAY)
        val minute = indiaTime.get(Calendar.MINUTE)
        var sdf = java.text.SimpleDateFormat("hh:mm a")

        var am_pm: String
        if(indiaTime.get(Calendar.HOUR_OF_DAY) < 12){
            am_pm = "AM"
        }else {
            am_pm = "PM"
            if(hour > 12){
                hour -= 12
            }
        }

        Log.d("CURRENT TIME: ", sdf.parse("$hour:$minute $am_pm").toString())
        return sdf.parse("$hour:$minute $am_pm")
    }

    fun getCurrentDate(): Date{
        val indiaTime = GregorianCalendar(TimeZone.getTimeZone("Asia/Singapore"))
        val year = indiaTime.get(Calendar.YEAR)
        val month = indiaTime.get(Calendar.MONTH) + 1
        val day = indiaTime.get(Calendar.DAY_OF_MONTH)

        Log.d("CURRENT DATE: ", java.text.SimpleDateFormat("yyyy-MM-dd").parse("$year-$month-$day").toString())
        return java.text.SimpleDateFormat("yyyy-MM-dd").parse("$year-$month-$day")
    }

    fun getOnGoingAndUpNext(scheduleDao: ScheduleDAO, roomAssignmentList: List<RoomAssignment>){
        var sdf = java.text.SimpleDateFormat("hh:mm a")
        var isThereOnGoing = false
        var isThereUpNext = false

        val sdfTime = getCurrentTime()
        val sdfDate = getCurrentDate()

        for(rooms in roomAssignmentList){
            Log.d("TODAYROOM: ", rooms.toString())
            val currentSched = scheduleDao.getSchedule(rooms.courseID)
            if(sdfTime.after(rooms.endTime)){
                Log.d("TIMEDEBUG:", "Schedule ${rooms.roomID} is FINISHED in ${rooms.roomNumber}")
            }else if(sdfTime.before(rooms.startTime)){
                Log.d("TIMEDEBUG:", "Schedule ${rooms.roomID} is ABOUT TO GO in ${rooms.roomNumber}")
                Schedule_teacher_layout.studUpNextCourseCode.text = currentSched.courseCode
                Schedule_teacher_layout.studUpNextTeacher.text = currentSched.teacher
                Schedule_teacher_layout.studUpNextBuilding.text = rooms.roomNumber
                Schedule_teacher_layout.studUpNextStartTime.text = sdf.format(rooms.startTime)
                Schedule_teacher_layout.studUpNextStartTime.text = sdf.format(rooms.endTime)
                isThereUpNext = true
            }else if(sdfTime.after(rooms.startTime) && sdfTime.before(rooms.endTime)){
                Log.d("TIMEDEBUG:", "Schedule ${rooms.roomID} is CURRENTLY in ${rooms.roomNumber}")
                Schedule_teacher_layout.studCourseCode.text = currentSched.courseCode
                Schedule_teacher_layout.studTeacher.text = currentSched.teacher
                Schedule_teacher_layout.studBuilding.text = rooms.roomNumber
                Schedule_teacher_layout.studStartTime.text = sdf.format(rooms.startTime)
                Schedule_teacher_layout.studStartTime.text = sdf.format(rooms.endTime)

                /* For status, it's supposed to get from the database that the teacher inputted. */
                Schedule_teacher_layout.studStatus.text = scheduleDao.getStatusByRoomIdAndDate(sdfDate, rooms.roomID).status
                isThereOnGoing = true
            }

            if(isThereOnGoing && isThereUpNext) break
        }
    }

    public fun refreshSchedule(view: View){
        getSchedule()
    }

    fun getDayString(today: Int): String{
        val day = arrayListOf("SUN", "M", "T", "W", "TH", "F", "SAT")

        for((ndx, day) in day.withIndex()){
            if(ndx == (today - 1)) return day
        }

        return "NONE"
    }

    fun debugPrintAllRoomAssignments(){
        val db = ScheduleDatabase.getInstance(this)
        var scheduleDao = db.scheduleDAO

        var roomAssignmentList = scheduleDao.getAllRoomAssignmentsByDay("W")
        var sdf = java.text.SimpleDateFormat("h:m a")

        for(rooms in roomAssignmentList){
            Log.d("ROOMASSN", "RoomID: ${rooms.roomID}")
            Log.d("ROOMASSN", "CourseID: ${rooms.courseID}")
            Log.d("ROOMASSN", "CourseCode: ${scheduleDao.getSchedule(rooms.courseID).courseCode}")
            Log.d("ROOMASSN", "StartTime: ${sdf.format(rooms.startTime)}")
            Log.d("ROOMASSN", "EndTime: ${sdf.format(rooms.endTime)}")
            Log.d("ROOMASSN", "DayAssigned: ${rooms.dayAssigned}")
        }

        ScheduleDatabase.destroyInstance()
    }
}

