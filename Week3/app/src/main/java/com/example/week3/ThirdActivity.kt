package com.example.week3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.week3.databinding.ActivityMainBinding
import com.example.week3.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(viewBinding.frameFragment.id, FirstFragment())
            .commitAllowingStateLoss()


        viewBinding.btnFragment1.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(viewBinding.frameFragment.id, FirstFragment())
                .commitAllowingStateLoss()
        }

        viewBinding.btnFragment2.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(viewBinding.frameFragment.id, SecondFragment())
                .commitAllowingStateLoss()
        }

        supportFragmentManager
            .setFragmentResultListener("requestKey", this) { requestKey, bundle ->
                val result = bundle.getString("bundleKey")
                Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
            }



    }
}