package usc.dcis.teacherattendancesystem

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.arch.persistence.room.Room
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.util.Log
import kotlinx.android.synthetic.main.menu_fragment_schedlistteacher.*
import java.sql.Date
import java.time.Instant
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

        if(roomAssignmentList.isNotEmpty()){
            getOnGoingAndUpNext(scheduleList, roomAssignmentList)
        }else {
            Log.d("NOTICE", "RoomAssignmentList is empty. Do something here (should say 'no schedule for today')")
        }

    }

    fun getOnGoingAndUpNext(scheduleList: List<scheduleDB>, roomAssignmentList: List<RoomAssignment>){
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY) + 8
        val minute = calendar.get(Calendar.MINUTE)
        val time = "$hour:$minute"

        Log.d("Time: ", "$hour:$minute")

        for((ndx, rooms) in roomAssignmentList.withIndex()){

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

        var roomAssignmentList = scheduleDao.getAllRoomAssignments()
        var sdf = java.text.SimpleDateFormat("h:m a")

        for(rooms in roomAssignmentList){
            Log.d("RoomID", "${rooms.roomID}")
            Log.d("CourseID", "${rooms.courseID}")
            Log.d("StartTime", "${sdf.format(rooms.startTime)}")
            Log.d("EndTime", "${sdf.format(rooms.endTime)}")
            Log.d("DayAssigned", "${rooms.dayAssigned}")
        }

        AppDatabase.destroyInstance()
    }
}
