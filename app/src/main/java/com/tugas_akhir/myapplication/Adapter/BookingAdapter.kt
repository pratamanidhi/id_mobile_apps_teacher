package com.tugas_akhir.myapplication.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.tugas_akhir.myapplication.Activity.DetailActivity.DetailActivity
import com.tugas_akhir.myapplication.Endpoint.Endpoint
import com.tugas_akhir.myapplication.Model.BookingModel
import com.tugas_akhir.myapplication.R
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.list_booking.view.*
import kotlinx.android.synthetic.main.list_teacher.view.btn_detail
import kotlinx.android.synthetic.main.list_teacher.view.edt_address
import kotlinx.android.synthetic.main.list_teacher.view.edt_matapelajaran
import kotlinx.android.synthetic.main.list_teacher.view.edt_name
import kotlinx.android.synthetic.main.list_teacher.view.edt_phone_number
import kotlinx.android.synthetic.main.list_teacher.view.edt_tingkat
import org.json.JSONException

class BookingAdapter (val context: Context,val datas : ArrayList<BookingModel>) : RecyclerView.Adapter<BookingAdapter.ViewHolder>(){
    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_booking, parent, false))
    }

    override fun onBindViewHolder(holder: BookingAdapter.ViewHolder, position: Int) {
        val data = datas[position]
        holder.date.text = data.date
        holder.time.text = data.time
        holder.getDetail(data.id)
        holder.detail.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("latitude", data.latitude)
            intent.putExtra("longitude", data.longitude)
            intent.putExtra("id", data.id)
            context.startActivity(intent)
        }
    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val date = view.edt_date
        val time = view.edt_time
        val name = view.edt_name
        val phone_number = view.edt_phone_number
        val address = view.edt_address
        val mapel = view.edt_matapelajaran
        val tingkat = view.edt_tingkat
        val detail = view.btn_detail

        fun getDetail(id : String){
            val url  = Endpoint.SEARCH_TEACHER_ID(id)
            val que = Volley.newRequestQueue(context)
            val req = JsonObjectRequest(Request.Method.GET, url, null,{
                    response ->
                try {
                    val content = response.getJSONObject("content")
                    val name_db  = content.getString("nama")
                    val address_db = content.getString("alamat")
                    val phoneNumber_db = content.getString("nomer_telp")
                    val id_mapel = content.getString("id_mapel")
                    val id_tingkat = content.getString("id_tingkat")
                    name.text = name_db
                    address.text = address_db
                    phone_number.text = phoneNumber_db
                    getMapel(id_mapel)
                    getTingkat(id_tingkat)
                }catch (e : JSONException){
                    Log.e("ERROR", e.toString())
                }
            }, {
                    error ->
                Log.e("ERROR", error.toString())
            })
            que.add(req)
        }

        fun getMapel(id : String){
            val que = Volley.newRequestQueue(context)
            val req = JsonObjectRequest(Request.Method.GET, Endpoint.MAPEL_LIST+id, null, {
                    response ->
                val content = response.getJSONObject("content")
                val mapel_db = content.getString("nama_mapel")
                mapel.text = mapel_db

            }, {
                    error ->
            })
            que.add(req)
        }

        fun getTingkat(id : String){
            val que = Volley.newRequestQueue(context)
            val req = JsonObjectRequest(Request.Method.GET, Endpoint.TINGKAT_LIST+id, null, {
                    response ->
                val content = response.getJSONObject("content")
                val tingkat_db = content.getString("nama_tingkat")
                tingkat.text = tingkat_db
            }, {
                    error ->
            })
            que.add(req)
        }
    }

}