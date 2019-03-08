package usc.dcis.teacherattendancesystem

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.RadioGroup
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

        wingGroup.setOnCheckedChangeListener { wingGroup, checkedId ->
            val wingID = lbb_wings.checkedRadioButtonId
            val wingBtn = myView.findViewById<RadioButton>(wingID)

            if(wingBtn.text.equals(firstWing)){

                if(radio.text.toString().equals("1st Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomFive.visibility = View.VISIBLE
                    lbbRoomTwo.text = "LB110"
                    lbbRoomOne.text = "LB111"
                    lbbRoomThree.text = "LB112"
                    lbbRoomFour.text = "LB113"
                    lbbRoomFive.text = "LB114"

                    lbbRoomTwo.setOnClickListener {
                        val activity = Intent(getActivity(), roomSchedule::class.java)
                        startActivity(activity)
                    }

                    lbbRoomOne.setOnClickListener {
                        val activity = Intent(getActivity(), roomSchedule::class.java)
                        startActivity(activity)
                    }

                }else if(radio.text.toString().equals("2nd Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomFive.visibility = View.VISIBLE
                    lbbRoomTwo.text = "LB210"
                    lbbRoomOne.text = "LB211"
                    lbbRoomThree.text = "LB212"
                    lbbRoomFour.text = "LB213"
                    lbbRoomFive.text = "LB214"
                }else if(radio.text.toString().equals("3rd Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomThree.visibility = View.VISIBLE
                    lbbRoomTwo.text = "LB321"
                    lbbRoomOne.text = "LB322"
                    lbbRoomThree.text = "LB323"
                    lbbRoomFour.visibility = View.GONE
                    lbbRoomFive.visibility = View.GONE
                }else if(radio.text.toString().equals("4th Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomTwo.text = "LB442"
                    lbbRoomOne.text = "LB443"
                    lbbRoomThree.visibility = View.GONE
                    lbbRoomFour.visibility = View.GONE
                    lbbRoomFive.visibility = View.GONE
                }
            }else if(wingBtn.text.equals(secondWing)){

                if(radio.text.toString().equals("1st Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomFive.visibility = View.VISIBLE
                    lbbRoomTwo.text = "LB120"
                    lbbRoomOne.text = "LB121"
                    lbbRoomThree.text = "LB122"
                    lbbRoomFour.text = "LB123"
                    lbbRoomFive.text = "LB124"




                }else if(radio.text.toString().equals("2nd Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomFive.visibility = View.VISIBLE
                    lbbRoomTwo.text = "LB220"
                    lbbRoomOne.text = "LB221"
                    lbbRoomThree.text = "LB222"
                    lbbRoomFour.text = "LB223"
                    lbbRoomFive.text = "LB224"
                }else if(radio.text.toString().equals("3rd Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomThree.visibility = View.VISIBLE
                    lbbRoomTwo.text = "LB331"
                    lbbRoomOne.text = "LB332"
                    lbbRoomThree.text = "LB333"
                    lbbRoomFour.visibility = View.GONE
                    lbbRoomFive.visibility = View.GONE
                }else if(radio.text.toString().equals("4th Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomTwo.text = "LB445"
                    lbbRoomOne.text = "LB446"
                    lbbRoomThree.text = "LB447"
                    lbbRoomFour.text = "LB448"
                    lbbRoomFive.text = "LB449"
                    lbbRoomThree.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomFive.visibility = View.VISIBLE
                }
            }else if(wingBtn.text.equals(thirdWing)){

                if(radio.text.toString().equals("1st Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomFive.visibility = View.VISIBLE
                    lbbRoomTwo.text = "LB130"
                    lbbRoomOne.text = "LB131"
                    lbbRoomThree.text = "LB132"
                    lbbRoomFour.text = "LB133"
                    lbbRoomFive.text = "LB134"
                }else if(radio.text.toString().equals("2nd Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomFive.visibility = View.VISIBLE
                    lbbRoomTwo.text = "LB230"
                    lbbRoomOne.text = "LB231"
                    lbbRoomThree.text = "LB232"
                    lbbRoomFour.text = "LB233"
                    lbbRoomFive.text = "LB234"
                }else if(radio.text.toString().equals("3rd Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomThree.visibility = View.VISIBLE
                    lbbRoomTwo.text = "LB341"
                    lbbRoomOne.text = "LB342"
                    lbbRoomThree.text = "LB343"
                    lbbRoomFour.visibility = View.GONE
                    lbbRoomFive.visibility = View.GONE
                }else if(radio.text.toString().equals("4th Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomTwo.text = "LB470"
                    lbbRoomOne.text = "LB469"
                    lbbRoomThree.text = "LB468"
                    lbbRoomFour.text = "LB467"
                    lbbRoomFive.text = "LB466"
                    lbbRoomThree.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomFive.visibility = View.VISIBLE
                }
            }else if(wingBtn.text.equals(fourthWing)){

                if(radio.text.toString().equals("1st Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomFive.visibility = View.VISIBLE
                    lbbRoomTwo.text = "LB140"
                    lbbRoomOne.text = "LB141"
                    lbbRoomThree.text = "LB142"
                    lbbRoomFour.text = "LB143"
                    lbbRoomFive.text = "LB144"
                }else if(radio.text.toString().equals("2nd Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomFive.visibility = View.VISIBLE
                    lbbRoomTwo.text = "LB240"
                    lbbRoomOne.text = "LB241"
                    lbbRoomThree.text = "LB242"
                    lbbRoomFour.text = "LB243"
                    lbbRoomFive.text = "LB244"
                }else if(radio.text.toString().equals("3rd Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomThree.visibility = View.VISIBLE
                    lbbRoomTwo.text = "LB341"
                    lbbRoomOne.text = "LB342"
                    lbbRoomThree.text = "LB343"
                    lbbRoomFour.visibility = View.GONE
                    lbbRoomFive.visibility = View.GONE
                }else if(radio.text.toString().equals("4th Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomTwo.text = "LB485"
                    lbbRoomOne.text = "LB486"
                    lbbRoomThree.text = "LB467"
                    lbbRoomFour.text = "LB488"
                    lbbRoomFive.text = "LB489"
                    lbbRoomThree.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomFive.visibility = View.VISIBLE
                }
            }else if(wingBtn.text.equals(fifthWing)){

                if(radio.text.toString().equals("3rd Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomThree.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomTwo.text = "LB306"
                    lbbRoomOne.text = "LB305"
                    lbbRoomThree.text = "LB304"

                    lbbRoomFive.visibility = View.GONE
                }else if(radio.text.toString().equals("4th Floor")) {
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomTwo.text = "LB401"
                    lbbRoomOne.text = "LB402"
                    lbbRoomThree.text = "LB403"
                    lbbRoomFour.text = "LB405"
                    lbbRoomFive.text = "LB404"
                    lbbRoomThree.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomFive.visibility = View.VISIBLE
                }
            }


        }
    }


}