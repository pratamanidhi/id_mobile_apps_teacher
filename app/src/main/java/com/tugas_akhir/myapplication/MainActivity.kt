package com.tugas_akhir.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var mapel : Spinner
    lateinit var tingkat : Spinner
    lateinit var adapter : TeacherAdapter
    var list : ArrayList<TeacherModel> = ArrayList()
    var id_mapel : String = ""
    var id_tingkat : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        getTingkatData()
        getMapelData()
        seacrh()
    }

    private fun seacrh(){
        btn_search.setOnClickListener {
            val obj = JSONObject()
            obj.put("id_mapel", id_mapel)
            obj.put("id_tingkat", id_tingkat)
            searchService(obj)

        }
    }


    private fun getTingkatData(){
        val que = Volley.newRequestQueue(this)
        val req = JsonObjectRequest(Request.Method.GET, Endpoint.TINGKAT, null, {
            response ->
            try {
                val tingkat_list = ArrayList<String>()
                val content = response.getJSONArray("content")
                for (i in 0 until content.length()){
                    val item = content.getJSONObject(i)
                    val item_name = item.getString("nama_tingkat")
                    tingkat_list.add(item_name)
                }
                tingkat = findViewById(R.id.spinner_tingkat)
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    tingkat_list
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                tingkat.adapter = adapter
                tingkat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        val item = tingkat.selectedItem.toString()
                        val content = response.getJSONArray("content")
                        for (i in 0 until content.length()){
                            val content_item = content.getJSONObject(i)
                            val item_name = content_item.getString("nama_tingkat")
                            if (item == item_name){
                                id_tingkat = content_item.getInt("id_tingkat").toString()
                            }
                        }
                    }
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
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

    private fun getMapelData(){
        val que = Volley.newRequestQueue(this)
        val req = JsonObjectRequest(Request.Method.GET, Endpoint.MAPEL, null, {
            response ->
            try {
                val mapel_list = ArrayList<String>()
                val content = response.getJSONArray("content")
                for(i in 0 until content.length()){
                    val item = content.getJSONObject(i)
                    val item_name = item.getString("nama_mapel")
                    mapel_list.add(item_name)
                }
                mapel = findViewById(R.id.spinner_mapel)
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    mapel_list
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                mapel.adapter = adapter
                mapel.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        val item = mapel.selectedItem.toString()
                        val content = response.getJSONArray("content")
                        for (i in 0 until content.length()){
                            val content_item = content.getJSONObject(i)
                            val item_name = content_item.getString("nama_mapel")
                            if (item_name == item){
                                id_mapel = content_item.getInt("id_mapel").toString()
                            }
                        }
                    }
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
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

    private fun searchService(obj : JSONObject){
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
                        val name = item.getString("nama")
                        val phone_number = item.getString("nomer_telp")
                        val address = item.getString("alamat")
                        val id_mapel = item.getString("id_mapel")
                        val tingkat = item.getString("id_tingkat")
                        list.add(
                            TeacherModel(
                                name,
                                phone_number,
                                address,
                                id_mapel,
                                tingkat
                            )
                        )
                        rv_orders.layoutManager = LinearLayoutManager(context)
                        adapter = TeacherAdapter(context, list)
                        rv_orders.adapter = adapter
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