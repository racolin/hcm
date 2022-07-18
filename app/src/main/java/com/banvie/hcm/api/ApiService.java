package com.banvie.hcm.api;

import com.banvie.hcm.config.Environment;
import com.banvie.hcm.model.RefreshToken;
import com.banvie.hcm.model.Token;
import com.banvie.hcm.model.checkout.CheckOut;
import com.banvie.hcm.model.notification.NotificationContainer;
import com.banvie.hcm.param.NotificationParam;
import com.banvie.hcm.param.UserParam;
import com.banvie.hcm.model.policy.PolicyContainer;
import com.squareup.moshi.Moshi;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiService {

    Moshi moshi = new Moshi.Builder().build();

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new CustomInterceptor()) // This is used to add ApplicationInterceptor.
//            .addNetworkInterceptor(new CustomInterceptor())
            .authenticator(new CustomAuthenticator())
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
    Call<PolicyContainer> getPolicy();

    @GET(Environment.PRODUCTION + "fileapp/store/file/get")
    Call<ResponseBody> getImage(@Query("subPath") String subPath);

    @POST("scheduleapp/v1.0/notification-mobile/list")
    Call<NotificationContainer> getNotifications(@Body NotificationParam param);

    @GET("mytimeapp/v1.0/status-check-in-out")
    Call<CheckOut> checkOut();

    @POST("accountapp/v1.0/auth/refresh")
    Call<Token> refreshToken(@Body RefreshToken refreshToken);

    @PUT("scheduleapp/v1.0/notification-mobile")
    Call<Void> readNotification(@Body List<String> notificationIds);
}
