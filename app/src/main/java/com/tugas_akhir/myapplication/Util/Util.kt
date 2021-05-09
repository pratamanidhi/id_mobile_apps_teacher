package com.tugas_akhir.myapplication.Util

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.EditText
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class Util {

    companion object{
        fun IDRFormat(price: Double): String{
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 2
            format.currency = Currency.getInstance("IDR")
            return format.format(price)
        }
        fun showCalendar(context: Context, target : EditText){
            val cal = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "dd MMMM yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                target.setText(sdf.format(cal.time))
            }

            DatePickerDialog(context, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        fun showTimePicker(context: Context, editText: EditText){
            val mcurrentTime: Calendar = Calendar.getInstance()
            val hour: Int = mcurrentTime.get(Calendar.HOUR_OF_DAY)
            val minute: Int = mcurrentTime.get(Calendar.MINUTE)
            val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(context,
                TimePickerDialog.OnTimeSetListener {
                        timePicker,
                        selectedHour,
                        selectedMinute -> editText.setText("$selectedHour:$selectedMinute") },
                hour,
                minute,
                true
            )
            mTimePicker.setTitle("Pilih Jam")
            mTimePicker.show()
        }
    }
}