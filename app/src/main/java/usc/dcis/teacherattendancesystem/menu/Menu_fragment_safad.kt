package usc.dcis.teacherattendancesystem.menu

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.ScrollView
import kotlinx.android.synthetic.main.menu_fragment_safad.*
import usc.dcis.teacherattendancesystem.R
import usc.dcis.teacherattendancesystem.roomSchedule
import usc.dcis.teacherattendancesystem.scheduleDatabase.ScheduleDatabase

class Menu_fragment_safad : Fragment() {

    lateinit var radioGroup: RadioGroup
    lateinit var wingGroup: RadioGroup
    lateinit var myView: View
    val SAFADBuilding = arrayOf(
        arrayOf("AFCB4", "AFCB3","AF3B02","AF3B01"),
        arrayOf("AF2B11", "AF2B09", "Painting Studio","2B07A","2B07B","2B07C","2B07D","2B07F","2B07G"),
        arrayOf("AF1B102","AF1B04","AF1B03","1B10A","1B10B","1B10C","1B12A","1B12B","1B12C","1B12D","1B12E","1B12F"),
        arrayOf("AF109A","AF109B","AF109A", "AF110", "AF111","AF104","AF104","AF103","AF102","AF101"),
        arrayOf("AF207", "AF208","AF209","AF210","AF211","AF212","AF213","AF214","AF215")
    )
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
            val scrollView = myView.findViewById<ScrollView>(R.id.safadScrollView)
            safad_hiddenwing.setChecked(true)

                if(radioButton.text.toString().equals("3rd Basement")){
                    scrollView.fullScroll(ScrollView.FOCUS_UP)
                    safadScrollView.visibility = View.VISIBLE
                    safadRoomOne.visibility = View.VISIBLE
                    safadRoomTwo.visibility = View.VISIBLE
                    safadRoomThree.visibility = View.VISIBLE
                    safadRoomFour.visibility = View.VISIBLE
                    safadRoomOne.text = SAFADBuilding[0][0]
                    safadRoomTwo.text = SAFADBuilding[0][1]
                    safadRoomThree.text = SAFADBuilding[0][2]
                    safadRoomFour.text = SAFADBuilding[0][3]
                    safadRoomFive.visibility = View.INVISIBLE
                    safadRoomSix.visibility = View.INVISIBLE
                    safadRoomSeven.visibility = View.INVISIBLE
                    safadRoomEight.visibility = View.INVISIBLE
                    safadRoomNine.visibility = View.INVISIBLE
                    safadRoomTen.visibility = View.INVISIBLE
                    safadRoomEleven.visibility = View.INVISIBLE
                    safadRoomTwelve.visibility = View.INVISIBLE

                }else if(radioButton.text.toString().equals("2nd Basement")){
                    scrollView.fullScroll(ScrollView.FOCUS_UP)
                    safadScrollView.visibility = View.VISIBLE
                    safadRoomFive.visibility = View.VISIBLE
                    safadRoomSix.visibility = View.VISIBLE
                    safadRoomSeven.visibility = View.VISIBLE
                    safadRoomEight.visibility = View.VISIBLE
                    safadRoomNine.visibility = View.VISIBLE
                    safadRoomOne.text = SAFADBuilding[1][0]
                    safadRoomTwo.text = SAFADBuilding[1][1]
                    safadRoomThree.text = SAFADBuilding[1][2]
                    safadRoomFour.text = SAFADBuilding[1][3]
                    safadRoomFive.text = SAFADBuilding[1][4]
                    safadRoomSix.text = SAFADBuilding[1][5]
                    safadRoomSeven.text = SAFADBuilding[1][6]
                    safadRoomEight.text = SAFADBuilding[1][7]
                    safadRoomNine.text = SAFADBuilding[1][8]
                    safadRoomTen.visibility = View.INVISIBLE
                    safadRoomEleven.visibility = View.INVISIBLE
                    safadRoomTwelve.visibility = View.INVISIBLE
                }else if(radioButton.text.toString().equals("1st Basement")){
                    scrollView.fullScroll(ScrollView.FOCUS_UP)
                    safadScrollView.visibility = View.VISIBLE
                    safadRoomFive.visibility = View.VISIBLE
                    safadRoomSix.visibility = View.VISIBLE
                    safadRoomSeven.visibility = View.VISIBLE
                    safadRoomEight.visibility = View.VISIBLE
                    safadRoomNine.visibility = View.VISIBLE
                    safadRoomTen.visibility = View.VISIBLE
                    safadRoomEleven.visibility = View.VISIBLE
                    safadRoomTwelve.visibility = View.VISIBLE
                    safadRoomOne.text = SAFADBuilding[2][0]
                    safadRoomTwo.text = SAFADBuilding[2][1]
                    safadRoomThree.text = SAFADBuilding[2][2]
                    safadRoomFour.text = SAFADBuilding[2][3]
                    safadRoomFive.text = SAFADBuilding[2][4]
                    safadRoomSix.text = SAFADBuilding[2][5]
                    safadRoomSeven.text = SAFADBuilding[2][6]
                    safadRoomEight.text = SAFADBuilding[2][7]
                    safadRoomNine.text = SAFADBuilding[2][8]
                    safadRoomTen.text = SAFADBuilding[2][9]
                    safadRoomEleven.text = SAFADBuilding[2][10]
                    safadRoomTwelve.text = SAFADBuilding[2][11]
                } else if(radioButton.text.toString().equals("1st Floor")){
                    scrollView.fullScroll(ScrollView.FOCUS_UP)
                    safadScrollView.visibility = View.VISIBLE
                    safadRoomFive.visibility = View.VISIBLE
                    safadRoomSix.visibility = View.VISIBLE
                    safadRoomSeven.visibility = View.VISIBLE
                    safadRoomEight.visibility = View.VISIBLE
                    safadRoomNine.visibility = View.VISIBLE
                    safadRoomTen.visibility = View.VISIBLE
                    safadRoomOne.text = SAFADBuilding[3][0]
                    safadRoomTwo.text = SAFADBuilding[3][1]
                    safadRoomThree.text = SAFADBuilding[3][2]
                    safadRoomFour.text = SAFADBuilding[3][3]
                    safadRoomFive.text = SAFADBuilding[3][4]
                    safadRoomSix.text = SAFADBuilding[3][5]
                    safadRoomSeven.text = SAFADBuilding[3][6]
                    safadRoomEight.text = SAFADBuilding[3][7]
                    safadRoomNine.text = SAFADBuilding[3][8]
                    safadRoomTen.text = SAFADBuilding[3][9]
                    safadRoomEleven.visibility = View.INVISIBLE
                    safadRoomTwelve.visibility = View.INVISIBLE
                }else if(radioButton.text.toString().equals("2nd Floor")){
                    scrollView.fullScroll(ScrollView.FOCUS_UP)
                    safadScrollView.visibility = View.VISIBLE
                    safadRoomFive.visibility = View.VISIBLE
                    safadRoomSix.visibility = View.VISIBLE
                    safadRoomSeven.visibility = View.VISIBLE
                    safadRoomEight.visibility = View.VISIBLE
                    safadRoomNine.visibility = View.VISIBLE
                    safadRoomOne.text = SAFADBuilding[4][0]
                    safadRoomTwo.text = SAFADBuilding[4][1]
                    safadRoomThree.text = SAFADBuilding[4][2]
                    safadRoomFour.text = SAFADBuilding[4][3]
                    safadRoomFive.text = SAFADBuilding[4][4]
                    safadRoomSix.text = SAFADBuilding[4][5]
                    safadRoomSeven.text = SAFADBuilding[4][6]
                    safadRoomEight.text = SAFADBuilding[4][7]
                    safadRoomNine.text = SAFADBuilding[4][8]
                    safadRoomTen.visibility = View.INVISIBLE
                    safadRoomEleven.visibility = View.INVISIBLE
                    safadRoomTwelve.visibility = View.INVISIBLE
                }

            roomSelect(safadRoomOne.text.toString(), safadRoomTwo.text.toString(),safadRoomThree.text.toString(),
                safadRoomFour.text.toString(), safadRoomFive.text.toString(), safadRoomSix.text.toString(),
                safadRoomSeven.text.toString(), safadRoomEight.text.toString(), safadRoomNine.text.toString(),
                safadRoomTen.text.toString(), safadRoomEleven.text.toString(), safadRoomTwelve.text.toString())
        }

    }

    fun roomSelect(roomOne: String, roomTwo: String, roomThree: String, roomFour: String, roomFive: String, roomSix: String,
                   roomSeven: String, roomEight: String, roomNine: String, roomTen: String, roomEleven: String, roomTwelve: String)
    {
        val db = ScheduleDatabase.getInstance(context!!)
        val scheduleListTest = db.scheduleDAO
        val sdf = java.text.SimpleDateFormat("h:m a")

        safadRoomOne.setOnClickListener {
            val activity = Intent(getActivity(), roomSchedule::class.java)
            activity.putExtra("RoomTxt", roomOne)
            startActivity(activity)
        }

        safadRoomTwo.setOnClickListener {
            val activity = Intent(getActivity(), roomSchedule::class.java)
            activity.putExtra("RoomTxt", roomTwo )
            startActivity(activity)
        }

        safadRoomThree.setOnClickListener {
            val activity = Intent(getActivity(), roomSchedule::class.java)
            activity.putExtra("RoomTxt", roomThree )
            startActivity(activity)
        }
        safadRoomFour.setOnClickListener {
            val activity = Intent(getActivity(), roomSchedule::class.java)

            activity.putExtra("RoomTxt", roomFour )
            startActivity(activity)
        }
        safadRoomFive.setOnClickListener {
            val activity = Intent(getActivity(), roomSchedule::class.java)

            activity.putExtra("RoomTxt", roomFive )
            startActivity(activity)
        }
        safadRoomSix.setOnClickListener {
            val activity = Intent(getActivity(), roomSchedule::class.java)

            activity.putExtra("RoomTxt", roomSix )
            startActivity(activity)
        }
        safadRoomSeven.setOnClickListener {
            val activity = Intent(getActivity(), roomSchedule::class.java)

            activity.putExtra("RoomTxt", roomSeven )
            startActivity(activity)
        }
        safadRoomEight.setOnClickListener {
            val activity = Intent(getActivity(), roomSchedule::class.java)

            activity.putExtra("RoomTxt", roomEight )
            startActivity(activity)
        }
        safadRoomNine.setOnClickListener {
            val activity = Intent(getActivity(), roomSchedule::class.java)

            activity.putExtra("RoomTxt", roomNine )
            startActivity(activity)
        }
        safadRoomTen.setOnClickListener {
            val activity = Intent(getActivity(), roomSchedule::class.java)

            activity.putExtra("RoomTxt", roomTen )
            startActivity(activity)
        }
        safadRoomEleven.setOnClickListener {
            val activity = Intent(getActivity(), roomSchedule::class.java)

            activity.putExtra("RoomTxt", roomEleven )
            startActivity(activity)
        }
        safadRoomTwelve.setOnClickListener {
            val activity = Intent(getActivity(), roomSchedule::class.java)

            activity.putExtra("RoomTxt", roomTwelve )
            startActivity(activity)
        }
    }

}