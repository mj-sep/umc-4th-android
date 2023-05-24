package com.example.week5mission

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week5mission.databinding.ActivityFavBinding
import com.example.week5mission.databinding.RecyclerviewFavBinding
import com.example.week5mission.databinding.RecyclerviewItemBinding

class FavAdapter(
    private var datalist: ArrayList<FavData>,
): RecyclerView.Adapter<FavAdapter.ViewHolder>(){

    private lateinit var binding: RecyclerviewFavBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RecyclerviewFavBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = datalist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datalist[position])
    }

    inner class ViewHolder(private val binding: RecyclerviewFavBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FavData) {
            binding.favContentListviewItem.text = item.contents
            binding.favBoolean.text = item.status
        }
    }
}