package com.example.cloud.network;

public interface ResponseResult {
    void onSuccess(String data);

    void onError(String error);
}
