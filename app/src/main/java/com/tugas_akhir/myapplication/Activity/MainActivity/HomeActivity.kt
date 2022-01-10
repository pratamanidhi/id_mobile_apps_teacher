package com.tugas_akhir.myapplication.Activity.MainActivity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tugas_akhir.myapplication.Activity.Account.LoginActivity
import com.tugas_akhir.myapplication.Activity.BookingActivity.BookingActivity
import com.tugas_akhir.myapplication.R
import kotlinx.android.synthetic.main.activity_choose.*

class HomeActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var shp : SharedPreferences
    lateinit var shpEditor: SharedPreferences.Editor
    val login = LoginActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)
        context = this
        shp = this.getSharedPreferences(login.my_shared_preferences, Context.MODE_PRIVATE)
        button()
        isLogout()
    }

    private fun button(){
        btb_elementary.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("tingkat","1")
            startActivity(intent)
        }
        btb_juniorHigh.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("tingkat","2")
            startActivity(intent)
        }

        btb_seniorHigh.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("tingkat","3")
            startActivity(intent)
        }

        btn_booking.setOnClickListener {
            startActivity(Intent(this, BookingActivity :: class.java))
        }

    }

    private fun isLogout(){
        btn_logout.setOnClickListener {
            logoutFunc()
        }
    }

    private fun logoutFunc(){
        shpEditor = shp.edit()
        shpEditor.putBoolean(login.sessionStatus, false)
        shpEditor.commit()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}