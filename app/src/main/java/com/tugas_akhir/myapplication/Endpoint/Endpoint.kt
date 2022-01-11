package com.tugas_akhir.myapplication.Endpoint

object Endpoint {

    val URL = "http://192.168.0.102:5000"

    val USER_LOGIN = URL + "/login"
    val USER_REGISTER = URL + "/client"
    fun SEARCH_USER_ID (id : String) : String{
        val url = URL + "/client/${id}"
        return url
    }

    val SEARCH_TEACHER = URL + "/list"
    fun SEARCH_TEACHER_ID (id : String) : String{
        val url = URL + "/list/${id}"
        return url
    }

    val MAPEL = URL + "/mapel"
    val MAPEL_LIST = URL + "/mapel/"

    val TINGKAT = URL + "/tingkat"
    val TINGKAT_LIST = URL + "/tingkat/"


    val BOOKING_TEACHER = URL + "/booking"
    val BOOKING_DETAIL = URL + "/booking_detail"
}