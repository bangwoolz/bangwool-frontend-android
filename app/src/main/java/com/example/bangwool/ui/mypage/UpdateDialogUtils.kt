package com.example.bangwool.ui.mypage;

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.example.bangwool.R
import com.example.bangwool.databinding.DialogUpdateBinding // 생성된 ViewBinding 클래스 임포트

object UpdateDialogUtils {

    fun showUpdateDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        val dialogViewBinding = DialogUpdateBinding.inflate(LayoutInflater.from(context))

        builder.setView(dialogViewBinding.root)

        val dialog = builder.create()

        // 확인 버튼 클릭 리스너 설정
        dialogViewBinding.buttonConfirm.setOnClickListener {
            dialog.dismiss()
        }

        dialog.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(context, R.drawable.dialog_background)
        )

        dialog.show()
    }
}