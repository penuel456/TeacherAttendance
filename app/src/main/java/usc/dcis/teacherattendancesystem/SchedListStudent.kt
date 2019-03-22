package usc.dcis.teacherattendancesystem

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_sched_list_teacher.*
import kotlinx.android.synthetic.main.sched_list_student.*
import kotlinx.android.synthetic.main.sched_list_student.view.*
import usc.dcis.teacherattendancesystem.DateManager.Companion.getCurrentDate
import usc.dcis.teacherattendancesystem.DateManager.Companion.getCurrentTime
import usc.dcis.teacherattendancesystem.DateManager.Companion.getDayString
import usc.dcis.teacherattendancesystem.scheduleDatabase.ScheduleDatabase
import usc.dcis.teacherattendancesystem.scheduleDatabase.RoomAssignment
import usc.dcis.teacherattendancesystem.scheduleDatabase.ScheduleDAO
import usc.dcis.teacherattendancesystem.scheduleDatabase.UserDB
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
        val roomAssignmentList = scheduleDao.getAllRoomAssignmentsByDay(DateManager.getDayString(day))
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

    fun getOnGoingAndUpNext(scheduleDao: ScheduleDAO, roomAssignmentList: List<RoomAssignment>){
        var sdf = java.text.SimpleDateFormat("hh:mm a")
        var isThereOnGoing = false
        var isThereUpNext = false

        val sdfTime = getCurrentTime()
        val sdfDate = getCurrentDate()

        for(rooms in roomAssignmentList){
            Log.d("TODAYROOM: ", rooms?.toString())
            val currentSched = scheduleDao.getScheduleByCourseCodeAndGroupNumber(rooms.courseCode, rooms.groupNumber)
            if(sdfTime.after(rooms?.endTime)){
                Log.d("TIMEDEBUG:", "Schedule ${rooms?.roomID} is FINISHED in ${rooms?.roomNumber}")
            }else if(sdfTime.before(rooms?.startTime)){
                Log.d("TIMEDEBUG:", "Schedule ${rooms?.roomID} is ABOUT TO GO in ${rooms?.roomNumber}")
                Schedule_layout.studUpNextCourseCode.text = currentSched?.courseCode
                Schedule_layout.studUpNextTeacher.text = scheduleDao.getTeacherFromSchedule(currentSched?.teacherId)?.name
                Schedule_layout.studUpNextBuilding.text = rooms?.roomNumber
                Schedule_layout.studUpNextStartTime.text = sdf.format(rooms?.startTime)
                Schedule_layout.studUpNextEndTime.text = sdf.format(rooms?.endTime)
                isThereUpNext = true
            }else if(sdfTime.after(rooms.startTime) && sdfTime.before(rooms.endTime)){
                Log.d("TIMEDEBUG:", "Schedule ${rooms?.roomID} is CURRENTLY in ${rooms?.roomNumber}")
                Schedule_layout.studCourseCode.text = currentSched?.courseCode
                Schedule_layout.studTeacher.text = scheduleDao.getTeacherFromSchedule(currentSched?.teacherId)?.name
                Schedule_layout.studBuilding.text = rooms?.roomNumber
                Schedule_layout.studStartTime.text = sdf.format(rooms?.startTime)
                Schedule_layout.studEndTime.text = sdf.format(rooms?.endTime)

                /* For status, it's supposed to get from the database that the teacher inputted. */
                Schedule_layout.studStatus.text = scheduleDao.getStatusByRoomIdAndDate(sdfDate, rooms.roomID)?.status
                isThereOnGoing = true
            }

            if(isThereOnGoing && isThereUpNext) break
        }

    }

    fun refreshSchedule(view: View){
        getSchedule()
    }

    fun debugPrintAllRoomAssignments(){
        val db = ScheduleDatabase.getInstance(this)
        var scheduleDao = db.scheduleDAO

        var roomAssignmentList = scheduleDao.getAllRoomAssignmentsByDay("W")
        var sdf = java.text.SimpleDateFormat("h:m a")

        for(rooms in roomAssignmentList){
            var schedDB = scheduleDao.getScheduleByCourseCodeAndGroupNumber(rooms.courseCode, rooms.groupNumber)
            var teacher: UserDB? = scheduleDao.getTeacherFromSchedule(schedDB?.teacherId)

            Log.d("ROOMASSN", "RoomID: ${rooms.roomID}")
            Log.d("ROOMASSN", "Teacher: ${teacher?.name}")
            Log.d("ROOMASSN", "GroupNumber: ${rooms.groupNumber}")
            Log.d("ROOMASSN", "CourseCode: ${rooms.courseCode}")
            Log.d("ROOMASSN", "StartTime: ${sdf.format(rooms.startTime)}")
            Log.d("ROOMASSN", "EndTime: ${sdf.format(rooms.endTime)}")
            Log.d("ROOMASSN", "DayAssigned: ${rooms.dayAssigned}")
        }

        ScheduleDatabase.destroyInstance()
    }
}

