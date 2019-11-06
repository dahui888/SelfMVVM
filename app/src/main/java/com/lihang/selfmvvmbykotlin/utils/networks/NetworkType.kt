package com.lihang.selfmvvmbykotlin.utils.networks

/**
 * Created by leo
 * on 2019/7/29.
 * net_work的枚举类
 */
enum class NetworkType private constructor(private val desc: String) {
    NETWORK_WIFI("wifi"),
    NETWORK_4G("4G"),
    NETWORK_3G("3G"),
    NETWORK_2G("2G"),
    NETWORK_UNKNOWN("net_unknow"),
    NETWORK_NO("no_network");

    override fun toString(): String {
        return desc
    }
}
