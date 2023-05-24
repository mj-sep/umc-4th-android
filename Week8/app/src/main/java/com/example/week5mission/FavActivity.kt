package com.example.week5mission

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week5mission.databinding.ActivityFavBinding


data class FavData(val contents: String, val status: String)

class FavActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavBinding
    private val datalist: ArrayList<FavData> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("sharedFav", MODE_PRIVATE)
        val allValues: Map<String, *> = sharedPreferences.all

        for((key, value) in allValues) {
            datalist.add(FavData(key, value as String))
        }

        binding.favrecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.favrecyclerView.adapter = FavAdapter(datalist)
        binding.favrecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        binding.btnBack.setOnClickListener {
            finish()
        }

    }
}