package com.example.bangwool

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.NumberPicker
import androidx.core.content.ContextCompat
import com.example.bangwool.databinding.CustomTimePickerBinding

class CustomTimePicker(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    private val binding: CustomTimePickerBinding

    init {
        binding = CustomTimePickerBinding.inflate(LayoutInflater.from(context), this, true)

        binding.hourPicker.minValue = 0
        binding.hourPicker.maxValue = 23

        binding.minutePicker.minValue = 0
        binding.minutePicker.maxValue = 59

        binding.hourPicker.setFormatter { value ->
            String.format("%02d", value)
        }

        binding.minutePicker.setFormatter { value ->
            String.format("%02d", value)
        }

        val textSizePx = resources.getDimensionPixelSize(R.dimen.text_size_header2)
        binding.hourPicker.setTextSize(textSizePx.toFloat())
        binding.minutePicker.setTextSize(textSizePx.toFloat())

        val numberPickerFields = NumberPicker::class.java.getDeclaredFields()
        for (field in numberPickerFields) {
            if (field.name == "mSelectorWheelPaint") {
                field.isAccessible = true
                val paint = field.get(binding.hourPicker) as Paint
                paint.color = ContextCompat.getColor(context, R.color.gray_200)
                paint.textSize = textSizePx.toFloat()
                break
            }
        }
    }
}
