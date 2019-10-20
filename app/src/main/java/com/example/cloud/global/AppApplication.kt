package com.example.cloud.global

import android.app.Application
import com.lzy.okgo.OkGo
import com.lzy.okgo.cache.CacheEntity
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.model.HttpHeaders
import com.lzy.okgo.model.HttpParams
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import com.lzy.okgo.https.HttpsUtils
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class AppApplication: Application() {
    override fun onCreate() {
        super.onCreate();
        initOkGo();
    }

    private fun initOkGo() {
        var headers = HttpHeaders();
        headers.put("content-type", "application/json");

        var builder = OkHttpClient.Builder();
        builder.readTimeout(6000, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(6000, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(6000, TimeUnit.MILLISECONDS);   //全局的连接超时时间
        var params = HttpParams();

        val sslParams1 = HttpsUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);

        OkGo.getInstance().init(this)                           //必须调用初始化
            .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
            .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
            .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
            .setRetryCount(1)                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
            .addCommonHeaders(headers)                      //全局公共头
            .addCommonParams(params);                       //全局公共参数
    }
}