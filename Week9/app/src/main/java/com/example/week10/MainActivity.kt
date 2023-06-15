package com.example.week10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.week10.databinding.ActivityMainBinding
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)


        // 네이버 로그인 API 초기화
        val naverClientId = getString(R.string.social_login_info_naver_client_id)
        val naverClientSecret = getString(R.string.social_login_info_naver_client_secret)
        val naverClientName = getString(R.string.social_login_info_naver_client_name)
        NaverIdLoginSDK.initialize(this, naverClientId, naverClientSecret , naverClientName)

        setLayoutState(false)

        viewBinding.tvNaverLogin.setOnClickListener {
            startNaverLogin()
        }
        viewBinding.tvNaverLogout.setOnClickListener {
            startNaverLogout()
        }
        viewBinding.tvNaverDeleteToken.setOnClickListener {
            startNaverDeleteToken()
        }



        // 날씨 API (openweathermap)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // 코로나19 API (https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15099842)
        // 초미세먼지 API (https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15073861)
        var gson = GsonBuilder().setLenient().create()
        val retrofitCovid = Retrofit.Builder()
            .baseUrl("https://apis.data.go.kr/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        // 어떤 주소를 입력했다
        val apiService = retrofit.create(ApiService::class.java)
        val apiServiceCovid = retrofitCovid.create(ApiService::class.java)


        val servicekey = "서비스 키 토큰"

        // OpenWeatherMap API
        val call = apiService.getCurrentWeatherByCityName(q = "New York", appid = "서비스 키 토큰")
        call.enqueue(object: Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                if(response.isSuccessful) {
                    val weatherResponse = response.body()?.main
                    var cTemp = weatherResponse!!.temp // 현재 기온
                    Log.d("Retrofit", "현재 기온 API / 측정 장소: ${response.body()!!.name}, 현재 기온: $cTemp")
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                Log.d("Retrofit", "result: Failure! " + t.message)
            }

        })


        // COVID-19 확진자 API
        val callCovid = apiServiceCovid.getcovid19CurrentStatsKoreaJason(serviceKey = servicekey )
        callCovid.enqueue(object: Callback<ResponseCovid> {
            override fun onResponse(call: Call<ResponseCovid>, response: retrofit2.Response<ResponseCovid>) {
                if (response.isSuccessful) {
                    val covidResponse = response.body()
                    val covidResult = covidResponse?.response?.result
                    val covidDate = covidResult?.get(0)?.mmddhh // 측정일자
                    val covidConfirm = covidResult?.get(0)?.cnt_confirmations // 확진자수
                    Log.d("Retrofit-Covid", "COVID-19 확진자수 API / 측정일시: $covidDate, 확진자수: $covidConfirm")
                } else {
                    Log.d("Retrofit-Covid", response.toString())
                }
            }

            override fun onFailure(call: Call<ResponseCovid>, t: Throwable) {
                Log.d("Retrofit-Covid", "result: Failure! " + t.message)
            }
        })


        // 주간 초미세먼지 예보 API
        val callDust = apiServiceCovid.getMinuDustWeekFrcstDspth(serviceKey = servicekey, searchDate = "2023-06-06")
        callDust.enqueue(object: Callback<DustWeekResponse> {
            override fun onResponse( call: Call<DustWeekResponse>, response: retrofit2.Response<DustWeekResponse>) {
                if(response.isSuccessful) {
                    val dustWeekResponse = response.body()
                    val dustWeekItem = dustWeekResponse?.response?.body?.items?.get(0)
                    val frcstOneCn = dustWeekItem?.frcstOneCn
                    if (dustWeekItem != null) {
                        Log.d("Retrofit-Dust", "초미세먼지 API / 기준일: ${dustWeekItem.presnatnDt}\n기준 첫째 날(${dustWeekItem.frcstOneDt}): ${dustWeekItem.frcstOneCn} \n기준 둘째 날(${dustWeekItem.frcstTwoDt}): ${dustWeekItem.frcstTwoCn} " +
                                "\n기준 셋쨰 날(${dustWeekItem.frcstThreeDt}): ${dustWeekItem.frcstThreeCn} " +
                                "\n기준 넷째 날(${dustWeekItem.frcstFourDt}: ${dustWeekItem.frcstFourCn}")
                    }
                } else Log.d("Retrofit-Dust", response.toString())

            }

            override fun onFailure(call: Call<DustWeekResponse>, t: Throwable) {
                Log.d("Retrofit-Dust", "result: Failure! " + t.message)
            }
        })
    }

    // 로그인
    private fun startNaverLogin() {
        var naverToken: String? = ""

        val profileCallback = object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(response: NidProfileResponse) {
                val userName = response.profile?.name
                val userEmail = response.profile?.email

                Toast.makeText(this@MainActivity, "네이버 아이디 로그인 성공!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainActivity, SecondActivity::class.java)
                intent.putExtra("resultCode", 200)
                intent.putExtra("userName", userName)
                intent.putExtra("userEmail", userEmail)
                startActivity(intent)

                setLayoutState(true)
            }
            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Toast.makeText(this@MainActivity, "errorCode: ${errorCode}\n" +
                        "errorDescription: ${errorDescription}", Toast.LENGTH_SHORT).show()
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
                naverToken = NaverIdLoginSDK.getAccessToken()
                //로그인 유저 정보 가져오기
                NidOAuthLogin().callProfileApi(profileCallback)
            }
            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Toast.makeText(this@MainActivity, "errorCode: ${errorCode}\n" +
                        "errorDescription: ${errorDescription}", Toast.LENGTH_SHORT).show()
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        NaverIdLoginSDK.authenticate(this, oauthLoginCallback)
    }

    // 로그아웃
    private fun startNaverLogout(){
        NaverIdLoginSDK.logout()
        setLayoutState(false)
        Toast.makeText(this@MainActivity, "네이버 아이디 로그아웃 성공!", Toast.LENGTH_SHORT).show()
    }

    // 연동 해제
    private fun startNaverDeleteToken(){
        NidOAuthLogin().callDeleteTokenApi(this, object : OAuthLoginCallback {
            override fun onSuccess() {
                //서버에서 토큰 삭제에 성공한 상태입니다.
                setLayoutState(false)
                Toast.makeText(this@MainActivity, "네이버 아이디 토큰삭제 성공!", Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(httpStatus: Int, message: String) {
                // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
                // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
                Log.d("naver", "errorCode: ${NaverIdLoginSDK.getLastErrorCode().code}")
                Log.d("naver", "errorDesc: ${NaverIdLoginSDK.getLastErrorDescription()}")
            }
            override fun onError(errorCode: Int, message: String) {
                // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
                // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
                onFailure(errorCode, message)
            }
        })
    }

    private fun setLayoutState(login: Boolean){
        if(login){
            viewBinding.tvNaverLogin.visibility = View.GONE
            viewBinding.tvNaverLogout.visibility = View.VISIBLE
            viewBinding.tvNaverDeleteToken.visibility = View.VISIBLE
        }else{
            viewBinding.tvNaverLogin.visibility = View.VISIBLE
            viewBinding.tvNaverLogout.visibility = View.GONE
            viewBinding.tvNaverDeleteToken.visibility = View.GONE
        }
    }
}

