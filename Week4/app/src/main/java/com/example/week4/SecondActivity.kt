package com.example.week4

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.week4.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    lateinit var binding: ActivitySecondBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "Second: onCreate", Toast.LENGTH_SHORT).show()
        Log.d("Lifecycle", "Second: onCreate")
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if(!intent.getStringExtra("data").equals("")) {
            binding.txtSecond.text = intent.getStringExtra("data")
        }
    }

    override fun onRestart() {
        // onRestart : Dialog를 활용하여 다시 작성할거냐고 묻는 창 띄우기
        super.onRestart()
        Toast.makeText(this, "Second: onRestart", Toast.LENGTH_SHORT).show()
        Log.d("Lifecycle", "Second: onRestart")
        showDialog_sec()

    }

    fun showDialog_sec() {
        val edittext = EditText(this)
        val msgBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
            .setTitle("Second Dialog")
            .setMessage("text를 변경을 원하면 변경할 텍스트 입력")
            .setView(edittext)
            .setPositiveButton("예",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    binding.txtSecond.text = edittext.text.toString()
                })
            .setNegativeButton("아니오",
                DialogInterface.OnClickListener { dialogInterface, i ->
                })
        val msgDlg: AlertDialog = msgBuilder.create()
        msgDlg.show()
    }
}