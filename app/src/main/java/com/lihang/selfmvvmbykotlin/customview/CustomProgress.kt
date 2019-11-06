package com.lihang.selfmvvmbykotlin.customview

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.AnimationDrawable
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.lihang.selfmvvmbykotlin.R

/**
 * Created by leo
 * on 2019/11/6.
 */
class CustomProgress : Dialog {
    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, themeResId: Int) : super(context, themeResId) {

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        var imageView = findViewById<ImageView>(R.id.spinnerImageView)
        var spinner = imageView.background as AnimationDrawable
        spinner.start()
    }

    fun setMessage(showMessage: String?) {
        var textView = findViewById<TextView>(R.id.message)
        if (!TextUtils.isEmpty(showMessage)) {
            textView.text = showMessage
            textView.visibility = View.VISIBLE
        } else {
            textView.text = ""
            textView.visibility = View.GONE
        }
    }

    companion object {
        var dialog: CustomProgress? = null
        fun show(
            context: Context,
            showMessage: String,
            cancleable: Boolean,
            cancelListener: DialogInterface.OnCancelListener?
        ): CustomProgress {
            dialog = CustomProgress(context, R.style.Custom_Progress)
            dialog!!.setContentView(R.layout.progress_leo)
            var textView = dialog!!.findViewById<TextView>(R.id.message)
            if (!TextUtils.isEmpty(showMessage)) {
                textView.text = showMessage
                textView.visibility = View.VISIBLE
            } else {
                textView.text = ""
                textView.visibility = View.GONE
            }
            dialog!!.setCancelable(cancleable)
            dialog!!.setCanceledOnTouchOutside(false)
            dialog!!.setOnCancelListener(cancelListener)
            var lp = dialog!!.window!!.attributes
            lp.gravity = Gravity.CENTER
            lp.dimAmount = 0.2f
            dialog!!.window!!.attributes = lp
            dialog!!.show()
            return dialog!!
        }
    }


}