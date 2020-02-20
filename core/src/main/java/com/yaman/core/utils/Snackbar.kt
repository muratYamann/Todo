package com.yaman.core.utils

import android.content.Context
import android.graphics.Color
import com.google.android.material.snackbar.Snackbar
import androidx.core.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.androidadvance.topsnackbar.TSnackbar
import com.yaman.core.R

open class Snackbar {
	companion object {
		fun show(
				layout: View,
				snackTitle: String,
				context: Context?,
				fromBottom: Boolean = false,
				hasError: Boolean = true
		) {

			val bgColor: Int = if (hasError)
				ContextCompat.getColor(context!!, R.color.error_text)
			else ContextCompat.getColor(context!!, R.color.success_text)

			if (fromBottom) {
				val params = layout.layoutParams as FrameLayout.LayoutParams
				val snackbar = Snackbar.make(layout, snackTitle, Snackbar.LENGTH_SHORT)
				val snackView = snackbar.view
				params.gravity = Gravity.TOP
				snackView.layoutParams = params
				snackbar.show()
				snackView.setBackgroundColor(bgColor)

			} else {
				val snackbar = TSnackbar.make(layout,
						snackTitle,
						TSnackbar.LENGTH_LONG)
				snackbar.setActionTextColor(Color.WHITE)
				val snackbarView = snackbar.view
				snackbarView.setBackgroundColor(bgColor)
				val textView = snackbarView.findViewById<View>(
						com.androidadvance.topsnackbar.R.id.snackbar_text) as TextView
				textView.setTextColor(Color.WHITE)
				snackbar.show()
			}
		}
	}
}
