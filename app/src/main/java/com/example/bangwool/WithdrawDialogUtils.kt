package com.example.bangwool

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.bangwool.databinding.DialogWithdrawBinding
import com.example.bangwool.databinding.DialogWithdrawCompeBinding


object WithdrawDialogUtils {
        fun showAboutDialog(context: Context) {
                val dialogBuilder = AlertDialog.Builder(context)
                val dialogViewBinding = DialogWithdrawBinding.inflate(LayoutInflater.from(context))

                dialogBuilder.setView(dialogViewBinding.root)
                val dialog = dialogBuilder.create()


                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                dialogViewBinding.buttonCancel.setOnClickListener {
                        dialog.dismiss()
                }

                dialogViewBinding.buttonConfirm.setOnClickListener {
                        dialog.dismiss()
                        showWithdrawCompleteDialog(context) // 수정된 부분
                }

                dialog.show()
        }

        private fun showWithdrawCompleteDialog(context: Context) { // 새로 추가된 함수
                val completeDialogBuilder = AlertDialog.Builder(context)
                val completeDialogViewBinding = DialogWithdrawCompeBinding.inflate(LayoutInflater.from(context))

                completeDialogBuilder.setView(completeDialogViewBinding.root)
                val completeDialog = completeDialogBuilder.create()

                completeDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                completeDialogViewBinding.buttonConfirm.setOnClickListener {
                        completeDialog.dismiss()
                        // Navigate to LoginActivity
                        val intent = Intent(context, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        context.startActivity(intent)
                }

                completeDialog.show()
        }
}
