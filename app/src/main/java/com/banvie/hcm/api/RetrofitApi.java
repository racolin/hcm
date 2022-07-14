package com.banvie.hcm.api;

import android.util.Log;

import com.banvie.hcm.listener.OnLoadNotificationsListener;
import com.banvie.hcm.listener.OnLoadedNoticesListener;
import com.banvie.hcm.listener.OnLoginListener;
import com.banvie.hcm.model.Token;
import com.banvie.hcm.model.checkout.CheckOut;
import com.banvie.hcm.model.notification.NotificationFull;
import com.banvie.hcm.param.NotificationParam;
import com.banvie.hcm.param.UserParam;
import com.banvie.hcm.model.policy.Notice;
import com.banvie.hcm.model.policy.Policy;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitApi {

    static public void login(UserParam userParam, OnLoginListener listener) {
        ApiService.apiService.login(userParam).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.body() != null) {
                    Header.ACCESS_TOKEN = response.body().getAccess_token();
                    Header.REFRESH_TOKEN = response.body().getRefresh_token();
                    Header.TOKEN_TYPE = response.body().getToken_type();
                    listener.setOnLoginListener(true);
                }
                listener.setOnLoginListener(false);
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {

            }
        });
    }

    static public void callNotices(OnLoadedNoticesListener listener) {
        ApiService.apiService.getPolicy().enqueue(new Callback<Policy>() {
            @Override
            public void onResponse(Call<Policy> call, Response<Policy> response) {
                if (response.isSuccessful()) {
                    List<Notice> notices = response.body().getData().getItems();
                    listener.setOnLoadedNotices(notices);
                    int len = notices.size();
                    for (int i = 0; i < len; i++) {
                        final int finalI = i;
                        ApiService.apiService.getImage(notices.get(i).getThumbnail()).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    listener.setOnLoadImageListener(response.body().bytes(), finalI);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }
                }
                else {
                }
            }

            @Override
            public void onFailure(Call<Policy> call, Throwable t) {

            }
        });
    }

    static public void getNotifications(NotificationParam param, OnLoadNotificationsListener listener) {
        ApiService.apiService.getNotifications(param).enqueue(new Callback<NotificationFull>() {
            @Override
            public void onResponse(Call<NotificationFull> call, Response<NotificationFull> response) {

            }

            @Override
            public void onFailure(Call<NotificationFull> call, Throwable t) {

            }
        });
    }

    static public void checkOut(Callback<CheckOut> callback) {
        ApiService.apiService.checkOut().enqueue(callback);
    }
}
