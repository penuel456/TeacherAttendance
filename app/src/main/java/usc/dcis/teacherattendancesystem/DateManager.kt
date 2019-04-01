package usc.dcis.teacherattendancesystem

import android.annotation.SuppressLint
import android.util.Log
import java.util.*

class DateManager {
    companion object {
        fun getDayString(today: Int): String{
            val day = arrayListOf("SUN", "M", "T", "W", "TH", "F", "SAT")

            for((ndx, day) in day.withIndex()){
                if(ndx == (today - 1)) return day
            }

            return "NONE"
        }

        @SuppressLint("SimpleDateFormat")
        fun getCurrentDate(): Date {
            val timeZone = GregorianCalendar(TimeZone.getTimeZone("Asia/Singapore"))
            val year = timeZone.get(Calendar.YEAR)
            val month = timeZone.get(Calendar.MONTH) + 1
            val day = timeZone.get(Calendar.DAY_OF_MONTH)
            val sdf = java.text.SimpleDateFormat("yyyy-MM-dd")
            sdf.timeZone = TimeZone.getTimeZone("GMT+8")

            Log.d("CURRENT DATE: ", sdf.parse("$year-$month-$day").toString())
            return sdf.parse("$year-$month-$day")
        }

        @SuppressLint("SimpleDateFormat")
        fun getCurrentTime(): Date{
            val timeZone = GregorianCalendar(TimeZone.getTimeZone("Asia/Singapore"))
            var hour = timeZone.get(Calendar.HOUR_OF_DAY)
            val minute = timeZone.get(Calendar.MINUTE)
            val sdf = java.text.SimpleDateFormat("hh:mm a")
            sdf.timeZone = TimeZone.getTimeZone("GMT+8")

            var am_pm: String
            if(timeZone.get(Calendar.HOUR_OF_DAY) < 12){
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

        fun getCurrentDay(): String {
            val timeZone = GregorianCalendar(TimeZone.getTimeZone("Asia/Singapore"))
            val day = timeZone.get(Calendar.DAY_OF_WEEK)

            Log.d("TODAY: ", getDayString(day))

            return getDayString(day)
        }
    }
}