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
import android.icu.lang.UCharacter.JoiningGroup.PE
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
        //region Arrays
        val wingList = arrayOf(
            arrayOf("Main Wing","First Wing", "Second Wing", "Third Wing", "Fourth Wing"),
            arrayOf("First Wing", "Second Wing", "Third Wing", "Fourth Wing"),
            arrayOf("Physics Department", "Chemistry Department", "Biology Department", "Mathematics Department"),
            arrayOf("Main hallway"),
            arrayOf("Main Wing")
        )
        val floorList = arrayOf (
            arrayOf("1st Floor","2nd Floor", "3rd Floor", "4th Floor"),
            arrayOf("Basement", "1st Floor"),
            arrayOf("Basement","1st Floor", "2nd Floor", "3rd Floor"),
            arrayOf("Basement 1","Basement 2","Basement 3","1st Floor", "2nd Floor"),
            arrayOf("Basement","1st Floor","2nd Floor", "3rd Floor", "4th Floor", "5th Floor"),
            arrayOf("1st Floor", "2nd Floor"),
            arrayOf("1st Floor")
        )
        val norooms = arrayOf("none")
        val buildingList = arrayOf("Lawrence Bunzel Building", "SMED Building", "SAFAD Building", "Basketball Court",
            "Philip van Engelen Building", "Josef Baumgartner Building", "Chapel")
        val bunzelBuilding_fifth = arrayOf("LB561", "LB562", "LB563")
        val bunzelBuilding_fourth = arrayOf(
            arrayOf("LB401","LB402","LB404"),
            arrayOf("LB482", "LB483", "LB484", "LB485", "LB486"),
            arrayOf("LB466", "LB467","LB468", "LB469"),
            arrayOf("LB444","LB445","LB446", "LB447", "LB448"),
            arrayOf("LB441","LB442")
        )
        val bunzelBuilding_third =arrayOf(
            arrayOf("LB306","LB305","LB304"),
            arrayOf("LB380","LB381", "LB382", "LB383", "LB384", "LB386"),
            arrayOf("LB363","LB364","LB366")
        )
        val bunzelBuilding_second =arrayOf(
            arrayOf("LB280", "LBCEA1", "LB285", "LB286"),
            arrayOf("LB264", "LB265","LB266", "LB267","LB268"),
            arrayOf("LB245","LB246","LB247", "LB248"),
            arrayOf("LB220")
        )
        val bunzelBuilding_first = arrayOf(
            arrayOf("LB167", "LB168", "LB172"),
            arrayOf("LB143", "LB144"),
            arrayOf("LBCH1","LBCH2")
        )
        val bunzelBuilding_basement = arrayOf("Cisco Laboratory")
        val peBuilding = arrayOf(
            arrayOf("PE11", "PE12", "PE13", "PE14", "PE15", "PE16"),
            arrayOf("PE21","PE22", "PE23", "PE24", "PE25", "PE26"),
            arrayOf("PE31","PE32", "PE34", "PE34B"),
            arrayOf("PE40","PE41", "PE44B", "PE45")
        )
        val SMEDBuilding_third = arrayOf(
            arrayOf("D314","D316", "D318"),
            arrayOf("EO323","EO325", "EO328", "EO329", "EO331","EO333", "EO336", "EO337","EO338")
        )
        val SMEDBuilding_second = arrayOf(
            arrayOf("D211","D212", "D214", "D215", "D216", "D218"),
            arrayOf("ES228","ES229", "ES231", "ES232","ES233", "ES234", "ES235")
        )
        val SMEDbuilding_first = arrayOf("FO115", "FO117", "FO118", "FO120", "ES122")
        val SMEDbuilding_basement = arrayOf("SMB1", "SMB2", "SMB3")
        val BCT_building = arrayOf("BCT1", "BCT2")
        val SAFADBuilding = arrayOf(
            arrayOf("AFCB4", "AFCB3","AF3B02","AF3B01"),
            arrayOf("AF2B11", "AF2B09", "Painting Studio","2B07A","2B07B","2B07C","2B07D","2B07F","2B07G"),
            arrayOf("AF1B102","AF1B04","AF1B03","1B10A","1B10B","1B10C","1B12A","1B12B","1B12C","1B12D","1B12E","1B12F"),
            arrayOf("AF109A","AF109B","AF109A", "AF110", "AF111","AF104","AF104","AF103","AF102","AF101"),
            arrayOf("AF207", "AF208","AF209","AF210","AF211","AF212","AF213","AF214","AF215")
        )
        val chapelBuilding = arrayOf("AJB06")

        val josefBuilding = arrayOf(
            arrayOf("JBB101", "JBB102", "JBB103", "JBB104", "JBB105"),
            arrayOf("BL101","BL102")
        )
        //endregion
        val adapter = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            buildingList // Array
        )
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        buildings.adapter = adapter
        //region LBB Arrayadapter
        val bunzel_fifth = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            bunzelBuilding_fifth // Array
        )
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
        val bunzel_basement = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            bunzelBuilding_basement // Array
        )
        //endregion
        //region PE Building Arrayadapter
        val PE_first  = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            peBuilding[0]// Array
        )
        val PE_second  = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            peBuilding[1]// Array
        )
        val PE_third  = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            peBuilding[2]// Array
        )
        val PE_fourth  = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            peBuilding[3]// Array
        )
        //endregion
        //region SMED Array adapter
        val SMED_third_a  = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            SMEDBuilding_third[0] // Array
        )
        val SMED_third_b  = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            SMEDBuilding_third[1] // Array
        )
        val SMED_second_a =ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            SMEDBuilding_second[0] // Array
        )
        val SMED_second_b = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            SMEDBuilding_second[1] // Array
        )
        val SMED_first = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            SMEDbuilding_first // Array
        )
        val SMED_basement = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            SMEDbuilding_basement // Array
        )
        //endregion
        val BCT  = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            BCT_building // Array
        )
        val SAFAD_second = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            SAFADBuilding[4] // Array
        )
        val SAFAD_first = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            SAFADBuilding[3] // Array
        )
        val SAFAD_basement = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            SAFADBuilding[2] // Array
        )
        val SAFAD_basement_b = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            SAFADBuilding[1] // Array
        )
        val SAFAD_basement_c = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            SAFADBuilding[0] // Array
        )
        val LRC_basement  = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            josefBuilding[0] // Array
        )
        val LRC_first  = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            josefBuilding[1] // Array
        )
        val chapel = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            chapelBuilding // Array
        )
        //region wing Arrayadapter
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
        val wing_c = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            wingList[2] // Array
        )
        val wing_d = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            wingList[3] // Array
        )
        val wing_e = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            wingList[4] // Array
        )
        //endregion
        //region floor Arrayadapter
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
        val floor_d = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            floorList[3] // Array
        )
        val floor_e = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            floorList[4] // Array
        )
        val floor_f = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            floorList[5] // Array
        )
        val floor_g = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            floorList[6] // Array
        )
        //endregion
        val no_rooms = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            norooms // Array
        )
        //region LBB setDropDown
        bunzel_fifth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_fourth_a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_fourth_b.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_fourth_c.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_fourth_d.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_third_a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_third_b.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_third_c.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_second_a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_second_b.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_second_c.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_second_d.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_first_a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_first_b.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_wing_first_c.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bunzel_basement.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //endregion
        //region PE setDropDown
        PE_first.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        PE_second.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        PE_third.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        PE_fourth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //endregion
        //region SMED setDropDown
        SMED_third_a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SMED_third_b.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SMED_second_a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SMED_second_b.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SMED_first.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SMED_basement.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //endregion
        //region BCT setDropDown
        BCT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //endregion
        //region SAFAD setDropDown
        SAFAD_second.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SAFAD_first.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SAFAD_basement.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SAFAD_basement_b.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SAFAD_basement_c.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //endregion
        //region LRC setDropDown
        LRC_basement.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        LRC_first.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //endregion
        //region Wings setDropDown
        wing_a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        wing_b.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        wing_c.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        wing_d.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        wing_e.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //endregion
        //region floor setDropDown
        floor_a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        floor_b.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        floor_c.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        floor_d.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        floor_e.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        floor_f.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        floor_g.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //endregion
        //region chapel setDropDown
        chapel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //endregion
        no_rooms.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        buildings.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                // Display the selected item text on text view
                buildingName ="${parent.getItemAtPosition(position).toString()}"
               // date.text = buildingName
                when (buildingName){
                    "Philip van Engelen Building" -> {
                        floor!!.setAdapter(floor_a)
                        wing!!.setAdapter(wing_d)}
                    "Lawrence Bunzel Building" -> {
                        floor!!.setAdapter(floor_a)
                        wing!!.setAdapter(wing_a)}
                    "SAFAD Building" -> {
                        floor!!.setAdapter(floor_d)
                        wing!!.setAdapter(wing_d)
                    }
                    "Basketball Court" -> {
                        floor!!.setAdapter(floor_g)
                        wing!!.setAdapter(wing_d)
                    }
                    "Josef Baumgartner Building" -> {
                        floor!!.setAdapter(floor_b)
                        wing!!.setAdapter(wing_e)
                    }
                    "SMED Building" ->{
                        floor!!.setAdapter(floor_c)
                        wing!!.setAdapter(wing_c)
                    }
                    "Chapel" ->{
                        floor!!.setAdapter(floor_g)
                        wing!!.setAdapter(wing_d)
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
                if(wingName.equals("First Wing") && floorName.equals("5th Floor") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_wing_fourth_a)
                }else if(wingName.equals("First Wing") && floorName.equals("4th Floor") && buildingName.equals("Lawrence Bunzel Building")){
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
                }else if(wingName.equals("First Wing") && floorName.equals("Basement") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_basement)
            }
                //endregion
                //region PE
                else if(wingName.equals("Main hallway") && floorName.equals("4th Floor") && buildingName.equals("Philip van Engelen Building")) {
                    room!!.setAdapter(PE_fourth)
                }else if(wingName.equals("Main hallway") && floorName.equals("3rd Floor") && buildingName.equals("Philip van Engelen Building")) {
                    room!!.setAdapter(PE_third)
                }else if(wingName.equals("Main hallway") && floorName.equals("2nd Floor") && buildingName.equals("Philip van Engelen Building")) {
                    room!!.setAdapter(PE_second)
                }else if(wingName.equals("Main hallway") && floorName.equals("1st Floor") && buildingName.equals("Philip van Engelen Building")) {
                    room!!.setAdapter(PE_first)
                }
                //endregion
                //SMED
                else if(wingName.equals("Mathematics Department ") && floorName.equals("3rd Floor") && buildingName.equals("SMED Building")) {
                    room!!.setAdapter(SMED_third_a)
                }else if(wingName.equals("Chemistry Department") && floorName.equals("3rd Floor") && buildingName.equals("SMED Building")) {
                    room!!.setAdapter(SMED_third_b)
                }else if(wingName.equals("Mathematics Department") && floorName.equals("2nd Floor") && buildingName.equals("SMED Building")) {
                    room!!.setAdapter(SMED_second_a)
                }else if(wingName.equals("Biology Department") && floorName.equals("2nd Floor") && buildingName.equals("SMED Building")) {
                    room!!.setAdapter(SMED_second_b)
                }else if(wingName.equals("Physics Department") && floorName.equals("1st Floor") && buildingName.equals("SMED Building")) {
                    room!!.setAdapter(SMED_first)
                }else if(wingName.equals("Mathematics Department") && floorName.equals("Basement") && buildingName.equals("SMED Building")) {
                    room!!.setAdapter(SMED_basement)
                }
                //BCT
                else if(wingName.equals("Main hallway") && floorName.equals("1st Floor") && buildingName.equals("Basketball Court")) {
                    room!!.setAdapter(BCT)
                }
                //LRC
                else if(wingName.equals("Main hallway") && floorName.equals("Basement") && buildingName.equals("Josef Baumgartner Building")) {
                    room!!.setAdapter(LRC_basement)
                } else if(wingName.equals("Main hallway") && floorName.equals("1st Floor") && buildingName.equals("Josef Baumgartner Building")) {
                    room!!.setAdapter(LRC_first)
                }
                //SAFAD
                else if(wingName.equals("Main hallway") && floorName.equals("2nd Floor") && buildingName.equals("SAFAD Building")) {
                    room!!.setAdapter(SAFAD_second)
                }else if(wingName.equals("Main hallway") && floorName.equals("1st Floor") && buildingName.equals("SAFAD Building")) {
                    room!!.setAdapter(SAFAD_first)
                }else if(wingName.equals("Main hallway") && floorName.equals("Basement 1") && buildingName.equals("SAFAD Building")) {
                    room!!.setAdapter(SAFAD_basement)
                }else if(wingName.equals("Main hallway") && floorName.equals("Basement 2") && buildingName.equals("SAFAD Building")) {
                    room!!.setAdapter(SAFAD_basement_b)
                }else if(wingName.equals("Main hallway") && floorName.equals("Basement 3") && buildingName.equals("SAFAD Building")) {
                    room!!.setAdapter(SAFAD_basement_c)
                }
                //chapel
                else if(wingName.equals("Main hallway") && floorName.equals("1st Floor") && buildingName.equals("Chapel")) {
                    room!!.setAdapter(chapel)

                }else{
                    room!!.setAdapter(no_rooms)
                }
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
                }else if(wingName.equals("Fourth Wing") && floorName.equals("Basement") && buildingName.equals("Lawrence Bunzel Building")){
                    room!!.setAdapter(bunzel_basement)
                }
                //endregion
                //region PE
                else if(wingName.equals("Main hallway") && floorName.equals("4th Floor") && buildingName.equals("Philip van Engelen Building")) {
                    room!!.setAdapter(PE_fourth)
                }else if(wingName.equals("Main hallway") && floorName.equals("3rd Floor") && buildingName.equals("Philip van Engelen Building")) {
                    room!!.setAdapter(PE_third)
                }else if(wingName.equals("Main hallway") && floorName.equals("2nd Floor") && buildingName.equals("Philip van Engelen Building")) {
                    room!!.setAdapter(PE_second)
                }else if(wingName.equals("Main hallway") && floorName.equals("1st Floor") && buildingName.equals("Philip van Engelen Building")) {
                    room!!.setAdapter(PE_first)
                }
                //endregion
                //SMED
                else if(wingName.equals("Mathematics Department ") && floorName.equals("3rd Floor") && buildingName.equals("SMED Building")) {
                    room!!.setAdapter(SMED_third_a)
                }else if(wingName.equals("Chemistry Department") && floorName.equals("3rd Floor") && buildingName.equals("SMED Building")) {
                    room!!.setAdapter(SMED_third_b)
                }else if(wingName.equals("Mathematics Department") && floorName.equals("2nd Floor") && buildingName.equals("SMED Building")) {
                    room!!.setAdapter(SMED_second_a)
                }else if(wingName.equals("Biology Department") && floorName.equals("2nd Floor") && buildingName.equals("SMED Building")) {
                    room!!.setAdapter(SMED_second_b)
                }else if(wingName.equals("Physics Department") && floorName.equals("1st Floor") && buildingName.equals("SMED Building")) {
                    room!!.setAdapter(SMED_first)
                }else if(wingName.equals("Mathematics Department") && floorName.equals("Basement") && buildingName.equals("SMED Building")) {
                    room!!.setAdapter(SMED_basement)
                }
                //BCT
                else if(wingName.equals("Main hallway") && floorName.equals("1st Floor") && buildingName.equals("Basketball Court")) {
                    room!!.setAdapter(BCT)
                }
                //LRC
                else if(wingName.equals("Main hallway") && floorName.equals("Basement") && buildingName.equals("Josef Baumgartner Building")) {
                    room!!.setAdapter(LRC_basement)
                } else if(wingName.equals("Main hallway") && floorName.equals("1st Floor") && buildingName.equals("Josef Baumgartner Building")) {
                    room!!.setAdapter(LRC_first)
                }
                //SAFAD
                else if(wingName.equals("Main hallway") && floorName.equals("2nd Floor") && buildingName.equals("SAFAD Building")) {
                    room!!.setAdapter(SAFAD_second)
                }else if(wingName.equals("Main hallway") && floorName.equals("1st Floor") && buildingName.equals("SAFAD Building")) {
                    room!!.setAdapter(SAFAD_first)
                }else if(wingName.equals("Main hallway") && floorName.equals("Basement 1") && buildingName.equals("SAFAD Building")) {
                    room!!.setAdapter(SAFAD_basement)
                }else if(wingName.equals("Main hallway") && floorName.equals("Basement 2") && buildingName.equals("SAFAD Building")) {
                    room!!.setAdapter(SAFAD_basement_b)
                }else if(wingName.equals("Main hallway") && floorName.equals("Basement 3") && buildingName.equals("SAFAD Building")) {
                    room!!.setAdapter(SAFAD_basement_c)
                    //chapel
                }else if(wingName.equals("Main hallway") && floorName.equals("1st Floor") && buildingName.equals("Chapel")) {
                        room!!.setAdapter(chapel)

                    }else{
                    room!!.setAdapter(no_rooms)
                }
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
