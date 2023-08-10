package com.example.bangwool.ui.statistic

import android.app.Dialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import com.example.bangwool.databinding.FragmentStatisticBinding
import com.example.bangwool.retrofit.MonthWorkStatistic
import com.example.bangwool.retrofit.MonthWorkStatisticRequest
import com.example.bangwool.retrofit.MonthWorkStatisticResponse
import com.example.bangwool.retrofit.RetrofitUtil
import com.example.bangwool.retrofit.WeekWorkStatisticResponse
import com.example.bangwool.retrofit.getGoalTime
import com.example.bangwool.retrofit.removeGoalTime
import com.example.bangwool.retrofit.saveGoalTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import kotlin.properties.Delegates


class StatisticFragment : Fragment() {
    lateinit var binding: FragmentStatisticBinding
    var goalHour = 0
    var nowDate: LocalDate = LocalDate.now()
    var weekHourArr: Array<Double> = arrayOf() // 월~일까지의 시간 배열 더미 데이터 (받아와야할 데이터)
    var calendarMonth: Int = LocalDate.now().month.value
    var monthWorks = listOf<MonthWorkStatistic>()
    lateinit var todayView: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatisticBinding.inflate(inflater, container, false)
//        removeGoalTime(requireContext())
//        Log.d("","${getGoalTime(requireContext())}sdfafa")
        val localGoalTime = getGoalTime(requireContext()).toInt()
        if (localGoalTime == 0) {
            // 목표시간 설정 버튼 클릭시 event
            binding.llCalenderAdd.setOnClickListener {
                val dlg = GoalTimeDialog(requireContext())
                dlg.setOnOKClickedListener { hour ->
                    binding.tvGoalTimeText.text = "${hour} 시간"
                    goalHour = hour.toInt()
                    saveGoalTime(requireContext(), hour.toInt())
                    binding.llCalenderMain.visibility = View.VISIBLE
                    binding.llCalenderAdd.visibility = View.GONE
                }
                dlg.show()
            }
        } else {
            goalHour = localGoalTime
            binding.tvGoalTimeText.text = "${localGoalTime} 시간"
            binding.llCalenderMain.visibility = View.VISIBLE
            binding.llCalenderAdd.visibility = View.GONE
        }


        binding.llGoalTimeEditText.setOnClickListener {
            val dlg = GoalTimeDialog(requireContext())
            dlg.setOnOKClickedListener { hour ->
                binding.tvGoalTimeText.text = "${hour} 시간"
                saveGoalTime(requireContext(), hour.toInt())
                goalHour = hour.toInt()
                makeWeekGraph()
                nowDate = LocalDate.now()
                makeCalender(nowDate)
            }
            dlg.show()
        }


        // 달력 구현하기


        binding.ivCalenderBackBtn.setOnClickListener {
            if (calendarMonth != 1) {
                if (calendarMonth == nowDate.month.value) {
                    binding.ivCalenderNextBtn.imageTintList =
                        ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.gray_700))
                }
                if (calendarMonth == 2) {
                    binding.ivCalenderBackBtn.imageTintList =
                        ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.gray_300))
                }
                if (calendarMonth == nowDate.month.value) {
                    todayView.background = null
                }
                getMonthWorkStatistic(LocalDate.of(nowDate.year, calendarMonth - 1, 1))
                calendarMonth--
            }
        }
        binding.ivCalenderNextBtn.setOnClickListener {
            if (calendarMonth != nowDate.month.value && calendarMonth != 12) {
                if (calendarMonth + 1 == nowDate.month.value) {
                    binding.ivCalenderNextBtn.imageTintList =
                        ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.gray_300))
                }
                if (calendarMonth == 1) {
                    binding.ivCalenderBackBtn.imageTintList =
                        ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.gray_700))
                }
                getMonthWorkStatistic(LocalDate.of(nowDate.year, calendarMonth + 1, 1))
                calendarMonth++
            }
        }
        getWeekWorkStatistic()
        getMonthWorkStatistic(nowDate)
        return binding.root
    }

    fun makeCalender(inputDate: LocalDate) {

        var goalWorkDayArr = listOf<Int>()
        if (monthWorks[0] != null) {
            val goalWorks = monthWorks.filter { item -> item.workHour >= goalHour }
//                        val goalWorks = works.filter { item -> item.workMin>=3 }
            goalWorkDayArr = goalWorks.map { item -> item.day }
        }

        var lastMonthDate = LocalDate.now()
        if (inputDate.month.value == 1) {
            lastMonthDate = LocalDate.of(inputDate.year - 1, 12, 1)
        } else {
            lastMonthDate = LocalDate.of(inputDate.year, inputDate.month.value - 1, 1)
        }
        var firstNowDate = inputDate.withDayOfMonth(1)
        var dateLength = inputDate.lengthOfMonth()
        val calenderDayTextArr: Array<TextView> = arrayOf(
            binding.tvCalender1r1c,
            binding.tvCalender1r2c,
            binding.tvCalender1r3c,
            binding.tvCalender1r4c,
            binding.tvCalender1r5c,
            binding.tvCalender1r6c,
            binding.tvCalender1r7c,
            binding.tvCalender2r1c,
            binding.tvCalender2r2c,
            binding.tvCalender2r3c,
            binding.tvCalender2r4c,
            binding.tvCalender2r5c,
            binding.tvCalender2r6c,
            binding.tvCalender2r7c,
            binding.tvCalender3r1c,
            binding.tvCalender3r2c,
            binding.tvCalender3r3c,
            binding.tvCalender3r4c,
            binding.tvCalender3r5c,
            binding.tvCalender3r6c,
            binding.tvCalender3r7c,
            binding.tvCalender4r1c,
            binding.tvCalender4r2c,
            binding.tvCalender4r3c,
            binding.tvCalender4r4c,
            binding.tvCalender4r5c,
            binding.tvCalender4r6c,
            binding.tvCalender4r7c,
            binding.tvCalender5r1c,
            binding.tvCalender5r2c,
            binding.tvCalender5r3c,
            binding.tvCalender5r4c,
            binding.tvCalender5r5c,
            binding.tvCalender5r6c,
            binding.tvCalender5r7c
        )
        var startIndex: Int = 100
        var calendarDay: Int = 1
        binding.tvMonthControlText.text = "${inputDate.month.value}월"
        for (i in 0 until calenderDayTextArr.size) {
            if (i > startIndex) {
                if (calendarDay > dateLength) {
                    calenderDayTextArr[i].text = ""
                    calenderDayTextArr[i].background = null
                } else {
                    calenderDayTextArr[i].text = calendarDay.toString()
                    if (goalWorkDayArr.contains(calendarDay)) {
                        calenderDayTextArr[i].background =
                            resources.getDrawable(com.example.bangwool.R.drawable.ic_circle_filled)
                        calenderDayTextArr[i].setTextColor(resources.getColor(com.example.bangwool.R.color.white))
                        calenderDayTextArr[i].backgroundTintList =
                            ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.primary_300))
                        if (inputDate.month.value == nowDate.month.value) {
                            if (nowDate.dayOfMonth == calendarDay) {
                                calenderDayTextArr[i].background =
                                    resources.getDrawable(com.example.bangwool.R.drawable.custom_circle_underline_layout)
                                calenderDayTextArr[i].backgroundTintList =
                                    ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.primary_300))
                                todayView = calenderDayTextArr[i]
                            }
                        }
                    } else {
                        calenderDayTextArr[i].background = null
                        calenderDayTextArr[i].setTextColor(resources.getColor(com.example.bangwool.R.color.gray_700))
                        if (inputDate.month.value == nowDate.month.value) {
                            if (nowDate.dayOfMonth == calendarDay) {
                                calenderDayTextArr[i].background =
                                    resources.getDrawable(com.example.bangwool.R.drawable.custom_underline_text)
                                calenderDayTextArr[i].backgroundTintList =
                                    ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.primary_300))
                                todayView = calenderDayTextArr[i]
                            }
                        }
                    }

                    calendarDay++
                }
            } else {
                if (i + 1 == firstNowDate.dayOfWeek.value) {
                    startIndex = i
                    calenderDayTextArr[i].text = calendarDay.toString()
                    if (goalWorkDayArr.contains(calendarDay)) {
                        calenderDayTextArr[i].background =
                            resources.getDrawable(com.example.bangwool.R.drawable.ic_circle_filled)
                        calenderDayTextArr[i].backgroundTintList =
                            ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.primary_300))
                        calenderDayTextArr[i].backgroundTintList =
                            ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.primary_300))
                        if (inputDate.month.value == nowDate.month.value) {
                            if (nowDate.dayOfMonth == calendarDay) {
                                calenderDayTextArr[i].background =
                                    resources.getDrawable(com.example.bangwool.R.drawable.custom_circle_underline_layout)
                                calenderDayTextArr[i].backgroundTintList =
                                    ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.primary_300))
                                todayView = calenderDayTextArr[i]
                            }
                        }
                    } else {
                        calenderDayTextArr[i].background = null
                        calenderDayTextArr[i].setTextColor(resources.getColor(com.example.bangwool.R.color.gray_700))
                        if (inputDate.month.value == nowDate.month.value) {
                            if (nowDate.dayOfMonth == calendarDay) {
                                calenderDayTextArr[i].background =
                                    resources.getDrawable(com.example.bangwool.R.drawable.custom_underline_text)
                                calenderDayTextArr[i].backgroundTintList =
                                    ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.primary_300))
                                todayView = calenderDayTextArr[i]
                            }
                        }
                    }

                    calendarDay++
                } else {
                    calenderDayTextArr[i].text =
                        (lastMonthDate.lengthOfMonth() - (firstNowDate.dayOfWeek.value - (i + 2))).toString()
                    calenderDayTextArr[i].setTextColor(resources.getColor(com.example.bangwool.R.color.gray_300))
                    calenderDayTextArr[i].background = null
                }
            }
        }

    }

    private fun makeWeekGraph() {
        Log.d("qwerty123", "hellohello")
        val hourTextArr: Array<TextView> = arrayOf(
            binding.tvMondayHourText,
            binding.tvTuesdayHourText,
            binding.tvWednesdayHourText,
            binding.tvThursdayHourText,
            binding.tvFridayHourText,
            binding.tvSaturdayHourText,
            binding.tvSundayHourText
        )
        val barArr: Array<View> = arrayOf(
            binding.vMondayHourBar,
            binding.vTuesdayHourBar,
            binding.vWednesdayHourBar,
            binding.vThursdayHourBar,
            binding.vFridayHourBar,
            binding.vSaturdayHourBar,
            binding.vSundayHourBar
        )
        // 그래프에 시간 text 입력
        for (i in 1..hourTextArr.size) {
            if (weekHourArr[i - 1] % 1 == 0.0) {
                hourTextArr[i - 1].text = "${(weekHourArr[i - 1] / 1).toInt()}h"
            } else {
                hourTextArr[i - 1].text = "${weekHourArr[i - 1]}h"
            }
        }

        // 그래프 색깔 설정
        for (i in 0 until hourTextArr.size) {
            if (weekHourArr[i] >= goalHour.toDouble()) {
                barArr[i].backgroundTintList =
                    ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.statistic_purple))
                hourTextArr[i].backgroundTintList =
                    ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.statistic_purple))
            } else {
                barArr[i].backgroundTintList =
                    ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.statistic_light_purple))
                hourTextArr[i].backgroundTintList =
                    ColorStateList.valueOf(resources.getColor(com.example.bangwool.R.color.statistic_light_purple))
            }
        }

        // 그래프 max 값에 따른 그래프 길이 조절
        val maxHour: Double = weekHourArr.max() //
        if (maxHour == 0.0) {
            for (i in 0 until weekHourArr.size) {
                val lp = barArr[i].layoutParams
                lp.height = 0
                barArr[i].layoutParams = lp
                val viewHeight = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    180F,
                    resources.displayMetrics
                ).toInt()
                val param = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                param.bottomMargin = viewHeight
                param.height = 0
                barArr[i].layoutParams = param
            }

        } else {
            for (i in 0 until weekHourArr.size) {
                val lp = barArr[i].layoutParams
                val dpValue = (180 * (weekHourArr[i] / maxHour))
                val viewHeight = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dpValue.toFloat(),
                    resources.displayMetrics
                ).toInt()
                lp.height = viewHeight
                barArr[i].layoutParams = lp
            }
        }
    }

    private fun getWeekWorkStatistic() {
        RetrofitUtil.getRetrofit().GetWeekWorkStatistic().enqueue(object :
            Callback<WeekWorkStatisticResponse> {
            override fun onResponse(
                call: Call<WeekWorkStatisticResponse>,
                response: Response<WeekWorkStatisticResponse>
            ) {
                if (response.isSuccessful) {
                    val works = response.body()!!.works
                    Log.d("", "works: ${works}")
                    if (works[0] != null) {
                        for (i in 1..7) {
                            val matchedItem = works.find { item -> item.dayOfWeek == i }
                            if (matchedItem != null) {
                                val totalTime: Double =
                                    matchedItem.workHour.toDouble() + (matchedItem.workMin * 10 / 60).toDouble() / 10.0
                                weekHourArr = weekHourArr.plus(totalTime)
                            } else {
                                weekHourArr = weekHourArr.plus(0.0)

                            }
                        }
                    } else {
                        weekHourArr = arrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
                    }

                    makeWeekGraph()
                    Log.d("", "성공함 works:${works}")
                } else {
                    Log.d("", "실패함")

                }
            }

            override fun onFailure(call: Call<WeekWorkStatisticResponse>, t: Throwable) {
                Log.d("", "실패함 onFailure")

            }
        })
    }

    private fun getMonthWorkStatistic(inputDate: LocalDate) {
        val monthWorkStatisticRequest =
            MonthWorkStatisticRequest(inputDate.year, inputDate.monthValue)
        RetrofitUtil.getRetrofit().GetMonthWorkStatistic(monthWorkStatisticRequest).enqueue(object :
            Callback<MonthWorkStatisticResponse> {
            override fun onResponse(
                call: Call<MonthWorkStatisticResponse>,
                response: Response<MonthWorkStatisticResponse>
            ) {
                if (response.isSuccessful) {
                    monthWorks = response.body()!!.works
                    Log.d("", "성공함 works:${monthWorks[0]}")
                    makeCalender(inputDate)
                } else {
                    Log.d("", "실패함")
                }
            }

            override fun onFailure(call: Call<MonthWorkStatisticResponse>, t: Throwable) {
                Log.d("", "실패함 onFailure")

            }
        })
    }

}