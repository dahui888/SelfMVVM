package com.lihang.selfmvvmbykotlin.bean.basebean

/**
 * Created by leo
 * on 2019/10/30.
 */
//这个用来拓展LiveData
class Resource<T> {
    companion object {
        //状态  这里有多个状态 0表示加载中；1表示成功；2表示联网失败；3表示接口虽然走通，但走的失败（如：关注失败）
        val LOADING = 0
        val SUCCESS = 1
        val ERROR = 2
        val FAIL = 3
        val PROGRESS = 4//注意只有下载文件和上传图片时才会有


        fun <T> loading(showMsg: String?): Resource<T> {
            return Resource(LOADING, null, showMsg)
        }

        fun <T> success(data: T): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> failure(msg: String): Resource<T> {
            return Resource(ERROR, null, msg)
        }

        fun <T> error(t: Throwable): Resource<T> {
            return Resource(ERROR, t)
        }

        fun <T> progress(precent: Int, total: Long): Resource<T> {
            return Resource(PROGRESS, precent, total)
        }


        fun <T> response(data: ResponModel<T>): Resource<T> {
//            if (data != null) {
            if (data.isSuccess()) {
                return Resource(SUCCESS, data.data, null)
            }
            return Resource(FAIL, null, data.errorMsg)
//            }
//            return Resource(ERROR, null, data.errorMsg)
        }
    }

    var state = 0
    var errorMsg: String? = null
    var data: T? = null
    var error: Throwable? = null

    //这里和文件和进度有关了
    var precent: Int = 0//文件下载百分比
    var total: Long = 0//文件总大小

    constructor(state: Int, data: T?, errorMsg: String?) {
        this.state = state
        this.data = data
        this.errorMsg = errorMsg
    }

    constructor(state: Int, error: Throwable) {
        this.state = state
        this.error = error
    }

    constructor(state: Int, precent: Int, total: Long) {
        this.state = state
        this.precent = precent
        this.total = total
    }


    fun handler(callback: OnHandleCallback<T>) {

        when (state) {
            LOADING -> callback.onLoading(errorMsg)
            SUCCESS -> callback.onSuccess(data)
            FAIL -> callback.onFailure(errorMsg)
            ERROR -> callback.onError(error)
            PROGRESS -> callback.onProgress(precent, total)
        }

        if (state != LOADING) {
            callback.onCompleted()
        }

    }


    public interface OnHandleCallback<T> {
        abstract fun onLoading(showMessage: String?)

        abstract fun onSuccess(data: T?)

        abstract fun onFailure(msg: String?)

        abstract fun onError(error: Throwable?)

        abstract fun onCompleted()

        abstract fun onProgress(precent: Int?, total: Long?)
    }


}