package com.example.week3

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.week3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var getResultText: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)


        getResultText = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {result ->
            if(result.resultCode == RESULT_OK) {
                val mString = result.data?.getStringExtra("str")
                Toast.makeText(applicationContext, mString, Toast.LENGTH_SHORT).show()
            }
        }

        viewBinding.btnToNextActivity.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("edittxt", viewBinding.tvEdit.text.toString())
            getResultText.launch(intent)
            // startActivity(intent)
        }

    }
}