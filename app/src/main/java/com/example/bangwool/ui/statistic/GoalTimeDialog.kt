package com.example.bangwool.ui.statistic

import com.example.bangwool.R
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
import com.example.bangwool.databinding.DialogGoalTimeBinding

class GoalTimeDialog(private val context: Context) {
    private lateinit var listener : MyDialogOKClickedListener
    private val dialog = Dialog(context)

    fun show() {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        val inflater = LayoutInflater.from(context)
        val binding: DialogGoalTimeBinding = DialogGoalTimeBinding.inflate(inflater)

        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)
        // 숫자 범위 설정하는 부분
        binding.numberPickerMinute.minValue = 1
        binding.numberPickerMinute.maxValue = 16

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
            val selectedHour = binding.numberPickerMinute.value
            listener.onOKClicked(selectedHour)
            // 선택한 시간에 따라 형식 지정
            val formattedTime =
                String.format("%d시간", selectedHour)

            // 텍스트 뷰에 반영
//            textViewWorkTime.text = formattedTime
            dialog.dismiss()
        }

        // 다이얼로그 크기 지정
//        dialogResize(dialog, 1.0f, 0.5f)
//        dialog.window?.setGravity(Gravity.BOTTOM)

        dialog.show()
    }

    fun setOnOKClickedListener(listener: (String) -> Unit) {
        this.listener = object: MyDialogOKClickedListener {
            override fun onOKClicked(hour: Int) {
                listener(hour.toString())
            }
        }
    }

    interface MyDialogOKClickedListener {
        fun onOKClicked(hour : Int)
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