package usc.dcis.teacherattendancesystem.menu

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.*
import kotlinx.android.synthetic.main.menu_fragment_schedlistteacher.*
import kotlinx.android.synthetic.main.menu_fragment_schedlistteacher.view.*
import usc.dcis.teacherattendancesystem.R
import usc.dcis.teacherattendancesystem.chooseschedule
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.text.SimpleDateFormat


class Menu_fragment_schedListTeacher : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.menu_fragment_schedlistteacher, null)

        view.editBtn.setOnClickListener { view ->
            //check_stat()
           // editSched()
            goToEditSchedule(view)
        }
        val statusSpinner = view.findViewById<Spinner>(R.id.status)
        val teacherStatus = arrayOf("Present", "Absent")
        val statusSpinner_adapter = ArrayAdapter(
            this.activity, // Context
            android.R.layout.simple_spinner_item, // Layout
            teacherStatus // Array
        )

        statusSpinner_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        statusSpinner.adapter = statusSpinner_adapter
        statusSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                // Display the selected item text on text view
                val statusValue ="${parent.getItemAtPosition(position).toString()}"
                // date.text = buildingName
                when (statusValue){
                    "Present" -> {
                        reason_2.visibility = View.GONE
                    }
                    "Absent" -> reason_2.visibility = View.VISIBLE
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val todaySched = MutableList(5) { scheduleList() }

    }
    fun goToEditSchedule(view: View){
        //Log.d("test", "YAY NAA KO DIRI")
        val chooseSched = Intent(activity, chooseschedule::class.java)
        activity!!.startActivity(chooseSched)
    }
    private fun scheduleList() = object {
        val courseCode: String = "IT 5001"
        val teacher: String = "Mr. Dummy"
        val room: String = "LBB 305TC"
        val startSchedule: String = "7:30"
        val endSchedule: String = "9:30"
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

    /*fun check_stat(){
        Log.d("Click", "Senpai clicked me")
        val Text = view?.status!!.selectedItem.toString()
        //status.text = Text
        Log.d("Click", Text)
        if(Text.equals("Absent")) {
            Log.d("Click", "I'm here")
            reason.visibility = View.VISIBLE
        }else{
            reason.visibility = View.INVISIBLE
        }
    }*/

    fun editSched(){
        val chooseSched = Intent(activity, chooseschedule::class.java)
        startActivity(chooseSched)
    }

}