package com.example.bangwool

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.TextView
import android.view.LayoutInflater
import android.view.Window
import android.widget.NumberPicker
import com.example.bangwool.databinding.DialogTimecheckBinding
import java.lang.reflect.Field

class TimeDialogUtils(private val context: Context) {
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

    fun showWorkTimeDialog(textViewWorkTime: TextView) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)

        val inflater = LayoutInflater.from(context)
        val binding: DialogTimecheckBinding = DialogTimecheckBinding.inflate(inflater)
        dialog.setContentView(binding.root)

        // 숫자 범위 설정 하는 부분
        binding.numberPickerHour.minValue = 0
        binding.numberPickerHour.maxValue = 23

        binding.numberPickerMinute.minValue = 0
        binding.numberPickerMinute.maxValue = 59

        // 시간 형식 설정 XX형식으로
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
            //데이터 바인딩으로다가..
            textViewWorkTime.text = formattedTime
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
//NumberPicker의 setFormatter 메서드를 사용하여 숫자 형식을 변경.
//binding.numberPickerHour.setFormatter { String.format("%02d", it) }를 통해 시간 값을 0부터 9까지는 "0X" 형식으로, 10부터 23까지는 "XX" 형식으로 표시하도록 설정.
//binding.numberPickerMinute.setFormatter { String.format("%02d", it) }를 통해 분 값을 0부터 9까지는 "0X" 형식으로, 10부터 59까지는 "XX" 형식으로 표시하도록 설정.
//구분선 안없어져서 탈모올듯
