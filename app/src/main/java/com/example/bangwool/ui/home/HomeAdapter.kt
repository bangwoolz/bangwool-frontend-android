package com.example.bangwool.ui.home

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.bangwool.MainActivity
import com.example.bangwool.R
import com.example.bangwool.databinding.ItemHomeBinding
import kotlin.math.max
import kotlin.math.min

class HomeAdapter(
    private val context: Context,
    private var itemList: ArrayList<HomeItem>
) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onDeleteItemClick(homeItem: HomeItem)
    }

    fun setOnClickListener(onItemClickListener: OnItemClickListener) {
        itemClickListener = onItemClickListener
    }

    // 아이템 삭제 메소드
    fun removeItem(item: HomeItem) {
        val position = itemList.indexOf(item)
        if (position != -1) {
            itemList.removeAt(position)
            notifyItemRemoved(position)
        }
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
                modifyBtn.setOnClickListener {
                    Log.d("CLICK!", "modifyBtn")

                }
                deleteBtn.setOnClickListener {
                    itemClickListener?.onDeleteItemClick(itemList)
                }
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

