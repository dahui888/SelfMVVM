package com.lihang.selfmvvmbykotlin.bean.basebean

import java.io.Serializable

/**
 * Created by leo
 * on 2019/10/30.
 */
class ResponModel<T> : Serializable {

    var data: T? = null
    var errorCode: Int? = null
    var errorMsg: String? = null

    companion object {
        val RESULT_SUCCESS: Int = 0
    }

    fun isSuccess() = RESULT_SUCCESS == errorCode
}