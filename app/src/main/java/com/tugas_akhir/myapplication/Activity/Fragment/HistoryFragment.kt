package com.tugas_akhir.myapplication.Activity.Fragment

import android.content.Context
import android.content.SharedPreferences
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
import com.tugas_akhir.myapplication.Activity.Account.LoginActivity
import com.tugas_akhir.myapplication.Adapter.BookingAdapter
import com.tugas_akhir.myapplication.Adapter.TeacherAdapter
import com.tugas_akhir.myapplication.Endpoint.Endpoint
import com.tugas_akhir.myapplication.Model.BookingModel
import com.tugas_akhir.myapplication.Model.TeacherModel
import com.tugas_akhir.myapplication.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_booking.*
import org.json.JSONException
import org.json.JSONObject

class HistoryFragment : Fragment() {
    lateinit var adapter : BookingAdapter
    var list : ArrayList<BookingModel> = ArrayList()
    var user_id : String = ""
    lateinit var shp : SharedPreferences
    lateinit var shpEditor: SharedPreferences.Editor
    val login = LoginActivity()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        shp = context!!.getSharedPreferences(login.my_shared_preferences, Context.MODE_PRIVATE)
        user_id = shp.getString("cst_id", login.cst_id).toString()
        Log.e("ID:", user_id)
        return inflater.inflate(R.layout.fragment_history, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBooking(user_id)

    }

    private fun showBooking(_userId : String){
        val obj = JSONObject()
        obj.put("user_id", _userId)
        val que = Volley.newRequestQueue(context)
        val req = JsonObjectRequest(Request.Method.POST, Endpoint.BOOKING_DETAIL, obj, {
                response ->
            try {
                val status = response.getString("status")
                if(status == "success"){
                    val content = response.getJSONArray("content")
                    for (i in 0 until  content.length()){
                        val item = content.getJSONObject(i)
                        val name = item.getString("nama")
                        val address = item.getString("alamat")
                        val id_mapel = item.getString("id_mapel")
                        val hours = item.getString("hours")
                        val days = item.getString("days")
                        val duration = item.getString("duration")
                        list.add(
                            BookingModel(
                                name,
                                address,
                                id_mapel,
                                hours,
                                days,
                                duration
                            )
                        )
                        rv_orders.layoutManager = LinearLayoutManager(context)
                        adapter = context?.let { BookingAdapter(it, list) }!!
                        rv_orders.adapter = adapter
                        rv_orders.visibility = View.VISIBLE

                    }
                }
                else{
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
        fun newInstance(): HistoryFragment {
            val fragment = HistoryFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}