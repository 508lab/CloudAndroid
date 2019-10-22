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


class AppApplication : Application() {

    companion object {
        lateinit var instance: AppApplication
            private set
    }

    override fun onCreate() {
        super.onCreate();
        instance = this;
        initOkGo();
    }


    private fun initOkGo() {
        var headers = HttpHeaders();
        headers.put("content-type", "application/json");

        var builder = OkHttpClient.Builder();
        builder.readTimeout(6000, TimeUnit.MILLISECONDS);
        builder.writeTimeout(6000, TimeUnit.MILLISECONDS);
        builder.connectTimeout(6000, TimeUnit.MILLISECONDS);
        var params = HttpParams();
        val sslParams1 = HttpsUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
        OkGo.getInstance().init(this)
            .setOkHttpClient(builder.build())
            .setCacheMode(CacheMode.NO_CACHE)
            .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
            .setRetryCount(1)
            .addCommonHeaders(headers)
            .addCommonParams(params);
    }


}