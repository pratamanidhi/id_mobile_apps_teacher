package com.tugas_akhir.myapplication.Activity.DetailActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tugas_akhir.myapplication.Activity.BookingActivity.StudentInputActivity
import com.tugas_akhir.myapplication.Endpoint.Endpoint
import com.tugas_akhir.myapplication.R
import kotlinx.android.synthetic.main.activity_detail.*
import org.json.JSONException

class DetailActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var context: Context
    private lateinit var mMap: GoogleMap
    var id_mapel : String =""
    var id_tingkat : String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        context = this
        this.supportActionBar?.hide()
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        val id = intent.getStringExtra("id")
        getDetail(id)
        bookingGuru(id)
    }

    private fun bookingGuru(id : String){
        btn_booking.setOnClickListener {
            val intent = Intent(this, StudentInputActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val lat = intent.getStringExtra("latitude").toDouble()
        val lng = intent.getStringExtra("longitude").toDouble()
        if (googleMap != null) {
            mMap = googleMap
        }
        val location = LatLng(lat,lng)
        mMap.addMarker(MarkerOptions().position(location).title("Lokasi"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12.0f))
        mMap.uiSettings.isRotateGesturesEnabled = false
        mMap.uiSettings.isZoomGesturesEnabled = false
        mMap.uiSettings.setAllGesturesEnabled(false)
    }

    private fun getDetail(id : String){
        val url  = Endpoint.SEARCH_TEACHER_ID(id)
        val que = Volley.newRequestQueue(this)
        val req = JsonObjectRequest(Request.Method.GET, url, null,{
            response ->
            try {
                val content = response.getJSONObject("content")
                val name  = content.getString("nama")
                val address = content.getString("alamat")
                val phoneNumber = content.getString("nomer_telp")
                id_mapel = content.getString("id_mapel")
                id_tingkat = content.getString("id_tingkat")
                edt_name.text = name
                edt_address.text = address
                edt_phone.text = phoneNumber
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

    private fun getMapel(id : String){
        val que = Volley.newRequestQueue(context)
        val req = JsonObjectRequest(Request.Method.GET, Endpoint.MAPEL_LIST+id, null, {
            response ->
            val content = response.getJSONObject("content")
            val mapel = content.getString("nama_mapel")
            edt_mapel.text = mapel

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
            edt_tingkat.text = tingkat
        }, {
            error ->
        })
        que.add(req)
    }

}