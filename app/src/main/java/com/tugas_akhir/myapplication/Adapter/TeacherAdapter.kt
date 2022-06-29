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
import com.tugas_akhir.myapplication.Model.TeacherModel
import com.tugas_akhir.myapplication.R
import kotlinx.android.synthetic.main.list_teacher.view.*


class TeacherAdapter( val context: Context, val datas : ArrayList<TeacherModel>) : RecyclerView.Adapter<TeacherAdapter.ViewHolder>() {
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
        holder.phone_number.text = data.phone_number
        holder.address.text = data.address

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

        val tingkat_id = data.id_tingkat
        val que1 = Volley.newRequestQueue(context)
        val req1 = JsonObjectRequest(Request.Method.GET, Endpoint.TINGKAT_LIST+tingkat_id, null, {
            response ->
            val content = response.getJSONObject("content")
            val tingkat = content.getString("nama_tingkat")
            holder.tingkat.text = tingkat
        }, {
            error ->
        })
        que1.add(req1)
        communicator = context as Communicator
        holder.detail.setOnClickListener {
//            val intent = Intent(context, DetailActivity::class.java)
//            intent.putExtra("latitude", data.latitude)
//            intent.putExtra("longitude", data.longitude)
//            intent.putExtra("id", data.id)
//            context.startActivity(intent)
            communicator._teacherId(data.id)
            (context as Activity).finish()

        }

    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val name = view.edt_name
        val phone_number = view.edt_phone_number
        val address = view.edt_address
        val mapel = view.edt_matapelajaran
        val tingkat = view.edt_tingkat
        val detail = view.btn_detail
    }



}