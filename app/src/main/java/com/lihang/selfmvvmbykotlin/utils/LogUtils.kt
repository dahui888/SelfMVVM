package com.lihang.selfmvvmbykotlin.utils

import android.util.Log

/**
 * Created by leo on 2017/9/12.
 */

object LogUtils {

    //可以全局控制是否打印log日志
    private val isEnableLog = true
    private val LOG_MAXLENGTH = 4000
    val TAG = LogUtils::class.java.simpleName
    private val TOP_DIVIDER = "┌────────────────────────────────────────────────────────"

    private val BOTTOM_DIVIDER = "└────────────────────────────────────────────────────────"
    private val MIDDLE_DIVIDER = "----------- 换行 ------------\n"

    fun v(msg: String) {
        v(TAG, msg)
    }

    fun v(tagName: String, msg: String?) {
        if (isEnableLog) {
            if (msg == null) {
                Log.v(tagName, "null")
                return
            }
            var strLength = msg.length
            var start = 0
            var end = LOG_MAXLENGTH
            if (strLength > end) {
                val sbf = StringBuffer()
                sbf.append(" \n$TOP_DIVIDER").append(msg)
                val trueMsg = sbf.toString()
                strLength = trueMsg.length
                while (strLength > end) {
                    if (start == 0) {
                        Log.v(tagName, trueMsg.substring(start, end))
                        start = end
                        end = end + LOG_MAXLENGTH
                    } else {
                        end = end - MIDDLE_DIVIDER.length
                        Log.v(tagName, MIDDLE_DIVIDER + trueMsg.substring(start, end))
                        start = end
                        end = end + LOG_MAXLENGTH
                    }
                }
                Log.v(tagName, trueMsg.substring(start, strLength))
                Log.v(tagName, " \n$BOTTOM_DIVIDER")
            } else {
                Log.v(tagName, msg)
            }
        }
    }

    fun d(msg: String) {
        d(TAG, msg)
    }

    fun d(tagName: String, msg: String?) {
        if (isEnableLog) {
            if (msg == null) {
                Log.d(tagName, "null")
                return
            }
            var strLength = msg.length
            var start = 0
            var end = LOG_MAXLENGTH
            if (strLength > end) {
                val sbf = StringBuffer()
                sbf.append(" \n$TOP_DIVIDER").append(msg)
                val trueMsg = sbf.toString()
                strLength = trueMsg.length
                while (strLength > end) {
                    if (start == 0) {
                        Log.d(tagName, trueMsg.substring(start, end))
                        start = end
                        end = end + LOG_MAXLENGTH
                    } else {
                        end = end - MIDDLE_DIVIDER.length
                        Log.d(tagName, MIDDLE_DIVIDER + trueMsg.substring(start, end))
                        start = end
                        end = end + LOG_MAXLENGTH
                    }
                }
                Log.d(tagName, trueMsg.substring(start, strLength))
                Log.d(tagName, " \n$BOTTOM_DIVIDER")
            } else {
                Log.d(tagName, msg)
            }
        }
    }

    fun i(msg: String) {
        i(TAG, msg)
    }


    fun i(tagName: String, msg: String?) {
        if (isEnableLog) {
            if (msg == null) {
                Log.i(tagName, "null")
                return
            }
            var strLength = msg.length
            var start = 0
            var end = LOG_MAXLENGTH
            if (strLength > end) {
                val sbf = StringBuffer()
                sbf.append(" \n$TOP_DIVIDER").append(msg)
                val trueMsg = sbf.toString()
                strLength = trueMsg.length
                while (strLength > end) {
                    if (start == 0) {
                        Log.i(tagName, trueMsg.substring(start, end))
                        start = end
                        end = end + LOG_MAXLENGTH
                    } else {
                        end = end - MIDDLE_DIVIDER.length
                        Log.i(tagName, MIDDLE_DIVIDER + trueMsg.substring(start, end))
                        start = end
                        end = end + LOG_MAXLENGTH
                    }
                }
                Log.i(tagName, trueMsg.substring(start, strLength))
                Log.i(tagName, " \n$BOTTOM_DIVIDER")
            } else {
                Log.i(tagName, msg)
            }
        }
    }

    fun w(msg: String) {
        w(TAG, msg)
    }

    fun w(tagName: String, msg: String?) {
        if (isEnableLog) {
            if (msg == null) {
                Log.w(tagName, "null")
                return
            }
            var strLength = msg.length
            var start = 0
            var end = LOG_MAXLENGTH
            if (strLength > end) {
                val sbf = StringBuffer()
                sbf.append(" \n$TOP_DIVIDER").append(msg)
                val trueMsg = sbf.toString()
                strLength = trueMsg.length
                while (strLength > end) {
                    if (start == 0) {
                        Log.w(tagName, trueMsg.substring(start, end))
                        start = end
                        end = end + LOG_MAXLENGTH
                    } else {
                        end = end - MIDDLE_DIVIDER.length
                        Log.w(tagName, MIDDLE_DIVIDER + trueMsg.substring(start, end))
                        start = end
                        end = end + LOG_MAXLENGTH
                    }
                }
                Log.w(tagName, trueMsg.substring(start, strLength))
                Log.w(tagName, " \n$BOTTOM_DIVIDER")
            } else {
                Log.w(tagName, msg)
            }
        }
    }

    fun e(msg: String) {
        e(TAG, msg)
    }

    fun e(tagName: String, msg: String?) {
        if (isEnableLog) {
            if (msg == null) {
                Log.e(tagName, "null")
                return
            }
            var strLength = msg.length
            var start = 0
            var end = LOG_MAXLENGTH
            if (strLength > end) {
                val sbf = StringBuffer()
                sbf.append(" \n$TOP_DIVIDER").append(msg)
                val trueMsg = sbf.toString()
                strLength = trueMsg.length
                while (strLength > end) {
                    if (start == 0) {
                        Log.e(tagName, trueMsg.substring(start, end))
                        start = end
                        end = end + LOG_MAXLENGTH
                    } else {
                        end = end - MIDDLE_DIVIDER.length
                        Log.e(tagName, MIDDLE_DIVIDER + trueMsg.substring(start, end))
                        start = end
                        end = end + LOG_MAXLENGTH
                    }
                }
                Log.e(tagName, trueMsg.substring(start, strLength))
                Log.e(tagName, " \n$BOTTOM_DIVIDER")
            } else {
                Log.e(tagName, msg)
            }
        }
    }
}
