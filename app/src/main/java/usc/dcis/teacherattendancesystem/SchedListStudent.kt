package usc.dcis.teacherattendancesystem

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.menu_fragment_schedliststudent.*
import kotlinx.android.synthetic.main.sched_list_student.*
import kotlinx.android.synthetic.main.sched_list_student.view.*
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
        val db = AppDatabase.getInstance(this)
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
        AppDatabase.destroyInstance()
    }

    fun displayNoSchedule(){
        scheduleLayout.visibility = View.INVISIBLE
        noSchedNotif.visibility = View.VISIBLE
    }

    fun getOnGoingAndUpNext(scheduleDao: ScheduleDAO, roomAssignmentList: List<RoomAssignment>){
        val indiaTime = GregorianCalendar(TimeZone.getTimeZone("Asia/Singapore"))
        var hour = indiaTime.get(Calendar.HOUR_OF_DAY)
        val minute = indiaTime.get(Calendar.MINUTE)
        var am_pm: String
        if(indiaTime.get(Calendar.HOUR_OF_DAY) < 12){
            am_pm = "AM"
        }else {
            am_pm = "PM"
            if(hour > 12){
                hour -= 12
            }
        }

        Log.d("TIME:", "$hour:$minute $am_pm")
        var sdf = java.text.SimpleDateFormat("hh:mm a")
        val sdfDate = sdf.parse("$hour:$minute $am_pm")

        Log.d("CURRENT TIME: ", "${sdf.format(sdfDate)}")

        for((ndx, rooms) in roomAssignmentList.withIndex()){
            val currentSched = scheduleDao.getSchedule(rooms.courseID)
            Log.d("STARTTIME", "${sdf.format(rooms.startTime)}")
            Log.d("ENDTIME", "${sdf.format(rooms.endTime)}")
            if(sdfDate.after(rooms.endTime)){
                Log.d("TIMEDEBUG:", "Schedule ${rooms.courseID} is FINISHED in ${rooms.roomNumber}")
            }else if(sdfDate.before(rooms.startTime)){
                Log.d("TIMEDEBUG:", "Schedule ${rooms.courseID} is ABOUT TO GO in ${rooms.roomNumber}")
                scheduleLayout.studUpNextCourseCode.text = currentSched.courseCode
                scheduleLayout.studUpNextTeacher.text = currentSched.teacher
                scheduleLayout.studUpNextBuilding.text = rooms.roomNumber
                studUpNextStartTime.text = sdf.format(rooms.startTime)
                studUpNextEndTime.text = sdf.format(rooms.endTime)
                //studStatus.text =
            }else if(sdfDate.before(rooms.endTime)){
                Log.d("TIMEDEBUG:", "Schedule ${rooms.courseID} is CURRENTLY in ${rooms.roomNumber}")
                scheduleLayout.studCourseCode.text = currentSched.courseCode
                scheduleLayout.studTeacher.text = currentSched.teacher
                scheduleLayout.studBuilding.text = rooms.roomNumber
                scheduleLayout.studStartTime.text = sdf.format(rooms.startTime)
                scheduleLayout.studEndTime.text = sdf.format(rooms.endTime)

            }
        }
    }

    fun getDayString(today: Int): String{
        val day = arrayListOf("SUN", "M", "T", "W", "TH", "F", "SAT")

        for((ndx, day) in day.withIndex()){
            if(ndx == (today - 1)) return day
        }

        return "NONE"
    }

    fun debugPrintAllRoomAssignments(){
        val db = AppDatabase.getInstance(this)
        var scheduleDao = db.scheduleDAO

        var roomAssignmentList = scheduleDao.getAllRoomAssignmentsByDay("TH")
        var sdf = java.text.SimpleDateFormat("h:m a")

        for(rooms in roomAssignmentList){
            Log.d("ROOMASSN", "RoomID: ${rooms.roomID}")
            Log.d("ROOMASSN", "CourseID: ${rooms.courseID}")
            Log.d("ROOMASSN", "StartTime: ${sdf.format(rooms.startTime)}")
            Log.d("ROOMASSN", "EndTime: ${sdf.format(rooms.endTime)}")
            Log.d("ROOMASSN", "DayAssigned: ${rooms.dayAssigned}")
        }

        AppDatabase.destroyInstance()
    }
}
