package com.example.week5mission

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week5mission.databinding.ActivityMainBinding

data class BusinessCard(val contents: String)

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val businessCardArrayList: ArrayList<BusinessCard> = arrayListOf()
    private lateinit var getResultText: ActivityResultLauncher<Intent> // 인텐트-데이터 주고 받기
    var rAdapter: RecyclerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        for (x in 0..30) {
            businessCardArrayList.add(BusinessCard("메모 $x"))
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        // binding.recyclerView.adapter = RecyclerAdapter(businessCardArrayList)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        getResultText = registerForActivityResult( ActivityResultContracts.StartActivityForResult() ) {
            result ->
            if(result.resultCode == RESULT_OK) {
                val mString = result.data?.getStringExtra("memo")
                val mType = result.data?.getIntExtra("type", -1)
                Log.d(TAG, "onCreate: good To go: $mString")
                if (mType == -1) {
                    businessCardArrayList.add(BusinessCard("$mString")) // 전달받은 string arraylist에 넣고 recyclerview 돌리기
                } else if (mType != null) {
                    businessCardArrayList[mType] = BusinessCard("$mString") // 전달받은 인덱스 값의 데이터 수정
                    listRefresh()
                }
            }
        }

        // #3: 메모 수정 구현 (Recycleradapter 내부에 클릭 인터페이스 구현하고, 해당 리스너 통해 상호작용)
        binding.recyclerView.adapter = RecyclerAdapter(businessCardArrayList, object: RecyclerAdapter.MyItemClickListener {
            override fun onItemClick(position: Int) { // itemClicked -> editText에 텍스트 그대로 가져가서 수정 가능하도록
                val rIntent = Intent(this@MainActivity, EditActivity::class.java)
                rIntent.putExtra("memo", businessCardArrayList[position].contents)
                rIntent.putExtra("type", position)
                getResultText.launch(rIntent)
            }

            override fun onLongClick(position: Int) { // 롱클릭 시, 아이템 삭제
                businessCardArrayList.removeAt(position)
                Toast.makeText(this@MainActivity, "삭제", Toast.LENGTH_SHORT).show()
            }
        })

        rAdapter = binding.recyclerView.adapter as RecyclerAdapter

        binding.btnAddmemo.setOnClickListener {
            val mIntent = Intent(this@MainActivity, EditActivity::class.java)
            getResultText.launch(mIntent)
        }

    }

    // 뷰를 지우고 재생성하는 함수 -> 데이터 수정 시 사용
    private fun listRefresh() {
        binding.recyclerView.removeAllViews()
        binding.recyclerView.adapter = rAdapter
    }

}