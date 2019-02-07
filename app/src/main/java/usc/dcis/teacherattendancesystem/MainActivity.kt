package usc.dcis.teacherattendancesystem

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginBtn = findViewById<Button>(R.id.loginBtn)

    }

    fun check(view: View){
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)

        // check username and password first

        // Create an Intent to start the second activity
        val menuActivity = Intent(this, Menu::class.java)

        // Start the new activity.
        startActivity(menuActivity)
    }
}
