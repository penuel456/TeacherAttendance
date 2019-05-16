package usc.dcis.teacherattendancesystem.menu

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.Button
import usc.dcis.teacherattendancesystem.R
import usc.dcis.teacherattendancesystem.roomSchedule

class Menu_fragment_bct : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.menu_fragment_bct, null)
        val bct1Btn = view.findViewById<Button>(R.id.bct1)
        val bct2Btn = view.findViewById<Button>(R.id.bct2)
        bct1Btn.setOnClickListener(){
            val activity = Intent(getActivity(), roomSchedule::class.java)
            activity.putExtra("RoomTxt", "BCT1" )
            startActivity(activity)
        }
        bct2Btn.setOnClickListener(){
            val activity = Intent(getActivity(), roomSchedule::class.java)
            activity.putExtra("RoomTxt", "BCT2" )
            startActivity(activity)
        }
        return view
    }
    public fun goToSchedList(view: View){
        Menu_fragment_schedListTeacher()
    }
    public fun goToBct1(view: View){
        val activity = Intent(getActivity(), roomSchedule::class.java)
        activity.putExtra("RoomTxt", "BCT1" )
        startActivity(activity)
    }
    public fun goToBct2(view: View){
        val activity = Intent(getActivity(), roomSchedule::class.java)
        activity.putExtra("RoomTxt", "BCT2" )
        startActivity(activity)
    }

}