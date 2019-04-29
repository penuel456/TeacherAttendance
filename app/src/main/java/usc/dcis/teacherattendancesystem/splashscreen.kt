package usc.dcis.teacherattendancesystem

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.nav_header_menu.*
import usc.dcis.teacherattendancesystem.R
import java.lang.Thread.sleep

class splashscreen : AppCompatActivity() {
    lateinit private var SASLogo : ImageView
    lateinit private var DCISLogo :ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
         SASLogo = findViewById(R.id.sas)
         DCISLogo = findViewById(R.id.dcis)
        var myanim = AnimationUtils.loadAnimation(this, R.anim.mytransition)
        SASLogo.startAnimation(myanim)
        DCISLogo.startAnimation(myanim)
        val i = Intent(this, MainActivity::class.java)
        val timer = Thread{
            run(i)
        }
        timer.start()
    }
    fun run(i:Intent){
        try{
            sleep(3000)
        }catch(e: InterruptedException){
            e.printStackTrace()
        }
        finally{
            startActivity(i)
            finish()
        }
    }
}

