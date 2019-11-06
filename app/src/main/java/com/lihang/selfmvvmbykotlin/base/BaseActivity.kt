package com.lihang.selfmvvmbykotlin.base

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.google.gson.JsonSyntaxException
import com.lihang.selfmvvmbykotlin.R
import com.lihang.selfmvvmbykotlin.bean.basebean.Resource
import com.lihang.selfmvvmbykotlin.customview.CustomProgress
import com.lihang.selfmvvmbykotlin.utils.ToastUtils
import com.lihang.selfmvvmbykotlin.utils.networks.NetWorkUtils
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.components.support.RxFragmentActivity
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Created by leo
 * on 2019/11/4.
 */
abstract class BaseActivity<VM : BaseViewModel<*>, VDB : ViewDataBinding> : RxFragmentActivity(),
    View.OnClickListener {
    //获取当前Activity的布局
    protected abstract fun getContentViewId(): Int

    //处理逻辑业务
    protected abstract fun processLogic()

    protected var mViewModel: VM? = null
    protected var binding: VDB? = null
    protected var dialog: CustomProgress? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getContentViewId())
        binding!!.lifecycleOwner = this
        createViewModel()
        processLogic()
    }


    //这里很可能有问题的

    fun createViewModel() {
        if (mViewModel == null) {
            var modelClass: Class<*>
            var type: Type = javaClass.genericSuperclass!!

            if (type is ParameterizedType) {
                modelClass = type.actualTypeArguments[0] as Class<ViewModel>
            } else {
                modelClass = BaseViewModel::class.java as Class<ViewModel>
            }
            mViewModel = ViewModelProviders.of(this).get(modelClass) as VM
            mViewModel!!.setObjectLifecycleTransformer(bindLifecycle())
        }
    }


    fun bindLifecycle(): LifecycleTransformer<Any> {
        return bindToLifecycle()
    }


    fun getContext(): Context {
        return this
    }


    abstract inner class OnCallback<T> : Resource.OnHandleCallback<T> {
        override fun onLoading(showMessage: String?) {
            if (dialog == null) {
                dialog = CustomProgress.show(this@BaseActivity, "", true, null)
            }

            if (!TextUtils.isEmpty(showMessage)) {
                dialog!!.setMessage(showMessage)
            }
            if (!dialog!!.isShowing) {
                dialog!!.show()
            }
        }

        override fun onError(error: Throwable?) {
            if (!NetWorkUtils.isNetworkConnected(getContext())) {
                ToastUtils.showToast(getContext().resources.getString(R.string.result_network_error))
            }

            if (error is ConnectException) {
                ToastUtils.showToast(getContext().resources.getString(R.string.result_server_error))
            } else if (error is SocketTimeoutException) {
                ToastUtils.showToast(getContext().resources.getString(R.string.result_server_timeout))
            } else if (error is JsonSyntaxException) {
                ToastUtils.showToast("数据解析出错")
            } else {
                ToastUtils.showToast(getContext().resources.getString(R.string.result_empty_error))
            }

        }


        override fun onFailure(msg: String?) {
            ToastUtils.showToast(msg!!)
        }


        override fun onCompleted() {
            if (dialog != null && dialog!!.isShowing) {
                dialog!!.dismiss()
            }
        }

        override fun onProgress(precent: Int?, total: Long?) {

        }


    }


}