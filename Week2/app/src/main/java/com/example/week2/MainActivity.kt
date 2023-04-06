package com.example.week2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.week2.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myaccount_ic.setOnClickListener {
            var intent = Intent(this, AccountHistory::class.java)
            startActivity(intent)
        }

        myprofile_ic.setOnClickListener{
            var intent2 = Intent(this, MenuActivity::class.java)
            startActivity(intent2)
        }

    }
}