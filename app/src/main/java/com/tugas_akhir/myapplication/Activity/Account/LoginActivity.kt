package com.tugas_akhir.myapplication.Activity.Account

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.tugas_akhir.myapplication.Endpoint.Endpoint
import com.tugas_akhir.myapplication.Activity.MainActivity.MainActivity
import com.tugas_akhir.myapplication.R
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var shp : SharedPreferences
    lateinit var shpEditor: SharedPreferences.Editor
    var session:Boolean = false
    var cst_id : String = ""
    val sessionStatus:String = "session_status"
    val TAG_CST_ID:String = "cst_id"
    val my_shared_preferences:String = "my_shared_preferences"

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_login)
        super.onCreate(savedInstanceState)
        context = this
        onLoginFuc()
        isLogin()
    }

    fun onLoginFuc(){
        btn_login.setOnClickListener {
            login(username.text.toString(), password.text.toString())
        }
    }
    fun isLogin(){
        shp = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE)
        session = shp.getBoolean(sessionStatus, false)
        cst_id = shp.getString(TAG_CST_ID, null).toString()
        if (session){
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(TAG_CST_ID, cst_id)
            startActivity(intent)
            finish()
        }
    }

    fun login(username : String, password : String){
        val obj = JSONObject()
        obj.put("username", username)
        obj.put("password", password)

        val que = Volley.newRequestQueue(this)
        val req = JsonObjectRequest(Request.Method.POST, Endpoint.USER_LOGIN, obj, {
            response ->
            try {
                if (response.getString("status") == "success"){
                    Log.e("mess", response.toString())
                    val content = response.getJSONArray("content")
                    val item = content.getJSONObject(0)
                    cst_id = item.getInt("id_user").toString()
                    shpEditor = shp.edit()
                    shpEditor.putBoolean(sessionStatus, true)
                    shpEditor.putString(TAG_CST_ID, cst_id)
                    shpEditor.commit()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this, "Username dan Password salah", Toast.LENGTH_LONG).show()
                }
            }catch (e : JSONException){
                Log.e("ERROR", e.toString())
            }
        }, {
            error ->
            Log.e("ERROR", error.toString())
        })
        que.add(req)
    }

}