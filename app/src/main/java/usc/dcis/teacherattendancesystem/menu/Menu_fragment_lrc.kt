package usc.dcis.teacherattendancesystem.menu

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.menu_fragment_lrc.*
import usc.dcis.teacherattendancesystem.R

class Menu_fragment_lrc : Fragment() {

    lateinit var radioGroup: RadioGroup
    lateinit var myView: View
    val josefBuilding = arrayOf(
        arrayOf("JBB101", "JBB102", "JBB103", "JBB104", "JBB105"),
        arrayOf("BL101","BL102")
    )

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


            if (radioButton.text.toString().equals("Basement")) {

                lrcScrollView.visibility = View.VISIBLE
                lrcRoomThree.visibility = View.VISIBLE
                lrcRoomFour.visibility = View.VISIBLE
                lrcRoomFive.visibility = View.VISIBLE
                lrcRoomOne.text = josefBuilding[0][0]
                lrcRoomTwo.text = josefBuilding[0][1]
                lrcRoomThree.text = josefBuilding[0][2]
                lrcRoomFour.text = josefBuilding[0][3]
                lrcRoomFive.text = josefBuilding[0][4]

            } else if (radioButton.text.toString().equals("1st Floor")) {
                lrcScrollView.visibility = View.VISIBLE
                lrcRoomThree.visibility = View.INVISIBLE
                lrcRoomFour.visibility = View.INVISIBLE
                lrcRoomFive.visibility = View.INVISIBLE
                lrcRoomOne.text = josefBuilding[1][0]
                lrcRoomTwo.text = josefBuilding[1][1]

            }
        }
    }
}