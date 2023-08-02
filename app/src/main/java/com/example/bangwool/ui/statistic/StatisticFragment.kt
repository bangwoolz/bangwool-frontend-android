package com.example.bangwool.ui.statistic

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.bangwool.databinding.FragmentStatisticBinding


class StatisticFragment : Fragment() {
    lateinit var binding : FragmentStatisticBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatisticBinding.inflate(inflater, container, false)
        binding.llCalenderAdd.setOnClickListener{
            binding.llCalenderMain.visibility = View.VISIBLE
            binding.llCalenderAdd.visibility = View.GONE
        }
        val hourArr:Array<Double> = arrayOf(4.0,2.5,0.5,2.5,5.5,7.0,3.0)
        val hourTextArr:Array<TextView> = arrayOf(binding.tvMondayHourText,binding.tvTuesdayHourText,binding.
        tvWednesdayHourText,binding.tvThursdayHourText,binding.tvFridayHourText,binding.tvSaturdayHourText,binding.tvSundayHourText)
        val barArr:Array<View> = arrayOf(binding.vMondayHourBar,binding.vTuesdayHourBar,binding.vWednesdayHourBar,
        binding.vThursdayHourBar,binding.vFridayHourBar,binding.vSaturdayHourBar,binding.vSundayHourBar)
        for (i in 1..hourTextArr.size) {
            hourTextArr[i-1].text = "${hourArr[i-1]}h"
        }
        val maxHour:Double = hourArr.max()
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
        return binding.root
    }
}