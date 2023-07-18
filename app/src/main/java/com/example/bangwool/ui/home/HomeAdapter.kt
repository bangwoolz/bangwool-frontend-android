package com.example.bangwool.ui.home

import android.content.Context
import android.graphics.ColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.bangwool.R
import com.example.bangwool.databinding.ItemHomeBinding

class HomeAdapter (private var itemList: ArrayList<HomeItem>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(homeItem: HomeItem)
    }

    fun setOnClickListener(onItemClickListener: OnItemClickListener) {
        itemClickListener = onItemClickListener
    }

    inner class ViewHolder(val binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context: Context = itemView.context
        fun bind(itemList: HomeItem) {

            binding.apply {
                homeItemTaskCircle.setColorFilter(context.getColor(itemList.taskColor))
//                homeItemTaskCircle.setBackgroundColor(context.getColor(itemList.taskColor))
//                homeItemTaskCircle.setBackgroundResource(R.drawable.circle_layout)
                homeItemTaskTv.text = itemList.taskName
                homeItemTaskTime.text = itemList.taskTime
                homeItemTaskTimeBtn.setImageResource(R.drawable.ic_play_filled)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])

    }
}
