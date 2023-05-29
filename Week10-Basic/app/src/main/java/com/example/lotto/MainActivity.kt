package com.example.lotto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.lotto.databinding.ActivityMainBinding
import com.example.lotto.databinding.ActivitySplashBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val lotteryNumbers =
            arrayListOf(binding.number1, binding.number2, binding.number3, binding.number4, binding.number5, binding.number6)


        val countDownTimer = object: CountDownTimer(3000, 100) {
            override fun onTick(p0: Long) { // 100ms마다 계속 호출됨
                lotteryNumbers.forEach {
                    val randomNumber = (Math.random() * 45 + 1).toInt()
                    it.text = "$randomNumber"
                }
            }

            override fun onFinish() { // 타이머가 다했을 때 호출됨
            }
        }

        binding.lotteryButton.setOnClickListener {
            if (binding.lotteryButton.isAnimating) {
                binding.lotteryButton.cancelAnimation()
                countDownTimer.cancel()
            } else {
                binding.lotteryButton.playAnimation()
                countDownTimer.start()
            }
        }
    }
}