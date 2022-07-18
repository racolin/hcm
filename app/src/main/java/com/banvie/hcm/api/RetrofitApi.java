package com.banvie.hcm.api;

import android.util.Log;

import com.banvie.hcm.listener.OnLoadNotificationsListener;
import com.banvie.hcm.listener.OnLoadedNoticesListener;
import com.banvie.hcm.listener.OnLoginListener;
import com.banvie.hcm.model.Token;
import com.banvie.hcm.model.checkout.CheckOut;
import com.banvie.hcm.model.notification.Notification;
import com.banvie.hcm.model.notification.NotificationContainer;
import com.banvie.hcm.param.NotificationParam;
import com.banvie.hcm.param.UserParam;
import com.banvie.hcm.model.policy.Policy;
import com.banvie.hcm.model.policy.PolicyContainer;

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
                    Constant.ACCESS_TOKEN = response.body().getAccess_token();
                    Constant.REFRESH_TOKEN = response.body().getRefresh_token();
                    Constant.EXPIRE_IN = response.body().getExpire_in();
                    Constant.TOKEN_TYPE = response.body().getToken_type();

                    listener.setOnLoginListener(true);
                    return;
                }
                listener.setOnLoginListener(false);
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {

            }
        });
    }

    static public void callNotices(OnLoadedNoticesListener listener) {
        ApiService.apiService.getPolicy().enqueue(new Callback<PolicyContainer>() {
            @Override
            public void onResponse(Call<PolicyContainer> call, Response<PolicyContainer> response) {
                if (response.isSuccessful()) {
                    List<Policy> policies = response.body().getData().getItems();
                    listener.setOnLoadedNotices(policies);
                    int len = policies.size();
                    for (int i = 0; i < len; i++) {
                        final int finalI = i;
                        ApiService.apiService.getImage(policies.get(i).getThumbnail()).enqueue(new Callback<ResponseBody>() {
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
            public void onFailure(Call<PolicyContainer> call, Throwable t) {

            }
        });
    }

    static public void getNotifications(NotificationParam param, OnLoadNotificationsListener listener) {
        ApiService.apiService.getNotifications(param).enqueue(new Callback<NotificationContainer>() {
            @Override
            public void onResponse(Call<NotificationContainer> call, Response<NotificationContainer> response) {
                List<Notification> notifications = response.body().getData().getData().getItems();
                listener.setOnLoadNotificationsListener(response.body());
                for (int i = 0; i < notifications.size(); i++) {
                    final int finalI = i;
                    String im = notifications.get(finalI).getImage();
                    if (im != null && !im.equals("")) {
                        ApiService.apiService.getImage(notifications.get(finalI).getImage()).enqueue(new Callback<ResponseBody>() {
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
            }

            @Override
            public void onFailure(Call<NotificationContainer> call, Throwable t) {

            }
        });
    }

    static public void checkOut(Callback<CheckOut> callback) {
        ApiService.apiService.checkOut().enqueue(callback);
    }
}
