package com.example.bangwool

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import android.widget.NumberPicker
import android.widget.TextView
import com.example.bangwool.databinding.DialogRestTimecheckBinding
import java.lang.reflect.Field

class RestTimerDialogUtil(private val context: Context) {

    private fun hideNumberPickerDivider(picker: NumberPicker) {
        try {
            val pickerFields: Array<Field> = NumberPicker::class.java.declaredFields
            for (field in pickerFields) {
                if (field.name == "mSelectionDivider") {
                    field.isAccessible = true
                    val colorDrawable = ColorDrawable(Color.TRANSPARENT)
                    field.set(picker, colorDrawable)
                    break
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showRestTimeDialog(textViewRestTime: TextView) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)

        val inflater = LayoutInflater.from(context)
        val binding: DialogRestTimecheckBinding = DialogRestTimecheckBinding.inflate(inflater)
        dialog.setContentView(binding.root)

        // 숫자 범위 설정
        binding.numberPickerHour.minValue = 0
        binding.numberPickerHour.maxValue = 23

        binding.numberPickerMinute.minValue = 0
        binding.numberPickerMinute.maxValue = 59

        // 시간 형식 설정
        binding.numberPickerHour.setFormatter { String.format("%02d", it) }
        binding.numberPickerMinute.setFormatter { String.format("%02d", it) }

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
            textViewRestTime.text = formattedTime
            dialog.dismiss()
        }

        // 구분선 숨기기
        dialog.setOnShowListener {
            hideNumberPickerDivider(binding.numberPickerHour)
            hideNumberPickerDivider(binding.numberPickerMinute)
        }

        dialog.show()
    }
}
