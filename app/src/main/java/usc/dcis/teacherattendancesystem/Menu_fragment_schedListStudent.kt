package usc.dcis.teacherattendancesystem

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.text.SimpleDateFormat

class Menu_fragment_schedListStudent : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.menu_fragment_schedliststudent, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val todaySched = MutableList(5) { scheduleList() }

    }

    private fun scheduleList() = object {
        val courseCode: String = ""
        val teacher: String = ""
        val room: String = ""
        val startSchedule: String = ""
        val endSchedule: String = ""
        val status: String = ""
    }

    private fun getCurrentTime(): String {
        val answer: String

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss")
            answer = current.format(formatter)
            Log.d("answer",answer)
        } else {
            var date = Date()
            val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
            answer = formatter.format(date)
            Log.d("answer",answer)
        }

        return answer
    }
}