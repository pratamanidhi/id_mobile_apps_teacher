package com.tugas_akhir.myapplication.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.tugas_akhir.myapplication.Endpoint.Endpoint
import com.tugas_akhir.myapplication.Interface.Communicator
import com.tugas_akhir.myapplication.Model.BookingModel
import com.tugas_akhir.myapplication.Model.TeacherModel
import com.tugas_akhir.myapplication.R
import kotlinx.android.synthetic.main.list_teacher.view.*


class BookingAdapter( val context: Context, val datas : ArrayList<BookingModel>) : RecyclerView.Adapter<BookingAdapter.ViewHolder>() {
    lateinit var communicator: Communicator
    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_teacher, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = datas[position]
        holder.name.text = data.name
        holder.address.text = data.address
        holder.phone_number.text = data.hours
        holder.detail.text = "Durasi : ${data.duration}"
        holder.tingkat.text = data.days
        holder.txt_phone_number.text = "Jam Pelajaran :"
        holder.txt_tingkat.text = "Hari :"



        val mapel_id = data.id_mapel
        val que = Volley.newRequestQueue(context)
        val req = JsonObjectRequest(Request.Method.GET, Endpoint.MAPEL_LIST+mapel_id, null, {
                response ->
            val content = response.getJSONObject("content")
            val mapel = content.getString("nama_mapel")
            holder.mapel.text = mapel
        }, {
                error ->
        })
        que.add(req)


    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val name = view.edt_name
        val phone_number = view.edt_phone_number
        val address = view.edt_address
        val mapel = view.edt_matapelajaran
        val tingkat = view.edt_tingkat
        val detail = view.btn_detail
        val txt_phone_number = view.tv_phone_number
        val txt_tingkat = view.tv_tingkat


    }



}