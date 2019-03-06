package usc.dcis.teacherattendancesystem

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

import kotlinx.android.synthetic.main.activity_choooseschedule.*

class chooseschedule : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choooseschedule)
        setSupportActionBar(toolbar)
        val chooseSched = findViewById<Spinner>(R.id.choosesched)
        val schedList = arrayOf("7:00 AM - 9:30 AM (MWF) LBB446", "12:00 PM - 5:00 PM (Sat) LBB466", "1:00 PM - 4:30 PM (TTH) LBB485")
        val schedAdapter  = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            schedList // Array
        )
        schedAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        chooseSched.adapter = schedAdapter
        chooseSched.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                // Display the selected item text on text view
                val Schedule ="${parent.getItemAtPosition(position).toString()}"
                // date.text = buildingName

            }

            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
            }
        }

    }

    fun goToEditSchedActivity(view : View){
        val activity = Intent(this, activity_editschedule::class.java)
        startActivity(activity)
    }

}
