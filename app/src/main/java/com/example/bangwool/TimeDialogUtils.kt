package com.example.bangwool

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import com.example.bangwool.databinding.DialogTimecheckBinding
import java.lang.reflect.Field

class TimeDialogUtils(private val context: Context) {
    private fun setUnselectedNumberColor(picker: NumberPicker, unselectedColor: Int) {
        val count = picker.childCount
        for (i in 0 until count) {
            val child = picker.getChildAt(i)
            if (child is ViewGroup) {
                val childCount = child.childCount
                for (j in 0 until childCount) {
                    val subChild = child.getChildAt(j)
                    if (subChild is TextView) {
                        subChild.setTextColor(unselectedColor)
                    }
                }
            }
        }
    }

    private fun setSelectedNumberColor(picker: NumberPicker, selectedColor: Int) {
        val selectedNumber = picker.getChildAt(picker.value)
        if (selectedNumber is EditText) {
            selectedNumber.setTextColor(selectedColor)
        }
    }

    fun showWorkTimeDialog(textViewWorkTime: TextView) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)

        val inflater = LayoutInflater.from(context)
        val binding: DialogTimecheckBinding = DialogTimecheckBinding.inflate(inflater)
        dialog.setContentView(binding.root)

        // 숫자 범위 설정하는 부분
        binding.numberPickerHour.minValue = 0
        binding.numberPickerHour.maxValue = 23

        binding.numberPickerMinute.minValue = 0
        binding.numberPickerMinute.maxValue = 59

        // 시간 형식 설정 XX 형식으로
        binding.numberPickerHour.setFormatter { String.format("%02d", it) }
        binding.numberPickerMinute.setFormatter { String.format("%02d", it) }

        // 색상 설정
        val selectedColor = Color.BLACK
        val unselectedColor = Color.GRAY
        setUnselectedNumberColor(binding.numberPickerHour, unselectedColor)
        setUnselectedNumberColor(binding.numberPickerMinute, unselectedColor)
        setSelectedNumberColor(binding.numberPickerHour, selectedColor)
        setSelectedNumberColor(binding.numberPickerMinute, selectedColor)

        binding.numberPickerHour.setOnValueChangedListener { picker, _, _ ->
            setUnselectedNumberColor(picker, unselectedColor)
            setSelectedNumberColor(picker, selectedColor)
        }

        binding.numberPickerMinute.setOnValueChangedListener { picker, _, _ ->
            setUnselectedNumberColor(picker, unselectedColor)
            setSelectedNumberColor(picker, selectedColor)
        }

        binding.buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        binding.buttonConfirm.setOnClickListener {
            val selectedHour = binding.numberPickerHour.value
            val selectedMinute = binding.numberPickerMinute.value

            // 선택한 시간에 따라 형식 지정
            val formattedTime = if (selectedHour == 0) {
                String.format("%02d분", selectedMinute)
            } else {
                String.format("%02d시%02d분", selectedHour, selectedMinute)
            }
            // 텍스트 뷰에 반영
            textViewWorkTime.text = formattedTime
            dialog.dismiss()
        }

        dialog.show()
    }
}
