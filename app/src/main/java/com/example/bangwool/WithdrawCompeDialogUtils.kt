package com.example.bangwool

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.bangwool.databinding.DialogWithdrawBinding
import com.example.bangwool.databinding.DialogWithdrawCompeBinding

object WithdrawCompeDialogUtils {
    fun showAboutDialog(context: Context) {
        val dialogBuilder = AlertDialog.Builder(context)
        val dialogViewBinding = DialogWithdrawCompeBinding.inflate(LayoutInflater.from(context))

        dialogBuilder.setView(dialogViewBinding.root)
        val dialog = dialogBuilder.create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogViewBinding.buttonConfirm.setOnClickListener {
            dialog.dismiss()


            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }

        dialog.show()
    }
}