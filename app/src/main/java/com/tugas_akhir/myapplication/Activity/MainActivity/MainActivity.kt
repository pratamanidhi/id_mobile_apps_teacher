package com.tugas_akhir.myapplication.Activity.MainActivity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.tugas_akhir.myapplication.Activity.Account.LoginActivity
import com.tugas_akhir.myapplication.Activity.BookingActivity.BookingActivity
import com.tugas_akhir.myapplication.Endpoint.Endpoint
import com.tugas_akhir.myapplication.R
import com.tugas_akhir.myapplication.Adapter.TeacherAdapter
import com.tugas_akhir.myapplication.Model.TeacherModel
import kotlinx.android.synthetic.main.activity_home_new.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.rv_orders
import org.json.JSONException
import org.json.JSONObject
import java.time.Duration
import java.time.Instant

class MainActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var mapel : Spinner
    lateinit var tingkat : Spinner
    lateinit var adapter : TeacherAdapter
    var list : ArrayList<TeacherModel> = ArrayList()
    var id_mapel : String = ""
    var id_tingkat : String = ""
    lateinit var shp : SharedPreferences
    lateinit var shpEditor: SharedPreferences.Editor
    val login = LoginActivity()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_new)
        context = this
        shp = this.getSharedPreferences(login.my_shared_preferences, Context.MODE_PRIVATE)
        seacrh()
    }

    private fun seacrh(){
        val tingkat = intent.getStringExtra("tingkat")
        btn_math.setOnClickListener {
            searchService("1",tingkat)
        }
        btn_english.setOnClickListener {
            searchService("2",tingkat)
        }
        btn_bahasa.setOnClickListener {
            searchService("3",tingkat)
        }
    }

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


//    private fun getTingkatData(){
//        val que = Volley.newRequestQueue(this)
//        val req = JsonObjectRequest(Request.Method.GET, Endpoint.TINGKAT, null, {
//            response ->
//            try {
//                val tingkat_list = ArrayList<String>()
//                val content = response.getJSONArray("content")
//                for (i in 0 until content.length()){
//                    val item = content.getJSONObject(i)
//                    val item_name = item.getString("nama_tingkat")
//                    tingkat_list.add(item_name)
//                }
//                tingkat = findViewById(R.id.spinner_tingkat)
//                val adapter = ArrayAdapter(
//                    this,
//                    android.R.layout.simple_spinner_item,
//                    tingkat_list
//                )
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                tingkat.adapter = adapter
//                tingkat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                        val item = tingkat.selectedItem.toString()
//                        val content = response.getJSONArray("content")
//                        for (i in 0 until content.length()){
//                            val content_item = content.getJSONObject(i)
//                            val item_name = content_item.getString("nama_tingkat")
//                            if (item == item_name){
//                                id_tingkat = content_item.getInt("id_tingkat").toString()
//                                Log.e("mess", id_tingkat)
//                            }
//                        }
//                    }
//                    override fun onNothingSelected(p0: AdapterView<*>?) {
//                        TODO("Not yet implemented")
//                    }
//                }
//            }catch (e : JSONException){
//                Log.e("ERROR", e.toString())
//            }
//        }, {
//            error ->
//            Log.e("ERROR", error.toString())
//        })
//        que.add(req)
//    }
//
//    private fun getMapelData(){
//        val que = Volley.newRequestQueue(this)
//        val req = JsonObjectRequest(Request.Method.GET, Endpoint.MAPEL, null, {
//            response ->
//            try {
//                val mapel_list = ArrayList<String>()
//                val content = response.getJSONArray("content")
//                for(i in 0 until content.length()){
//                    val item = content.getJSONObject(i)
//                    val item_name = item.getString("nama_mapel")
//                    mapel_list.add(item_name)
//                }
//                mapel = findViewById(R.id.spinner_mapel)
//                val adapter = ArrayAdapter(
//                    this,
//                    android.R.layout.simple_spinner_item,
//                    mapel_list
//                )
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                mapel.adapter = adapter
//                mapel.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                        val item = mapel.selectedItem.toString()
//                        val content = response.getJSONArray("content")
//                        for (i in 0 until content.length()){
//                            val content_item = content.getJSONObject(i)
//                            val item_name = content_item.getString("nama_mapel")
//                            if (item_name == item){
//                                id_mapel = content_item.getInt("id_mapel").toString()
//                                Log.e("mess", id_mapel)
//                            }
//                        }
//                    }
//                    override fun onNothingSelected(p0: AdapterView<*>?) {
//                        TODO("Not yet implemented")
//                    }
//                }
//            }catch (e : JSONException){
//                Log.e("ERROR", e.toString())
//            }
//        },{
//            error ->
//            Log.e("ERROR", error.toString())
//        })
//        que.add(req)
//    }

    private fun searchService(mapel : String, tingkat : String){
        val obj = JSONObject()
        obj.put("id_mapel", mapel)
        obj.put("id_tingkat", tingkat)
        Log.e("obj", obj.toString())
        val que = Volley.newRequestQueue(this)
        val req = JsonObjectRequest(Request.Method.POST, Endpoint.SEARCH_TEACHER, obj, {
            response ->
            try {

                list = ArrayList()
                val status = response.getString("status")
                if (status == "success"){
                    val content = response.getJSONArray("content")
                    for( i in 0 until content.length()){
                        val item = content.getJSONObject(i)
                        val id = item.getInt("id_guru").toString()
                        val name = item.getString("nama")
                        val phone_number = item.getString("nomer_telp")
                        val address = item.getString("alamat")
                        val id_mapel = item.getString("id_mapel")
                        val tingkat = item.getString("id_tingkat")
                        val latitude = item.getString("latitude")
                        val longitude = item.getString("longitude")
                        list.add(
                            TeacherModel(
                                id,
                                name,
                                phone_number,
                                address,
                                id_mapel,
                                tingkat,
                                latitude,
                                longitude
                            )
                        )
                        rv_orders.layoutManager = LinearLayoutManager(context)
                        adapter = TeacherAdapter(context, list)
                        rv_orders.adapter = adapter
                        rv_orders.visibility = View.VISIBLE
                    }
                }else{
                    rv_orders.visibility = View.GONE
                    Toast.makeText(this, "Content not found", Toast.LENGTH_LONG).show()
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

}