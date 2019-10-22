package com.example.cloud.network

import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import com.lzy.okgo.request.base.Request


class HttpClient {

    public fun httpPost(url: String, data: HashMap<String, String>, callback: ((String) -> Unit)) {

        OkGo.post<String>(url)
            .params(data)//123456
            .execute(object : StringCallback() {
                override fun onStart(request: Request<String, out Request<Any, Request<*, *>>>?) {
                    super.onStart(request);
                }

                override fun onFinish() {
                    super.onFinish()
                }

                override fun onSuccess(response: Response<String>) {
                    val data = response.body();
                    callback(data.toString());
                }

                override fun onError(response: Response<String>) {
                    super.onError(response);
                }
            });

    }

}

