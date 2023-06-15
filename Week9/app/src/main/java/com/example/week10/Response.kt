package com.example.week10

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.google.gson.annotations.SerializedName

data class Response (
    val main: MainWT,
    val name: String
)

data class MainWT (
    @SerializedName("temp") var temp: Double,
    @SerializedName("temp_min") var temp_min: Double,
    @SerializedName("temp_max") var temp_max: Double,
)

data class ResponseCovid (
    @SerializedName("resultMsg") // 결과 메시지
    var resultMsg: String?,
    @SerializedName("resultCode") // 결과 코드
    var resultCode: String?,
    @SerializedName("resultCnt") // 결과 개수
    var resultCnt: String?,
    @SerializedName("response")
    var response: CovidResponse?
)

data class CovidResponse (
    @SerializedName("result") var result: List<ResultList>?
    )

data class ResultList (
    @SerializedName("mmddhh") // 데이터 조회 기준 일시
    var mmddhh: String?,
    @SerializedName("cnt_confirmations") // 일일 확진
    var cnt_confirmations: String?,
    @SerializedName("cnt_death") // 일일 사망
    var cnt_death: String?,
    @SerializedName("rate_deaths") // 인구 10만명당 사망
    var rate_deaths: String?,
    @SerializedName("cnt_severe_symptoms") // 일일 재원 위중증 발생현황
    var cnt_severe_symptoms: String?,
    @SerializedName("rate_severe_symptoms") // 인구 10만명 당 재원 위중증
    var rate_severe_symptoms: String?,
    @SerializedName("cnt_hospitalizations") // 일일 신규 입원
    var cnt_hospitalizations: String?,
    @SerializedName("rate_hospitalizations") // 인규 10만명당 신규 입원
    var rate_hospitalizations: String?,
    @SerializedName("rate_confirmations") // 인규 10만명당 확진
    var rate_confirmations: String?,
)

data class DustWeekResponse (
    @SerializedName("response")
    val response: DustWeekResponseData
)

data class DustWeekResponseData (
    @SerializedName("body")
    val body: DustWeekResponseBody,
    @SerializedName("header")
    val header: DustWeekResponseHeader
)

data class DustWeekResponseBody(
    @SerializedName("totalCount")
    val totalCount: Int,
    @SerializedName("items")
    val items: List<DustWeekItem>,
    @SerializedName("pageNo")
    val pageNo: Int,
    @SerializedName("numOfRows")
    val numOfRows: Int
)

data class DustWeekItem(
    @SerializedName("frcstFourDt")
    val frcstFourDt: String,
    @SerializedName("frcstThreeDt")
    val frcstThreeDt: String,
    @SerializedName("frcstTwoCn")
    val frcstTwoCn: String,
    @SerializedName("gwthcnd")
    val gwthcnd: String,
    @SerializedName("frcstTwoDt")
    val frcstTwoDt: String,
    @SerializedName("frcstFourCn")
    val frcstFourCn: String,
    @SerializedName("frcstThreeCn")
    val frcstThreeCn: String,
    @SerializedName("frcstOneDt")
    val frcstOneDt: String,
    @SerializedName("frcstOneCn")
    val frcstOneCn: String,
    @SerializedName("presnatnDt")
    val presnatnDt: String
)

data class DustWeekResponseHeader(
    @SerializedName("resultMsg")
    val resultMsg: String,
    @SerializedName("resultCode")
    val resultCode: String
)
