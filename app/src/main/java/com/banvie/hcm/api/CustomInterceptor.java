package com.banvie.hcm.api;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CustomInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Headers headers = new Headers.Builder()
                .add("Authorization", Header.TOKEN_TYPE + " " + Header.ACCESS_TOKEN)
                .build();

        Request newRequest = originalRequest.newBuilder()
                .headers(headers)
                .build();

        Response response = chain.proceed(newRequest);
        return response;
    }
}
