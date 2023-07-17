package com.example.bangwool

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.NumberPicker
import com.example.bangwool.databinding.DialogTimecheckBinding

class TimeDialogUtils(private val context: Context) {
    fun showWorkTimeDialog(textViewWorkTime: TextView) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)

        val inflater = LayoutInflater.from(context)
        val binding: DialogTimecheckBinding = DialogTimecheckBinding.inflate(inflater)
        dialog.setContentView(binding.root)

        //숫자 범위
        binding.numberPickerHour.minValue = 0
        binding.numberPickerHour.maxValue = 23

        binding.numberPickerMinute.minValue = 0
        binding.numberPickerMinute.maxValue = 59

        binding.buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        binding.buttonConfirm.setOnClickListener {
            val selectedHour = binding.numberPickerHour.value
            val selectedMinute = binding.numberPickerMinute.value
            if(selectedHour==0){
                val formattedTime = String.format("%02d분", selectedMinute)
                textViewWorkTime.text = formattedTime

            }else{
                val formattedTime = String.format("%02d시%02d분", selectedHour, selectedMinute)
                textViewWorkTime.text = formattedTime

            }

            dialog.dismiss()
        }

        dialog.show()
    }
}
