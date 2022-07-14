package com.banvie.hcm.api;

import com.banvie.hcm.config.Environment;
import com.banvie.hcm.model.Token;
import com.banvie.hcm.model.checkout.CheckOut;
import com.banvie.hcm.model.notification.NotificationFull;
import com.banvie.hcm.param.NotificationParam;
import com.banvie.hcm.param.UserParam;
import com.banvie.hcm.model.policy.Policy;
import com.squareup.moshi.Moshi;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    Moshi moshi = new Moshi.Builder().build();

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new CustomInterceptor()) // This is used to add ApplicationInterceptor.
//            .addNetworkInterceptor(new CustomInterceptor())
            .build();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl(Environment.getEnvironment())
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiService.class);

    @POST("accountapp/v1.0/auth")
    Call<Token> login(@Body UserParam param);

    @GET("mytimeapp/v1.0/policies")
    Call<Policy> getPolicy();

    @GET(Environment.PRODUCTION + "fileapp/store/file/get")
    Call<ResponseBody> getImage(@Query("subPath") String subPath);

    @POST("scheduleapp/v1.0/notification-mobile/list")
    Call<NotificationFull> getNotifications(@Body NotificationParam param);

    @GET("mytimeapp/v1.0/status-check-in-out")
    Call<CheckOut> checkOut();
}