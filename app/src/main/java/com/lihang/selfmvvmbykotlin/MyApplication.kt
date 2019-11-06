package com.lihang.selfmvvmbykotlin

import android.app.Application
import android.content.Context

/**
 * Created by leo
 * on 2019/10/30.
 */
class MyApplication : Application() {

    companion object {
        private var context: MyApplication? = null
        fun getContext(): Context? {
            if (context == null) {
                return MyApplication()
            }
            return context
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }


}