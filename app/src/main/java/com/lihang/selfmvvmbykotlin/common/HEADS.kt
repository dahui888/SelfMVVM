package com.lihang.selfmvvmbykotlin.common

import java.util.HashMap

/**
 * Created by leo
 * on 2019/11/5.
 */
object HEADS {
    fun login(token: String): HashMap<String, String> {
        var map = HashMap<String, String>()
        map.put("Token", token)
        return map
    }
}