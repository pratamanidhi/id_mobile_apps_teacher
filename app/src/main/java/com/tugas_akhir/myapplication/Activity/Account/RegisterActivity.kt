package com.tugas_akhir.myapplication.Activity.Account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.tugas_akhir.myapplication.Endpoint.Endpoint
import com.tugas_akhir.myapplication.R
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONException
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_register)
        super.onCreate(savedInstanceState)
        context = this
        onRegister()
    }

    fun onRegister(){
        btn_register.setOnClickListener {
            register(username.text.toString(), password.text.toString())
        }
    }

    fun register (username : String, password : String){
        val obj = JSONObject()
        obj.put("username", username)
        obj.put("password", password)

        val que = Volley.newRequestQueue(this)
        val req = JsonObjectRequest(Request.Method.POST, Endpoint.USER_REGISTER, obj, {
            response ->
            try {
                Log.e("mess", response.toString())
                if (response.getString("response") == "success"){
                    Toast.makeText(this, "Registrasi Berhasil", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Registrasi Gagal", Toast.LENGTH_LONG).show()
                }

            }catch (e : JSONException){
                Log.e("ERROR", e.toString())
            }
        }, {
            error->

        })
        que.add(req)
    }
}