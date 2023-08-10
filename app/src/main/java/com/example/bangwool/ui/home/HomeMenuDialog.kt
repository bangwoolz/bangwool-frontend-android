package com.example.bangwool.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.bangwool.R
import com.example.bangwool.databinding.DialogHomeMenuBinding
import com.example.bangwool.databinding.DialogTimerDeleteBinding
import com.example.bangwool.ui.etc.TimerSettingActivity


class HomeMenuDialog : DialogFragment() {
    lateinit var binding: DialogHomeMenuBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogHomeMenuBinding.inflate(LayoutInflater.from(context))
//        binding.dialogCl.setBackgroundResource(R.drawable.dialog_layout)
        val builder = AlertDialog.Builder(requireActivity(), R.style.RoundedDialogTheme)
            .setView(binding.root)

        binding.alarmSetting.setOnClickListener {
            val i = Intent(requireContext(), TimerSettingActivity::class.java)
            startActivity(i)
            dialog?.dismiss()
        }
        binding.addSetting.setOnClickListener {
            val i = Intent(requireContext(), TimerSettingActivity::class.java)
            startActivity(i)
            dialog?.dismiss()
        }

        val dialog = builder.create()

        return dialog
    }
}