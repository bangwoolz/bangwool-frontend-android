package com.example.bangwool

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.NumberPicker
import com.example.bangwool.databinding.CustomTimePickerBinding

class CustomTimePicker(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    private val binding: CustomTimePickerBinding
    init {
        binding = CustomTimePickerBinding.inflate(LayoutInflater.from(context), this, true)

        // Set up number pickers and their ranges
        binding.hourPicker.minValue = 0
        binding.hourPicker.maxValue = 23

        binding.minutePicker.minValue = 0
        binding.minutePicker.maxValue = 59

        // Set formatter to display values as 00:00 format
        binding.hourPicker.setFormatter { value ->
            String.format("%02d", value)
        }

        binding.minutePicker.setFormatter { value ->
            String.format("%02d", value)
        }
        val dividerId = resources.getIdentifier("selectionDivider", "id", "android")
        val selectionDivider = findViewById<LinearLayout>(dividerId)
        selectionDivider?.setBackgroundResource(R.drawable.divider_custom)
    }

}
