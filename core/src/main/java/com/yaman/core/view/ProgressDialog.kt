package com.yaman.core.view

import android.app.Dialog
import android.content.Context
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.yaman.core.R

class ProgressDialog {
    companion object {
        fun progressDialog(context: Context): Dialog {
            val dialog = Dialog(context, R.style.ProgressDialogStyle)
            val layout = RelativeLayout(context)
            val progressBar = ProgressBar(context, null, android.R.attr.progressBarStyleLarge)
            val resource = context.resources
            val params = RelativeLayout.LayoutParams(resource.getDimensionPixelSize(R.dimen.progress_bar_size), resource.getDimensionPixelSize(R.dimen.progress_bar_size))
            params.addRule(RelativeLayout.CENTER_IN_PARENT)
            layout.addView(progressBar, params)
            dialog.setContentView(layout)
            dialog.setCancelable(false)
            return dialog
        }
    }
}