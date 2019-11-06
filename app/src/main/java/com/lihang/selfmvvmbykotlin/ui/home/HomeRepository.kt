package com.lihang.selfmvvmbykotlin.ui.home

import androidx.lifecycle.MutableLiveData
import com.lihang.selfmvvmbykotlin.base.BaseModel
import com.lihang.selfmvvmbykotlin.bean.BannerBean
import com.lihang.selfmvvmbykotlin.bean.basebean.Resource

/**
 * Created by leo
 * on 2019/11/4.
 */
class HomeRepository : BaseModel() {
    fun getBannerList(): MutableLiveData<Resource<List<BannerBean>>> {
        var liveData = MutableLiveData<Resource<List<BannerBean>>>()
        return observeGo(getApiService()!!.getBanner(), liveData)
    }
}