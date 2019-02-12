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


    }

    fun check(view : View){
        //val username = findViewById<EditText>(R.id.username)
        //val password = findViewById<EditText>(R.id.password)

        // check username and password first
        val userLists = MutableList(5) { userList() }

        val status = if (!username.text.toString().isBlank() && username.text.toString().toInt().equals(userLists[0].idnumber)
            && password.text.toString().equals(userLists[0].password)) "Logged in Successfully"
        else if (username.text.toString().isBlank() || password.text.toString().isBlank()) "Please enter an input in the empty fields."
        else "Incorrect username/password."

        val text = Toast.makeText(this, status, Toast.LENGTH_SHORT)
        text.show()

        // Create an Intent to start the second activity
        if (!username.text.toString().isBlank() && username.text.toString().toInt().equals(userLists[0].idnumber) && password.text.toString().equals(userLists[0].password)) {
            val menuActivity = Intent(this, Menu::class.java)

            // Start the new activity.
            startActivity(menuActivity)
        }
    }

    private fun userList() = object {
        val idnumber: Int = 15102593
        val password: String = "test123"
    }
}
