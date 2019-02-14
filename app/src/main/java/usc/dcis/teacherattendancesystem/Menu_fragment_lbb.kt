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

          if(radioButton.text.toString().equals("1st Floor")){
              lbb_wings.visibility = View.VISIBLE
              lbb_wing1.text = "Wing 1"
              lbb_wing2.text = "Wing 2"
              lbb_wing3.text = "Wing 3"
              lbb_wing4.text = "Wing 4"
              lbb_wing5.visibility = View.GONE
          }else if(radioButton.text.toString().equals("2nd Floor")){
              lbb_wings.visibility = View.VISIBLE
              lbb_wing1.text = "Wing 256"
              lbb_wing2.text = "Wing 261"
              lbb_wing3.text = "Wing 273"
              lbb_wing4.text = "Wing 283"
              lbb_wing5.visibility = View.GONE
          }else if(radioButton.text.toString().equals("3rd Floor")){
              lbb_wings.visibility = View.VISIBLE
              lbb_wing5.visibility = View.VISIBLE
              lbb_wing1.text = "Wing 320"
              lbb_wing2.text = "Wing 330"
              lbb_wing3.text = "Wing 340"
              lbb_wing4.text = "Wing 350"
              lbb_wing5.text = "Main Hallway"
          }else if(radioButton.text.toString().equals("4th Floor")){
              lbb_wings.visibility = View.VISIBLE
              lbb_wing5.visibility = View.VISIBLE
              lbb_wing1.text = "Wing 442"
              lbb_wing2.text = "Wing 445"
              lbb_wing3.text = "Wing 480"
              lbb_wing4.text = "Wing 460"
              lbb_wing5.text = "Main Hallway"
          }

        }
    }

}