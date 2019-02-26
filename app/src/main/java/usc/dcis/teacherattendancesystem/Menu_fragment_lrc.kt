package usc.dcis.teacherattendancesystem

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.menu_fragment_lrc.*

class Menu_fragment_lrc : Fragment() {

    lateinit var radioGroup: RadioGroup
    lateinit var myView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.menu_fragment_lrc, null)
        return myView

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        radioGroup = myView.findViewById(R.id.lrc_floors)


        radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            val id = lrc_floors.checkedRadioButtonId
            val radioButton = myView.findViewById<RadioButton>(id)


            if (radioButton.text.toString().equals("1st Floor")) {
                lrcScrollView.visibility = View.VISIBLE
                lrcRoomThree.visibility = View.GONE
                lrcRoomOne.text = "LRC 1ST FLR R1"
                lrcRoomTwo.text = "LRC 1ST FLR R2"

            } else if (radioButton.text.toString().equals("2nd Floor")) {
                lrcScrollView.visibility = View.VISIBLE
                lrcRoomThree.visibility = View.GONE
                lrcRoomOne.text = "LRC 2ND FLR R1"
                lrcRoomTwo.text = "LRC 2ND FLR R2"

            }else if (radioButton.text.toString().equals("Basement")){
                lrcScrollView.visibility = View.VISIBLE
                lrcRoomThree.visibility = View.VISIBLE
                lrcRoomOne.text = "LRC BASEMENT R1"
                lrcRoomTwo.text = "LRC BASEMENT R2"
                lrcRoomThree.text = "LRC BASEMENT R3"
            }
        }
    }
}