package com.example.bangwool

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.bangwool.databinding.DialogAboutBinding

object AboutDialogUtils {

    fun showAboutDialog(context: Context) {
        val dialogBuilder = AlertDialog.Builder(context)
        val dialogViewBinding = DialogAboutBinding.inflate(LayoutInflater.from(context))

        dialogBuilder.setView(dialogViewBinding.root)
        val dialog = dialogBuilder.create()

        dialogViewBinding.buttonCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialogViewBinding.buttonConfirm.setOnClickListener {
            dialog.dismiss()
        }



        dialog.show()
    }

}
