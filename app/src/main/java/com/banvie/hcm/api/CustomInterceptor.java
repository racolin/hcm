package com.banvie.hcm.api;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CustomInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Headers headers = new Headers.Builder()
                .add("Authorization", Constant.TOKEN_TYPE + " " + Constant.ACCESS_TOKEN)
                .build();

        Request newRequest = originalRequest.newBuilder()
                .headers(headers)
                .build();

        Log.d("debug:" + this.getClass(), "URL: " + originalRequest.url().toString());

        Response response = chain.proceed(newRequest);

        return response;
    }
}
