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
        fun bind(item: HomeItem) {

            binding.apply {
                homeItemTaskCircle.setColorFilter(context.getColor(item.taskColor))
//                homeItemTaskCircle.setBackgroundColor(context.getColor(itemList.taskColor))
//                homeItemTaskCircle.setBackgroundResource(R.drawable.circle_layout)
                homeItemTaskTv.text = item.taskName
                homeItemTaskTime.text = item.taskTime
                homeItemTaskTimeBtn.setImageResource(R.drawable.ic_play_filled)
                homeItemTaskTimeBtn.setOnClickListener {
                    val i = Intent(context, TimerActivity::class.java)
                    context.startActivity(i)
                }
                modifyBtn.setOnClickListener {
                    val i = Intent(context, TimerEditActivity::class.java)
                    i.putExtra("taskColor", item.taskColor)
                    i.putExtra("taskName", item.taskName)
                    i.putExtra("taskTime", item.taskTime)
                    i.putExtra("taskState", item.taskState)
                    context.startActivity(i)
                }
                deleteBtn.setOnClickListener {
                    itemClickListener?.onDeleteItemClick(item)
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

