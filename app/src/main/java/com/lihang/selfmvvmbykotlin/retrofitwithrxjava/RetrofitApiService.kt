package com.lihang.selfmvvmbykotlin.retrofitwithrxjava

import com.lihang.selfmvvmbykotlin.bean.BannerBean
import com.lihang.selfmvvmbykotlin.bean.basebean.ResponModel
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * Created by leo
 * on 2019/10/30.
 */
interface RetrofitApiService {
    @GET("banner/json")
    fun getBanner(): Observable<ResponModel<List<BannerBean>>>

    @POST
    @Multipart
    fun uploadPic(@Url url: String, @FieldMap map: HashMap<String, String>, @Part file: MultipartBody.Part): Observable<ResponseBody>
}