package com.lihang.selfmvvmbykotlin.base

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.lihang.selfmvvmbykotlin.bean.basebean.ParamsBuilder
import com.lihang.selfmvvmbykotlin.bean.basebean.Resource
import com.lihang.selfmvvmbykotlin.bean.basebean.ResponModel
import com.lihang.selfmvvmbykotlin.retrofitwithrxjava.Interceptor.NetCacheInterceptor
import com.lihang.selfmvvmbykotlin.retrofitwithrxjava.Interceptor.OfflineCacheInterceptor
import com.lihang.selfmvvmbykotlin.retrofitwithrxjava.RetrofitApiService
import com.lihang.selfmvvmbykotlin.retrofitwithrxjava.RetrofitManager
import com.lihang.selfmvvmbykotlin.retrofitwithrxjava.downloadutils.DownFileUtils
import com.lihang.selfmvvmbykotlin.retrofitwithrxjava.uploadutils.UploadFileRequestBody
import com.lihang.selfmvvmbykotlin.utils.LogUtils
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by leo
 * on 2019/10/30.
 */
abstract class BaseModel {
    var compositeDisposable: CompositeDisposable? = null
    var onNetTags: ArrayList<String>? = null
    var objectLifecycleTransformer: LifecycleTransformer<Any>? = null

    fun getApiService(): RetrofitApiService? {
        return RetrofitManager.retrofitManager.getApiService()
    }


    fun <T> observeGo(
        observable: Observable<*>,
        liveData: MutableLiveData<T>,
        paramsBuilder: ParamsBuilder = ParamsBuilder.build()
    ): MutableLiveData<T> {
        var retryCount = if (paramsBuilder == null) 0 else paramsBuilder.retryCount
        return if (retryCount > 0) {
            observeWithRetry(observable, liveData, paramsBuilder!!)
        } else {
            observe(observable, liveData, paramsBuilder!!)
        }
    }


    //不会重连的（统一操作全封装在这里）
    fun <T> observe(
        observable: Observable<*>,
        liveData: MutableLiveData<T>,
        paramsBuilder: ParamsBuilder
    ): MutableLiveData<T> {

        var paramsBuilder = paramsBuilder
        if (paramsBuilder == null) {
            paramsBuilder = ParamsBuilder.build()
        }
        var showDialog = paramsBuilder.isShowDialog
        var loadingMessage = paramsBuilder.loadingMessage
        var onlineCacheTime = paramsBuilder.onlineCacheTime
        var offlineCacheTime = paramsBuilder.offlineCacheTime
        var cancleNet = paramsBuilder.cancleNet
        if (onlineCacheTime > 0) {
            setOnlineCacheTime(onlineCacheTime)
        }
        if (offlineCacheTime > 0) {
            setOfflineCacheTime(offlineCacheTime)
        }

        var oneTag = paramsBuilder.oneTag

        if (!TextUtils.isEmpty(oneTag)) {
            if (onNetTags!!.contains(oneTag)) {
                return liveData
            }
        }


        var disposable = observable.subscribeOn(Schedulers.io())
            .doOnSubscribe({ disposable ->
                if (!TextUtils.isEmpty(oneTag)) {
                    onNetTags!!.add(oneTag!!)
                }

                if (showDialog) {
                    liveData.postValue(Resource.loading<T>(loadingMessage) as T)
                }
            }).observeOn(AndroidSchedulers.mainThread())
            .compose(objectLifecycleTransformer)
            .subscribe({ o ->
                liveData.postValue(Resource.response(o as ResponModel<Object>) as T)
                if (!TextUtils.isEmpty(oneTag)) {
                    onNetTags!!.remove(oneTag!!)
                }
            }, { throwable ->
                liveData.postValue(Resource.error<T>(throwable) as T)
                if (!TextUtils.isEmpty(oneTag)) {
                    onNetTags!!.remove(oneTag!!)
                }
            })
        if (cancleNet) {
            compositeDisposable!!.add(disposable)
        }
        return liveData
    }


    //这是带重连的（统一操作封装在这里）
    fun <T> observeWithRetry(
        observable: Observable<*>,
        liveData: MutableLiveData<T>,
        paramsBuilder: ParamsBuilder
    ): MutableLiveData<T> {
        var paramsBuilder = paramsBuilder
        if (paramsBuilder == null) {
            paramsBuilder = ParamsBuilder.build()
        }

        var showDialog = paramsBuilder.isShowDialog
        var loadingMessage = paramsBuilder.loadingMessage
        var onlineCacheTime = paramsBuilder.onlineCacheTime
        var offlineCacheTime = paramsBuilder.offlineCacheTime
        var cancleNet = paramsBuilder.cancleNet
        if (onlineCacheTime > 0) {
            setOnlineCacheTime(onlineCacheTime)
        }

        if (offlineCacheTime > 0) {
            setOfflineCacheTime(offlineCacheTime)
        }

        var oneTag = paramsBuilder.oneTag

        if (!TextUtils.isEmpty(oneTag)) {
            if (onNetTags!!.contains(oneTag)) {
                return liveData
            }
        }

        val maxCount = paramsBuilder.retryCount
        var currentCount = 0


        var disposable = observable.subscribeOn(Schedulers.io())
            .retryWhen({ throwableObservable ->
                throwableObservable.flatMap({ throwable ->
                    if (currentCount <= maxCount) {
                        currentCount++
                        Observable.just(1).delay(5000, TimeUnit.MILLISECONDS)
                    } else {
                        //到次数了跑出异常
                        Observable.error(Throwable("重连次数已达最高,请求超时"))
                    }
                })
            }).doOnSubscribe({ disposable ->
                if (!TextUtils.isEmpty(oneTag)) {
                    onNetTags!!.add(oneTag!!)
                }
                if (showDialog) {
                    liveData.postValue(Resource.loading<T>(loadingMessage) as T)
                }
            }).observeOn(AndroidSchedulers.mainThread())
            .compose(objectLifecycleTransformer)
            .subscribe({ o ->
                liveData.postValue(Resource.response(o as ResponModel<Any>) as T)
                if (!TextUtils.isEmpty(oneTag)) {
                    onNetTags!!.remove(oneTag!!)
                }
            }, { throwable ->
                liveData.postValue(Resource.error<T>(throwable) as T)
                if (!TextUtils.isEmpty(oneTag)) {
                    onNetTags!!.remove(oneTag)
                }
            })

        if (cancleNet) {
            compositeDisposable!!.add(disposable)
        }
        return liveData
    }

    //设置在线网络缓存
    fun setOnlineCacheTime(time: Int) {
        NetCacheInterceptor.getInstance().setOnlineTime(time)
    }

    //设置离线网络缓存
    fun setOfflineCacheTime(time: Int) {
        OfflineCacheInterceptor.getInstance().setOfflineCacheTime(time)
    }

    fun <T> downLoadFile(
        observable: Observable<ResponseBody>,
        liveData: MutableLiveData<T>,
        destDir: String,
        fileName: String,
        currentLength: Long = 0
    ): MutableLiveData<T> {
        observable.subscribeOn(Schedulers.io())
            .map(Function<ResponseBody, File> { requestBody ->
                if (currentLength == 0L) {
                    //重新开始下载
                    DownFileUtils.saveFile(requestBody, destDir, fileName, liveData)
                } else {
                    //断点下载
                    DownFileUtils.saveFile(requestBody, destDir, fileName, currentLength, liveData)
                }
            }).compose(objectLifecycleTransformer)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ file ->
                liveData.postValue(Resource.success(file) as T)
            }, { throwable ->
                liveData.postValue(Resource.error<T>(throwable) as T)
            })
        return liveData
    }


    fun <T> upLoadFile(
        url: String,
        map: HashMap<String, String>,
        files: HashMap<String, File>,
        liveData: MutableLiveData<T>
    ): MutableLiveData<T> {
        var uploadFileRequestBody = UploadFileRequestBody(files, liveData)
        var body = MultipartBody.Part.create(uploadFileRequestBody)
        return upLoadFile(getApiService()!!.uploadPic(url, map, body), liveData, true, "")
    }


    //上传文件
    fun <T> upLoadFile(
        observable: Observable<ResponseBody>,
        liveData: MutableLiveData<T>,
        showDialog: Boolean,
        message: String
    ): MutableLiveData<T> {
        observable.subscribeOn(Schedulers.io())
            .doOnSubscribe({ disposable ->
                if (showDialog) {
                    liveData.postValue(Resource.loading<T>(message) as T)
                }
            }).observeOn(AndroidSchedulers.mainThread())
            .compose(objectLifecycleTransformer)
            .subscribe({ o ->
                liveData.postValue(Resource.success("成功了") as T)
            }, { throwable ->
                liveData.postValue(Resource.error<T>(throwable) as T)
            })
        return liveData
    }


}




