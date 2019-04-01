package usc.dcis.teacherattendancesystem.menu

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.ScrollView
import kotlinx.android.synthetic.main.content_chooseschedule.*
import kotlinx.android.synthetic.main.menu_fragment_pe.*
import usc.dcis.teacherattendancesystem.R

class Menu_fragment_pe : Fragment() {

    lateinit var radioGroup: RadioGroup
    lateinit var wingGroup: RadioGroup
    lateinit var myView: View
    val peBuilding = arrayOf(
        arrayOf("PE11", "PE12", "PE13", "PE14", "PE15", "PE16"),
        arrayOf("PE21","PE22", "PE23", "PE24", "PE25", "PE26"),
        arrayOf("PE31","PE32", "PE34", "PE34B"),
        arrayOf("PE40","PE41", "PE44B", "PE45")
    )

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
            val scrollView = myView.findViewById<ScrollView>(R.id.peScrollView)
            pe_hiddenwing.setChecked(true)

                if(radioButton.text.toString().equals("1st Floor")){
                    scrollView.fullScroll(ScrollView.FOCUS_UP)
                    peScrollView.visibility = View.VISIBLE
                    peRoomFive.visibility = View.VISIBLE
                    peRoomSix.visibility = View.VISIBLE
                    peRoomOne.text = peBuilding[0][0]
                    peRoomTwo.text = peBuilding[0][1]
                    peRoomThree.text = peBuilding[0][2]
                    peRoomFour.text = peBuilding[0][3]
                    peRoomFive.text = peBuilding[0][4]
                    peRoomSix.text = peBuilding[0][5]
                }else if(radioButton.text.toString().equals("2nd Floor")){
                    scrollView.fullScroll(ScrollView.FOCUS_UP)
                    peScrollView.visibility = View.VISIBLE
                    peRoomFive.visibility = View.VISIBLE
                    peRoomSix.visibility = View.VISIBLE
                    peRoomOne.text = peBuilding[1][0]
                    peRoomTwo.text = peBuilding[1][1]
                    peRoomThree.text = peBuilding[1][2]
                    peRoomFour.text = peBuilding[1][3]
                    peRoomFive.text = peBuilding[1][4]
                    peRoomSix.text = peBuilding[1][5]
                }else if(radioButton.text.toString().equals("3rd Floor")){
                    scrollView.fullScroll(ScrollView.FOCUS_UP)
                    peScrollView.visibility = View.VISIBLE
                    peRoomThree.visibility = View.VISIBLE
                    peRoomOne.text = peBuilding[2][0]
                    peRoomTwo.text = peBuilding[2][1]
                    peRoomThree.text = peBuilding[2][2]
                    peRoomFour.text = peBuilding[2][3]
                    peRoomFive.visibility = View.INVISIBLE
                    peRoomSix.visibility = View.INVISIBLE
                }else if(radioButton.text.toString().equals("4th Floor")){
                    scrollView.fullScroll(ScrollView.FOCUS_UP)
                    peScrollView.visibility = View.VISIBLE
                    peRoomOne.text =  peBuilding[3][0]
                    peRoomTwo.text = peBuilding[3][1]
                    peRoomThree.text = peBuilding[3][2]
                    peRoomFour.text = peBuilding[3][3]
                    peRoomFive.visibility = View.INVISIBLE
                    peRoomSix.visibility = View.INVISIBLE
                }

        }

    }







}