package com.lihang.selfmvvmbykotlin.bean.basebean

/**
 * Created by leo
 * on 2019/10/30.
 */
class ParamsBuilder {
    //okhttp 在线缓存时间，不设置就是不用
     var onlineCacheTime: Int = 0
    //okhttp 离线缓存时间，不设置就是不用
     var offlineCacheTime: Int = 0
    //离开页面的时候 是否取消网络。( 默认是取消 )
     var cancleNet: Boolean = true

    //重连次数，默认为0 不重连。大于0 开启重连
     var retryCount: Int = 0

    //同一网络还在加载时，有且只能请求一次(默认可以请求多次)
    //同一网络，oneTag只能用一次
     var oneTag: String? = null

    //是否显示加载loading （默认显示）
     var isShowDialog: Boolean = true

    //加载进度条上是否显示文字（默认不显示文字）
     var loadingMessage: String? = null


    companion object {
        fun build(): ParamsBuilder {
            return ParamsBuilder()
        }
    }



    fun loadingMessage(loadingMessage: String): ParamsBuilder {
        this.loadingMessage = loadingMessage
        return this
    }

    fun isShowDialog(isShowDialog: Boolean): ParamsBuilder {
        this.isShowDialog = isShowDialog
        return this
    }


    fun oneTag(oneTag: String): ParamsBuilder {
        this.oneTag = oneTag
        return this
    }


    fun isRetry(retryCount: Int): ParamsBuilder {
        this.retryCount = retryCount
        return this
    }


    fun cancleNet(cancleNet: Boolean): ParamsBuilder {
        this.cancleNet = cancleNet
        return this
    }


    fun offlineCacheTime(offlineCacheTime: Int): ParamsBuilder {
        this.offlineCacheTime = offlineCacheTime
        return this
    }

    fun onlineCacheTime(onlineCacheTime: Int): ParamsBuilder {
        this.onlineCacheTime = onlineCacheTime
        return this
    }


}