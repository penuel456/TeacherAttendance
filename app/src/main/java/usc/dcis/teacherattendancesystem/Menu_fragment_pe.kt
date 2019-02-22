package usc.dcis.teacherattendancesystem

import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.menu_fragment_pe.*

class Menu_fragment_pe : Fragment() {

    lateinit var radioGroup: RadioGroup
    lateinit var wingGroup: RadioGroup
    lateinit var myView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.menu_fragment_pe, null)
        return myView

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        radioGroup = myView.findViewById(R.id.pe_floors)


        radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            val id = pe_floors.checkedRadioButtonId
            val radioButton = myView.findViewById<RadioButton>(id)

            pe_hiddenwing.setChecked(true)

            if(radioButton.text.toString().equals("1st Floor")){
                pe_wings.visibility = View.VISIBLE
                pe_wing1.text = "Main Hallway"


                wingSelect(pe_wing1.text.toString(), radioButton)



            }else if(radioButton.text.toString().equals("2nd Floor")){
                pe_wings.visibility = View.VISIBLE
                pe_wing1.text = "Main Hallway"


                wingSelect(pe_wing1.text.toString(), radioButton)




            }else if(radioButton.text.toString().equals("3rd Floor")){
                pe_wings.visibility = View.VISIBLE
                pe_wing1.text = "Main Hallway"


                wingSelect(pe_wing1.text.toString(), radioButton)



            }else if(radioButton.text.toString().equals("4th Floor")){
                pe_wings.visibility = View.VISIBLE
                pe_wing1.text = "Main Hallway"


                wingSelect(pe_wing1.text.toString(), radioButton)



            }
        }

    }

    fun wingSelect(firstWing: String, radio: RadioButton) {
        wingGroup = myView.findViewById(R.id.pe_wings)

        wingGroup.setOnCheckedChangeListener { wingGroup, checkedId ->
            val wingID = pe_wings.checkedRadioButtonId
            val wingBtn = myView.findViewById<RadioButton>(wingID)

            if(wingBtn.text.equals(firstWing)){

                if(radio.text.toString().equals("1st Floor")){
                    peScrollView.visibility = View.VISIBLE
                    peRoomFour.visibility = View.VISIBLE
                    peRoomFive.visibility = View.VISIBLE
                    peRoomOne.text = "PE11"
                    peRoomTwo.text = "PE12"
                    peRoomThree.text = "PE13"
                    peRoomFour.text = "PE14"
                    peRoomFive.text = "PE15"
                }else if(radio.text.toString().equals("2nd Floor")){
                    peScrollView.visibility = View.VISIBLE
                    peRoomFour.visibility = View.VISIBLE
                    peRoomFive.visibility = View.VISIBLE
                    peRoomOne.text = "PE21"
                    peRoomTwo.text = "PE22"
                    peRoomThree.text = "PE23"
                    peRoomFour.text = "PE24"
                    peRoomFive.text = "PE25"
                }else if(radio.text.toString().equals("3rd Floor")){
                    peScrollView.visibility = View.VISIBLE
                    peRoomThree.visibility = View.VISIBLE
                    peRoomOne.text = "PE31"
                    peRoomTwo.text = "PE32"
                    peRoomThree.text = "PE33"
                    peRoomFour.visibility = View.GONE
                    peRoomFive.visibility = View.GONE
                }else if(radio.text.toString().equals("4th Floor")){
                    peScrollView.visibility = View.VISIBLE
                    peRoomOne.text = "PE41"
                    peRoomTwo.text = "PE42"
                    peRoomThree.visibility = View.GONE
                    peRoomFour.visibility = View.GONE
                    peRoomFive.visibility = View.GONE
                }
            }

        }
    }
}