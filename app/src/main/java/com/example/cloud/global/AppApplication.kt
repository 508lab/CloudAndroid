package com.example.cloud.global

import android.app.Application
import android.content.Context
import android.text.TextUtils
import com.example.cloud.util.Constant
import com.example.cloud.util.SPUtils
import com.kongzue.dialog.util.DialogSettings
import com.lzy.okgo.OkGo
import com.lzy.okgo.cache.CacheEntity
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.model.HttpHeaders
import com.lzy.okgo.model.HttpParams
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import com.lzy.okgo.https.HttpsUtils
import com.lzy.okgo.interceptor.HttpLoggingInterceptor
import java.util.logging.Level


class AppApplication : Application() {



    companion object {
        lateinit var instance: AppApplication
            private set

    }

    override fun onCreate() {
        super.onCreate();

        instance = this;
        initOkGo();
        DialogSettings.isUseBlur = false
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

        val loggingInterceptor = HttpLoggingInterceptor("OkGo")
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY)        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO)                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor)                                 //添加OkGo默认debug日志

        builder.addInterceptor { chain ->
            val originalRequest = chain.request()
            val builder = originalRequest.newBuilder()

            val token = SPUtils.get(Constant.TOKEN, "") as String
            if (!TextUtils.isEmpty(token)) {
                //设置具体的header内容
                builder.header("AUTHORIZATION", token)
            }

            val requestBuilder =
                builder.method(originalRequest.method(), originalRequest.body())
            val request = requestBuilder.build()
            val proceed = chain.proceed(request)
            proceed
        }
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
        OkGo.getInstance().init(this)
            .setOkHttpClient(builder.build())
            .setCacheMode(CacheMode.NO_CACHE)
            .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
            .setRetryCount(1)
            .addCommonHeaders(headers)
            .addCommonParams(params);
    }

    fun getContext(): Context {
        return instance
    }


}