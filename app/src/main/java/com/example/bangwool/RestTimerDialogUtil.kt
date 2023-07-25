package com.example.bangwool

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import com.example.bangwool.databinding.DialogRestTimecheckBinding

class RestTimerDialogUtil(private val context: Context) {

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

        val selectedTextColor = Color.BLACK
        val unselectedTextColor = Color.BLACK

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
                String.format("%02d시간%02d분", selectedHour, selectedMinute)
            }

            // 텍스트 뷰에 반영
            textViewRestTime.text = formattedTime
            dialog.dismiss()
        }

        // 선택한 숫자의 색상 변경
        binding.numberPickerHour.setOnValueChangedListener { picker, oldVal, newVal ->
            setSelectedTextColor(binding.numberPickerHour)
        }

        binding.numberPickerMinute.setOnValueChangedListener { picker, oldVal, newVal ->
            setSelectedTextColor(binding.numberPickerMinute)
        }

        // 다이얼로그 크기 지정
        dialogResize(dialog, 0.7f, 0.5f)

        dialog.show()
    }

    private fun setSelectedTextColor(np: NumberPicker) {
        val count = np.childCount
        val selectedTextColor = Color.BLACK

        for (i in 0 until count) {
            val child = np.getChildAt(i)
            if (child is EditText) {
                child.setTextColor(if (i == np.value) selectedTextColor else child.textColors.defaultColor)
                np.invalidate()
                break
            }
        }
    }

    private fun dialogResize(dialog: Dialog, width: Float, height: Float) {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val displayMetrics = context.resources.displayMetrics
        val dialogWidth = (displayMetrics.widthPixels * width).toInt()
        val dialogHeight = (displayMetrics.heightPixels * height).toInt()

        dialog.window?.setLayout(dialogWidth, dialogHeight)
    }
}
