package com.example.bangwool.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.bangwool.R
import com.example.bangwool.databinding.DialogTimerDeleteBinding

class TimerDeleteDialog : DialogFragment() {
    lateinit var binding: DialogTimerDeleteBinding

    interface OnDeleteItemClickListener {
        fun onDeleteItemClicked()
    }

    private var onDeleteItemClickListener: OnDeleteItemClickListener? = null

    fun setOnDeleteItemClickListener(listener: OnDeleteItemClickListener) {
        onDeleteItemClickListener = listener
    }

    // 다이얼로그 내의 삭제 버튼 클릭 시 이벤트를 처리하는 메소드
    private fun onDeleteButtonClicked() {
        onDeleteItemClickListener?.onDeleteItemClicked()
        dismiss() // 다이얼로그 닫기
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogTimerDeleteBinding.inflate(LayoutInflater.from(context))
//        binding.dialogCl.setBackgroundResource(R.drawable.dialog_layout)
        val builder = AlertDialog.Builder(requireActivity(), R.style.RoundedDialogTheme)
            .setView(binding.root)

        val dataJson = arguments?.getString("taskData")
        if (dataJson != null) {
//            val data = Gson().fromJson(dataJson, HomeItem::class.java)
            Log.d("taskData", dataJson.toString())
            val originalText = binding.dialogTv.text.toString()
//            val newText = originalText.replace("\"taskName\"", dataJson.toString())
            val newText = originalText.replace("taskName", dataJson.toString())
            binding.dialogTv.text = newText
        }

        binding.cancelBtn.setOnClickListener {
            dismiss()
        }
        binding.deleteBtn.setOnClickListener {
            onDeleteButtonClicked()
        }

        return builder.create()
    }
}