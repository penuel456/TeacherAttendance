package usc.dcis.teacherattendancesystem

import android.annotation.TargetApi
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.View
import android.widget.Toast
import android.icu.util.Calendar

import kotlinx.android.synthetic.main.activity_editschedule.*
import android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT
import android.app.DatePickerDialog
import android.app.PendingIntent.getActivity
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.Calendar.DAY_OF_MONTH


class activity_editschedule : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editschedule)
        setSupportActionBar(toolbar)

      /*  fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
    }
    @TargetApi(Build.VERSION_CODES.P)
    fun clickTimePicker(view: View) {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR)
        val minute = c.get(Calendar.MINUTE)

        val tpd = TimePickerDialog(this,TimePickerDialog.OnTimeSetListener(function = { view, h, m ->

            Toast.makeText(this, h.toString() + " : " + m +" : " , Toast.LENGTH_LONG).show()
            val Start_time = findViewById<TextView>(R.id.start_time)
            Start_time.text = h.toString() + " : " + m

        }),hour,minute,false)

        tpd.show()

    }
    @TargetApi(Build.VERSION_CODES.P)
    fun clickTimePicker_end(view: View) {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR)
        val minute = c.get(Calendar.MINUTE)

        val tpd = TimePickerDialog(this,TimePickerDialog.OnTimeSetListener(function = { view, h, m ->

            Toast.makeText(this, h.toString() + " : " + m +" : " , Toast.LENGTH_LONG).show()
            val End_time = findViewById<TextView>(R.id.end_time)
            End_time.text = h.toString() + " : " + m.toString()

        }),hour,minute,false)

        tpd.show()

    }
    @TargetApi(Build.VERSION_CODES.P)
    fun clickDatePicker(view: View) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener(function = { view, year, monthOfYear, dayOfMonth  ->

            Toast.makeText(this, monthOfYear.toString() + " / " + dayOfMonth.toString() +"/"+ year.toString() , Toast.LENGTH_LONG).show()
            val date = findViewById<TextView>(R.id.date)
            date.text = monthOfYear.toString() + " / " + dayOfMonth.toString() +"/"+ year.toString()

        }),year, month, day)

        dpd.show()

    }
}
