package usc.dcis.teacherattendancesystem

import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import org.jetbrains.annotations.Nullable
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.RadioGroup
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.menu_fragment_lbb.view.*
import android.widget.Toast
import kotlinx.android.synthetic.main.menu_fragment_lbb.*

class Menu_fragment_lbb : Fragment() {

    lateinit var radioGroup: RadioGroup
   lateinit var myView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.menu_fragment_lbb, null)
        return myView

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        radioGroup = myView.findViewById(R.id.lbb_floors)


      lbb_confirm.setOnClickListener {
            /*val text = Toast.makeText(activity, "You selected "+ radioButton.text, Toast.LENGTH_LONG)
            text.show()*/
          val id = lbb_floors.checkedRadioButtonId
          val radioButton = myView.findViewById<RadioButton>(id)

        }
    }
}