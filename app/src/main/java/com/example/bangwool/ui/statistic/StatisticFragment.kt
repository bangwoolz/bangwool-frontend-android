package com.example.bangwool.ui.statistic

import android.app.Dialog
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.bangwool.databinding.DialogTimecheckBinding
import com.example.bangwool.databinding.FragmentStatisticBinding
import kotlin.properties.Delegates


class StatisticFragment : Fragment() {
    lateinit var binding : FragmentStatisticBinding
    var goalHour = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatisticBinding.inflate(inflater, container, false)

        // 목표시간 설정 버튼 클릭시 event
        binding.llCalenderAdd.setOnClickListener{
            val dlg = GoalTimeDialog(requireContext())
            dlg.setOnOKClickedListener{ hour ->
                binding.tvGoalTimeText.text = "${hour} 시간"
                binding.llCalenderMain.visibility = View.VISIBLE
                binding.llCalenderAdd.visibility = View.GONE
            }
            dlg.show()
        }


        val hourArr:Array<Double> = arrayOf(4.0,2.5,0.5,2.5,5.5,7.0,3.0) // 월~일까지의 시간 배열 더미 데이터 (받아와야할 데이터)
        val hourTextArr:Array<TextView> = arrayOf(binding.tvMondayHourText,binding.tvTuesdayHourText,binding.
        tvWednesdayHourText,binding.tvThursdayHourText,binding.tvFridayHourText,binding.tvSaturdayHourText,binding.tvSundayHourText)
        val barArr:Array<View> = arrayOf(binding.vMondayHourBar,binding.vTuesdayHourBar,binding.vWednesdayHourBar,
        binding.vThursdayHourBar,binding.vFridayHourBar,binding.vSaturdayHourBar,binding.vSundayHourBar)

        // 그래프에 시간 text 입력
        for (i in 1..hourTextArr.size) {
            if(hourArr[i-1]%1==0.0){
                hourTextArr[i-1].text = "${(hourArr[i-1]/1).toInt()}h"
            } else {
                hourTextArr[i-1].text = "${hourArr[i-1]}h"
            }
        }

        // 그래프 max 값에 따른 그래프 길이 조절
        val maxHour:Double = hourArr.max() //
        if(maxHour==0.0){
            for( i in 0 until hourArr.size){
                val lp = barArr[i].layoutParams
                lp.height = 0
                barArr[i].layoutParams = lp
            }
        } else {
            for( i in 0 until hourArr.size){
                val lp = barArr[i].layoutParams
                val dpValue = (180*(hourArr[i]/maxHour))
                val viewHeight = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dpValue.toFloat(),
                    resources.displayMetrics
                ).toInt()
                lp.height = viewHeight
                barArr[i].layoutParams = lp
            }
        }



        return binding.root
    }


}