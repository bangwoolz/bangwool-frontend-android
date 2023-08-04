package com.example.bangwool

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import com.example.bangwool.databinding.DialogFindpasswordBinding

class DialogUtils(val context: Context) {
    fun showFindPasswordDialog() {

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)

        val inflater = LayoutInflater.from(context)
        val binding: DialogFindpasswordBinding = DialogFindpasswordBinding.inflate(inflater)
        dialog.setContentView(binding.root)


        binding.buttonConfirm.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }


        // 다이얼로그 크기 지정
        dialogResize(dialog, 0.911111f, 0.24f)
        dialog.window?.setGravity(Gravity.CENTER)

        dialog.show()

    }

    private fun dialogResize(dialog: Dialog, width: Float, height: Float) {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = context.resources.displayMetrics
        val dialogWidth = (displayMetrics.widthPixels * width).toInt()
        val dialogHeight = (displayMetrics.heightPixels * height).toInt()

        dialog.window?.setLayout(dialogWidth, dialogHeight)
    }


}