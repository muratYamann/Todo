package com.yaman.core.application

import android.app.Dialog
import android.content.Context
import androidx.fragment.app.Fragment
import com.yaman.core.view.ProgressDialog

open class BaseFragment : Fragment() {

    private var dialog: Dialog? = null

    protected open fun hideLoading() {
        dialog?.let {
            dialog!!.dismiss()
        }
    }

    protected open fun showLoading(context: Context? = null) {
        dialog = ProgressDialog.progressDialog(context!!)
        dialog!!.show()
    }
}