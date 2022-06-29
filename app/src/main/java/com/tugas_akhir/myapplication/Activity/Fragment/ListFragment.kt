package com.tugas_akhir.myapplication.Activity.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.tugas_akhir.myapplication.Activity.MainActivity.HomeActivity
import com.tugas_akhir.myapplication.Adapter.TeacherAdapter
import com.tugas_akhir.myapplication.Endpoint.Endpoint
import com.tugas_akhir.myapplication.Model.TeacherModel
import com.tugas_akhir.myapplication.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONException
import org.json.JSONObject

class ListFragment : Fragment() {
    lateinit var adapter : TeacherAdapter
    var list : ArrayList<TeacherModel> = ArrayList()
    var type : String = ""
    var subject : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        type = arguments?.getString("type").toString()
        subject = arguments?.getString("subject").toString()
        Log.e("type / subject ", "${type} / ${subject}")
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchService(type,subject)
    }

    private fun searchService(mapel : String, tingkat : String){
        val obj = JSONObject()
        obj.put("id_mapel", mapel)
        obj.put("id_tingkat", tingkat)
        Log.e("obj", obj.toString())
        val que = Volley.newRequestQueue(context)
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
                        adapter = context?.let { TeacherAdapter(it, list) }!!
                        rv_orders.adapter = adapter
                        rv_orders.visibility = View.VISIBLE
                    }
                }else{
                    rv_orders.visibility = View.GONE
                    Toast.makeText(context, "Content not found", Toast.LENGTH_LONG).show()
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

    companion object {
        fun newInstance(): ListFragment {
            val fragment = ListFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}