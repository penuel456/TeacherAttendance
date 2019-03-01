package usc.dcis.teacherattendancesystem

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.menu_fragment_safad.*

class Menu_fragment_safad : Fragment() {

    lateinit var radioGroup: RadioGroup
    lateinit var wingGroup: RadioGroup
    lateinit var myView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.menu_fragment_safad, null)
        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        radioGroup = myView.findViewById(R.id.safad_floors)


        radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            val id = safad_floors.checkedRadioButtonId
            val radioButton = myView.findViewById<RadioButton>(id)

            safad_hiddenwing.setChecked(true)

            if(radioButton.text.toString().equals("1st Floor")){
                safad_wings.visibility = View.VISIBLE
                chooseAWing.visibility = View.VISIBLE
                safad_wing1.text = "Wing 1-1"
                safad_wing2.text = "Wing 1-2"
                safad_wing3.text = "Wing 1-3"



                wingSelect(safad_wing1.text.toString(), safad_wing2.text.toString(), safad_wing3.text.toString(), radioButton)



            }else if(radioButton.text.toString().equals("2nd Floor")){
                safad_wings.visibility = View.VISIBLE
                chooseAWing.visibility = View.VISIBLE
                safad_wing1.text = "Wing 2-1"
                safad_wing2.text = "Wing 2-2"
                safad_wing3.text = "Wing 2-3"


                wingSelect(safad_wing1.text.toString(), safad_wing2.text.toString(), safad_wing3.text.toString(), radioButton )




            }else if(radioButton.text.toString().equals("3rd Floor")){
                chooseAWing.visibility = View.VISIBLE
                safad_wings.visibility = View.VISIBLE
                safad_wing1.text = "Wing 3-1"
                safad_wing2.text = "Wing 3-2"
                safad_wing3.text = "Wing 3-3"

                wingSelect(safad_wing1.text.toString(), safad_wing2.text.toString(), safad_wing3.text.toString(), radioButton)
            }
        }

    }


    fun wingSelect(firstWing: String, secondWing: String, thirdWing: String, radio: RadioButton){
        wingGroup = myView.findViewById(R.id.safad_wings)

        wingGroup.setOnCheckedChangeListener { wingGroup, checkedId ->
            val wingID = safad_wings.checkedRadioButtonId
            val wingBtn = myView.findViewById<RadioButton>(wingID)

            if(wingBtn.text.equals(firstWing)){

                if(radio.text.toString().equals("1st Floor")){
                    safadScrollView.visibility = View.VISIBLE
                    safadRoomFour.visibility = View.VISIBLE
                    safadRoomFive.visibility = View.VISIBLE
                    safadRoomOne.text = "1-1-1"
                    safadRoomTwo.text = "1-1-2"
                    safadRoomThree.text = "1-1-3"
                    safadRoomFour.text = "1-1-4"
                    safadRoomFive.text = "1-1-5"
                }else if(radio.text.toString().equals("2nd Floor")){
                    safadScrollView.visibility = View.VISIBLE
                    safadRoomFour.visibility = View.VISIBLE
                    safadRoomFive.visibility = View.VISIBLE
                    safadRoomOne.text = "2-1-1"
                    safadRoomTwo.text = "2-1-2"
                    safadRoomThree.text = "2-1-3"
                    safadRoomFour.text = "2-1-4"
                    safadRoomFive.text = "2-1-5"
                }else if(radio.text.toString().equals("3rd Floor")){
                    safadScrollView.visibility = View.VISIBLE
                    safadRoomOne.text = "3-1-1"
                    safadRoomTwo.text = "3-1-2"
                    safadRoomThree.text = "3-1-3"
                    safadRoomFour.visibility = View.GONE
                    safadRoomFive.visibility = View.GONE
                }
            }else if(wingBtn.text.equals(secondWing)){

                if(radio.text.toString().equals("1st Floor")){
                    safadScrollView.visibility = View.VISIBLE
                    safadRoomFour.visibility = View.VISIBLE
                    safadRoomFive.visibility = View.VISIBLE
                    safadRoomOne.text = "1-2-1"
                    safadRoomTwo.text = "1-2-2"
                    safadRoomThree.text = "1-2-3"
                    safadRoomFour.text = "1-2-4"
                    safadRoomFive.text = "1-2-5"
                }else if(radio.text.toString().equals("2nd Floor")){
                    safadScrollView.visibility = View.VISIBLE
                    safadRoomFour.visibility = View.VISIBLE
                    safadRoomFive.visibility = View.VISIBLE
                    safadRoomOne.text = "2-2-1"
                    safadRoomTwo.text = "2-2-2"
                    safadRoomThree.text = "2-2-3"
                    safadRoomFour.text = "2-2-4"
                    safadRoomFive.text = "2-2-5"
                }else if(radio.text.toString().equals("3rd Floor")){
                    safadScrollView.visibility = View.VISIBLE
                    safadRoomOne.text = "3-2-1"
                    safadRoomTwo.text = "3-2-2"
                    safadRoomThree.text = "3-2-3"
                    safadRoomFour.visibility = View.GONE
                    safadRoomFive.visibility = View.GONE
                }
            }else if(wingBtn.text.equals(thirdWing)){

                if(radio.text.toString().equals("1st Floor")){
                    safadScrollView.visibility = View.VISIBLE
                    safadRoomFour.visibility = View.VISIBLE
                    safadRoomFive.visibility = View.VISIBLE
                    safadRoomOne.text = "1-3-1"
                    safadRoomTwo.text = "1-3-2"
                    safadRoomThree.text = "1-3-3"
                    safadRoomFour.text = "1-3-4"
                    safadRoomFive.text = "1-3-5"
                }else if(radio.text.toString().equals("2nd Floor")){
                    safadScrollView.visibility = View.VISIBLE
                    safadRoomFour.visibility = View.VISIBLE
                    safadRoomFive.visibility = View.VISIBLE
                    safadRoomOne.text = "2-3-1"
                    safadRoomTwo.text = "2-3-2"
                    safadRoomThree.text = "2-3-3"
                    safadRoomFour.text = "2-3-4"
                    safadRoomFive.text = "2-3-5"
                }else if(radio.text.toString().equals("3rd Floor")){
                    safadScrollView.visibility = View.VISIBLE
                    safadRoomOne.text = "3-3-1"
                    safadRoomTwo.text = "3-3-2"
                    safadRoomThree.text = "3-3-3"
                    safadRoomFour.visibility = View.GONE
                    safadRoomFive.visibility = View.GONE
                }
            }

        }
    }
}