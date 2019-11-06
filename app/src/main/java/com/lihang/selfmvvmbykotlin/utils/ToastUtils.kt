package com.lihang.selfmvvmbykotlin.utils

import android.widget.Toast
import com.lihang.selfmvvmbykotlin.MyApplication


/**
 * Created by leo on 2017/9/4.
 * 防止重复点击toast，一直显示未隐藏
 */

object ToastUtils {
    private val context = MyApplication.getContext()
    /** 之前显示的内容  */
    private var oldMsg: String? = null
    /** Toast对象  */
    private var toast: Toast? = null
    /** 第一次时间  */
    private var oneTime: Long = 0
    /** 第二次时间  */
    private var twoTime: Long = 0

    /**
     * 显示Toast
     * @param message
     */
    fun showToast(message: String) {
        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
            toast!!.show()
            oneTime = System.currentTimeMillis()
        } else {
            twoTime = System.currentTimeMillis()
            if (message == oldMsg) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast!!.show()
                }
            } else {
                oldMsg = message
                toast!!.setText(message)
                toast!!.show()
            }
        }
        oneTime = twoTime
    }

}
