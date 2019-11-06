package com.lihang.selfmvvmbykotlin.ui.home

import android.view.View
import androidx.lifecycle.Observer
import com.lihang.selfmvvmbykotlin.R
import com.lihang.selfmvvmbykotlin.base.BaseActivity
import com.lihang.selfmvvmbykotlin.bean.BannerBean
import com.lihang.selfmvvmbykotlin.bean.basebean.Resource
import com.lihang.selfmvvmbykotlin.databinding.ActivityHomeTestBinding
import com.lihang.selfmvvmbykotlin.utils.GlideImageLoader
import com.lihang.selfmvvmbykotlin.utils.LogUtils
import com.youth.banner.BannerConfig
import java.util.ArrayList

/**
 * Created by leo
 * on 2019/11/4.
 */
class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeTestBinding>() {
    override fun getContentViewId(): Int {
        return R.layout.activity_home_test
    }

    override fun processLogic() {
        binding!!.onclickListener = this
        initBanner()
        getBanner()
    }


    override fun onStart() {
        super.onStart()
        binding!!.banner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        binding!!.banner.stopAutoPlay()
    }

    fun getBanner() {

        mViewModel!!.getBanner().observe(this, Observer { t ->
            t.handler(object : OnCallback<List<BannerBean>>() {
                override fun onSuccess(data: List<BannerBean>?) {
                    updateBanner(data)
                }


            })
        })

    }

    fun initBanner() {
        binding!!.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
        binding!!.banner.setImageLoader(GlideImageLoader())
    }

    private fun updateBanner(data: List<BannerBean>?) {
        if (data == null || data.size <= 0) {
            return
        }
        val urls = ArrayList<String>()
        val titles = ArrayList<String>()
        for (i in data.indices) {
            data.get(i).imagePath?.let { urls.add(it) }
            titles.add(data.get(i).title!!)
        }
        binding!!.banner.setBannerTitles(titles)
        binding!!.banner.setImages(urls)
        binding!!.banner.start()
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}