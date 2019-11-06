package com.lihang.selfmvvmbykotlin.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.lihang.selfmvvmbykotlin.R
import com.youth.banner.loader.ImageLoader

/**
 * Created by leo
 * on 2019/10/30.
 */
class GlideImageLoader : ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        Glide.with(context!!).load(path).placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .centerCrop().into(imageView!!)
        //这里有点奇怪4.9的，不主动倒包。渐变加载动画，可以传参int durtion.默认300
    }

}