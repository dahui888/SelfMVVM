package com.lihang.selfmvvmbykotlin.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.loader.app.LoaderManager
import com.lihang.selfmvvmbykotlin.utils.LogUtils
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.disposables.CompositeDisposable
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by leo
 * on 2019/11/4.
 */
abstract class BaseViewModel<T : BaseModel>(application: Application) :
    AndroidViewModel(application) {
    var compositeDisposable: CompositeDisposable? = null
    private var repository: T? = null
    private var onNetTags: ArrayList<String>? = null


    //构造方法
    init {
        this.repository = createRepository()
        compositeDisposable = CompositeDisposable()
        onNetTags = ArrayList()
    }


    fun setObjectLifecycleTransformer(objectLifecycleTransformer: LifecycleTransformer<Any>) {
        repository!!.objectLifecycleTransformer = objectLifecycleTransformer
        repository!!.compositeDisposable = compositeDisposable
        repository!!.onNetTags = onNetTags
    }

    fun getRepository(): T {
        return repository!!
    }

    fun createRepository(): T {
        var modelClass: Class<*>
        var type: Type = javaClass.genericSuperclass!!

        if (type is ParameterizedType) {
            modelClass = type.actualTypeArguments[0] as Class<BaseModel>
        } else {
            modelClass = BaseModel::class.java as Class<BaseModel>
        }
        return modelClass.newInstance() as T
    }


    override fun onCleared() {
        super.onCleared()
        //销毁页面后，移除当前页面正在执行的所有网络请求
        compositeDisposable?.dispose()
        repository = null
        onNetTags = null
    }


}