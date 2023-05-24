package com.example.week5mission

import android.content.Context
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week5mission.databinding.RecyclerviewItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RecyclerAdapter(
    private val context: Context,
    private var contentsList: List<Memo>,

) : RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {

    lateinit var binding: RecyclerviewItemBinding

    // 리스너 객체 참조를 저장하는 변수
    private var mListener: OnItemClickListener ?= null
    private var rListener: OnItem2ClickListener ?= null

    interface OnItemClickListener {
        fun onItemClick(con: String)
        fun onItemLongClick(con: String)
    }

    interface OnItem2ClickListener {
        fun onItemClick(con: String, status: String) // Fav
        fun onItemLikeClick (con: String, status: Boolean)
    }

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    fun setOnItem2ClickListener(listener: OnItem2ClickListener) {
        rListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        binding = RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int = contentsList.size


    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(contentsList[position])

        holder.binding.contentsLike.setOnClickListener {
            val pos = contentsList[position].contents
            val isChecked = holder.binding.contentsLike.isChecked
            Log.d("pos 인식 테스트-좋아요", "$pos, $isChecked, $rListener")
            rListener?.onItemLikeClick(pos, isChecked)
        }
    }

    // Create ViewHolder
    inner class RecyclerViewHolder (val binding: RecyclerviewItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        // 0523-1429 미션 3: 메모 수정 구현 (클릭 리스너 초기화)
        init {
            // 아이템 클릭 이벤트 처리
            itemView.setOnClickListener {
                val pos = contentsList[adapterPosition].contents
                Log.d("pos 인식 테스트-onClick", "$pos, $mListener")
                mListener?.onItemClick(pos)
                return@setOnClickListener
            }
            itemView.setOnLongClickListener {
                val pos = contentsList[adapterPosition].contents
                Log.d("pos 인식 테스트-onLongClick", pos)
                mListener?.onItemLongClick(pos)
                return@setOnLongClickListener true
            }

            binding.contentsFav.setOnClickListener {
                val positionId = contentsList[adapterPosition].contents
                Log.d("pos 인식 테스트-즐겨찾기", positionId)
                rListener?.onItemClick(positionId, binding.favBoolean.text as String)
                if(binding.favBoolean.text.equals("false")) {
                    binding.favBoolean.text = "true"
                    binding.contentsFav.setImageResource(R.drawable.star)
                } else {
                    binding.favBoolean.text = "false"
                    binding.contentsFav.setImageResource(R.drawable.blankstar)
                }
                return@setOnClickListener
            }

        }

        fun bind(item: Memo) {
            binding.contentListviewItem.text = item.contents
            binding.contentsLike.isChecked = item.likes

            /*
            binding.contentsLike.setOnClickListener {
                val posi = item.contents
                val like = item.likes

                Log.d("pos 인식 테스트-좋아요", "$posi, $like, $rListener")
                rListener?.onItemLongClick(posi, like)
            }

             */
        }
    }
}



