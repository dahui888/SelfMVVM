package com.lihang.selfmvvmbykotlin.utils

import android.content.Context
import android.content.SharedPreferences


import com.lihang.selfmvvmbykotlin.MyApplication

import java.io.Serializable
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.ArrayList


object PreferenceUtil {

    private val context = MyApplication.getContext()
    val FILE_NAME = "leo_pro"


    /*
     * 返回表名 -- FILE_NAME里所有的数据，以Map键值对的方式
     * */
    val all: Map<String, *>
        get() {
            val sp = context!!.getSharedPreferences(
                FILE_NAME,
                Context.MODE_PRIVATE
            )
            return sp.all
        }

    /**
     * 这里是对基本数据类型进行的操作
     */
    /*
     * 这里是保存基本数据类型 -- 表名是上面设置的FILE_NAME
     * */
    fun put(key: String, `object`: Any) {

        val sp = context!!.getSharedPreferences(
            FILE_NAME,
            Context.MODE_PRIVATE
        )
        val editor = sp.edit()

        if (`object` is String) {
            editor.putString(key, `object`)
        } else if (`object` is Int) {
            editor.putInt(key, `object`)
        } else if (`object` is Boolean) {
            editor.putBoolean(key, `object`)
        } else if (`object` is Float) {
            editor.putFloat(key, `object`)
        } else if (`object` is Long) {
            editor.putLong(key, `object`)
        } else {
            editor.putString(key, `object`.toString())
        }
        editor.commit()
    }

    /*
     * 这里是根据key，获取数据。表名是 -- FILE_NAME
     * 第二个参数是  默认值
     * */
    operator fun get(key: String, defaultObject: Any): Any? {
        val sp = context!!.getSharedPreferences(
            FILE_NAME,
            Context.MODE_PRIVATE
        )

        if (defaultObject is String) {
            return sp.getString(key, defaultObject)
        } else if (defaultObject is Int) {
            return sp.getInt(key, defaultObject)
        } else if (defaultObject is Boolean) {
            return sp.getBoolean(key, defaultObject)
        } else if (defaultObject is Float) {
            return sp.getFloat(key, defaultObject)
        } else if (defaultObject is Long) {
            return sp.getLong(key, defaultObject)
        }

        return null
    }


    /*
     * 根据某个key值获取数据  表名 -- FILE_NAME
     * */
    fun remove(key: String) {
        val sp = context!!.getSharedPreferences(
            FILE_NAME,
            Context.MODE_PRIVATE
        )
        val editor = sp.edit()
        editor.remove(key)
        editor.commit()
    }

    /*
     * 清楚 表名 -- FILE_NAME 里所有的数据
     * */
    fun clear() {
        val sp = context!!.getSharedPreferences(
            FILE_NAME,
            Context.MODE_PRIVATE
        )
        val editor = sp.edit()
        editor.clear()
        editor.commit()
    }

    /*
     * 判断当前key值 是否存在于  表名--FILE_NAME 表里
     * */
    operator fun contains(key: String): Boolean {
        val sp = context!!.getSharedPreferences(
            FILE_NAME,
            Context.MODE_PRIVATE
        )
        return sp.contains(key)
    }


    /**
     * 以下是保存类的方式，跟上面的FILE_NAME表不在一个表里
     */
    /*
     * 保存类，当前SharedPreferences以 class类名被表名
     * */
    fun <T : Serializable> putByClass(entity: T?, key: String): Boolean {
        if (entity == null) {
            return false
        }
        val prefFileName = entity.javaClass.name
        val sp = context!!.getSharedPreferences(prefFileName, 0)
        val et = sp.edit()
        val json = GsonUtil.ser(entity)
        et.putString(key, json)
        return et.commit()
    }


    /*
     * 获取某个以class 为表名的。所有class 对象
     * */
    fun <T : Serializable> getAllByClass(clazz: Class<T>): List<T> {
        val prefFileName = clazz.name
        val sp = context!!.getSharedPreferences(prefFileName, 0)
        val values = sp.all as Map<String, String>
        val results = ArrayList<T>()
        if (values.isEmpty())
            return results
        val colles = values.values
        for (json in colles) {
            results.add(GsonUtil.deser(json, clazz)!!)
        }
        return results
    }

    /*
     * 获取以类名为表名的，某个key值上的value
     * 第二个参数是，类名class,也就是当前的表名
     * */
    fun <T : Serializable> getByClass(key: String, clazz: Class<T>): T? {
        val prefFileName = clazz.name
        val sp = context!!.getSharedPreferences(prefFileName, 0)

        val json = sp.getString(key, null) ?: return null
        return GsonUtil.deser(json, clazz)
    }

    /*
     * 在以类名为表名的表上，移除key值
     * 第二个参数是，类名class,也就是当前的表名
     * */
    fun <T : Serializable> removeByClass(key: String, clazz: Class<T>) {
        val prefFileName = clazz.name
        val sp = context!!.getSharedPreferences(prefFileName, 0)
        if (sp.contains(key)) {
            sp.edit().remove(key).commit()
        }
    }

    /*
     * 移除 某个以类名为表名上的所有的值
     * */
    fun <T : Serializable> clearByClass(clazz: Class<T>) {
        val prefFileName = clazz.name
        val sp = context!!.getSharedPreferences(prefFileName, 0)
        sp.edit().clear().commit()
    }

}
