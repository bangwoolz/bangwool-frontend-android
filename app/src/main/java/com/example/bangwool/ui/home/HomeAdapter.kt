package com.example.bangwool.ui.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.example.bangwool.R
import com.example.bangwool.databinding.ItemHomeBinding
import com.example.bangwool.retrofit.Ppomodoro
import com.example.bangwool.retrofit.PpomodoroId

class HomeAdapter(
    private val homeFragment: HomeFragment,
    private val context: Context,
    private var itemList: ArrayList<PpomodoroId>
) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    var itemClickListener: OnItemClickListener? = null
    val colorMap = mapOf(
        "red" to R.color.timer_color_red,
        "pink" to R.color.timer_color_pink,
        "orange" to R.color.timer_color_orange,
        "yellow" to R.color.timer_color_yellow,
        "purple" to R.color.timer_color_purple,
        "blue" to R.color.timer_color_blue,
        "skyblue" to R.color.timer_color_skyblue,
        "green" to R.color.timer_color_green
    )


    interface OnItemClickListener {
        fun onDeleteItemClick(homeItem: PpomodoroId)
        fun onEditItemClick(homeItem: PpomodoroId)

    }

    fun setOnClickListener(onItemClickListener: OnItemClickListener) {
        itemClickListener = onItemClickListener
    }

    fun findId(item: PpomodoroId): Int {
        // 뽀모도로 아이디 찾기
        val Id = item.id
        return Id
    }

    inner class ViewHolder(val binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context: Context = itemView.context
        fun bind(item: PpomodoroId) {
            val workTime = item.workHour * 60 + item.workMin
            val workTimeText = workTime.toString() + " : 00"

            binding.apply {

                homeItemTaskCircle.setColorFilter(getColor(context, colorMap[item.color]!!))
//                homeItemTaskCircle.setBackgroundColor(context.getColor(itemList.taskColor))
//                homeItemTaskCircle.setBackgroundResource(R.drawable.circle_layout)
                homeItemTaskTv.text = item.name
                homeItemTaskTime.text = workTimeText
//                homeItemTaskTimeBtn.setImageResource(R.drawable.ic_play_filled)
                homeItemTaskTimeBtn.setOnClickListener {
                    val i = Intent(context, TimerActivity::class.java)
                    i.putExtra("id", item.id.toString())
                    i.putExtra("name", item.name)
                    i.putExtra("color", item.color)
                    i.putExtra("workHour", item.workHour.toString())
                    i.putExtra("workMin", item.workMin.toString())
                    i.putExtra("restTime", item.restTime.toString())
                    context.startActivity(i)
                }
                modifyBtn.setOnClickListener {
                    val i = Intent(context, TimerEditActivity::class.java)
                    i.putExtra("taskColor", item.color)
                    i.putExtra("taskName", item.name)
                    i.putExtra("taskTime", workTimeText)
//                    i.putExtra("taskState", item.taskState)
                    i.putExtra("timerTitle", "타이머 수정")
                    i.putExtra("PpomoId", item.id.toString())
//                    context.startActivity(i)
                    homeFragment.updatePpomodoro.launch(i)

                    itemClickListener?.onEditItemClick(item)
                }
                deleteBtn.setOnClickListener {
                    itemClickListener?.onDeleteItemClick(item)
//                    deletePpomo()

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

