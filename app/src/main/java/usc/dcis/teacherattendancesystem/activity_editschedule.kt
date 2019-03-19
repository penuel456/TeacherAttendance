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
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_activity_editschedule.*
import kotlinx.android.synthetic.main.content_activity_editschedule.view.*
import kotlinx.android.synthetic.main.menu_fragment_schedlistteacher.*
import kotlinx.android.synthetic.main.menu_fragment_schedlistteacher.view.*
import usc.dcis.teacherattendancesystem.menu.Menu_fragment_schedListTeacher
import usc.dcis.teacherattendancesystem.scheduleDatabase.RoomAssignment
import usc.dcis.teacherattendancesystem.scheduleDatabase.ScheduleDatabase
import java.util.Calendar.DAY_OF_MONTH
import java.util.*
import java.util.Calendar.AM


class activity_editschedule : AppCompatActivity() {
        var roomId : Int = 0
        lateinit var startTime: Date
        lateinit var endTime: Date
        var roomNumber :String = "empty"
        var day : String = "empty"
        var startTimeText:String = "empty"
        var endTimeText:String = "empty"
        var floorName:String = "empty"
        var wingName:String = "empty"
        var buildingName:String = "empty"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editschedule)
        setSupportActionBar(toolbar)
        // Get radio group selected item using on checked change listener
        chooseDay.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                Toast.makeText(applicationContext," On checked change : ${radio.text}",
                    Toast.LENGTH_SHORT).show()
                day = radio.text.toString()
            })
        roomId = intent.getIntExtra("RoomID", 0)
        Log.d("EDIT Room ID:","$roomId")
        val room = findViewById<Spinner>(R.id.room)
        val wing = findViewById<Spinner>(R.id.wing)
        val floor = findViewById<Spinner>(R.id.floor)
        val buildings = findViewById<Spinner>(R.id.buildings)
        val wingList = arrayOf(
            arrayOf("First Wing", "Second Wing", "Third Wing", "Fourth Wing", "Chicken Wings"),
            arrayOf ("Main hallway","Chicken Wings")
        )
        val floorList = arrayOf (
            arrayOf("1st Floor","2nd Floor", "3rd Floor", "4th Floor"),
            arrayOf("1st Floor", "2nd Floor", "3rd Floor"),
            arrayOf("Basement","1st Floor", "2nd Floor", "3rd Floor")
        )
        val buildingList = arrayOf("Lawrence Bunzel Building", "SMED Building", "SAFAD Building", "Basketball Court",
            "Philip van Engelen Building", "Josef Baumgartner Building")

        val bunzelBuilding_fourth = arrayOf(
            arrayOf("LBB482", "LBB483", "LBB484", "LB485", "LB486"),
            arrayOf("LB466", "LBB467","LBB468", "LBB469"),
            arrayOf("LB444","LB445","LB446", "LB447", "LB448"),
            arrayOf("LBB441","LB442")
        )
        val bunzelBuilding_third =arrayOf(
            arrayOf("LBB382", "LBB383", "LBB384", "LB385", "LB386"),
            arrayOf("LB366", "LBB367","LBB368", "LBB369"),
            arrayOf("LB344","LB345","LB346", "LB347", "LB348"),
            arrayOf("LBB341","LB342")
        )
        val bunzelBuilding_second =arrayOf(
            arrayOf("LBB282", "LBB283", "LBB284", "LB285", "LB286"),
            arrayOf("LB266", "LBB267","LBB268", "LBB269"),
            arrayOf("LB244","LB245","LB246", "LB247", "LB248"),
            arrayOf("LBB241","LB242")
        )
        val bunzelBuilding_first =arrayOf(
            arrayOf("LBB182", "LBB183", "LBB184", "LB185", "LB186"),
            arrayOf("LB166", "LBB167","LBB168", "LBB169"),
            arrayOf("LB144","LB145","LB146", "LB147", "LB148"),
            arrayOf("LBB141","LB142")
        )
        val peBuilding_first = arrayOf(
            arrayOf("LBB182", "LBB183", "LBB184", "LB185", "LB186"),
            arrayOf("LB166", "LBB167","LBB168", "LBB169"),
            arrayOf("LB144","LB145","LB146", "LB147", "LB148"),
            arrayOf("LBB141","LB142")
        )


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
        val bunzel_wing_fourth_a  = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            bunzelBuilding_fourth[0] // Array
        )
        val bunzel_wing_fourth_b  = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            bunzelBuilding_fourth[1] // Array
        )
        val bunzel_wing_fourth_c  = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            bunzelBuilding_fourth[2] // Array
        )
        val bunzel_wing_fourth_d  = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            bunzelBuilding_fourth[3] // Array
        )
        val bunzel_wing_third_a = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            bunzelBuilding_third[0] // Array
        )
        val bunzel_wing_third_b = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            bunzelBuilding_third[1] // Array
        )
        val bunzel_wing_third_c = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            bunzelBuilding_third[2] // Array
        )
        val bunzel_wing_third_d = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            bunzelBuilding_third[3] // Array
        )
        val bunzel_wing_second_a = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            bunzelBuilding_second[0] // Array
        )
        val bunzel_wing_second_b = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            bunzelBuilding_second[1] // Array
        )
        val bunzel_wing_second_c = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            bunzelBuilding_second[2] // Array
        )
        val bunzel_wing_second_d = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            bunzelBuilding_second[3] // Array
        )
        val bunzel_wing_first_a = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            bunzelBuilding_first[0] // Array
        )
        val bunzel_wing_first_b = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            bunzelBuilding_first[1] // Array
        )
        val bunzel_wing_first_c = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            bunzelBuilding_first[2] // Array
        )
        val bunzel_wing_first_d = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            bunzelBuilding_first[3] // Array
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
        val wing_a =ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            wingList[0] // Array
        )
        val wing_b = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
           wingList[1] // Array
        )
        val floor_a = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            floorList[0] // Array
        )
        val floor_b = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            floorList[1] // Array
        )
        val floor_c = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            floorList[2] // Array
        )
        bunzel_wing_fourth_a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_fourth_b.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_fourth_c.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_fourth_d.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_third_a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_third_b.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_third_c.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_third_d.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_second_a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_second_b.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_second_c.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_second_d.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_first_a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_first_b.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_first_c.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_first_d.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        PE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SMED.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        BCT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SAFAD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        LRC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        wing_a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        wing_b.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        buildings.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                // Display the selected item text on text view
                buildingName ="${parent.getItemAtPosition(position).toString()}"
               // date.text = buildingName
                when (buildingName){
                    "Philip van Engelen Building" -> {
                        floor!!.setAdapter(floor_a)
                        wing!!.setAdapter(wing_b)}
                    "Lawrence Bunzel Building" -> {
                        floor!!.setAdapter(floor_a)
                        wing!!.setAdapter(wing_a)}
                    "SAFAD Building" -> {
                        floor!!.setAdapter(floor_a)
                        wing!!.setAdapter(wing_a)
                    }
                    "Basketball Court" -> {
                        floor!!.setAdapter(floor_a)
                        wing!!.setAdapter(wing_b)
                    }
                    "Josef Baumgartner Building" -> {
                        floor!!.setAdapter(floor_b)
                        wing!!.setAdapter(wing_b)
                    }
                    "SMED Building" ->{
                        floor!!.setAdapter(floor_c)
                        wing!!.setAdapter(wing_a)
                    }
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
            }
        }
        floor.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                floorName = "${parent.getItemAtPosition(position).toString()}"
                //region LBB
                if(wingName.equals("First Wing") && floorName.equals("4th Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_fourth_a)
                }else if(wingName.equals("Second Wing") && floorName.equals("4th Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_fourth_b)
                }else if(wingName.equals("Third Wing") && floorName.equals("4th Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_fourth_c)
                }else if(wingName.equals("Fourth Wing") && floorName.equals("4th Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_fourth_d)
                }else if(wingName.equals("First Wing") && floorName.equals("3rd Floor") && buildingName.equals("Lawrence Bunzel Building")) {
                    room!!.setAdapter(bunzel_wing_third_a)
                }else if(wingName.equals("Second Wing") && floorName.equals("3rd Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_third_b)
                }else if(wingName.equals("Third Wing") && floorName.equals("3rd Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_third_c)
                }else if(wingName.equals("Fourth Wing") && floorName.equals("3rd Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_third_d)
                }else if(wingName.equals("First Wing") && floorName.equals("2nd Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_second_a)
                }else if(wingName.equals("Second Wing") && floorName.equals("2nd Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_second_b)
                }else if(wingName.equals("Third Wing") && floorName.equals("2nd Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_second_c)
                }else if(wingName.equals("Fourth Wing") && floorName.equals("2nd Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_second_d)
                }else if(wingName.equals("First Wing") && floorName.equals("1st Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_second_a)
                }else if(wingName.equals("Second Wing") && floorName.equals("1st Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_second_b)
                }else if(wingName.equals("Third Wing") && floorName.equals("1st Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_second_c)
                }else if(wingName.equals("Fourth Wing") && floorName.equals("1st Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_second_d)
                }
                //endregion
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }
        wing.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
              wingName = "${parent.getItemAtPosition(position).toString()}"
                //region LBB
                if(wingName.equals("First Wing") && floorName.equals("4th Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_fourth_a)
                }else if(wingName.equals("Second Wing") && floorName.equals("4th Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_fourth_b)
                }else if(wingName.equals("Third Wing") && floorName.equals("4th Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_fourth_c)
                }else if(wingName.equals("Fourth Wing") && floorName.equals("4th Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_fourth_d)
                }else if(wingName.equals("First Wing") && floorName.equals("3rd Floor") && buildingName.equals("Lawrence Bunzel Building")) {
                    room!!.setAdapter(bunzel_wing_third_a)
                }else if(wingName.equals("Second Wing") && floorName.equals("3rd Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_third_b)
                }else if(wingName.equals("Third Wing") && floorName.equals("3rd Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_third_c)
                }else if(wingName.equals("Fourth Wing") && floorName.equals("3rd Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_third_d)
                }else if(wingName.equals("First Wing") && floorName.equals("2nd Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_second_a)
                }else if(wingName.equals("Second Wing") && floorName.equals("2nd Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_second_b)
                }else if(wingName.equals("Third Wing") && floorName.equals("2nd Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_second_c)
                }else if(wingName.equals("Fourth Wing") && floorName.equals("2nd Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_second_d)
                }else if(wingName.equals("First Wing") && floorName.equals("1st Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_first_a)
                }else if(wingName.equals("Second Wing") && floorName.equals("1st Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_first_b)
                }else if(wingName.equals("Third Wing") && floorName.equals("1st Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_first_c)
                }else if(wingName.equals("Fourth Wing") && floorName.equals("1st Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_first_d)
                }
                //endregion
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }
        room.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Display the selected item text on text view
                roomNumber = "${parent.getItemAtPosition(position).toString()}"


            }

            override fun onNothingSelected(parent: AdapterView<*>) {
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
        var sdf = java.text.SimpleDateFormat("hh:mm a")
        val tpd = TimePickerDialog(this,TimePickerDialog.OnTimeSetListener(function = { view, h, m ->
            var hourformat:Int
            var status = "AM"
            if(h > 12) {
                status = "PM"
                hourformat = h - 12
            }else if (h == 0) {
                status = "AM"
                hourformat = 12
            }else{
                hourformat = h
            }
            startTime = java.text.SimpleDateFormat("h:m a").parse("$hourformat:$m $status")
            Toast.makeText(this, hourformat.toString() + ":" + String.format("%02d", m)+" "+status , Toast.LENGTH_LONG).show()
            val Start_time_textview = findViewById<TextView>(R.id.start_time)
            Start_time_textview.text = sdf.format(startTime)
            startTimeText = sdf.format(startTime)
        }),hour,minute,false)

        tpd.show()

    }
    @TargetApi(Build.VERSION_CODES.P)
    fun clickTimePicker_end(view: View) {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR)
        val minute = c.get(Calendar.MINUTE)
        var sdf = java.text.SimpleDateFormat("hh:mm a")

        val tpd = TimePickerDialog(this,TimePickerDialog.OnTimeSetListener(function = { view, h, m ->
            var hourformat:Int
            var status = "AM"
            if(h > 12) {
                status = "PM"
                hourformat = h - 12
            }else if (h == 0) {
                status = "AM"
                hourformat = 12
            }else{
                hourformat = h
            }
            Toast.makeText(this, hourformat.toString() + ":" + String.format("%02d", m)+" "+status , Toast.LENGTH_LONG).show()
            endTime = java.text.SimpleDateFormat("hh:mm a").parse("$hourformat:$m $status")
            val End_time_textview = findViewById<TextView>(R.id.end_time)
            End_time_textview.text = sdf.format(endTime)
            endTimeText = sdf.format(endTime)

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
    fun updateAllValues(view: View){
        val db = ScheduleDatabase.getInstance(this)
        val dbDAO = db.scheduleDAO
        if(startTimeText.equals("empty") || endTimeText.equals("empty") || day.equals("empty") || roomNumber.equals("empty")) {
            Toast.makeText(this, "Please fill out all required fields." , Toast.LENGTH_LONG).show()
        }else{
            dbDAO.updateRoomAssignmentsByRoomId(roomId, roomNumber, startTime, endTime, day)
            val activity = Intent(this, SchedListTeacher::class.java)
            startActivity(activity)
        }
    }
}
