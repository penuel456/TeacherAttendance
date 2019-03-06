package usc.dcis.teacherattendancesystem

import android.annotation.TargetApi
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.View
import android.widget.TextView
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_room_assignment_info.*

class room_assignment_info : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_assignment_info)
        setSupportActionBar(toolbar)

    }
    @TargetApi(Build.VERSION_CODES.P)
    fun clickDatePicker(view: View) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener(function = { view, year, monthOfYear, dayOfMonth  ->

                Toast.makeText(this, monthOfYear.toString() + " / " + dayOfMonth.toString() +"/"+ year.toString() , Toast.LENGTH_LONG).show()
                val date = findViewById<TextView>(R.id.date_2)
                date.text = monthOfYear.toString() + " / " + dayOfMonth.toString() +"/"+ year.toString()

            }),year, month, day)

        dpd.show()

    }

}
