package com.example.bangwool

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.bangwool.databinding.DialogAboutBinding

object AboutDialogUtils {

    fun showAboutDialog(context: Context) {
        val dialogBuilder = AlertDialog.Builder(context)
        val dialogViewBinding = DialogAboutBinding.inflate(LayoutInflater.from(context))

        dialogBuilder.setView(dialogViewBinding.root)
        val dialog = dialogBuilder.create()

        dialogViewBinding.root.setBackgroundResource(R.drawable.dialog_background)

        dialogViewBinding.buttonConfirm.setOnClickListener {

            dialog.dismiss()

        }

        dialog.show()
    }

}
