package com.tugas_akhir.myapplication.Activity.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tugas_akhir.myapplication.Activity.MainActivity.HomeActivity
import com.tugas_akhir.myapplication.Interface.Communicator
import com.tugas_akhir.myapplication.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {
    lateinit var communicator: Communicator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false) as ViewGroup
        communicator = activity as Communicator
        view.btb_elementary.setOnClickListener {
            communicator.schoolType("1")
        }
        view.btb_juniorHigh.setOnClickListener {
            communicator.schoolType("2")
        }
        view.btb_seniorHigh.setOnClickListener {
            communicator.schoolType("3")
        }
        return view
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        btb_elementary.setOnClickListener {
//            (context as HomeActivity).addFragment(ClassFragment.newInstance())
//        }
//    }

    companion object {
        fun newInstance(): HomeFragment{
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}