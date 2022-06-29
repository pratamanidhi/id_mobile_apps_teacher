package com.tugas_akhir.myapplication.Activity.MainActivity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tugas_akhir.myapplication.Activity.Account.LoginActivity
import com.tugas_akhir.myapplication.Activity.BookingActivity.BookingActivity
import com.tugas_akhir.myapplication.Activity.Fragment.*
import com.tugas_akhir.myapplication.Interface.Communicator
import com.tugas_akhir.myapplication.R
import kotlinx.android.synthetic.main.activity_choose.*

class HomeActivity : AppCompatActivity(),Communicator {
    lateinit var context: Context
//    lateinit var shp : SharedPreferences
//    lateinit var shpEditor: SharedPreferences.Editor
//    val login = LoginActivity()

    private var content: FrameLayout? = null


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId){
            R.id.home -> {
                val fragment = HomeFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false

    }
    fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.content, fragment, fragment.javaClass.getSimpleName())
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)
        context = this
        navigation.setOnNavigationItemReselectedListener { mOnNavigationItemSelectedListener }
        val fragment = WelcomeFragment.newInstance()
        addFragment(fragment)
//        shp = this.getSharedPreferences(login.my_shared_preferences, Context.MODE_PRIVATE)
//        button()
//        isLogout()
    }

    override fun _schoolType() {
        val transaction = this.supportFragmentManager.beginTransaction()
        val fragment = HomeFragment()
        transaction.replace(R.id.content,fragment)
        transaction.addToBackStack(null)
//        transaction.remove(fragment)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }


    override fun classType(text: String) {
        val bundle = Bundle()
        bundle.putString("text", text)
        val transaction = this.supportFragmentManager.beginTransaction()
        val fragment = ClassFragment()
        fragment.arguments = bundle
        transaction.replace(R.id.content,fragment)
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }

    override fun _teacherId(id : String){
        val bundle = Bundle()
        bundle.putString("Id", id)
        val transaction = this.supportFragmentManager.beginTransaction()
        val fragment = DetailFragment()
        fragment.arguments = bundle
        transaction.replace(R.id.content, fragment)
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }

    override fun params(type: String, subject: String) {
        val bundle = Bundle()
        bundle.putString("type",type)
        bundle.putString("subject", subject)
        val transaction = this.supportFragmentManager.beginTransaction()
        val fragment = ListFragment()
        fragment.arguments = bundle
        transaction.replace(R.id.content,fragment)
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }

//    private fun button(){
//        btb_elementary.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            intent.putExtra("tingkat","1")
//            startActivity(intent)
//        }
//        btb_juniorHigh.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            intent.putExtra("tingkat","2")
//            startActivity(intent)
//        }
//
//        btb_seniorHigh.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            intent.putExtra("tingkat","3")
//            startActivity(intent)
//        }
//
//        btn_booking.setOnClickListener {
//            startActivity(Intent(this, BookingActivity :: class.java))
//        }
//
//    }
//
//    private fun isLogout(){
//        btn_logout.setOnClickListener {
//            logoutFunc()
//        }
//    }
//
//    private fun logoutFunc(){
//        shpEditor = shp.edit()
//        shpEditor.putBoolean(login.sessionStatus, false)
//        shpEditor.commit()
//        startActivity(Intent(this, LoginActivity::class.java))
//        finish()
//    }
}