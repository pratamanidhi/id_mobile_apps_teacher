package com.tugas_akhir.myapplication

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.test_layout.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class TestClass : AppCompatActivity(){


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_layout)
        val format1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("2021-08-09T16:26:09.371Z")
        val parsedDate_start = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(format1)
        val parsedDate2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").apply {
            this.timeZone = TimeZone.getTimeZone("UTC")
        }.parse(parsedDate_start)
        val time_start = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(parsedDate2)
        Log.e("mess", time_start)

    }


}

