package com.tugas_akhir.myapplication.Activity.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tugas_akhir.myapplication.Activity.MainActivity.HomeActivity
import com.tugas_akhir.myapplication.Interface.Communicator
import com.tugas_akhir.myapplication.R
import kotlinx.android.synthetic.main.fragment_class.*
import kotlinx.android.synthetic.main.fragment_class.view.*
import kotlinx.android.synthetic.main.fragment_home.*

class ClassFragment : Fragment() {
    lateinit var communicator: Communicator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_class, container, false) as ViewGroup
        val text = arguments?.getString("text")
        communicator = activity as Communicator
        view.btb_math.setOnClickListener {
            if (text != null) {
                communicator.params(text,"1")
            }
        }
        view.btb_eng.setOnClickListener {
            if (text != null) {
                communicator.params(text,"2")
            }
        }
        view.btb_bahasa.setOnClickListener {
            if (text != null) {
                communicator.params(text,"3")
            }
        }
        return view
    }
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        btb_eng.setOnClickListener {
//            (context as HomeActivity).addFragment(ListFragment.newInstance())
//        }
//    }


    companion object {
        fun newInstance(): ClassFragment{
            val fragment = ClassFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}