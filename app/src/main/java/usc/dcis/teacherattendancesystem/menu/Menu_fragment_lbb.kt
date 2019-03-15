package usc.dcis.teacherattendancesystem.menu

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.menu_fragment_lbb.*
import usc.dcis.teacherattendancesystem.R
import usc.dcis.teacherattendancesystem.roomSchedule
import usc.dcis.teacherattendancesystem.scheduleDatabase.ScheduleDatabase
import usc.dcis.teacherattendancesystem.scheduleDatabase.*

class Menu_fragment_lbb : Fragment() {

    lateinit var radioGroup: RadioGroup
    lateinit var wingGroup: RadioGroup
   lateinit var myView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.menu_fragment_lbb, null)
        return myView

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        radioGroup = myView.findViewById(R.id.lbb_floors)

        //insertRooms()

        radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            val id = lbb_floors.checkedRadioButtonId
            val radioButton = myView.findViewById<RadioButton>(id)
            lbb_hiddenwing.setChecked(true)

            if(radioButton.text.toString().equals("1st Floor")){
                lbb_wings.visibility = View.VISIBLE
                chooseAWing.visibility = View.VISIBLE
                lbb_wing1.text = "Wing 1"
                lbb_wing2.text = "Wing 2"
                lbb_wing3.text = "Wing 3"
                lbb_wing4.text = "Wing 4"
                lbb_wing5.visibility = View.GONE

                wingSelect(lbb_wing1.text.toString(), lbb_wing2.text.toString(), lbb_wing3.text.toString(), lbb_wing4.text.toString(), "", radioButton)


            }else if(radioButton.text.toString().equals("2nd Floor")){
                lbb_wings.visibility = View.VISIBLE
                chooseAWing.visibility = View.VISIBLE
                lbb_wing1.text = "Wing 256"
                lbb_wing2.text = "Wing 261"
                lbb_wing3.text = "Wing 273"
                lbb_wing4.text = "Wing 283"
                lbb_wing5.visibility = View.GONE


                wingSelect(lbb_wing1.text.toString(), lbb_wing2.text.toString(), lbb_wing3.text.toString(), lbb_wing4.text.toString(), "", radioButton )




            }else if(radioButton.text.toString().equals("3rd Floor")){
                chooseAWing.visibility = View.VISIBLE
                lbb_wings.visibility = View.VISIBLE
                lbb_wing5.visibility = View.VISIBLE
                lbb_wing1.text = "Wing 320"
                lbb_wing2.text = "Wing 330"
                lbb_wing3.text = "Wing 340"
                lbb_wing4.text = "Wing 350"
                lbb_wing5.text = "Main Hallway 3"

                wingSelect(lbb_wing1.text.toString(), lbb_wing2.text.toString(), lbb_wing3.text.toString(), lbb_wing4.text.toString(), lbb_wing5.text.toString(), radioButton)



            }else if(radioButton.text.toString().equals("4th Floor")){
                chooseAWing.visibility = View.VISIBLE
                lbb_wings.visibility = View.VISIBLE
                lbb_wing5.visibility = View.VISIBLE
                lbb_wing1.text = "Wing 442"
                lbb_wing2.text = "Wing 445"
                lbb_wing3.text = "Wing 480"
                lbb_wing4.text = "Wing 460"
                lbb_wing5.text = "Main Hallway 4"


                wingSelect(lbb_wing1.text.toString(), lbb_wing2.text.toString(), lbb_wing3.text.toString(), lbb_wing4.text.toString(), lbb_wing5.text.toString(), radioButton)



            }
        }

    }

    fun wingSelect(firstWing: String, secondWing: String, thirdWing: String, fourthWing: String, fifthWing:String, radio: RadioButton){
        wingGroup = myView.findViewById(R.id.lbb_wings)
        val db = ScheduleDatabase.getInstance(context!!)
        val scheduleListTest = db.scheduleDAO
        val sdf = java.text.SimpleDateFormat("h:m a")

        wingGroup.setOnCheckedChangeListener { wingGroup, checkedId ->
            val wingID = lbb_wings.checkedRadioButtonId
            val wingBtn = myView.findViewById<RadioButton>(wingID)

            if(wingBtn.text.equals(firstWing)){

                if(radio.text.toString().equals("1st Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomFive.visibility = View.VISIBLE
                    lbbRoomOne.text = scheduleListTest.getAllRoomAssignmentsByRoomNumber("LB110TC")[0].roomNumber
                    lbbRoomTwo.text = scheduleListTest.getAllRoomAssignmentsByRoomNumber("LB111TC")[0].roomNumber
                    lbbRoomThree.text = scheduleListTest.getAllRoomAssignmentsByRoomNumber("LB112TC")[0].roomNumber
                    lbbRoomFour.text = scheduleListTest.getAllRoomAssignmentsByRoomNumber("LB113TC")[0].roomNumber
                    lbbRoomFive.text = scheduleListTest.getAllRoomAssignmentsByRoomNumber("LB114TC")[0].roomNumber

                }else if(radio.text.toString().equals("2nd Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomFive.visibility = View.VISIBLE
                    lbbRoomOne.text = "LB210TC"
                    lbbRoomTwo.text = "LB211TC"
                    lbbRoomThree.text = "LB212TC"
                    lbbRoomFour.text = "LB213TC"
                    lbbRoomFive.text = "LB214TC"


                }else if(radio.text.toString().equals("3rd Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomThree.visibility = View.VISIBLE
                    lbbRoomOne.text = "LB321TC"
                    lbbRoomTwo.text = "LB322TC"
                    lbbRoomThree.text = "LB323TC"
                    lbbRoomFour.visibility = View.GONE
                    lbbRoomFive.visibility = View.GONE
                }else if(radio.text.toString().equals("4th Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomOne.text = "LB442TC"
                    lbbRoomTwo.text = "LB443TC"
                    lbbRoomThree.visibility = View.GONE
                    lbbRoomFour.visibility = View.GONE
                    lbbRoomFive.visibility = View.GONE
                }
            }else if(wingBtn.text.equals(secondWing)){

                if(radio.text.toString().equals("1st Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomFive.visibility = View.VISIBLE
                    lbbRoomOne.text = scheduleListTest.getAllRoomAssignmentsByRoomNumber("LB120TC")[0].roomNumber
                    lbbRoomTwo.text = "LB121TC"
                    lbbRoomThree.text = "LB122TC"
                    lbbRoomFour.text = "LB123TC"
                    lbbRoomFive.text = "LB124TC"




                }else if(radio.text.toString().equals("2nd Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomFive.visibility = View.VISIBLE
                    lbbRoomOne.text = "LB220TC"
                    lbbRoomTwo.text = "LB221TC"
                    lbbRoomThree.text = "LB222TC"
                    lbbRoomFour.text = "LB223TC"
                    lbbRoomFive.text = "LB224TC"
                }else if(radio.text.toString().equals("3rd Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomThree.visibility = View.VISIBLE
                    lbbRoomOne.text = "LB331TC"
                    lbbRoomTwo.text = "LB332TC"
                    lbbRoomThree.text = "LB333TC"
                    lbbRoomFour.visibility = View.GONE
                    lbbRoomFive.visibility = View.GONE
                }else if(radio.text.toString().equals("4th Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomOne.text = "LB445TC"
                    lbbRoomTwo.text = "LB446TC"
                    lbbRoomThree.text = "LB447TC"
                    lbbRoomFour.text = "LB448TC"
                    lbbRoomFive.text = "LB449TC"
                    lbbRoomThree.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomFive.visibility = View.VISIBLE
                }
            }else if(wingBtn.text.equals(thirdWing)){

                if(radio.text.toString().equals("1st Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomFive.visibility = View.VISIBLE
                    lbbRoomOne.text = "LB130TC"
                    lbbRoomTwo.text = "LB131TC"
                    lbbRoomThree.text = "LB132TC"
                    lbbRoomFour.text = "LB133TC"
                    lbbRoomFive.text = "LB134TC"
                }else if(radio.text.toString().equals("2nd Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomFive.visibility = View.VISIBLE
                    lbbRoomOne.text = "LB230TC"
                    lbbRoomTwo.text = "LB231TC"
                    lbbRoomThree.text = "LB232TC"
                    lbbRoomFour.text = "LB233TC"
                    lbbRoomFive.text = "LB234TC"
                }else if(radio.text.toString().equals("3rd Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomThree.visibility = View.VISIBLE
                    lbbRoomOne.text = "LB341TC"
                    lbbRoomTwo.text = "LB342TC"
                    lbbRoomThree.text = "LB343TC"
                    lbbRoomFour.visibility = View.GONE
                    lbbRoomFive.visibility = View.GONE
                }else if(radio.text.toString().equals("4th Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomOne.text = "LB470TC"
                    lbbRoomTwo.text = "LB469TC"
                    lbbRoomThree.text = "LB468TC"
                    lbbRoomFour.text = "LB467TC"
                    lbbRoomFive.text = "LB466TC"
                    lbbRoomThree.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomFive.visibility = View.VISIBLE
                }
            }else if(wingBtn.text.equals(fourthWing)){

                if(radio.text.toString().equals("1st Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomFive.visibility = View.VISIBLE
                    lbbRoomOne.text = "LB140TC"
                    lbbRoomTwo.text = "LB141TC"
                    lbbRoomThree.text = "LB142TC"
                    lbbRoomFour.text = "LB143TC"
                    lbbRoomFive.text = "LB144TC"
                }else if(radio.text.toString().equals("2nd Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomFive.visibility = View.VISIBLE
                    lbbRoomOne.text = "LB240TC"
                    lbbRoomTwo.text = "LB241TC"
                    lbbRoomThree.text = "LB242TC"
                    lbbRoomFour.text = "LB243TC"
                    lbbRoomFive.text = "LB244TC"
                }else if(radio.text.toString().equals("3rd Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomThree.visibility = View.VISIBLE
                    lbbRoomOne.text = "LB341TC"
                    lbbRoomTwo.text = "LB342TC"
                    lbbRoomThree.text = "LB343TC"
                    lbbRoomFour.visibility = View.GONE
                    lbbRoomFive.visibility = View.GONE
                }else if(radio.text.toString().equals("4th Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomOne.text = "LB485TC"
                    lbbRoomTwo.text = "LB486TC"
                    lbbRoomThree.text = "LB467TC"
                    lbbRoomFour.text = "LB488TC"
                    lbbRoomFive.text = "LB489TC"
                    lbbRoomThree.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomFive.visibility = View.VISIBLE
                }
            }else if(wingBtn.text.equals(fifthWing)){

                if(radio.text.toString().equals("3rd Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomThree.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomOne.text = "LB306TC"
                    lbbRoomTwo.text = "LB305TC"
                    lbbRoomThree.text = "LB304TC"

                    lbbRoomFive.visibility = View.GONE
                }else if(radio.text.toString().equals("4th Floor")) {
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomOne.text = "LB401TC"
                    lbbRoomTwo.text = "LB402TC"
                    lbbRoomThree.text = "LB403TC"
                    lbbRoomFour.text = "LB405TC"
                    lbbRoomFive.text = "LB404TC"
                    lbbRoomThree.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomFive.visibility = View.VISIBLE
                }
            }

            roomSelect(lbbRoomOne.text.toString(), lbbRoomTwo.text.toString(), lbbRoomThree.text.toString(),
                lbbRoomFour.text.toString(), lbbRoomFive.text.toString())
        }

    }



    fun roomSelect(roomOne: String, roomTwo: String, roomThree: String, roomFour: String, roomFive: String)
    {
        val db = ScheduleDatabase.getInstance(context!!)
        val scheduleListTest = db.scheduleDAO
        val sdf = java.text.SimpleDateFormat("h:m a")

        lbbRoomOne.setOnClickListener {
            if(roomOne.equals(scheduleListTest.getAllRoomAssignmentsByRoomNumber("LB110TC")[0].roomNumber)){

                val activity = Intent(getActivity(), roomSchedule::class.java)
                val lb110list = scheduleListTest.getAllRoomAssignmentsByRoomNumber("LB110TC")
                activity.putExtra("RoomTxt", lb110list[0].roomNumber.toString())
                startActivity(activity)
            }

        }

        lbbRoomTwo.setOnClickListener {
            if(roomTwo.equals("LB111TC")) {
                val activity = Intent(getActivity(), roomSchedule::class.java)
                activity.putExtra("RoomTxt", roomTwo)
                startActivity(activity)
            }
            val activity = Intent(getActivity(), roomSchedule::class.java)
            activity.putExtra("RoomTxt", roomTwo)
            startActivity(activity)
        }

        lbbRoomThree.setOnClickListener {
            val activity = Intent(getActivity(), roomSchedule::class.java)
            activity.putExtra("RoomTxt",roomThree )
            startActivity(activity)
        }
        lbbRoomFour.setOnClickListener {
            val activity = Intent(getActivity(), roomSchedule::class.java)
            activity.putExtra("RoomTxt",roomFour )
            startActivity(activity)
        }
        lbbRoomFive.setOnClickListener {
            val activity = Intent(getActivity(), roomSchedule::class.java)
            activity.putExtra("RoomTxt",roomFive )
            startActivity(activity)
        }
    }

    fun insertRooms(){
        val db = ScheduleDatabase.getInstance(context!!)
        val scheduleListTest = db.scheduleDAO
        val sdf = java.text.SimpleDateFormat("h:m a")

        val rooms1stFloor = arrayListOf("LB110TC", "LB111TC", "LB112TC", "LB113TC", "LB114TC", "LB120TC", "LB121TC", "LB122TC")

        //110
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 1, rooms1stFloor[0], sdf.parse("1:30 PM"),
            sdf.parse("3:30 PM"), "T"))
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 1, rooms1stFloor[0], sdf.parse("1:30 PM"),
            sdf.parse("4:30 PM"), "TH"))
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 1, rooms1stFloor[0], sdf.parse("7:30 AM"),
            sdf.parse("10:30 AM"), "W"))

        //111
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 1, rooms1stFloor[1], sdf.parse("8:30 AM"),
            sdf.parse("10:30 AM"), "M"))
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 1, rooms1stFloor[1], sdf.parse("7:30 AM"),
            sdf.parse("10:30 AM"), "F"))

        //112
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 2, rooms1stFloor[2], sdf.parse("8:30 AM"),
            sdf.parse("10:30 AM"), "T"))
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 2, rooms1stFloor[2], sdf.parse("7:30 AM"),
            sdf.parse("10:30 AM"), "TH"))

        //113
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 2, rooms1stFloor[3], sdf.parse("3:30 PM"),
            sdf.parse("6:00 PM"), "M"))
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 2, rooms1stFloor[3], sdf.parse("4:30 PM"),
            sdf.parse("6:00 PM"), "W"))
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 2, rooms1stFloor[3], sdf.parse("1:30 PM"),
            sdf.parse("4:30 PM"), "F"))

        //114
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 3, rooms1stFloor[4], sdf.parse("6:30 AM"),
            sdf.parse("11:30 AM"), "T"))
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 3, rooms1stFloor[4], sdf.parse("8:00 AM"),
            sdf.parse("11:30 AM"), "TH"))
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 3, rooms1stFloor[4], sdf.parse("7:30 AM"),
            sdf.parse("10:30 AM"), "F"))

        //120
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 3, rooms1stFloor[5], sdf.parse("10:30 AM"),
            sdf.parse("11:30 AM"), "M"))
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 3, rooms1stFloor[5], sdf.parse("11:00 AM"),
            sdf.parse("11:30 AM"), "T"))
        scheduleListTest.insertRoomAssignment(RoomAssignment(0, 3, rooms1stFloor[5], sdf.parse("10:30 AM"),
            sdf.parse("10:30 AM"), "W"))

    }


}