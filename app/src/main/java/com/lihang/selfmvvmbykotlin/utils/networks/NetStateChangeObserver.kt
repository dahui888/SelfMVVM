package com.lihang.selfmvvmbykotlin.utils.networks

/**
 * Created by leo
 * on 2019/7/29.
 * 观察者模式接口，目前可理解为。回调
 */
interface NetStateChangeObserver {
    //网络断开连接的回调
    fun onNetDisconnected()

    //有网络连接的回调
    fun onNetConnected(networkType: NetworkType)
}


