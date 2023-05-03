package com.example.week5mission

import android.app.Activity
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week5mission.databinding.RecyclerviewItemBinding

class RecyclerAdapter(
    private val businessCardArraylist: ArrayList<BusinessCard>,
    private var mItemClickListener: MyItemClickListener // 클릭 리스너 선언
) : RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {

    private val itemStatus = SparseBooleanArray()
    lateinit var binding: RecyclerviewItemBinding

    // 미션 3: 메모 수정 구현 (클릭 인터페이스 정의)
    interface MyItemClickListener {
        fun onItemClick(position: Int) fun onLongClick(position: Int)
    }

    // 미션 3: 메모 수정 구현 (클릭 리스너 등록 메소드 (메인 액티비티에서 람다식 혹은 inner 클래스로 호출 예정))
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        binding = RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int = businessCardArraylist.size


    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(businessCardArraylist[position])

    }

    // Create ViewHolder
    inner class RecyclerViewHolder (private val binding: RecyclerviewItemBinding): RecyclerView.ViewHolder(binding.root) {

        // 미션 3: 메모 수정 구현 (클릭 리스너 초기화)
        init {
            itemView.setOnClickListener{
                mItemClickListener.onItemClick(adapterPosition)
                notifyDataSetChanged()
                return@setOnClickListener
            }
            itemView.setOnLongClickListener {
                mItemClickListener.onLongClick(adapterPosition)
                notifyDataSetChanged()
                return@setOnLongClickListener true
            }
        }

        fun bind(item: BusinessCard) {
            binding.contentListviewItem.text = item.contents

            /*
            // 미션 2) 아이템 클릭시, 해당 아이템 삭제
            binding.rootView.setOnClickListener {
                businessCardArraylist.removeAt(adapterPosition)
                notifyDataSetChanged()
            }
            */


            // 미션 1) 스위치 설정
            /*
            binding.switchItem.isChecked = switchStatus[adapterPosition]
            binding.switchItem.setOnClickListener {
                if (!binding.switchItem.isChecked) switchStatus.put(adapterPosition, false)
                else switchStatus.put(adapterPosition, true)
                notifyItemChanged(adapterPosition)
            }

             */

        }





    }



}