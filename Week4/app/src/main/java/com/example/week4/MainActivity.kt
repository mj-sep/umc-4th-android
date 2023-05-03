package com.example.week4

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.week4.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var pausesave = "" // onPause() 호출 시 현재까지 작성한 내용 전역변수에 담기

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show()
        Log.d("Lifecycle", "onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnMain.setOnClickListener {
            var intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("data", binding.edtMain.text.toString())
            startActivity(intent)
        }
    }

    override fun onResume() {
        // onResume : onPause에서 저장한 전역변수 내용으로 EditText 내용으로 설정하기
        super.onResume()
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show()
        Log.d("Lifecycle", "onResume")
        if(!pausesave.equals("")) { // 변수 값이 비어있다면 아무것도 안 하기
            binding.edtMain.setText(pausesave)
        }
    }

    override fun onPause() {
        // onPause : 현재까지 작성한 내용 Activity의 전역변수에 담아두기
        super.onPause()
        pausesave = binding.edtMain.text.toString()
        Toast.makeText(this, "onPause: $pausesave", Toast.LENGTH_SHORT).show()
        Log.d("Lifecycle", "onPause")
    }

    override fun onRestart() {
        // onRestart : Dialog를 활용하여 다시 작성할거냐고 묻는 창 띄우기
        super.onRestart()
        Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show()
        Log.d("Lifecycle", "onRestart")
        showDialog()

    }


    fun showDialog() {
        val msgBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
            .setTitle("onRestart Dialog")
            .setMessage("다시 작성하시겠습니까?")
            .setPositiveButton("예",
                DialogInterface.OnClickListener { dialogInterface, i ->  })
            .setNegativeButton("아니오",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    pausesave = "" // 다시 작성하지 않겠다고 하면 onPause에서 저장했던 변수 비우기
                })
        val msgDlg: AlertDialog = msgBuilder.create()
        msgDlg.show()
    }

}