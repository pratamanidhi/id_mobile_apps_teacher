package com.tugas_akhir.myapplication.Activity.Fragment

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.tugas_akhir.myapplication.Activity.Account.LoginActivity
import com.tugas_akhir.myapplication.Endpoint.Endpoint
import com.tugas_akhir.myapplication.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_booking.*
import org.json.JSONException
import org.json.JSONObject
import kotlin.math.E


class DetailFragment : Fragment() {
    var _id : String = ""
    var _hourId : String = ""
    var _dayId : String = ""
    var _durationId : String = ""
    var user_id : String = ""
    lateinit var shp : SharedPreferences
    lateinit var shpEditor: SharedPreferences.Editor
    val login = LoginActivity()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _id = arguments?.getString("Id").toString()
        shp = context!!.getSharedPreferences(login.my_shared_preferences, Context.MODE_PRIVATE)
        user_id = shp.getString("cst_id", login.cst_id).toString()
        Log.e("ID:", _id)
        return inflater.inflate(R.layout.fragment_booking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getHours()
        getDay()
        getDuration()
        btn_booking_.setOnClickListener {
            booking(_id, _hourId, _dayId,_durationId, user_id)
        }
    }

    fun getHours(){
        val que = Volley.newRequestQueue(context)
        val req = JsonObjectRequest(Request.Method.GET, Endpoint.HOURS, null, {
                response ->
            try {
                val status = response.getString("status")
                if (status == "success"){

                    val content = response.getJSONArray("content")
                    val _id_h1 = content.getJSONObject(0).getString("hour_id")
                    val _hour_1 = content.getJSONObject(0).getString("hours")
                    Log.e("hours", _hour_1)
                    hour_1_.text = _hour_1

                    val _id_h2 = content.getJSONObject(1).getString("hour_id")
                    val _hour_2 = content.getJSONObject(1).getString("hours")
                    hour_2_.text = _hour_2

                    val _id_h3 = content.getJSONObject(2).getString("hour_id")
                    val _hour_3 = content.getJSONObject(2).getString("hours")
                    hour_3_.text = _hour_3

                    val _id_h4 = content.getJSONObject(3).getString("hour_id")
                    val _hour_4 = content.getJSONObject(3).getString("hours")
                    hour_4_.text = _hour_4

                    hour_1_.setOnClickListener {
                        _hourId = _id_h1
                    }
                    hour_2_.setOnClickListener {
                        _hourId = _id_h2
                    }
                    hour_3_.setOnClickListener {
                        _hourId = _id_h3
                    }
                    hour_4_.setOnClickListener {
                        _hourId = _id_h4
                    }
                }
            }catch (e : JSONException){
                Log.e("ERROR", e.toString())
            }
        }, {
                error ->
            Log.e("ERROR", error.toString())
        })
        que.add(req)

    }
    fun getDay(){
        val que = Volley.newRequestQueue(context)
        val req = JsonObjectRequest(Request.Method.GET, Endpoint.DAYS, null, {
                response ->
            try {
                val status = response.getString("status")
                if (status == "success"){

                    val content = response.getJSONArray("content")
                    val _id_d1 = content.getJSONObject(0).getString("day_id")
                    val _day_1 = content.getJSONObject(0).getString("days")
                    day_1_.text = _day_1

                    val _id_d2 = content.getJSONObject(1).getString("day_id")
                    val _day_2 = content.getJSONObject(1).getString("days")
                    day_2_.text = _day_2

                    val _id_d3 = content.getJSONObject(2).getString("day_id")
                    val _day_3 = content.getJSONObject(2).getString("days")
                    day_3_.text = _day_3

                    val _id_d4 = content.getJSONObject(3).getString("day_id")
                    val _day_4 = content.getJSONObject(3).getString("days")
                    day_4_.text = _day_4

                    val _id_d5 = content.getJSONObject(4).getString("day_id")
                    val _day_5 = content.getJSONObject(4).getString("days")
                    day_5_.text = _day_5

                    day_1_.setOnClickListener {
                        _dayId = _id_d1
                    }
                    day_2_.setOnClickListener {
                        _dayId = _id_d2
                    }
                    day_3_.setOnClickListener {
                        _dayId = _id_d3
                    }
                    day_4_.setOnClickListener {
                        _dayId = _id_d4
                    }
                    day_5_.setOnClickListener {
                        _dayId = _id_d5
                    }

                }
            }catch (e : JSONException){
                Log.e("ERROR", e.toString())
            }
        }, {
                error ->
            Log.e("ERROR", error.toString())
        })
        que.add(req)
    }



    fun getDuration(){
        val que = Volley.newRequestQueue(context)
        val req = JsonObjectRequest(Request.Method.GET, Endpoint.DURATION, null, {
                response ->
            try {
                val status = response.getString("status")
                if (status == "success"){

                    val content = response.getJSONArray("content")
                    val _id_duration1 = content.getJSONObject(0).getString("duration_id")
                    val _duration_1 = content.getJSONObject(0).getString("duration")
                    duration_1_.text = _duration_1

                    val _id_duration2 = content.getJSONObject(1).getString("duration_id")
                    val _duration_2 = content.getJSONObject(1).getString("duration")
                    duration_2_.text = _duration_2

                    duration_1_.setOnClickListener {
                        _durationId = _id_duration1
                        Log.e("duration :", _durationId)
                    }
                    duration_2_.setOnClickListener {
                        _durationId = _id_duration2
                        Log.e("duration :", _durationId)
                    }


                }
            }catch (e : JSONException){
                Log.e("ERROR", e.toString())
            }
        }, {
                error ->
            Log.e("ERROR", error.toString())
        })
        que.add(req)
    }

    private fun booking(_teacherId : String, _hourId : String, _dayId : String,_durationId : String, _user_id : String){
        if (_teacherId != "" && _hourId != "" && _dayId != ""){
            val obj = JSONObject()
            obj.put("teacher_id", _teacherId)
            obj.put("hours_id", _hourId)
            obj.put("days_id", _dayId)
            obj.put("duration_id", _durationId)
            obj.put("user_id",_user_id)
            Log.e("JSON :", obj.toString())

            val que = Volley.newRequestQueue(context)
            val req = JsonObjectRequest(Request.Method.POST, Endpoint.BOOKING_TEACHER, obj, {
                    response ->
                try {
                    teacherDetail(_teacherId)
                }catch (e : JSONException){
                    Log.e("ERROR", e.toString())
                }
            },{
                    error ->
                Log.e("ERROR", error.toString())
            })
            que.add(req)
        } else{

            Toast.makeText(context, "Silakan pilih Jam, Hari dan Durasi anda !!", Toast.LENGTH_LONG).show()
        }

    }

    private fun teacherDetail(id : String){
        val que = Volley.newRequestQueue(context)
        val req = JsonObjectRequest(Request.Method.GET, Endpoint.SEARCH_TEACHER_ID(id), null, {
            response ->
            try {
                val status = response.getString("status")
                if (status == "success"){
                    val content = response.getJSONObject("content")
                    val name = content.getString("nama")
                    val subject = content.getString("nama_mapel")
                    dialog(name, subject)
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

    private fun dialog(_name : String, _subject : String){
        val builder = context?.let { AlertDialog.Builder(it) }
//        builder?.setTitle("Pemberitahuan")
        builder?.setMessage("Anda telah terdaftar pada mata pelajaran ${_subject} dengan guru ${_name}")
        builder?.setNeutralButton("OK"){
                dialogInterface, i ->
            dialogInterface.dismiss()
        }
        val alertDialog : AlertDialog? = builder?.create()
        if (alertDialog != null) {
            alertDialog.setCancelable(false)
        }
        if (alertDialog != null) {
            alertDialog.show()
        }
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