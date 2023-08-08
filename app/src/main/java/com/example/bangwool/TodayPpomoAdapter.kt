package com.example.bangwool

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bangwool.databinding.ItemTodayPpomoBinding
import com.example.bangwool.retrofit.WorkTodayResponse
class TodayPpomoAdapter(
    private val context: Context,
    private var itemList: ArrayList<WorkTodayResponse>
) :
    RecyclerView.Adapter<TodayPpomoAdapter.ViewHolder>() {

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

