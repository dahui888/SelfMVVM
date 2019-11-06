package com.lihang.selfmvvmbykotlin.common


import com.lihang.selfmvvmbykotlin.utils.GsonUtil

import java.util.HashMap

/**
 * Created by leo on 2017/9/13.
 * 键值对上传类
 */

object JSONS {

    fun login(userName: String, password: String): String {
        val map = HashMap<String, Any>()
        map["userName"] = userName
        map["password"] = password
        map["userType"] = 1
        return GsonUtil.ser(map)
    }


}
