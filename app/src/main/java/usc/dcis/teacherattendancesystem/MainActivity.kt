package usc.dcis.teacherattendancesystem

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val loginBtn = findViewById<Button>(R.id.loginBtn)

        loginBtn.setOnClickListener {
            var status=if(username.text.toString().toInt().equals(15102593)
                && password.text.toString().equals("test123")) "Logged in Successfully" else "Incorrect username/password."

          val text =  Toast.makeText(this, status, Toast.LENGTH_SHORT)
            text.show()
            check()
        }
    }

    fun check(){
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)

        // check username and password first

        // Create an Intent to start the second activity
        val menuActivity = Intent(this, Menu::class.java)

        // Start the new activity.
        startActivity(menuActivity)
    }
}
