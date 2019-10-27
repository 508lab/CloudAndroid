package com.example.cloud.network

import com.example.cloud.util.Constant
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import com.lzy.okgo.request.base.Request


class HttpClient {

    public fun httpPost(url: String, data: HashMap<String, String>, callback: ResponseResult) {

        OkGo.post<String>(url)
            .params(data)//123456
            .execute(object : StringCallback() {
                override fun onStart(request: Request<String, out Request<Any, Request<*, *>>>?) {
                    super.onStart(request);
                }

                override fun onFinish() {
                    super.onFinish()
                }

                override fun onSuccess(response: Response<String>?) {
                    callback.onSuccess(response?.body().toString());

                }

                override fun onError(response: Response<String>?) {
                    super.onError(response)
                    callback.onError(response?.message())

                }
            });

    }

    fun getPath(data: HashMap<String, String>,callback: ResponseResult) {
        OkGo.get<String>(Constant.BASE_URL + "/path")
            .params(data)
            .execute(object : StringCallback(){
                override fun onSuccess(response: Response<String>?) {
                    callback.onSuccess(response?.body().toString());
                }

                override fun onError(response: Response<String>?) {
                    super.onError(response)
                    callback.onError(response?.body().toString())
                }
            })
    }
}

