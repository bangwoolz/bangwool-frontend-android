package com.example.bangwool.ui.home

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.bangwool.R
import com.example.bangwool.TimeChooseDialog
import com.example.bangwool.databinding.ActivityTimerEditBinding
import com.example.bangwool.retrofit.Ppomodoro
import com.example.bangwool.retrofit.PpomodoroResponse
import com.example.bangwool.retrofit.RetrofitUtil
import com.example.bangwool.retrofit.getAccessToken
import com.example.bangwool.retrofit.getMemberId
import retrofit2.Call
import retrofit2.Response

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
            val timerTitle = intent.getStringExtra("timerTitle")
            binding.tvTimerEditTitle.setText(timerTitle)

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
            editTextName.setOnKeyListener { view, keyCode, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        editTextName.clearFocus()
                        clTimerName.requestFocus()
                        val inputMethodManager =
                            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(editTextName.windowToken, 0)
                    }
                }
                return@setOnKeyListener false
            }
            llWorkTime.setOnClickListener {
                val str = tvWorkTimeClock.text.toString()
                val workTimeDialog = TimeChooseDialog(
                    this@TimerEditActivity,
                    "작업 시간",
                    480,
                    str.substring(0, str.length - 3).toInt()
                )
                workTimeDialog.showWorkTimeDialog(tvWorkTimeClock)
            }
            llRestTime.setOnClickListener {
                val str = tvRestTimeClock.text.toString()
                val restTimeDialog = TimeChooseDialog(
                    this@TimerEditActivity,
                    "쉬는 시간",
                    480,
                    str.substring(0, str.length - 3).toInt()
                )
                restTimeDialog.showWorkTimeDialog(tvRestTimeClock)
            }
            setCheckViewList()
            setCheckViewOnClickListener()
            updateCheckedColor("red")
            btnSave.setOnClickListener {
                val token = getAccessToken(this@TimerEditActivity)
//                val memberId = getMemberId(token)
                val memberId = getMemberId(this@TimerEditActivity)
                postPpomodoros(memberId)
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


    private fun postPpomodoros(memberId: Int) {
        val workTime = binding.tvWorkTimeClock.text.split(":")
        val workHour = workTime[0].toInt() / 60
        val workMinute = workTime[0].toInt() % 60
        val ppomodoro = Ppomodoro(
            binding.editTextName.toString(),
            "",
            workHour,
            workMinute,
            binding.tvRestTimeClock.toString().toInt()
        )
        val retrofit = RetrofitUtil.getRetrofit()
        retrofit.postPpomodoros(memberId, ppomodoro).enqueue(object : retrofit2.Callback<PpomodoroResponse> {
            override fun onResponse(
                call: Call<PpomodoroResponse>,
                response: Response<PpomodoroResponse>
            ) {
                if (response.isSuccessful) {
                    val ppomodorosResponse = response.body()
                    Log.i("POSTPPOMODORO/SUCCESS", ppomodorosResponse.toString())

                    if (ppomodorosResponse != null) {
                        // 타이머리스트 리사이클러뷰에 연결
                    }
                }
            }

            override fun onFailure(call: Call<PpomodoroResponse>, t: Throwable) {
                Log.i("POSTPPOMODORO/FAILURE", t.message.toString())
            }

        })
    }
}
