package com.tugas_akhir.myapplication.Activity.BookingActivity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.tugas_akhir.myapplication.Activity.Account.LoginActivity
import com.tugas_akhir.myapplication.Activity.MainActivity.MainActivity
import com.tugas_akhir.myapplication.Endpoint.Endpoint
import com.tugas_akhir.myapplication.R
import com.tugas_akhir.myapplication.Util.Util.Companion.showCalendar
import com.tugas_akhir.myapplication.Util.Util.Companion.showTimePicker
import kotlinx.android.synthetic.main.activity_student_input.*
import org.json.JSONException
import org.json.JSONObject

class StudentInputActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var shp : SharedPreferences
    lateinit var shpEditor: SharedPreferences.Editor
    val login = LoginActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_input)
        context = this
        shp = this.getSharedPreferences(login.my_shared_preferences, Context.MODE_PRIVATE)
        val id = intent.getStringExtra("id")
        getTeacherDetail(id)
        val user_id = shp.getString("cst_id", login.cst_id).toString()
        getUserDetail(user_id)
        pickedTime()
        pickedDate()
        Booking(user_id, id)
    }

    private fun Booking(
        user_id : String,
        client_id : String,
    ){
        btn_booking.setOnClickListener {
            val name = edt_studentName.text.toString()
            val time = edt_jam.text.toString()
            val date = edt_tanggal.text.toString()
            bookingTeacher(user_id, client_id, name, time, date)

        }
    }

    private fun pickedTime(){
        edt_jam.setOnClickListener { showTimePicker(this, edt_jam) }
        edt_jam.setOnFocusChangeListener{ _, hasFocus ->
            if(hasFocus){
                showTimePicker(this,edt_jam)
            }
        }
    }

    private fun pickedDate(){
        edt_tanggal.setOnClickListener { showCalendar(this,edt_tanggal) }
        edt_tanggal.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus){
                showCalendar(this,edt_tanggal)
            }
        }
    }


    fun getUserDetail(id : String){
        val que = Volley.newRequestQueue(this)
        val req  = JsonObjectRequest(Request.Method.GET, Endpoint.SEARCH_USER_ID(id), null, {
            response->
            try {
                Log.e("user", response.toString())
                val content = response.getJSONArray("content")
                val item = content.getJSONObject(0)
                val name = item.getString("name")
                Log.e("mess", name)
                edt_studentName.setText(name)
            }catch (e : JSONException){

            }
        }, {
            error->
        })
        que.add(req)
    }

    private fun getTeacherDetail(id : String){
        val que = Volley.newRequestQueue(this)
        val req = JsonObjectRequest(Request.Method.GET, Endpoint.SEARCH_TEACHER_ID(id), null, {
            response ->
            try {
                val content = response.getJSONObject("content")
                val name = content.getString("nama")
                val address = content.getString("alamat")
                val id_mapel = content.getString("id_mapel")
                val id_tingkat = content.getString("id_tingkat")

                edt_teacherName.setText(name)
                edt_teacherAddress.setText(address)
                getMapel(id_mapel)
                getTingkat(id_tingkat)
            }catch (e : JSONException){

            }
        }, {
            error ->
        })
        que.add(req)
    }

    private fun getMapel(id : String){
        val que = Volley.newRequestQueue(context)
        val req = JsonObjectRequest(Request.Method.GET, Endpoint.MAPEL_LIST+id, null, {
                response ->
            val content = response.getJSONObject("content")
            val mapel = content.getString("nama_mapel")
            edt_teacherMapel.setText(mapel)


        }, {
                error ->
        })
        que.add(req)
    }

    private fun getTingkat(id : String){
        val que = Volley.newRequestQueue(context)
        val req = JsonObjectRequest(Request.Method.GET, Endpoint.TINGKAT_LIST+id, null, {
                response ->
            val content = response.getJSONObject("content")
            val tingkat = content.getString("nama_tingkat")
            edt_teacherTingkat.setText(tingkat)

        }, {
                error ->
        })
        que.add(req)
    }

    private fun bookingTeacher(
        user_id : String,
        client_id : String,
        user_name : String,
        time : String,
        date : String
    ){
        val obj = JSONObject()
        obj.put("user_id", user_id)
        obj.put("client_id", client_id)
        obj.put("user_name", user_name)
        obj.put("time", time)
        obj.put("date",date)

        val que = Volley.newRequestQueue(this)
        val req = JsonObjectRequest(Request.Method.POST, Endpoint.BOOKING_TEACHER, obj, {
            response ->
            try {
                Toast.makeText(this, "Sukses untuk memesan guru", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }catch (e : JSONException){
                Log.e("ERROR", e.toString())
                Toast.makeText(this, "Terjadi kesalahan dalam pemesanan", Toast.LENGTH_LONG).show()
            }
        }, {
            error ->
            Log.e("ERROR", error.toString())
            Toast.makeText(this, "Terjadi kesalahan dalam pemesanan", Toast.LENGTH_LONG).show()
        })
        que.add(req)
    }
}