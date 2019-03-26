package usc.dcis.teacherattendancesystem.scheduleDatabase

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class ScheduleDebug {
    companion object DebugSchedule {
        // PRINTING EVERYTHING
        fun printAllUserSchedules(dao: ScheduleDAO, userID: Int){
            if(dao.getAllUserSchedules(userID).schedules?.count() != 0){
                for(schedules in dao.getAllUserSchedules(userID).schedules!!){
                    Log.d("DEBUG USER SCHED: ", schedules.toString())
                    Log.d("DEBUG  USER ROOMASSN",
                        "${dao.getRoomAssignmentByCourseCodeAndGroupNumber(schedules.courseCode,
                            schedules.groupNumber)}")
                }
            }else {
                Log.d("DEBUG USER SCHED",
                    "${dao.getAllUserSchedules(userID).user!!.idNumber}'s SCHEDULE LIST IS EMPTY")
            }


        }

        fun printAllStatus(dao: ScheduleDAO){
            if(dao.getAllStatus().count() != 0){
                for(status in dao.getAllStatus()){
                    Log.d("DEBUG STATUS: ", status.toString())
                }
            }else {
                Log.d("DEBUG STATUS", "STATUS LIST IS EMPTY")
            }

        }

        fun printAllRoomAssignments(dao: ScheduleDAO){
            if(dao.getAllRoomAssignments().count() != 0){
                for(rooms in dao.getAllRoomAssignments()){
                    Log.d("DEBUG ROOM: ", rooms.toString())
                }
            }else {
                Log.d("DEBUG ROOM: ", "ROOM ASSIGNMENT LIST IS EMPTY")
            }

        }

        fun printAllSchedules(dao: ScheduleDAO){
            if(dao.getAllSchedules().count() != 0){
                for(sched in dao.getAllSchedules()){
                    Log.d("DEBUG SCHED: ", sched.toString())
                }
            }else {
                Log.d("DEBUG SCHED: ", "SCHEDULE LIST IS EMPTY")
            }
        }

        // PRINTING ROOM ASSIGNMENT BY DAY
        fun printAllRoomAssignmentsByDay(dao: ScheduleDAO, currentDay: String){
            if(dao.getAllRoomAssignmentsByDay(currentDay).count() != 0){
                for(rooms in dao.getAllRoomAssignmentsByDay(currentDay)){
                    Log.d("DEBUG ROOMDAY: ", rooms.toString())
                }
            }else {
                Log.d("DEBUG ROOMDAY: ", "NO SCHEDULES FOR TODAY")
            }
        }




    }
}

