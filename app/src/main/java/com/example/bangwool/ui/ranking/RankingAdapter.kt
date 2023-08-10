package com.example.bangwool.ui.ranking

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bangwool.R
import com.example.bangwool.databinding.ItemRankingBinding

class RankingAdapter(var items: ArrayList<RankingInfo>) :
    RecyclerView.Adapter<RankingAdapter.MyViewHolder>() {

//    private lateinit var mRecyclerView: RecyclerView

    inner class MyViewHolder(val binding: ItemRankingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bind(position: Int) {
            binding.rankingItemTvRank.text = items[position].rank.toString()
            binding.rankingItemTvNickname.text = items[position].name
            binding.rankingItemTvTime.text = items[position].time.toString() + " Min"
            if(items[position].loginedUser){
                binding.llRankingAll.setBackgroundColor(Color.parseColor("#EEEEEE"))
//                mRecyclerView.scrollTo(binding.llRankingAll.x.toInt(),binding.llRankingAll.y.toInt())
            }
            //id와 일치하는 랭킹에 user_ranking_layout 백그라운드 적용
//            var userRank = 3 // 이거 나중에 적절한 값으로 변경해야함
//            if (items[position].rank == userRank){
//                binding.itemRankingLl.setBackgroundResource(R.drawable.user_ranking_layout)
//            } else if (items[position].rank == 2){
//                binding.itemRankingLl.background = null
//            } else {
            binding.itemRankingLl.setBackgroundResource(R.drawable.non_user_ranking_layout)
//            }

        }
    }

//    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
//        super.onAttachedToRecyclerView(recyclerView)
//        mRecyclerView = recyclerView
//    }
//
//    fun scrollToPosition(position: Int){
//        mRecyclerView.scrollToPosition(position)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position)
    }
}

