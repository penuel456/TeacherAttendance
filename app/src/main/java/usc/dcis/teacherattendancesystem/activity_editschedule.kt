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
import android.app.PendingIntent.getActivity
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*


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
            End_time.text = h.toString() + " : " + m

        }),hour,minute,false)

        tpd.show()

    }
}
