package usc.dcis.teacherattendancesystem.menu

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.RadioGroup
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.menu_fragment_lbb.*
import usc.dcis.tea.ScheduleFirebase
import usc.dcis.teacherattendancesystem.R
import usc.dcis.teacherattendancesystem.roomSchedule
import usc.dcis.teacherattendancesystem.scheduleDatabase.ScheduleDatabase
import usc.dcis.teacherattendancesystem.scheduleDatabase.*

class Menu_fragment_lbb : Fragment() {

    lateinit var radioGroup: RadioGroup
    lateinit var wingGroup: RadioGroup
   lateinit var myView: View

    //initializing rooms
    private val bunzelBuilding_basement = arrayOf("Cisco Laboratory")
    private val bunzelBuilding_fifth = arrayOf("LB561", "LB562", "LB563")
    private val bunzelBuilding_first = arrayOf(
        arrayOf("LB167", "LB168", "LB172"),
        arrayOf("LB143", "LB144"),
        arrayOf("LBCH1","LBCH2")
    )
    private val bunzelBuilding_second =arrayOf(
        arrayOf("LB280", "LBCEA1", "LB285", "LB286"),
        arrayOf("LB264", "LB265","LB266", "LB267","LB268"),
        arrayOf("LB245","LB246","LB247", "LB248"),
        arrayOf("LB220")
    )
    private  val bunzelBuilding_third =arrayOf(
        arrayOf("LB306","LB305","LB304"),
        arrayOf("LB380","LB381", "LB382", "LB383", "LB384", "LB386"),
        arrayOf("LB363","LB364","LB366")
    )
    private val bunzelBuilding_fourth = arrayOf(
        arrayOf("LB401","LB402","LB404"),
        arrayOf("LB482", "LB483", "LB484", "LB485", "LB486"),
        arrayOf("LB466", "LB467","LB468", "LB469"),
        arrayOf("LB444","LB445","LB446", "LB447", "LB448"),
        arrayOf("LB441","LB442")
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.menu_fragment_lbb, null)
        return myView

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        radioGroup = myView.findViewById(R.id.lbb_floors)

        insertRooms()

        radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            val id = lbb_floors.checkedRadioButtonId
            val radioButton = myView.findViewById<RadioButton>(id)


            lbb_hiddenwing.setChecked(true)

            if(radioButton.text.toString().equals("1st Floor")){
                lbb_wings.visibility = View.VISIBLE
                chooseAWing.visibility = View.VISIBLE
                lbb_wing3.visibility = View.VISIBLE
                lbb_wing1.text = "Wing 1"
                lbb_wing2.text = "Wing 2"
                lbb_wing3.text = "Wing 3"
                lbb_wing4.visibility = View.INVISIBLE
                lbb_wing5.visibility = View.INVISIBLE

                wingSelect(lbb_wing1.text.toString(), lbb_wing2.text.toString(), lbb_wing3.text.toString(), "", "", radioButton)


            }else if(radioButton.text.toString().equals("2nd Floor")){
                lbb_wings.visibility = View.VISIBLE
                chooseAWing.visibility = View.VISIBLE
                lbb_wing3.visibility = View.VISIBLE
                lbb_wing4.visibility = View.VISIBLE
                lbb_wing1.text = "Wing 1"
                lbb_wing2.text = "Wing 2"
                lbb_wing3.text = "Wing 3"
                lbb_wing4.text = "Wing 4"
                lbb_wing5.visibility = View.INVISIBLE


                wingSelect(lbb_wing1.text.toString(), lbb_wing2.text.toString(), lbb_wing3.text.toString(), lbb_wing4.text.toString(), "", radioButton )




            }else if(radioButton.text.toString().equals("3rd Floor")){
                chooseAWing.visibility = View.VISIBLE
                lbb_wings.visibility = View.VISIBLE
                lbb_wing5.visibility = View.VISIBLE
                lbb_wing1.text = "Wing 1"
                lbb_wing2.text = "Wing 2"
                lbb_wing5.text = "Main Wing"

                lbb_wing3.visibility = View.GONE
                lbb_wing4.visibility = View.GONE

                wingSelect(lbb_wing1.text.toString(), lbb_wing2.text.toString(), "", "", lbb_wing5.text.toString(), radioButton)



            }else if(radioButton.text.toString().equals("4th Floor")){
                chooseAWing.visibility = View.VISIBLE
                lbb_wings.visibility = View.VISIBLE
                lbb_wing3.visibility = View.VISIBLE
                lbb_wing4.visibility = View.VISIBLE
                lbb_wing5.visibility = View.VISIBLE
                lbb_wing1.text = "Wing 1"
                lbb_wing2.text = "Wing 2"
                lbb_wing3.text = "Wing 3"
                lbb_wing4.text = "Wing 4"
                lbb_wing5.text = "Main Wing"


                wingSelect(lbb_wing1.text.toString(), lbb_wing2.text.toString(), lbb_wing3.text.toString(), lbb_wing4.text.toString(), lbb_wing5.text.toString(), radioButton)
            }else if(radioButton.text.toString().equals("5th Floor")){
                chooseAWing.visibility = View.INVISIBLE
                lbb_wings.visibility = View.INVISIBLE
                lbbScrollView.visibility = View.VISIBLE
                lbbRoomTwo.visibility = View.VISIBLE
                lbbRoomThree.visibility = View.VISIBLE
                lbbRoomOne.text = bunzelBuilding_fifth[0]
                lbbRoomTwo.text = bunzelBuilding_fifth[1]
                lbbRoomThree.text = bunzelBuilding_fifth[2]
                lbbRoomFour.visibility = View.INVISIBLE
                lbbRoomFive.visibility = View.INVISIBLE
                roomSelect(lbbRoomOne.text.toString(), lbbRoomTwo.text.toString(), lbbRoomThree.text.toString(),
                    "", "")
            }else if(radioButton.text.toString().equals("Basement")){
                chooseAWing.visibility = View.INVISIBLE
                lbb_wings.visibility = View.INVISIBLE
                lbbScrollView.visibility = View.VISIBLE
                lbbRoomOne.text = bunzelBuilding_basement[0]
                lbbRoomTwo.visibility = View.INVISIBLE
                lbbRoomThree.visibility = View.INVISIBLE
                lbbRoomFour.visibility = View.INVISIBLE
                lbbRoomFive.visibility = View.INVISIBLE
                roomSelect(lbbRoomOne.text.toString(), "", "", "", "")

            }
        }

    }

    fun wingSelect(firstWing: String, secondWing: String, thirdWing: String, fourthWing: String, fifthWing:String, radio: RadioButton){
        wingGroup = myView.findViewById(R.id.lbb_wings)
        val db = ScheduleDatabase.getInstance(context!!)
        val scheduleListTest = db.scheduleDAO
        val sdf = java.text.SimpleDateFormat("h:m a")

        wingGroup.setOnCheckedChangeListener { wingGroup, checkedId ->
            val wingID = lbb_wings.checkedRadioButtonId
            val wingBtn = myView.findViewById<RadioButton>(wingID)

            if(wingBtn.text.equals(firstWing)){

                if(radio.text.toString().equals("1st Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomTwo.visibility = View.VISIBLE
                    lbbRoomThree.visibility = View.VISIBLE
                    lbbRoomOne.text = bunzelBuilding_first[0][0]
                    lbbRoomTwo.text = bunzelBuilding_first[0][1]
                    lbbRoomThree.text = bunzelBuilding_first[0][2]
                    lbbRoomFour.visibility = View.INVISIBLE
                    lbbRoomFive.visibility = View.INVISIBLE

                }else if(radio.text.toString().equals("2nd Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomTwo.visibility = View.VISIBLE
                    lbbRoomThree.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomOne.text = bunzelBuilding_second[0][0]
                    lbbRoomTwo.text = bunzelBuilding_second[0][1]
                    lbbRoomThree.text = bunzelBuilding_second[0][2]
                    lbbRoomFour.text = bunzelBuilding_second[0][3]
                    lbbRoomFive.visibility = View.INVISIBLE

                }else if(radio.text.toString().equals("3rd Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomTwo.visibility = View.VISIBLE
                    lbbRoomThree.visibility = View.VISIBLE
                    lbbRoomOne.text = bunzelBuilding_third[1][0]
                    lbbRoomTwo.text = bunzelBuilding_third[1][1]
                    lbbRoomThree.text = bunzelBuilding_third[1][2]
                    lbbRoomFour.visibility = View.INVISIBLE
                    lbbRoomFive.visibility = View.INVISIBLE
                }else if(radio.text.toString().equals("4th Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomTwo.visibility = View.VISIBLE
                    lbbRoomThree.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomFive.visibility = View.VISIBLE
                    lbbRoomOne.text = bunzelBuilding_fourth[1][0]
                    lbbRoomTwo.text = bunzelBuilding_fourth[1][1]
                    lbbRoomThree.text = bunzelBuilding_fourth[1][2]
                    lbbRoomFour.text = bunzelBuilding_fourth[1][3]
                    lbbRoomFive.text = bunzelBuilding_fourth[1][4]

                }
            }else if(wingBtn.text.equals(secondWing)){

                if(radio.text.toString().equals("1st Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomTwo.visibility = View.VISIBLE
                    lbbRoomOne.text = bunzelBuilding_first[1][0]
                    lbbRoomTwo.text = bunzelBuilding_first[1][1]
                    lbbRoomThree.visibility = View.INVISIBLE
                    lbbRoomFour.visibility = View.INVISIBLE
                    lbbRoomFive.visibility = View.INVISIBLE

                }else if(radio.text.toString().equals("2nd Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomTwo.visibility = View.VISIBLE
                    lbbRoomThree.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomFive.visibility = View.VISIBLE
                    lbbRoomOne.text = bunzelBuilding_second[1][0]
                    lbbRoomTwo.text = bunzelBuilding_second[1][1]
                    lbbRoomThree.text = bunzelBuilding_second[1][2]
                    lbbRoomFour.text = bunzelBuilding_second[1][3]
                    lbbRoomFive.text = bunzelBuilding_second[1][4]

                }else if(radio.text.toString().equals("3rd Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomTwo.visibility = View.VISIBLE
                    lbbRoomThree.visibility = View.VISIBLE
                    lbbRoomOne.text = bunzelBuilding_third[2][0]
                    lbbRoomTwo.text = bunzelBuilding_third[2][1]
                    lbbRoomThree.text = bunzelBuilding_third[2][1]
                    lbbRoomFour.visibility = View.INVISIBLE
                    lbbRoomFive.visibility = View.INVISIBLE
                }else if(radio.text.toString().equals("4th Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomTwo.visibility = View.VISIBLE
                    lbbRoomThree.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomOne.text =  bunzelBuilding_fourth[2][0]
                    lbbRoomTwo.text =  bunzelBuilding_fourth[2][1]
                    lbbRoomThree.text =  bunzelBuilding_fourth[2][2]
                    lbbRoomFour.text =  bunzelBuilding_fourth[2][3]

                    lbbRoomFive.visibility = View.INVISIBLE
                }
            }else if(wingBtn.text.equals(thirdWing)){

                if(radio.text.toString().equals("1st Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomTwo.visibility = View.VISIBLE
                    lbbRoomOne.text = bunzelBuilding_first[2][0]
                    lbbRoomTwo.text = bunzelBuilding_first[2][1]
                    lbbRoomThree.visibility = View.INVISIBLE
                    lbbRoomFour.visibility = View.INVISIBLE
                    lbbRoomFive.visibility = View.INVISIBLE
                }else if(radio.text.toString().equals("2nd Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomTwo.visibility = View.VISIBLE
                    lbbRoomThree.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomOne.text =  bunzelBuilding_second[2][0]
                    lbbRoomTwo.text =  bunzelBuilding_second[2][1]
                    lbbRoomThree.text =  bunzelBuilding_second[2][2]
                    lbbRoomFour.text =  bunzelBuilding_second[2][3]

                    lbbRoomFive.visibility = View.INVISIBLE

                }else if(radio.text.toString().equals("4th Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomTwo.visibility = View.VISIBLE
                    lbbRoomThree.visibility = View.VISIBLE
                    lbbRoomFour.visibility = View.VISIBLE
                    lbbRoomFive.visibility = View.VISIBLE
                    lbbRoomOne.text = bunzelBuilding_fourth[3][0]
                    lbbRoomTwo.text = bunzelBuilding_fourth[3][1]
                    lbbRoomThree.text = bunzelBuilding_fourth[3][2]
                    lbbRoomFour.text = bunzelBuilding_fourth[3][3]
                    lbbRoomFive.text = bunzelBuilding_fourth[3][4]
                }
            }else if(wingBtn.text.equals(fourthWing)){

                if(radio.text.toString().equals("2nd Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomOne.text = bunzelBuilding_second[3][0]
                    lbbRoomTwo.visibility = View.INVISIBLE
                    lbbRoomThree.visibility = View.INVISIBLE
                    lbbRoomFour.visibility = View.INVISIBLE
                    lbbRoomFive.visibility = View.INVISIBLE
                }else if(radio.text.toString().equals("4th Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomTwo.visibility = View.VISIBLE
                    lbbRoomOne.text = bunzelBuilding_fourth[4][0]
                    lbbRoomTwo.text = bunzelBuilding_fourth[4][1]
                    lbbRoomThree.visibility = View.INVISIBLE
                    lbbRoomFour.visibility = View.INVISIBLE
                    lbbRoomFive.visibility = View.INVISIBLE
                }
            }else if(wingBtn.text.equals(fifthWing)){

                if(radio.text.toString().equals("3rd Floor")){
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomTwo.visibility = View.VISIBLE
                    lbbRoomThree.visibility = View.VISIBLE

                    lbbRoomOne.text = bunzelBuilding_third[0][0]
                    lbbRoomTwo.text = bunzelBuilding_third[0][1]
                    lbbRoomThree.text = bunzelBuilding_third[0][2]
                    lbbRoomFour.visibility = View.INVISIBLE
                    lbbRoomFive.visibility = View.INVISIBLE
                }else if(radio.text.toString().equals("4th Floor")) {
                    lbbScrollView.visibility = View.VISIBLE
                    lbbRoomTwo.visibility = View.VISIBLE
                    lbbRoomThree.visibility = View.VISIBLE

                    lbbRoomOne.text = bunzelBuilding_fourth[0][0]
                    lbbRoomTwo.text = bunzelBuilding_fourth[0][1]
                    lbbRoomThree.text = bunzelBuilding_fourth[0][2]
                    lbbRoomFour.visibility = View.INVISIBLE
                    lbbRoomFive.visibility = View.INVISIBLE
                }
            }

            roomSelect(lbbRoomOne.text.toString(), lbbRoomTwo.text.toString(), lbbRoomThree.text.toString(),
                lbbRoomFour.text.toString(), lbbRoomFive.text.toString())
        }

    }



    fun roomSelect(roomOne: String, roomTwo: String, roomThree: String, roomFour: String, roomFive: String)
    {
        val db = ScheduleDatabase.getInstance(context!!)
        val scheduleListTest = db.scheduleDAO
        val sdf = java.text.SimpleDateFormat("h:m a")

        lbbRoomOne.setOnClickListener {
            if(roomOne.equals(scheduleListTest.getAllRoomAssignmentsByRoomNumber(bunzelBuilding_basement[0])[0].roomNumber)){

                val activity = Intent(getActivity(), roomSchedule::class.java)
                val lista = scheduleListTest.getAllRoomAssignmentsByRoomNumber(bunzelBuilding_basement[0])
                activity.putExtra("RoomTxt", lista[0].roomNumber.toString())
                startActivity(activity)
            }else if(roomOne.equals(scheduleListTest.getAllRoomAssignmentsByRoomNumber(bunzelBuilding_first[0][0])[0].roomNumber)){

                val activity = Intent(getActivity(), roomSchedule::class.java)
                val listo = scheduleListTest.getAllRoomAssignmentsByRoomNumber(bunzelBuilding_first[0][0])
                activity.putExtra("RoomTxt", listo[0].roomNumber.toString())
                startActivity(activity)
            }

        }

        lbbRoomTwo.setOnClickListener {
            if(roomTwo.equals("LB111TC")) {
                val activity = Intent(getActivity(), roomSchedule::class.java)
                activity.putExtra("RoomTxt", roomTwo)
                startActivity(activity)
            }
            val activity = Intent(getActivity(), roomSchedule::class.java)
            activity.putExtra("RoomTxt", roomTwo)
            startActivity(activity)
        }

        lbbRoomThree.setOnClickListener {
            val activity = Intent(getActivity(), roomSchedule::class.java)
            activity.putExtra("RoomTxt",roomThree )
            startActivity(activity)
        }
        lbbRoomFour.setOnClickListener {
            val activity = Intent(getActivity(), roomSchedule::class.java)
            activity.putExtra("RoomTxt",roomFour )
            startActivity(activity)
        }
        lbbRoomFive.setOnClickListener {
            val activity = Intent(getActivity(), roomSchedule::class.java)
            activity.putExtra("RoomTxt",roomFive )
            startActivity(activity)
        }
    }

    fun insertRooms(){
        val db = ScheduleDatabase.getInstance(context!!)
        val scheduleListTest = db.scheduleDAO
        val sdf = java.text.SimpleDateFormat("h:m a")


        //basement
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, "IT5001", 1, bunzelBuilding_basement[0], sdf.parse("10:30 AM"),
                sdf.parse("12:00 PM"), "M"
            )
        )
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, "IT5001", 1, bunzelBuilding_basement[0], sdf.parse("9:30 AM"),
                sdf.parse("12:00 PM"), "W"
            )
        )
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, "IT5001", 1, bunzelBuilding_basement[0], sdf.parse("10:30 AM"),
                sdf.parse("12:00 PM"), "F"
            )
        )

        //1st floor
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, "IT5001", 1, bunzelBuilding_first[0][0], sdf.parse("1:00 PM"),
                sdf.parse("3:00 PM"), "T"
            )
        )
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, "IT1101", 1, bunzelBuilding_first[0][0], sdf.parse("10:30 AM"),
                sdf.parse("11:30 AM"), "TH"
            )
        )

        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, "IT1101", 1, bunzelBuilding_first[0][1], sdf.parse("9:30 AM"),
                sdf.parse("12:00 PM"), "W"
            )
        )
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, "IT1101", 1, bunzelBuilding_first[0][1], sdf.parse("9:30 AM"),
                sdf.parse("12:00 PM"), "F"
            )
        )
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, "MATH25", 1, bunzelBuilding_first[0][2], sdf.parse("10:30 AM"),
                sdf.parse("12:00 PM"), "M"
            )
        )
        scheduleListTest.insertRoomAssignment(
            RoomAssignment(
                0, "MATH25", 1, bunzelBuilding_first[0][2], sdf.parse("10:30 AM"),
                sdf.parse("12:00 PM"), "F"
            )
        )



       // ScheduleFirebase.AddMultipleRoomAssignments(FirebaseFirestore.getInstance(), scheduleListTest.getAllRoomAssignments())

        /*
        //region

        ScheduleFirebase.AddRoomAssignment(FirebaseFirestore.getInstance(), RoomAssignment(0, 1, bunzelBuilding_first[0][0], sdf.parse("1:30 PM"),
            sdf.parse("3:30 PM"), "T"))
        ScheduleFirebase.AddRoomAssignment(FirebaseFirestore.getInstance(), RoomAssignment(1, 1, bunzelBuilding_first[0][0], sdf.parse("10:30 AM"),
            sdf.parse("2:30 PM"), "TH"))
        ScheduleFirebase.AddRoomAssignment(FirebaseFirestore.getInstance(), RoomAssignment(2, 1, bunzelBuilding_first[0][0], sdf.parse("1:30 PM"),
            sdf.parse("4:30 PM"), "F"))

        var firestore = FirebaseFirestore.getInstance()
        //adding the stuff in firebase to all rooms

        firestore.collection("roomAssignment")
            .get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    var documents = task.result
                    var rooms = documents.toObjects(RoomAssignment::class.java)

                    for(room in rooms){
                        //make sure to change this to scheduleListTest.getRoomAssignmentCountByroomId
                        if(scheduleListTest.getRoomAssignmentCountByroomID(room.roomID) == 0){
                            scheduleListTest.insertRoomAssignment(room)
                        }
                    }
                }
            }
        //endregion
        */



    }


}