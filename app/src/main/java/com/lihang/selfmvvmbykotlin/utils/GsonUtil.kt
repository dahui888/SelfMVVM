package com.lihang.selfmvvmbykotlin.utils

import com.google.gson.Gson
import java.lang.reflect.Type

object GsonUtil {

    private var gson: Gson? = null

    private fun getGson(): Gson {
        if (gson == null) {
            gson = Gson()
        }
        return gson!!
    }

    /*
     * 对象转Gson字符串
     * */
    fun <T : Any> ser(`object`: T): String {
        return getGson().toJson(`object`)
    }


    /*
     * Gson字符串转可序列化对象 class
     * */
    fun <T : Any> deser(`object`: String, clazz: Class<T>): T? {
        var result: T? = null
        try {
            result = getGson().fromJson(`object`, clazz)
        } catch (e: Exception) {
            result = null
            e.printStackTrace()
        }

        return result
    }


    /*
     * Gson转可序列化对象，type
     * */
    fun <T : Any> deser(`object`: String, type: Type): T? {
        var result: T? = null
        try {
            result = getGson().fromJson<T>(`object`, type)
        } catch (e: Exception) {
            result = null
            e.printStackTrace()
        }

        return result
    }

}
