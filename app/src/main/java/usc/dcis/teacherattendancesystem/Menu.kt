package usc.dcis.teacherattendancesystem

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.app_bar_menu.*

class Menu : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        displayFragment(-1)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_logout -> {
                logout()
                //else -> return super.onOptionsItemSelected(item)
            }
        }

        return true
    }

    fun displayFragment(id: Int){
        val fragment = when(id){
            R.id.nav_lbb -> {
                setTitle("Lawrence Bunzel Building")
                Menu_fragment_lbb()
            }
            R.id.nav_smed -> {
                setTitle("SMED Building")
                Menu_fragment_smed()
            }
            R.id.nav_safad -> {
                setTitle("SAFAD Building")
                Menu_fragment_safad()
            }
            R.id.nav_bct -> {
                setTitle("Basketball Court")
                Menu_fragment_bct()
            }
            R.id.nav_pe -> {
                setTitle("Philip van Engelen Building")
                Menu_fragment_pe()
            }
            R.id.nav_lrc -> {
                setTitle("Josef Baumgartner Building")
                Menu_fragment_lrc()
            }
            R.id.nav_schedule -> {
                setTitle("Schedule")
                Menu_fragment_schedListStudent()
            }
            R.id.nav_schedule_teacher -> {
                setTitle("Schedule")
                Menu_fragment_schedListTeacher()
            }
            else -> {
               setTitle("Schedule")
                Menu_fragment_schedListStudent()
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        displayFragment(item.itemId)

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun logout(){
        // Create an Intent to start the second activity
        val mainActivity = Intent(this, MainActivity::class.java)

        // Start the new activity.
        startActivity(mainActivity)
    }
    fun Edit_Sched(){
        val edit_Sched = Intent(this, activity_editschedule::class.java)
        Log.d("Click", "Ngano di man ko mu abli")
        startActivity(edit_Sched)
    }
}
