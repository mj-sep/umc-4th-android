package com.example.week10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.week10.databinding.ActivityMainBinding
import com.example.week10.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        if(intent.getIntExtra("resultCode", 0) == 200) {
            viewBinding.naverLoginName.text = "${intent.getStringExtra("userName")} 님 \n로그인 성공했습니다"
            viewBinding.naverLoginEmail.text = "${intent.getStringExtra("userEmail")}"
        }
    }
}