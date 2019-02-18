package usc.dcis.teacherattendancesystem

import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import org.jetbrains.annotations.Nullable
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.RadioGroup
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.menu_fragment_lbb.view.*
import android.widget.Toast
import kotlinx.android.synthetic.main.menu_fragment_lbb.*

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


        radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            val id = lbb_floors.checkedRadioButtonId
            val radioButton = myView.findViewById<RadioButton>(id)

            if(radioButton.text.toString().equals("1st Floor")){
                lbb_wings.visibility = View.VISIBLE
                lbb_wing1.text = "Wing 1"
                lbb_wing2.text = "Wing 2"
                lbb_wing3.text = "Wing 3"
                lbb_wing4.text = "Wing 4"
                lbb_wing5.visibility = View.GONE

                wingSelect(lbb_wing1.text.toString(), lbb_wing2.text.toString(), lbb_wing3.text.toString(), lbb_wing4.text.toString(), "", radioButton)

            }else if(radioButton.text.toString().equals("2nd Floor")){
                lbb_wings.visibility = View.VISIBLE
                lbb_wing1.text = "Wing 256"
                lbb_wing2.text = "Wing 261"
                lbb_wing3.text = "Wing 273"
                lbb_wing4.text = "Wing 283"
                lbb_wing5.visibility = View.GONE

                wingSelect(lbb_wing1.text.toString(), lbb_wing2.text.toString(), lbb_wing3.text.toString(), lbb_wing4.text.toString(), "", radioButton )


            }else if(radioButton.text.toString().equals("3rd Floor")){
                lbb_wings.visibility = View.VISIBLE
                lbb_wing5.visibility = View.VISIBLE
                lbb_wing1.text = "Wing 320"
                lbb_wing2.text = "Wing 330"
                lbb_wing3.text = "Wing 340"
                lbb_wing4.text = "Wing 350"
                lbb_wing5.text = "Main Hallway 3"

                wingSelect(lbb_wing1.text.toString(), lbb_wing2.text.toString(), lbb_wing3.text.toString(), lbb_wing4.text.toString(), lbb_wing5.text.toString(), radioButton)
            }else if(radioButton.text.toString().equals("4th Floor")){
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

        wingGroup.setOnCheckedChangeListener { wingGroup, checkedId ->
            val wingID = lbb_wings.checkedRadioButtonId
            val wingBtn = myView.findViewById<RadioButton>(wingID)

            if(wingBtn.text.equals(firstWing)){

                if(radio.text.toString().equals("1st Floor")){
                    wingBtn.text = "1st floor"
                    lbbScrollView.visibility = View.VISIBLE
                }else if(radio.text.toString().equals("2nd Floor")){
                    wingBtn.text = "2nd floor"
                }else if(radio.text.toString().equals("3rd Floor")){
                    wingBtn.text = "3rd floor"
                }else if(radio.text.toString().equals("4th Floor")){
                    wingBtn.text = "4th  floor"
                }
            }else if(wingBtn.text.equals(secondWing)){

                if(radio.text.toString().equals("1st Floor")){
                    wingBtn.text = "1st floor desu"
                }else if(radio.text.toString().equals("2nd Floor")){
                    wingBtn.text = "2nd floor a"
                }else if(radio.text.toString().equals("3rd Floor")){
                    wingBtn.text = "3rd floor b"
                }else if(radio.text.toString().equals("4th Floor")){
                    wingBtn.text = "4th  floor c"
                }
            }else if(wingBtn.text.equals(thirdWing)){

                if(radio.text.toString().equals("1st Floor")){
                    wingBtn.text = "1st floor 3rd w"
                }else if(radio.text.toString().equals("2nd Floor")){
                    wingBtn.text = "2nd floor 3rd w"
                }else if(radio.text.toString().equals("3rd Floor")){
                    wingBtn.text = "3rd floor 3rd w"
                }else if(radio.text.toString().equals("4th Floor")){
                    wingBtn.text = "4th  floor 3rd w"
                }
            }else if(wingBtn.text.equals(fourthWing)){

                if(radio.text.toString().equals("1st Floor")){
                    wingBtn.text = "1st floor 4th w"
                }else if(radio.text.toString().equals("2nd Floor")){
                    wingBtn.text = "2nd floor 4th w"
                }else if(radio.text.toString().equals("3rd Floor")){
                    wingBtn.text = "3rd floor 4th w"
                }else if(radio.text.toString().equals("4th Floor")){
                    wingBtn.text = "4th  floor 4th w"
                }
            }else if(wingBtn.text.equals(fifthWing)){

                if(radio.text.toString().equals("3rd Floor")){
                    wingBtn.text = "1st floor 5th w"
                }else if(radio.text.toString().equals("4th Floor")) {
                    wingBtn.text = "2nd floor 5th w"
                }
            }

        }
    }
}