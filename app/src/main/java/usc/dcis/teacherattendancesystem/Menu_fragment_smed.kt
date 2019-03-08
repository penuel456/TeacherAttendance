package usc.dcis.teacherattendancesystem

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.menu_fragment_smed.*

class Menu_fragment_smed : Fragment() {

    lateinit var radioGroup: RadioGroup
    lateinit var wingGroup: RadioGroup
    lateinit var myView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.menu_fragment_smed, null)
        return myView
    }


}