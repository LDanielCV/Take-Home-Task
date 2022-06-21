package com.assigment.taskcurrencyapp.ui.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.assigment.taskcurrencyapp.R

class ErrorDialog: DialogFragment() {
    var error:String = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        builder.setTitle(getString(R.string.error_title))
        builder.setMessage(error)
        builder.setCancelable(true)
        builder.setPositiveButton(getString(R.string.close_text),object :DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                requireActivity().onBackPressed()
            }

        })

        this.isCancelable = false
        return builder.show()
    }
}