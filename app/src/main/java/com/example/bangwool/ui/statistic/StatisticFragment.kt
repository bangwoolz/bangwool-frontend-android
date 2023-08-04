package com.example.bangwool.ui.statistic

import android.app.Dialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
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
import java.time.LocalDate
import kotlin.properties.Delegates


class StatisticFragment : Fragment() {
    lateinit var binding : FragmentStatisticBinding
    var goalHour =0
    var nowDate:LocalDate = LocalDate.now()
    var calendarMonth:Int =LocalDate.now().month.value
    lateinit var todayView:TextView
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
                goalHour = hour.toInt()
                binding.llCalenderMain.visibility = View.VISIBLE
                binding.llCalenderAdd.visibility = View.GONE
            }
            dlg.show()
        }

        binding.llGoalTimeEditText.setOnClickListener{
            val dlg = GoalTimeDialog(requireContext())
            dlg.setOnOKClickedListener{ hour ->
                binding.tvGoalTimeText.text = "${hour} 시간"
                goalHour = hour.toInt()
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

        // 달력 구현하기

        makeCalender(nowDate);


        binding.ivCalenderBackBtn.setOnClickListener{
            if(calendarMonth!=1){
                if(calendarMonth==nowDate.month.value){
                    binding.ivCalenderNextBtn.imageTintList = ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.gray_700))
                }
                if (calendarMonth==2){
                    binding.ivCalenderBackBtn.imageTintList = ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.gray_300))
                }
                if(calendarMonth==nowDate.month.value){
                    todayView.background = null
                }
                makeCalender(LocalDate.of(nowDate.year,calendarMonth-1,1))
                calendarMonth--
            }
        }
        binding.ivCalenderNextBtn.setOnClickListener{
            if(calendarMonth!=nowDate.month.value&&calendarMonth!=12){
                if(calendarMonth+1==nowDate.month.value){
                    binding.ivCalenderNextBtn.imageTintList = ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.gray_300))
                }
                if(calendarMonth==1){
                    binding.ivCalenderBackBtn.imageTintList = ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.gray_700))
                }
                makeCalender(LocalDate.of(nowDate.year,calendarMonth+1,1))
                calendarMonth++
            }
        }
        return binding.root
    }

    fun makeCalender(inputDate:LocalDate) {
        var lastMonthDate = LocalDate.now()
        if(inputDate.month.value==1){
            lastMonthDate = LocalDate.of(inputDate.year-1,12,1)
        } else {
            lastMonthDate = LocalDate.of(inputDate.year,inputDate.month.value-1,1)
        }
        var firstNowDate = inputDate.withDayOfMonth(1)
        var dateLength = inputDate.lengthOfMonth()
        var lastNowDate = inputDate.withDayOfMonth(dateLength)
        val calenderDayTextArr:Array<TextView> = arrayOf(binding.tvCalender1r1c,binding.tvCalender1r2c,binding.tvCalender1r3c,
            binding.tvCalender1r4c,binding.tvCalender1r5c,binding.tvCalender1r6c,binding.tvCalender1r7c,binding.tvCalender2r1c,
            binding.tvCalender2r2c,binding.tvCalender2r3c,binding.tvCalender2r4c,binding.tvCalender2r5c,binding.tvCalender2r6c,
            binding.tvCalender2r7c,binding.tvCalender3r1c,binding.tvCalender3r2c,binding.tvCalender3r3c,binding.tvCalender3r4c,
            binding.tvCalender3r5c,binding.tvCalender3r6c,binding.tvCalender3r7c,binding.tvCalender4r1c,binding.tvCalender4r2c,
            binding.tvCalender4r3c,binding.tvCalender4r4c,binding.tvCalender4r5c,binding.tvCalender4r6c,binding.tvCalender4r7c,
            binding.tvCalender5r1c,binding.tvCalender5r2c,binding.tvCalender5r3c,binding.tvCalender5r4c,binding.tvCalender5r5c,
            binding.tvCalender5r6c,binding.tvCalender5r7c)
        var startIndex:Int = 100
        var calendarDay:Int = 1
        binding.tvMonthControlText.text = "${inputDate.month.value}월"
        for( i in 0 until calenderDayTextArr.size){
            if(i>startIndex){
                if(calendarDay>dateLength){
                    calenderDayTextArr[i].text=""
                } else {
                    calenderDayTextArr[i].text=calendarDay.toString()
                    calenderDayTextArr[i].setTextColor(resources.getColor(com.example.bangwool.R.color.gray_700))
                    if(inputDate.month.value==nowDate.month.value){
                        if(nowDate.dayOfMonth==calendarDay){
                            calenderDayTextArr[i].background = resources.getDrawable(com.example.bangwool.R.drawable.custom_underline_text)
                            todayView = calenderDayTextArr[i]
                        }
                    }
                    calendarDay++
                }
            } else {
                if(i+1==firstNowDate.dayOfWeek.value){
                    startIndex = i
                    calenderDayTextArr[i].text=calendarDay.toString()
                    calenderDayTextArr[i].setTextColor(resources.getColor(com.example.bangwool.R.color.gray_700))
                    calendarDay++
                } else {
                    calenderDayTextArr[i].text=(lastMonthDate.lengthOfMonth()-(firstNowDate.dayOfWeek.value-(i+2))).toString()
                    calenderDayTextArr[i].setTextColor(resources.getColor(com.example.bangwool.R.color.gray_300))
                }
            }
        }

    }

}