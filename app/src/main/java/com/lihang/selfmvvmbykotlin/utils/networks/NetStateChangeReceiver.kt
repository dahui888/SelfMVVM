package com.lihang.selfmvvmbykotlin.utils.networks

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager


import com.lihang.selfmvvmbykotlin.MyApplication

import java.util.ArrayList

/**
 * Created by leo
 * on 2019/7/29.
 * 网络监听的广播
 *
 */
class NetStateChangeReceiver : BroadcastReceiver() {

    private var mType = MyApplication.getContext()?.let { NetWorkUtils.getNetworkType(it) }

    private val mObservers = ArrayList<NetStateChangeObserver>()

    private object InstanceHolder {
        internal val INSTANCE = NetStateChangeReceiver()
    }


    //注册了网络状态改变监听，一旦网络状态改变就会调用的方法
    override fun onReceive(context: Context, intent: Intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION == intent.action) {
            val networkType = NetWorkUtils.getNetworkType(context)
            notifyObservers(networkType)
        }
    }

    private fun notifyObservers(networkType: NetworkType) {
        if (mType == networkType) {
            return
        }
        mType = networkType
        if (networkType == NetworkType.NETWORK_NO) {
            for (observer in mObservers) {
                observer.onNetDisconnected()
            }
        } else {
            for (observer in mObservers) {
                observer.onNetConnected(networkType)
            }
        }
    }

    companion object {

        //注册广播
        fun registerReceiver(context: Context) {
            val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            context.registerReceiver(InstanceHolder.INSTANCE, intentFilter)
        }


        //注释广播
        fun unRegisterReceiver(context: Context) {
            context.unregisterReceiver(InstanceHolder.INSTANCE)
        }


        //添加观察者，一旦有改变，观察者会通知
        fun registerObserver(observer: NetStateChangeObserver?) {
            if (observer == null) {
                return
            }
            if (!InstanceHolder.INSTANCE.mObservers.contains(observer)) {
                InstanceHolder.INSTANCE.mObservers.add(observer)
            }
        }

        //观察者
        fun unRegisterObserver(observer: NetStateChangeObserver?) {
            if (observer == null) {
                return
            }
            if (InstanceHolder.INSTANCE.mObservers == null) {
                return
            }
            InstanceHolder.INSTANCE.mObservers.remove(observer)
        }
    }
}
