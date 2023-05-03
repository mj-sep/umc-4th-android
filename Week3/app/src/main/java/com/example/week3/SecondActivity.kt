package com.example.week3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.week3.databinding.ActivityMainBinding
import com.example.week3.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val extras = intent.extras
        val data = extras?.getString("edittxt")

        viewBinding.tvSecond.text = data

        viewBinding.btnThird.setOnClickListener {
            val intent2 = Intent(this, ThirdActivity::class.java)
            startActivity(intent2)
        }

        viewBinding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("str", "Back")
            }
            setResult(RESULT_OK, intent)
            if(!isFinishing) finish()
        }

    }
}