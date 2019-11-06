package com.lihang.selfmvvmbykotlin.common


import java.io.File
import java.util.ArrayList
import java.util.HashMap

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * Created by leo on 2017/9/13.
 * 键值对上传类
 */

object PARAMS {
    var pageSize = "10"

    fun gankPost(
        url: String,
        desc: String,
        who: String,
        type: String,
        debug: String
    ): HashMap<String, String> {
        val map = HashMap<String, String>()
        map["url"] = url
        map["desc"] = desc
        map["who"] = who
        map["type"] = type
        map["debug"] = debug
        return map
    }


    fun changeToRquestBody(param: String): RequestBody {
        return RequestBody.create("multipart/form-data".toMediaTypeOrNull(), param)
    }

    fun changeToMutiPartBody(key: String, file: File): MultipartBody.Part {
        val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        return MultipartBody.Part.createFormData(key, file.name, requestFile)
    }

    //不同key 不同图片
    fun manyFileToPartBody(fileMap: Map<String, File>): Map<String, RequestBody> {
        val requestBodyMap = HashMap<String, RequestBody>()
        for (key in fileMap.keys) {
            requestBodyMap[key] =
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), fileMap[key]!!)
        }
        return requestBodyMap
    }

    //同一key 多张图片
    fun manyFileToPartBody(key: String, files: ArrayList<File>): Map<String, RequestBody> {
        val requestBodyMap = HashMap<String, RequestBody>()
        for (i in files.indices) {
            requestBodyMap[key] =
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), files[i])
        }
        return requestBodyMap
    }


}
