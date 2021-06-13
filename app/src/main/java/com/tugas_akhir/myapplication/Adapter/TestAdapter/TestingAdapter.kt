package com.tugas_akhir.myapplication.Adapter.TestAdapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.tugas_akhir.myapplication.TestModel
import com.tugas_akhir.myapplication.R
import kotlinx.android.synthetic.main.list_student.view.*
import org.json.JSONException

class TestingAdapter(val context: Context, val studentList : ArrayList<TestModel>, val callback : StudentInterface) : RecyclerView.Adapter<TestingAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val titles = view.et_title
        val name = view.et_name
        fun bind(model :TestModel, position: Int){
            val req = Volley.newRequestQueue(context)
            val que = JsonObjectRequest(Request.Method.GET, "http://api.dolano.id:8003/title/all", null, {
                    response ->
                try {
                    val content = response.getJSONArray("content")
                    val title = ArrayList<String>()
                    for (i in 0 until content.length()){
                        val item = content.getJSONObject(i)
                        val name = item.getString("name")
                        title.add(name)
                    }
                    val adapter = ArrayAdapter( context, android.R.layout.simple_spinner_item, title)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    titles.adapter = adapter
                    titles.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                            val text = titles.selectedItem.toString()
                            Log.e("mess", text)
                            callback.onTitleChange(text, position)
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }

                    }
                }catch (e : JSONException){
                    e.printStackTrace()
                }
            }, {
                    error ->
            })
            req.add(que)

            name.doAfterTextChanged {
                callback.onNameChange(it.toString(), position)
            }
        }
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestingAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_student, parent, false))
    }

    override fun onBindViewHolder(holder: TestingAdapter.ViewHolder, position: Int) {
        holder.bind(studentList[position], position)

    }
}

interface StudentInterface {
    fun onTitleChange(text : String, idx : Int)
    fun onNameChange(text : String, idx : Int)
}

