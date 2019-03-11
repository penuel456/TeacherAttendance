package usc.dcis.teacherattendancesystem.menu

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.Button
import usc.dcis.teacherattendancesystem.R

class Menu_fragment_bct : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.menu_fragment_bct, null)
        val bct1Btn = view.findViewById<Button>(R.id.bct1)
        val bct2Btn = view.findViewById<Button>(R.id.bct2)
        return view
    }
    public fun goToSchedList(view: View){
        Menu_fragment_schedListTeacher()
    }
}