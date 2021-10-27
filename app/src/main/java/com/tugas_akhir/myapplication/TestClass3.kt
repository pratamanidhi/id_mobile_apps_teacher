package com.tugas_akhir.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.test_layout3.*

class TestClass3 : AppCompatActivity() {
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_layout3)
        context = this
        test_btn.setOnClickListener {
            val text = test_edt.toString()
            val intent = Intent(this, TestClass2::class.java)
            intent.putExtra("value", text)
            startActivity(intent)
        }
    }
}