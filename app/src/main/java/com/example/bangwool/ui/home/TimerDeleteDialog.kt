package com.example.bangwool.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.bangwool.R
import com.example.bangwool.databinding.DialogTimerDeleteBinding
import com.google.gson.Gson

class TimerDeleteDialog : DialogFragment() {
    lateinit var binding: DialogTimerDeleteBinding

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
            val bundle = Bundle()
            val deleteState = false
            bundle.putString("taskData", Gson().toJson(deleteState))

            // OrderDialog 호출
            val homeFragment = HomeFragment()
            homeFragment.arguments = bundle
            dismiss()
        }

        return builder.create()
    }

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = DialogTimerDeleteBinding.inflate(inflater, container, false)
////        binding.dialogCl.setBackgroundResource(R.drawable.dialog_layout)
//        val builder = AlertDialog.Builder(requireActivity(), R.style.RoundedDialogTheme)
//            .setView(binding.root)
//
//        val dataJson = arguments?.getString("taskData")
//        if (dataJson != null) {
////            val data = Gson().fromJson(dataJson, HomeItem::class.java)
//            Log.d("taskData", dataJson.toString())
//            val originalText = binding.dialogTv.text.toString()
////            val newText = originalText.replace("\"taskName\"", dataJson.toString())
//            val newText = originalText.replace("taskName", dataJson.toString())
//            binding.dialogTv.text = newText
//        }
//
//        binding.cancelBtn.setOnClickListener {
//            dismiss()
//        }
//        binding.deleteBtn.setOnClickListener {
//
//        }
//
//
//
//        return builder.create()
//    }
}