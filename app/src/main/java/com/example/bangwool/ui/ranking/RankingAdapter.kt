package com.example.bangwool.ui.ranking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bangwool.databinding.ItemRankingBinding

class RankingAdapter(var items: ArrayList<RankingInfo>): RecyclerView.Adapter<RankingAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: ItemRankingBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            binding.rankingItemTvRank.text = items[position].rank.toString()
            binding.rankingItemTvNickname.text = items[position].name
            binding.rankingItemTvTime.text = items[position].time.toString() + " Min"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position)
    }
}