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
import android.icu.util.Calendar

import kotlinx.android.synthetic.main.activity_editschedule.*
import android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT
import android.app.DatePickerDialog
import android.app.PendingIntent.getActivity
import android.app.ProgressDialog.show
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_activity_editschedule.*
import kotlinx.android.synthetic.main.menu_fragment_schedlistteacher.*
import kotlinx.android.synthetic.main.menu_fragment_schedlistteacher.view.*
import java.util.Calendar.DAY_OF_MONTH


class activity_editschedule : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editschedule)
        setSupportActionBar(toolbar)
        val room = findViewById<Spinner>(R.id.room)
        val buildings = findViewById<Spinner>(R.id.buildings)
        val buildingList = arrayOf("Lawrence Bunzel Building", "SMED Building", "SAFAD Building", "Basketball Court",
            "Philip van Engelen Building", "Josef Baumgartner Building")
        val bunzelBuilding = arrayOf("LB466", "LB467","LB468","LB469")
        val peBuilding = arrayOf("PE13", "PE12","PE11","PE10")
        val SMEDBuilding = arrayOf("FO10", "FO12","FO11","FO13")
        val BCT_building = arrayOf("none")
        val SAFADBuilding = arrayOf("SAFAD1", "SAFAD2","SAFAD3","SAFAD4")
        val josefBuilding = arrayOf("LRC1", "LRC2","LRC3","LRC4")
        val adapter = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            buildingList // Array
        )
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        buildings.adapter = adapter
        val bunzel  = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            bunzelBuilding // Array
        )
        val PE  = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            peBuilding // Array
        )
        val SMED  = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            SMEDBuilding // Array
        )
        val BCT  = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            BCT_building // Array
        )
        val SAFAD  = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            SAFADBuilding // Array
        )
        val LRC  = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            josefBuilding // Array
        )
        bunzel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        PE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SMED.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        BCT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SAFAD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        LRC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        buildings.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                // Display the selected item text on text view
                val buildingName ="${parent.getItemAtPosition(position).toString()}"
               // date.text = buildingName
                when (buildingName){
                    "Philip van Engelen Building" -> room!!.setAdapter(PE)
                    "Lawrence Bunzel Building" -> room!!.setAdapter(bunzel)
                    "SAFAD Building" -> room!!.setAdapter(SAFAD)
                    "Basketball Court" -> room!!.setAdapter(BCT)
                    "Josef Baumgartner Building" -> room!!.setAdapter(LRC)
                    "SMED Building" -> room!!.setAdapter(SMED)
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
            }
        }




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
            val status : String
            val hourFormat : Int
            if(h > 11) {
                status = "PM"
                hourFormat = h - 12
            }else if (h == 0) {
                hourFormat = 12
                status = "AM"
            }else{
                status = "AM"
                hourFormat  = h
            }
            Toast.makeText(this, hourFormat.toString() + ":" + String.format("%02d", m) +" " +status , Toast.LENGTH_LONG).show()
            val Start_time = findViewById<TextView>(R.id.start_time)
            Start_time.text = hourFormat.toString() + ":" + String.format("%02d", m) +" " +status


        }),hour,minute,false)

        tpd.show()

    }
    @TargetApi(Build.VERSION_CODES.P)
    fun clickTimePicker_end(view: View) {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR)
        val minute = c.get(Calendar.MINUTE)

        val tpd = TimePickerDialog(this,TimePickerDialog.OnTimeSetListener(function = { view, h, m ->
            val status : String
            val hourFormat : Int
            if(h > 11) {
                status = "PM"
                hourFormat = h - 12
            }else if (h == 0) {
                hourFormat = 12
                status = "AM"
            }else{
                status = "AM"
                hourFormat  = h
            }
            Toast.makeText(this, hourFormat.toString() + ":" + String.format("%02d", m) +" " +status , Toast.LENGTH_LONG).show()
            val End_time = findViewById<TextView>(R.id.end_time)
            End_time.text = hourFormat.toString() + ":" + String.format("%02d", m) +" " +status

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
