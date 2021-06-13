package com.tugas_akhir.myapplication.Activity.BookingActivity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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
import org.json.JSONException
import org.json.JSONObject

class BookingActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var shp : SharedPreferences
    val login = LoginActivity()
    lateinit var adapter : BookingAdapter
    var list : ArrayList<BookingModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        context = this
        shp = this.getSharedPreferences(login.my_shared_preferences, Context.MODE_PRIVATE)
        val user_id = shp.getString("cst_id", login.cst_id).toString()
        booking_list(user_id)
    }

    fun booking_list(id : String){
        val obj = JSONObject()
        obj.put("user_id", id)
        val que = Volley.newRequestQueue(this)
        val req = JsonObjectRequest(Request.Method.POST, Endpoint.BOOKING_DETAIL, obj, {
            response->
            try {
                list = ArrayList()
                val content = response.getJSONArray("content")
                for (i in 0 until content.length()){
                    val item = content.getJSONObject(i)
                    val id = item.getString("client_id")
                    val date = item.getString("date")
                    val time = item.getString("time")
                    val latitude = item.getString("latitude")
                    val longitude = item.getString("longitude")
                    list.add(
                        BookingModel(
                            date,
                            time,
                            id,
                            latitude,
                            longitude
                        )
                    )
                    rv_orders.layoutManager = LinearLayoutManager(context)
                    adapter = BookingAdapter(context, list)
                    rv_orders.adapter = adapter
                }
            }catch (e : JSONException){

            }
        },{
            error->
        })
        que.add(req)
    }

}