package com.example.week10

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    // openweathermap API
    @GET("data/2.5/weather")
    fun getCurrentWeatherByCityName (
        @Query("q") q: String,
        @Query("appid") appid: String
    ): Call<Response>

    // 공공데이터포털 코로나19 확진자수 API
    @GET("1790387/covid19CurrentStatusKorea/covid19CurrentStatusKoreaJason")
    fun getcovid19CurrentStatsKoreaJason (
        @Query("serviceKey") serviceKey: String
    ): Call<ResponseCovid>

    // 공공데이터포털 초미세먼지(주간) 예보 API
    @GET("B552584/ArpltnInforInqireSvc/getMinuDustWeekFrcstDspth")
    fun getMinuDustWeekFrcstDspth (
        @Query("serviceKey") serviceKey: String,
        @Query("numOfRows") numOfRows: Int = 1,
        @Query("returnType") returnType: String = "json",
        @Query("searchDate") searchDate: String,
        @Query("pageNo") pageNo: Int = 1
    ): Call<DustWeekResponse>
}
