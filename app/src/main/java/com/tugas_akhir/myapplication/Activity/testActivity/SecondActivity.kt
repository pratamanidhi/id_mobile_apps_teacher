package com.tugas_akhir.myapplication.Activity.testActivity

import android.content.Context
import com.tugas_akhir.myapplication.Adapter.TestAdapter.Student2Adapter
import com.tugas_akhir.myapplication.Adapter.TestAdapter.StudentInterface
import com.tugas_akhir.myapplication.Student
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.tugas_akhir.myapplication.Adapter.TestAdapter.TestingAdapter
import com.tugas_akhir.myapplication.R
import com.tugas_akhir.myapplication.TestModel

import kotlinx.android.synthetic.main.activity_second.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class SecondActivity : AppCompatActivity(), StudentInterface {
    lateinit var context : Context
    val studentList : ArrayList<TestModel> = ArrayList()
    lateinit var studentAdapter : TestingAdapter
    val array = JSONArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        context = this
        setAdapter()
        var count = 5
        for (j in 0 until count){
            collections(j)
        }
        check_visitor.setOnClickListener {
            if (check_visitor.isChecked){
                setAdapter()
                for (i in 0 until count - 1){
                    collections(i)
                }
            }
        }
        btn_submit.setOnClickListener {
//            studentList.forEachIndexed { index, student ->
//                if (student.name == "" && array.length() < count){
//                    Toast.makeText(this, "data pengunjung belum lengkap", Toast.LENGTH_SHORT).show()
//                }else{
//                    array.put(index, "${student.title} ${student.name}")
//                }
//            }
//            Log.e("array", array.toString())
        }
//        newDolanoCode()
        amadeusFlight()

    }

    private fun setAdapter(){
        studentAdapter = TestingAdapter(this,studentList, this)
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@SecondActivity)
            adapter = studentAdapter
        }
    }


    override fun onTitleChange(text: String, idx: Int) {
        studentList[idx].title = text
    }

    override fun onNameChange(text: String, idx: Int) {
        studentList[idx].name = text
    }
    private fun collections(count : Int){
            studentList.add(count,TestModel())
            studentAdapter.notifyItemInserted(studentList.size)
    }



    fun newDolanoCode(){
        val dolano_product = JSONObject()
        dolano_product.put("ticket_id", 1)
        dolano_product.put("promo_id", 1)
        dolano_product.put("season_multiplier_id", 1)
        dolano_product.put("zone_multiplier_id", 1)
        dolano_product.put("guide_price_id", 1)
        dolano_product.put("price", 1)
        dolano_product.put("partner_type_id", 1)
        dolano_product.put("guide_type_id", 1)
        dolano_product.put("special_request", 1)
        dolano_product.put("language_id", 1)
        dolano_product.put("city_guide_type_id", 1)

        val quantity = JSONObject()
        quantity.put("value",1)
        quantity.put("unit","units")
        quantity.put("unit_name","orang")

        val order_time = JSONObject()
        order_time.put("start","2021-05-30T15:17:10.804Z")
        order_time.put("end","2021-05-30T15:17:10.804Z")

        val origin = JSONObject()
        origin.put("lat",-6.99306)
        origin.put("lon",110.4208)
        origin.put("locationType","TO")

        val locations = JSONArray()
        locations.put(0,origin)

        val add_Dolano = JSONObject()
        add_Dolano.put("customer_code","CST000000001")
        add_Dolano.put("dolano_product", dolano_product)
        add_Dolano.put("quantity",quantity)
        add_Dolano.put("order_time",order_time)
        add_Dolano.put("locations", locations)

        Log.e("mess", add_Dolano.toString())

    }

    fun amadeusFlight(){
        val obj = JSONObject()
        obj.put("originLocationCode","CGK")
        obj.put("destinationLocationCode","SUB")
        obj.put("departureDate","2021-06-10")
        obj.put("adults",1)
        obj.put("children",1)

        val que = Volley.newRequestQueue(this)
        val req = JsonObjectRequest(Request.Method.POST, " https://api.dolano.id/amadeus/flight/flight_offer/search", obj, {
             response ->
            try {
                val content = response.getJSONObject("content")
                val data = content.getJSONArray("data")
                for (i in 0 until data.length()){
                    val data_item = data.getJSONObject(i)
                    val itineraries = data_item.getJSONArray("itineraries")
                    val itineraries_item = itineraries.getJSONObject(0)
                    val segments = itineraries_item.getJSONArray("segments")
                    for( j in 0 until segments.length()){
                        val segments_item = segments.getJSONObject(j)
                        val carrier_code = segments_item.getString("carrierCode")
                        Log.e("code", carrier_code)
                    }
                }

            }catch (e : JSONException){
                Log.e("error", e.toString())
            }
        },{
            error->
            Log.e("error", error.toString())

        })
        que.add(req)

    }
}
