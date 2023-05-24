package com.example.week5mission

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.week5mission.databinding.ActivityMemoBinding

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val rIntent = intent
        binding.memoEdittext.setText(rIntent.getStringExtra("memo"))
        val isType = rIntent.getIntExtra("type", -1)

        // 저장 Button을 누르면 메인 화면으로 넘길 데이터를 설정하고 메모 화면 닫기
        binding.memoSave.setOnClickListener {
            val mIntent = Intent(this, MainActivity::class.java).apply {
                putExtra("memo", binding.memoEdittext.text.toString()) // editText 작성한 메모 저장
                putExtra("type", isType) // 타입도 함께 전송 (신규->-1, 수정->해당 index)
            }
            setResult(RESULT_OK, mIntent)
            if(!isFinishing) finish()
        }
    }
}