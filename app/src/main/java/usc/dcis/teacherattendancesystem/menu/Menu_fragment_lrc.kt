package usc.dcis.teacherattendancesystem.menu

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.menu_fragment_lrc.*
import usc.dcis.teacherattendancesystem.R
import usc.dcis.teacherattendancesystem.roomSchedule
import usc.dcis.teacherattendancesystem.scheduleDatabase.ScheduleDatabase

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

            roomSelect(lrcRoomOne.text.toString(), lrcRoomTwo.text.toString(), lrcRoomThree.text.toString(),
                lrcRoomFour.text.toString(), lrcRoomFive.text.toString())
        }
    }

    fun roomSelect(roomOne: String, roomTwo: String, roomThree: String, roomFour: String, roomFive: String)
    {
        val db = ScheduleDatabase.getInstance(context!!)
        val scheduleListTest = db.scheduleDAO
        val sdf = java.text.SimpleDateFormat("h:m a")

        lrcRoomOne.setOnClickListener {
            val activity = Intent(getActivity(), roomSchedule::class.java)
            activity.putExtra("RoomTxt", roomOne)
            startActivity(activity)


        }

        lrcRoomTwo.setOnClickListener {
            val activity = Intent(getActivity(), roomSchedule::class.java)
            activity.putExtra("RoomTxt", roomTwo )
            startActivity(activity)
        }

        lrcRoomThree.setOnClickListener {
            val activity = Intent(getActivity(), roomSchedule::class.java)
            activity.putExtra("RoomTxt", roomThree )
            startActivity(activity)
        }
        lrcRoomFour.setOnClickListener {
            val activity = Intent(getActivity(), roomSchedule::class.java)

            activity.putExtra("RoomTxt", roomFour )
            startActivity(activity)
        }
        lrcRoomFive.setOnClickListener {
            val activity = Intent(getActivity(), roomSchedule::class.java)

            activity.putExtra("RoomTxt", roomFive )
            startActivity(activity)
        }
    }
}