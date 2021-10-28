package com.tugas_akhir.myapplication

import android.R.attr
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.test_layout2.*


class TestClass2 : AppCompatActivity() {
    lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_layout2)
        context = this
        setPickup()
        val test = intent.getStringExtra("value")
        test_tv.text = test
        Log.e("mess", test)

    }

    fun setPickup(){
        btn_pickup.setOnClickListener {
            val intent = Intent(this, TestClass::class.java)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 1) {
            if (resultCode === RESULT_OK) {
                edt_pickup.setText(data?.getStringExtra("editTextValue"))
                val lat = data?.getStringExtra("lat")
                val lon = data?.getStringExtra("lon")
                Log.e("mess", lat + " / " + lon)
            }
        }
    }


}