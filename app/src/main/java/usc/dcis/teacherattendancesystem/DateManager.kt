package usc.dcis.teacherattendancesystem

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

        fun getCurrentDate(): Date {
            val indiaTime = GregorianCalendar(TimeZone.getTimeZone("Asia/Singapore"))
            val year = indiaTime.get(Calendar.YEAR)
            val month = indiaTime.get(Calendar.MONTH) + 1
            val day = indiaTime.get(Calendar.DAY_OF_MONTH)

            Log.d("CURRENT DATE: ", java.text.SimpleDateFormat("yyyy-MM-dd").parse("$year-$month-$day").toString())
            return java.text.SimpleDateFormat("yyyy-MM-dd").parse("$year-$month-$day")
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

        fun getCurrentDay(): String {
            val indiaTime = GregorianCalendar(TimeZone.getTimeZone("Asia/Singapore"))
            val day = indiaTime.get(Calendar.DAY_OF_WEEK)

            Log.d("TODAY: ", getDayString(day))

            return getDayString(day)
        }
    }
}