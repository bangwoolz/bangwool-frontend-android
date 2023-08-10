package com.example.bangwool.ui.home

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.bangwool.R
import com.example.bangwool.ui.login.TimeChooseDialog
import com.example.bangwool.databinding.ActivityTimerEditBinding
import com.example.bangwool.retrofit.Ppomodoro
import com.example.bangwool.retrofit.PpomodorosResponse
import com.example.bangwool.retrofit.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TimerEditActivity : AppCompatActivity() {
    lateinit var binding: ActivityTimerEditBinding
    var colorList =
        arrayListOf<String>("red", "pink", "orange", "yellow", "purple", "blue", "skyblue", "green")
    var checkViewList = arrayListOf<View>()
    var btnViewList = arrayListOf<View>()
    var selectedColor = "red"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerEditBinding.inflate(layoutInflater)

        val timerTitle = intent.getStringExtra("timerTitle")
        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        val color = intent.getStringExtra("color")
        val workHour = intent.getStringExtra("workHour")
        val workMin = intent.getStringExtra("workMin")
        val restTime = intent.getStringExtra("restTime")



        initLayout()
        setContentView(binding.root)
    }

    fun initLayout() {
        RetrofitUtil.accessTokenString?.toString()?.let { Log.d("qwerty123", it) }

        setCheckViewList()
        setCheckViewOnClickListener()

        binding.apply {
            val timerTitle = intent.getStringExtra("timerTitle")
            binding.tvTimerEditTitle.setText(timerTitle)

            if (timerTitle.equals("타이머 수정")) {
                val color = intent.getStringExtra("taskColor")
                val name = intent.getStringExtra("taskName")
                val time = intent.getStringExtra("taskTime")

                Log.i("color", color!!)

                editTextName.setText(name)
                tvWorkTimeClock.setText(time)
                if (color != null) {
                    updateCheckedColor(color)
                }

            } else if (timerTitle.equals("타이머 추가")) {
                // 새로 추가 시 코드
                updateCheckedColor("red")

            }

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
                    str.split(":")[0].trim().toInt()
                )
                workTimeDialog.showWorkTimeDialog(tvWorkTimeClock)
            }
            llRestTime.setOnClickListener {
                val str = tvRestTimeClock.text.toString()
                val restTimeDialog = TimeChooseDialog(
                    this@TimerEditActivity,
                    "쉬는 시간",
                    480,
                    str.split(":")[0].trim().toInt()
                )
                restTimeDialog.showWorkTimeDialog(tvRestTimeClock)
            }

            btnSave.setOnClickListener {
                val name = binding.editTextName.text.toString()
                val color = selectedColor
                val workTime = binding.tvWorkTimeClock.text.toString().split(":")
                val workHour = workTime[0].trim().toInt() / 60
                val workMin = workTime[0].trim().toInt() % 60
                val restTime = binding.tvRestTimeClock.text.toString().split(":")[0].trim().toInt()

                if (tvTimerEditTitle.text.toString().equals("타이머 추가")) {
                    postPpomo()
                } else if (tvTimerEditTitle.text.toString().equals("타이머 수정")) {
                    val Id = intent.getStringExtra("PpomoId")!!.toInt()
                    putPpomo(Id)
                } else {
                    Log.d("error", "btnSave")
                }
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
        selectedColor = color
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

    private fun postPpomo() {
        val name = binding.editTextName.text.toString()
        val color = selectedColor
        val workTime = binding.tvWorkTimeClock.text.toString().split(":")
        val workHour = workTime[0].trim().toInt() / 60
        val workMin = workTime[0].trim().toInt() % 60
        val restTime = binding.tvRestTimeClock.text.toString().split(":")[0].trim().toInt()

        val Ppomo = Ppomodoro(name, color, workHour, workMin, restTime)

        RetrofitUtil.getRetrofit().PostPpomodoro(Ppomo).enqueue(object :
            Callback<PpomodorosResponse> {
            override fun onResponse(
                call: Call<PpomodorosResponse>,
                response: Response<PpomodorosResponse>
            ) {
                if (response.isSuccessful) {
                    Log.i("POSTPpomo/Success", response.body()!!.toString())
//                    val ppomoId = response.body()!!.toString().toInt()
                    finish()
                }
            }

            override fun onFailure(call: Call<PpomodorosResponse>, t: Throwable) {
                Log.i("POSTPpomo/Failure", "fail")

            }
        })

    }

    private fun putPpomo(ppomoId: Int) {
        val name = binding.editTextName.text.toString()
        val color = selectedColor
        val workTime = binding.tvWorkTimeClock.text.toString().split(":")
        val workHour = workTime[0].trim().toInt() / 60
        val workMin = workTime[0].trim().toInt() % 60
        val restTime = binding.tvRestTimeClock.text.toString().split(":")[0].trim().toInt()


        var Ppomo = Ppomodoro(name!!, color!!, workHour, workMin, restTime)

        RetrofitUtil.getRetrofit().PutPpomodoro(ppomoId, Ppomo).enqueue(object :
            Callback<PpomodorosResponse> {
            override fun onResponse(
                call: Call<PpomodorosResponse>,
                response: Response<PpomodorosResponse>
            ) {
                if (response.isSuccessful) {
                    Log.i("PUTPpomo/Success", response.body()!!.toString())
                    finish()
                }
            }

            override fun onFailure(call: Call<PpomodorosResponse>, t: Throwable) {
                Log.i("PUTPpomo/Failure", "fail")

            }
        })
    }
}


