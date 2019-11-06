package com.lihang.selfmvvmbykotlin.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import com.lihang.selfmvvmbykotlin.base.BaseViewModel
import com.lihang.selfmvvmbykotlin.bean.BannerBean
import com.lihang.selfmvvmbykotlin.bean.basebean.Resource
import com.lihang.selfmvvmbykotlin.utils.LogUtils

/**
 * Created by leo
 * on 2019/11/4.
 */
class HomeViewModel(application: Application) : BaseViewModel<HomeRepository>(application) {

    fun getBanner(): LiveData<Resource<List<BannerBean>>> {
        return getRepository().getBannerList()
    }
}