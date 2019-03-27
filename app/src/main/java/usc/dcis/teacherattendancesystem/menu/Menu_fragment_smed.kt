package usc.dcis.teacherattendancesystem.menu

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.menu_fragment_smed.*
import usc.dcis.teacherattendancesystem.R
import usc.dcis.teacherattendancesystem.scheduleDatabase.ScheduleDatabase

class Menu_fragment_smed : Fragment() {
    lateinit var radioGroup: RadioGroup
    lateinit var wingGroup: RadioGroup
    lateinit var myView: View
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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       myView = inflater.inflate(R.layout.menu_fragment_smed, null)
        return  myView
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        radioGroup = myView.findViewById(R.id.smed_floors)

        //insertRooms()

        radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            val id = smed_floors.checkedRadioButtonId
            val radioButton = myView.findViewById<RadioButton>(id)


            //lbb_hiddenwing.setChecked(true)

            if(radioButton.text.toString().equals("Basement")||radioButton.text.toString().equals("1st Floor")
                ||radioButton.text.toString().equals("2nd Floor") || radioButton.text.toString().equals("3rd Floor")){
                smed_wings.visibility = View.VISIBLE
                chooseWing_2.visibility = View.VISIBLE
                smed_wing3.visibility = View.VISIBLE
                //smed_wing1.text = "Wing 1"
                //smed_wing2.text = "Wing 2"
                //smed_wing3.text = "Wing 3"
                //smed_wing4.visibility = View.INVISIBLE
                //lbb_wing5.visibility = View.INVISIBLE

                wingSelect(smed_wing1.text.toString(), smed_wing2.text.toString(), smed_wing3.text.toString(), smed_wing4.text.toString(), "", radioButton)
            }

        }

    }
    fun wingSelect(Mathematics: String, Biology: String, Chemistry: String, Physics: String, fifthWing:String, radio: RadioButton){
        wingGroup = myView.findViewById(R.id.smed_wings)
        val db = ScheduleDatabase.getInstance(context!!)
        val scheduleListTest = db.scheduleDAO
        val sdf = java.text.SimpleDateFormat("h:m a")

        wingGroup.setOnCheckedChangeListener { wingGroup, checkedId ->
            val wingID = smed_wings.checkedRadioButtonId
            val wingBtn = myView.findViewById<RadioButton>(wingID)

            if(wingBtn.text.equals(Mathematics)){

                if(radio.text.toString().equals("2nd Floor")){
                    smedScrollView.visibility = View.VISIBLE
                    smedRoomFour.visibility = View.VISIBLE
                    smedRoomFive.visibility = View.VISIBLE
                    smedRoomSix.visibility = View.VISIBLE
                    smedRoomOne.text = SMEDBuilding_second[0][0]
                    smedRoomTwo.text = SMEDBuilding_second[0][1]
                    smedRoomThree.text = SMEDBuilding_second[0][2]
                    smedRoomFour.text = SMEDBuilding_second[0][3]
                    smedRoomFive.text = SMEDBuilding_second[0][4]
                    smedRoomSix.text = SMEDBuilding_second[0][5]
                    smedRoomSeven.visibility = View.INVISIBLE
                    smedRoomEight.visibility = View.INVISIBLE
                    smedRoomNine.visibility = View.INVISIBLE

                }else if(radio.text.toString().equals("3rd Floor")) {
                    smedScrollView.visibility = View.VISIBLE
                    smedRoomOne.text = SMEDBuilding_third[0][0]
                    smedRoomTwo.text = SMEDBuilding_third[0][1]
                    smedRoomThree.text = SMEDBuilding_third[0][2]
                    smedRoomFour.visibility = View.INVISIBLE
                    smedRoomFive.visibility = View.INVISIBLE
                    smedRoomSix.visibility = View.INVISIBLE
                    smedRoomSeven.visibility = View.INVISIBLE
                    smedRoomEight.visibility = View.INVISIBLE
                    smedRoomNine.visibility = View.INVISIBLE
                }else if(radio.text.toString().equals("Basement")) {
                    smedScrollView.visibility = View.VISIBLE
                    smedRoomOne.text = SMEDbuilding_basement[0]
                    smedRoomTwo.text = SMEDbuilding_basement[1]
                    smedRoomThree.text = SMEDbuilding_basement[2]
                    smedRoomFour.visibility = View.INVISIBLE
                    smedRoomFive.visibility = View.INVISIBLE
                    smedRoomSix.visibility = View.INVISIBLE
                    smedRoomSeven.visibility = View.INVISIBLE
                    smedRoomEight.visibility = View.INVISIBLE
                    smedRoomNine.visibility = View.INVISIBLE
                }else{
                    smedScrollView.visibility = View.INVISIBLE
                }
            }else if(wingBtn.text.equals(Biology)){

                if(radio.text.toString().equals("2nd Floor")){
                    smedScrollView.visibility = View.VISIBLE
                    smedRoomFour.visibility = View.VISIBLE
                    smedRoomFive.visibility = View.VISIBLE
                    smedRoomSix.visibility = View.VISIBLE
                    smedRoomSeven.visibility = View.VISIBLE
                    smedRoomOne.text = SMEDBuilding_second[1][0]
                    smedRoomTwo.text = SMEDBuilding_second[1][1]
                    smedRoomThree.text = SMEDBuilding_second[1][2]
                    smedRoomFour.text = SMEDBuilding_second[1][3]
                    smedRoomFive.text = SMEDBuilding_second[1][4]
                    smedRoomSix.text = SMEDBuilding_second[1][5]
                    smedRoomSeven.text = SMEDBuilding_second[1][6]
                    smedRoomEight.visibility = View.INVISIBLE
                    smedRoomNine.visibility = View.INVISIBLE

                }else{
                    smedScrollView.visibility = View.INVISIBLE
                }
            }else if(wingBtn.text.equals(Chemistry)){

                if(radio.text.toString().equals("1st Floor")){
                    smedScrollView.visibility = View.VISIBLE
                    smedRoomFour.visibility = View.VISIBLE
                    smedRoomFive.visibility = View.VISIBLE
                    smedRoomSix.visibility = View.VISIBLE
                    smedRoomSeven.visibility = View.VISIBLE
                    smedRoomEight.visibility = View.VISIBLE
                    smedRoomNine.visibility = View.VISIBLE
                    smedRoomOne.text = SMEDBuilding_third[1][0]
                    smedRoomTwo.text = SMEDBuilding_third[1][1]
                    smedRoomThree.text = SMEDBuilding_third[1][2]
                    smedRoomFour.text = SMEDBuilding_third[1][3]
                    smedRoomFive.text = SMEDBuilding_third[1][4]
                    smedRoomSix.text = SMEDBuilding_third[1][5]
                    smedRoomSeven.text = SMEDBuilding_third[1][6]
                    smedRoomEight.text = SMEDBuilding_third[1][7]
                    smedRoomNine.text = SMEDBuilding_third[1][8]
                }else{
                    smedScrollView.visibility = View.INVISIBLE
                }
            }else if(wingBtn.text.equals(Physics)){

                if(radio.text.toString().equals("1st Floor")){
                    smedScrollView.visibility = View.VISIBLE
                    smedRoomFour.visibility = View.VISIBLE
                    smedRoomFive.visibility = View.VISIBLE
                    smedRoomOne.text = SMEDbuilding_first[0]
                    smedRoomTwo.text = SMEDbuilding_first[1]
                    smedRoomThree.text = SMEDbuilding_first[2]
                    smedRoomFour.text = SMEDbuilding_first[3]
                    smedRoomFive.text = SMEDbuilding_first[4]
                    smedRoomSix.visibility = View.INVISIBLE
                    smedRoomSeven.visibility = View.INVISIBLE
                    smedRoomEight.visibility = View.INVISIBLE
                    smedRoomNine.visibility = View.INVISIBLE
                }else{
                smedScrollView.visibility = View.INVISIBLE
                }
            }else {
                smedScrollView.visibility = View.INVISIBLE
            }

           /* roomSelect(lbbRoomOne.text.toString(), lbbRoomTwo.text.toString(), lbbRoomThree.text.toString(),
                lbbRoomFour.text.toString(), lbbRoomFive.text.toString())*/
        }

    }

}