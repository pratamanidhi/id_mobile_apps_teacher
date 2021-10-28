package com.tugas_akhir.myapplication
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tugas_akhir.myapplication.R
import com.tugas_akhir.myapplication.TestClass2
import kotlinx.android.synthetic.main.test_layout3.*

class Testjava : AppCompatActivity(){
    lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_layout3)
        context = this
        test_btn.setOnClickListener {
            val test = test_edt.text.trim()
            val intent = Intent(this, TestClass2::class.java)
            intent.putExtra("value", test)
            startActivity(intent)
        }
    }

}