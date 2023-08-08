package com.example.bangwool

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.bangwool.R
import com.example.bangwool.databinding.ItemHomeBinding
import com.example.bangwool.databinding.ItemTodayPpomoBinding
import com.example.bangwool.retrofit.Ppomodoro
import com.example.bangwool.retrofit.PpomodoroId
import com.example.bangwool.retrofit.WorkTodayResponse
import com.example.bangwool.retrofit.WorksTodayResponse
import com.example.bangwool.ui.home.TimerActivity
import com.example.bangwool.ui.home.TimerEditActivity

class TodayPpomoAdapter(
    private val context: Context,
    private var itemList: ArrayList<WorkTodayResponse>
) :
    RecyclerView.Adapter<TodayPpomoAdapter.ViewHolder>() {

//    var itemClickListener: OnItemClickListener? = null
//
//    interface OnItemClickListener {
//        fun onItemClick(homeItem: PpomodoroId)
//    }
//
//    fun setOnClickListener(onItemClickListener: OnItemClickListener) {
//        itemClickListener = onItemClickListener
//    }

    inner class ViewHolder(val binding: ItemTodayPpomoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WorkTodayResponse) {
            val workTime = item.workHour.toString() + "h " + item.workMin.toString() + "m"
            Log.i("TodayPpomoAdapter_workTime", workTime)

            binding.apply {
                ppomoName.text = item.name
                ppomoTime.text = workTime
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemTodayPpomoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])

    }

}

