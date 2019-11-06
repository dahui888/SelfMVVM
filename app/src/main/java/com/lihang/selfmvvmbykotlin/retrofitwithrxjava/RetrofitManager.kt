package com.lihang.selfmvvmbykotlin.retrofitwithrxjava

import android.os.Environment
import com.lihang.selfmvvmbykotlin.common.SystemConst
import com.lihang.selfmvvmbykotlin.retrofitwithrxjava.Interceptor.HttpLogInterceptor
import com.lihang.selfmvvmbykotlin.retrofitwithrxjava.Interceptor.NetCacheInterceptor
import com.lihang.selfmvvmbykotlin.retrofitwithrxjava.Interceptor.OfflineCacheInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by leo
 * on 2019/10/30.
 */
class RetrofitManager {
    companion object {
        //这样就双重检验了，还不用定方法名
        val retrofitManager: RetrofitManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {

            RetrofitManager()
        }
    }

    private var okHttpClient: OkHttpClient? = null
    private var retrofit: Retrofit? = null
    private var retrofitApiService: RetrofitApiService? = null

    private constructor() {
        initOkHttpClient()
        initRetrofit()
    }

    fun getApiService(): RetrofitApiService? {
        return retrofitManager.retrofitApiService
    }


    private fun initRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl(SystemConst.DEFAULT_SERVER)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
        retrofitApiService = retrofit!!.create(RetrofitApiService::class.java)
    }

    private fun initOkHttpClient() {
        okHttpClient = OkHttpClient.Builder()
            //设置缓存文件路径，和文件大小
            .cache(
                Cache(
                    File(Environment.getExternalStorageDirectory().toString() + "/okhttp_cache/"),
                    50 * 1024 * 1024
                )
            )
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(HttpLogInterceptor())
            //设置在线和离线缓存
            .addInterceptor(OfflineCacheInterceptor.getInstance())
            .addNetworkInterceptor(NetCacheInterceptor.getInstance())
            .build()
    }

}