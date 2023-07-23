package com.example.bangwool

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import com.example.bangwool.databinding.DialogFindpasswordBinding

object DialogUtils {
    fun showFindPasswordDialog(context: Context) {
        val dialogBuilder = AlertDialog.Builder(context)
        val binding = DialogFindpasswordBinding.inflate(LayoutInflater.from(context))
        dialogBuilder.setView(binding.root)

        val alert = dialogBuilder.create()
        alert.setCancelable(false)
        binding.buttonCancel.setOnClickListener {
            alert.dismiss()
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }

        binding.buttonConfirm.setOnClickListener {
            alert.dismiss()
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }

        alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alert.window?.requestFeature(Window.FEATURE_NO_TITLE)
        alert.show()

    }

}