package com.example.bangwool.ui.login

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import com.example.bangwool.R
import com.example.bangwool.databinding.DialogTimechooseBinding

class TimeChooseDialog(private val context: Context, val title: String, val range: Int, val chosen_number: Int) {

    fun showWorkTimeDialog(textViewWorkTime: TextView) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)

        val inflater = LayoutInflater.from(context)
        val binding: DialogTimechooseBinding = DialogTimechooseBinding.inflate(inflater)
        dialog.setContentView(binding.root)

        binding.textViewTitle.text = title

        // 숫자 범위 설정하는 부분
        binding.numberPickerMinute.minValue = 1
        binding.numberPickerMinute.maxValue = range
        binding.numberPickerMinute.value = chosen_number

        // 시간 형식 설정 XX 형식으로
        binding.numberPickerMinute.setFormatter { String.format("%d", it) }

        // 선택한 숫자의 색상 변경
        binding.numberPickerMinute.setOnValueChangedListener { picker, oldVal, newVal ->
            setSelectedTextColor(binding.numberPickerMinute)
        }


        binding.buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        binding.buttonConfirm.setOnClickListener {
            val selectedMinute = binding.numberPickerMinute.value

            // 선택한 시간에 따라 형식 지정
            val formattedTime = String.format("%d", selectedMinute) + " : 00"
            // 텍스트 뷰에 반영
            textViewWorkTime.text = formattedTime
            dialog.dismiss()
        }

        // 다이얼로그 크기 지정
        dialogResize(dialog, 1.0f, 0.5f)
        dialog.window?.setGravity(Gravity.BOTTOM)

        dialog.show()
    }

    private fun setSelectedTextColor(np: NumberPicker) {
        val count = np.childCount
        val selectedTextColor = context.resources.getColor(R.color.black)

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
