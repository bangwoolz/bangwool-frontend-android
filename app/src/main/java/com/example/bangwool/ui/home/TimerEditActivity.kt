package com.example.bangwool.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.bangwool.R
import com.example.bangwool.TimeChooseDialog
import com.example.bangwool.databinding.ActivityTimerEditBinding

class TimerEditActivity : AppCompatActivity() {
    lateinit var binding: ActivityTimerEditBinding
    var colorList =
        arrayListOf<String>("red", "pink", "orange", "yellow", "purple", "blue", "skyblue", "green")
    var checkViewList = arrayListOf<View>()
    var btnViewList = arrayListOf<View>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerEditBinding.inflate(layoutInflater)
        initLayout()
        setContentView(binding.root)
    }

    fun initLayout() {
        binding.apply {
            textInputLayoutName.hint = ""
            editTextName.hint = "ex) 시험공부"
            textInputLayoutName.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus && editTextName.text.isNullOrEmpty()) {
                        editTextName.setTextColor(getColor(R.color.gray_400))
                        editTextName.hint = "ex) 시험공부"
                    } else {
                        editTextName.setTextColor(getColor(R.color.gray_900))
                        editTextName.hint = ""
                    }
                }
            llWorkTime.setOnClickListener {
                val workTimeDialog = TimeChooseDialog(this@TimerEditActivity, "작업 시간", 480)
                workTimeDialog.showWorkTimeDialog(tvWorkTimeClock)
            }
            llRestTime.setOnClickListener {
                val restTimeDialog = TimeChooseDialog(this@TimerEditActivity, "쉬는 시간", 480)
                restTimeDialog.showWorkTimeDialog(tvRestTimeClock)
            }
            setCheckViewList()
            setCheckViewOnClickListener()
            updateCheckedColor("red")
            btnSave.setOnClickListener {
                finish()
            }
            icTimerEditBack.setOnClickListener {
                finish()
            }
        }
    }

    fun setCheckViewOnClickListener() {
        for (i in 0 until btnViewList.size) {
            btnViewList[i].setOnClickListener {
                updateCheckedColor(colorList[i])
            }
        }
    }

    fun updateCheckedColor(color: String) {
        checkViewList.forEach {
            it.visibility = View.GONE
        }
        val i = colorList.indexOf(color)
        checkViewList[i].visibility = View.VISIBLE
    }

    fun setCheckViewList() {
        checkViewList.add(binding.btnColorRedCheck)
        checkViewList.add(binding.btnColorPinkCheck)
        checkViewList.add(binding.btnColorOrangeCheck)
        checkViewList.add(binding.btnColorYellowCheck)
        checkViewList.add(binding.btnColorPurpleCheck)
        checkViewList.add(binding.btnColorBlueCheck)
        checkViewList.add(binding.btnColorSkyblueCheck)
        checkViewList.add(binding.btnColorGreenCheck)

        btnViewList.add(binding.btnColorRed)
        btnViewList.add(binding.btnColorPink)
        btnViewList.add(binding.btnColorOrange)
        btnViewList.add(binding.btnColorYellow)
        btnViewList.add(binding.btnColorPurple)
        btnViewList.add(binding.btnColorBlue)
        btnViewList.add(binding.btnColorSkyblue)
        btnViewList.add(binding.btnColorGreen)
    }
}
