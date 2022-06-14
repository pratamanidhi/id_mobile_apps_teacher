package com.tugas_akhir.myapplication.Activity.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tugas_akhir.myapplication.R

class DetailFragment : Fragment() {
    var _id : String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _id = arguments?.getString("Id").toString()
        Log.e("ID:", _id)
        return inflater.inflate(R.layout.fragment_booking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    companion object {
        fun newInstance(): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}