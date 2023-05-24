package com.example.week5mission

import android.content.ContentValues.TAG
import android.content.Context
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

    private lateinit var getResultText: ActivityResultLauncher<Intent> // 인텐트-데이터 주고 받기
    private lateinit var rAdapter: RecyclerAdapter

    private var contentsList = mutableListOf<Memo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // roomDB 세팅
        val roomDb = AppDatabase.getInstance(this)

        var contentsList = roomDb!!.memoDao().selectAll()

        rAdapter = RecyclerAdapter(this@MainActivity, contentsList)
        rAdapter.notifyDataSetChanged()

        // 수정 및 삭제
        rAdapter.setOnItemClickListener(object : RecyclerAdapter.OnItemClickListener {
            override fun onItemClick(con: String) {
                Log.d("setOnClickListener 테스트", "Main OK")
                val rIntent = Intent(this@MainActivity, EditActivity::class.java)
                rIntent.putExtra("memo", con)
                val posClick = roomDb?.memoDao()?.selectbyContentsName(con)?.contentsId
                rIntent.putExtra("type", posClick)
                getResultText.launch(rIntent)
            }

            override fun onItemLongClick(con: String) {
                val posLong = roomDb?.memoDao()?.selectbyContentsName(con)!!.contentsId
                val deleteContent = Memo("", false, posLong)
                Log.d("setOnLongClickListener 테스트", "Main OK")
                roomDb?.memoDao()?.delete(deleteContent)
                listRefresh()
                Toast.makeText(this@MainActivity, "삭제", Toast.LENGTH_SHORT).show()
            }
        })

        rAdapter.setOnItem2ClickListener(object: RecyclerAdapter.OnItem2ClickListener {
            // 즐겨찾기 기능
            override fun onItemClick(con: String, status: String) {
                if (status == "false") { // 즐겨찾기 해제 상태 -> 활성화로 변경
                    val sharedPreferences =
                        getSharedPreferences("sharedFav", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString(con, status)
                    editor.commit()
                } else { // 즐겨찾기 활성화 -> 해제상태로 변경
                    val sharedPreferences =
                        getSharedPreferences("sharedFav", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.remove(con)
                    editor.commit()
                }
            }

            // 좋아요 기능
            override fun onItemLikeClick(con: String, status: Boolean) {
                var posToggle = roomDb?.memoDao()?.selectbyContentsName(con)?.contentsId
                Log.d("onToggleChecked 테스트", "Main OK & pos=$posToggle")
                if (status) {
                    if (posToggle != null) {
                        roomDb?.memoDao()?.updateLikesByContentsId(posToggle, true)
                        contentsList = roomDb!!.memoDao().selectAll()
                        Log.d("roomDB toggle true", contentsList.toString())
                        Log.d("roomDB toggle true", "=================================")
                        listRefresh()
                    }
                } else {
                    if (posToggle != null) {
                        roomDb?.memoDao()?.updateLikesByContentsId(posToggle, false)
                        contentsList = roomDb!!.memoDao().selectAll()
                        Log.d("roomDB toggle false", contentsList.toString())
                        Log.d("roomDB toggle false", "=================================")
                    }
                }
            }

        })
        rAdapter.notifyDataSetChanged()


        binding.recyclerView.adapter = rAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        Log.d("roomDB", contentsList.toString())

        binding.recyclerView.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))


        getResultText = registerForActivityResult( ActivityResultContracts.StartActivityForResult() ) {
            result ->
            if(result.resultCode == RESULT_OK) {
                val mString = result.data?.getStringExtra("memo")
                val mType = result.data?.getIntExtra("type", -1)
                Log.d(TAG, "type: $mType, onCreate: good To go: $mString")
                if (mType == -1) { // 데이터 추가
                    roomDb?.memoDao()?.insert(Memo("$mString", false))
                    listRefresh()
                } else if (mType != null) { // 데이터 수정
                    roomDb?.memoDao()?.updateContentsByContentsId(mType, "$mString")
                    listRefresh()
                }
            }
        }



        binding.btnAddmemo.setOnClickListener {
            val mIntent = Intent(this@MainActivity, EditActivity::class.java)
            getResultText.launch(mIntent)
        }

        binding.btnFavmemo.setOnClickListener {
            val fIntent = Intent(this@MainActivity, FavActivity::class.java)
            startActivity(fIntent)
        }

    }

    // 뷰를 지우고 재생성하는 함수 -> 데이터 수정 시 사용
    private fun listRefresh() {
        binding.recyclerView.removeAllViews()

        val roomDb = AppDatabase.getInstance(this)
        val contentsList = roomDb!!.memoDao().selectAll()
        rAdapter = RecyclerAdapter(this, contentsList)
        binding.recyclerView.adapter = rAdapter
    }

}