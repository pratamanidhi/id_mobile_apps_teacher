package com.tugas_akhir.myapplication.Activity.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.tugas_akhir.myapplication.Activity.MainActivity.HomeActivity
import com.tugas_akhir.myapplication.Endpoint.Endpoint
import com.tugas_akhir.myapplication.Interface.Communicator
import com.tugas_akhir.myapplication.R
import kotlinx.android.synthetic.main.fragment_class.*
import kotlinx.android.synthetic.main.fragment_class.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONException

class ClassFragment : Fragment() {
    lateinit var communicator: Communicator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_class, container, false) as ViewGroup
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val _text = arguments?.getString("text")
        getSubjects(_text!!)
    }

    private  fun getSubjects(_level : String){
        val que = Volley.newRequestQueue(context)
        val req = JsonObjectRequest(Request.Method.GET, Endpoint.MAPEL, null, {
            response ->
            try {
                val status = response.getString("status")
                if(status == "success"){
                    communicator = activity as Communicator
                    val content = response.getJSONArray("content")
                    val  _idMath = content.getJSONObject(0).getString("id_mapel")
                    val _math = content.getJSONObject(0).getString("nama_mapel")
                    btb_math.text = _math

                    val  _idEng = content.getJSONObject(1).getString("id_mapel")
                    val _eng = content.getJSONObject(1).getString("nama_mapel")
                    btb_eng.text = _eng

                    val  _idBahasa = content.getJSONObject(2).getString("id_mapel")
                    val _bahasa = content.getJSONObject(2).getString("nama_mapel")
                    btb_bahasa.text = _bahasa

                    btb_math.setOnClickListener {
                        communicator.params(_level,_idMath)
                    }
                    btb_eng.setOnClickListener {
                        communicator.params(_level,_idEng)
                    }
                    btb_bahasa.setOnClickListener {
                        communicator.params(_level,_idBahasa)
                    }
                }
            }catch (e : JSONException){
                Log.e("ERROR", e.toString())
            }
        },{
            error ->
            Log.e("ERROR", error.toString())
        })
        que.add(req)
    }


    companion object {
        fun newInstance(): ClassFragment{
            val fragment = ClassFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}