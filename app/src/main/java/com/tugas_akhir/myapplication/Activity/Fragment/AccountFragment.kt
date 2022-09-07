package com.tugas_akhir.myapplication.Activity.Fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tugas_akhir.myapplication.Activity.Account.LoginActivity
import com.tugas_akhir.myapplication.R
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : Fragment() {
    lateinit var shp : SharedPreferences
    lateinit var shpEditor: SharedPreferences.Editor
    val login = LoginActivity()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shp = context!!.getSharedPreferences(login.my_shared_preferences, Context.MODE_PRIVATE)
        isLogout()
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
        startActivity(Intent(activity, LoginActivity::class.java))
        activity?.finish()
    }
    companion object {
        fun newInstance(): ListFragment {
            val fragment = ListFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}