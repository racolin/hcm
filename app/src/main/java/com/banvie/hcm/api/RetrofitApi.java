package com.banvie.hcm.api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.banvie.hcm.listener.OnCheckOutListener;
import com.banvie.hcm.listener.OnLoadEmployeeListener;
import com.banvie.hcm.listener.OnLoadImageListener;
import com.banvie.hcm.listener.OnLoadItemOrganizationListener;
import com.banvie.hcm.listener.OnLoadNotificationsListener;
import com.banvie.hcm.listener.OnLoadNotificationsNumberListener;
import com.banvie.hcm.listener.OnLoadNotifySoundListener;
import com.banvie.hcm.listener.OnLoadProfileListener;
import com.banvie.hcm.listener.OnLoadedNoticesListener;
import com.banvie.hcm.listener.OnLoginListener;
import com.banvie.hcm.model.Token;
import com.banvie.hcm.model.checkout.CheckOutContainer;
import com.banvie.hcm.model.education.EducationContainer;
import com.banvie.hcm.model.employee.Em;
import com.banvie.hcm.model.employee.Employee;
import com.banvie.hcm.model.employee.EmployeeContainer;
import com.banvie.hcm.model.employee_duration.EmployeeDurationContainer;
import com.banvie.hcm.model.individual.IndividualContainer;
import com.banvie.hcm.model.notification.Notification;
import com.banvie.hcm.model.notification.NotificationContainer;
import com.banvie.hcm.model.notification_sound.NotifySoundContainer;
import com.banvie.hcm.model.organization_chart.OrganizationChart;
import com.banvie.hcm.model.shui.ShuiContainer;
import com.banvie.hcm.model.summary.SummaryContainer;
import com.banvie.hcm.param.EmployeeParam;
import com.banvie.hcm.param.NotificationParam;
import com.banvie.hcm.param.UserParam;
import com.banvie.hcm.model.policy.Policy;
import com.banvie.hcm.model.policy.PolicyContainer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
                if (response.isSuccessful() && response.body() != null) {
                    Constant.ACCESS_TOKEN = response.body().access_token;
                    Constant.REFRESH_TOKEN = response.body().refresh_token;
                    Constant.EXPIRE_IN = response.body().expire_in;
                    Constant.TOKEN_TYPE = response.body().token_type;

                    listener.setOnLoginListener(true);
                    return;
                }
                listener.setOnLoginListener(false);
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.d("rrrrlogin", t.getMessage());
            }
        });
    }

    static public void callNotices(OnLoadedNoticesListener listener) {
        ApiService.apiService.getPolicy().enqueue(new Callback<PolicyContainer>() {
            @Override
            public void onResponse(Call<PolicyContainer> call, Response<PolicyContainer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Policy> policies = response.body().data.items;
                    listener.setOnLoadedNotices(policies);
                    int len = policies.size();
                    for (int i = 0; i < len; i++) {
                        if (policies.get(i).thumbnail != null && !policies.get(i).thumbnail.equals("")) {
                            getImage(policies.get(i).thumbnail, i, listener);
                        }
                    }
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<PolicyContainer> call, Throwable t) {
                Log.d("rrrrcallNotices", t.getMessage());
            }
        });
    }

    static public void getNotifications(NotificationParam param, OnLoadNotificationsListener listener) {
        ApiService.apiService.getNotifications(param).enqueue(new Callback<NotificationContainer>() {
            @Override
            public void onResponse(Call<NotificationContainer> call, Response<NotificationContainer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Notification> notifications = response.body().data.data.items;
                    listener.setOnLoadNotificationsListener(response.body());
                    for (int i = 0; i < notifications.size(); i++) {
                        String im = notifications.get(i).image;
                        if (im != null && !im.equals("")) {
                            getImage(im, i + param.pageNumber * param.pageSize, listener);
                        }
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<NotificationContainer> call, Throwable t) {
                Log.d("rrrrgetNotifications", t.getMessage());
            }
        });
    }

    static public void getImage(String path, int i, OnLoadImageListener listener) {
        ApiService.apiService.getImage(path).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        byte[] image = response.body().bytes();
                        int len = image.length;
                        if (len > 5000) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, len);
                            Log.d("rrr", image.length + "");
                            if (bitmap != null) {
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, (int) ((5000.0 /  len) * 100), bos);
                                image = bos.toByteArray();
                            } else {
                                listener.setOnLoadImageListener(image, i);
                                return;
                            }
                            Log.d("rrrrrrrrrr", len + " " + image.length);
                        }
                        listener.setOnLoadImageListener(image, i);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("rrrrgetImage", t.getMessage());
            }
        });
    }

    static public void getNotifications(NotificationParam param, OnLoadNotificationsNumberListener listener) {
        ApiService.apiService.getNotifications(param).enqueue(new Callback<NotificationContainer>() {
            @Override
            public void onResponse(Call<NotificationContainer> call, Response<NotificationContainer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.setOnNotificationsNumberListener(response.body().unReadCount);
                } else {

                }
            }

            @Override
            public void onFailure(Call<NotificationContainer> call, Throwable t) {
                Log.d("rrrrgetNotifications", t.getMessage());
            }
        });
    }

    static public void checkOut(OnCheckOutListener listener) {
        ApiService.apiService.checkOut().enqueue(new Callback<CheckOutContainer>() {
            @Override
            public void onResponse(Call<CheckOutContainer> call, Response<CheckOutContainer> response) {
                listener.setOnCheckOutListener(response.isSuccessful() && response.body() != null);
            }

            @Override
            public void onFailure(Call<CheckOutContainer> call, Throwable t) {

            }
        });
    }

    static public void getUserSummary(OnLoadProfileListener listener) {
        ApiService.apiService.getUserSummary(Constant.userInformation.userId)
                .enqueue(new Callback<SummaryContainer>() {
                    @Override
                    public void onResponse(Call<SummaryContainer> call, Response<SummaryContainer> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            listener.setOnSummaryListener(response.body().data);
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<SummaryContainer> call, Throwable t) {
                        Log.d("rrrrgetUserSummary", t.getMessage());
                    }
                });
    }

    static public void getEmployeeDuration(OnLoadProfileListener listener) {
        ApiService.apiService.getEmployeeDuration(Constant.userInformation.userId)
                .enqueue(new Callback<EmployeeDurationContainer>() {
                    @Override
                    public void onResponse(Call<EmployeeDurationContainer> call, Response<EmployeeDurationContainer> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            listener.setOnEmployeeDurationListener(response.body().data);
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<EmployeeDurationContainer> call, Throwable t) {
                        Log.d("rrrrgetEmployeeDuration", t.getMessage());
                    }
                });
    }

    static public void getEducation(OnLoadProfileListener listener) {
        ApiService.apiService.getEducation(Constant.userInformation.userId)
                .enqueue(new Callback<EducationContainer>() {
                    @Override
                    public void onResponse(Call<EducationContainer> call, Response<EducationContainer> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            listener.setOnEducationListener(response.body().data);
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<EducationContainer> call, Throwable t) {
                        Log.d("rrrrgetEducation", t.getMessage());
                    }
                });
    }

    static public void getShui(OnLoadProfileListener listener) {
        ApiService.apiService.getShui(Constant.userInformation.userId)
                .enqueue(new Callback<ShuiContainer>() {
                    @Override
                    public void onResponse(Call<ShuiContainer> call, Response<ShuiContainer> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            listener.setOnShuiListener(response.body().data);
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<ShuiContainer> call, Throwable t) {
                        Log.d("rrrrgetShui", t.getMessage());
                    }
                });
    }

    static public void getIndividual(OnLoadProfileListener listener) {
        ApiService.apiService.getIndividual(Constant.userInformation.userId)
                .enqueue(new Callback<IndividualContainer>() {
                    @Override
                    public void onResponse(Call<IndividualContainer> call, Response<IndividualContainer> response) {
                        if (response.isSuccessful() && response.body() != null) {
//                          listener.
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<IndividualContainer> call, Throwable t) {
                        Log.d("rrrrgetIndividual", t.getMessage());
                    }
                });
    }

    static public void getNotifySound(OnLoadNotifySoundListener listener) {
        ApiService.apiService.getNotifySound().enqueue(new Callback<List<NotifySoundContainer>>() {
            @Override
            public void onResponse(Call<List<NotifySoundContainer>> call, Response<List<NotifySoundContainer>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.setOnLoadNotifySoundListener(response.body());
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<NotifySoundContainer>> call, Throwable t) {
                Log.d("rrrrgetNotifySound", t.getMessage());
            }
        });
    }

    static public void readNotification(OnLoadNotificationsListener listener, List<String> notifications) {
        ApiService.apiService.readNotification(notifications).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    listener.updateNotificationRead(notifications);
                } else {

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("rrrrreadNotification", t.getMessage());
            }
        });
    }

    static public void removeNotification(OnLoadNotificationsListener listener, List<String> notifications) {
//        if (!isRead) {
//            ApiService.apiService.readNotification(notifications).enqueue(new Callback<Void>() {
//                @Override
//                public void onResponse(Call<Void> call, Response<Void> response) {
//                    if (response.isSuccessful()) {
//                        ApiService.apiService.removeNotification(notifications).enqueue(new Callback<Void>() {
//                            @Override
//                            public void onResponse(Call<Void> call, Response<Void> response) {
//                                if (response.isSuccessful()) {
//                                    listener.updateNotificationRemove(notifications);
//                                } else {
//
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<Void> call, Throwable t) {
//                                Log.d("rrrrremoveNotification", t.getMessage());
//                            }
//                        });
//                    } else {
//
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<Void> call, Throwable t) {
//                    Log.d("rrrrremoveNotification", t.getMessage());
//                }
//            });
//        }
        ApiService.apiService.removeNotification(notifications).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    listener.updateNotificationRemove(notifications);
                } else {

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("rrrrremoveNotification", t.getMessage());
            }
        });
    }

    static public void readNotifications(OnLoadNotificationsListener listener, List<String> userIds) {
        ApiService.apiService.readNotifications(userIds).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    listener.updateNotificationRead(null);
                } else {

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("rrrrreadNotifications", t.getMessage());
            }
        });

    }

    static public void getEmployees(EmployeeParam param, OnLoadEmployeeListener listener) {

//        Call<EmployeeContainer> call = ApiService.apiService.getEmployees(param.page, param.search, param.isMore);
        ApiService.apiService.getEmployees(param.page, param.search, param.isMore).enqueue(new Callback<EmployeeContainer>() {
            @Override
            public void onResponse(Call<EmployeeContainer> call, Response<EmployeeContainer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    EmployeeContainer container = response.body();
                    listener.setOnLoadEmployeeListener(container);
//                    List<Em> employees = container.data.items;
//                    int len = employees.size();
//                    final int size = container.data.size;
//                    final int page = container.data.page;
//                    for (int i = 0; i < len; i++) {
//                        if (employees.get(i).image != null && !employees.get(i).image.equals("")) {
//                            getImage(employees.get(i).image, i + size * (page - 1), listener);
//                        }
//                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<EmployeeContainer> call, Throwable t) {
                Log.d("rrrrgetEmployees", t.getMessage());

            }
        });
//        call.cancel();
    }

    static public void getOrganizationChart(OnLoadItemOrganizationListener listener) {
        ApiService.apiService.getOrganizationChart().enqueue(new Callback<OrganizationChart>() {
            @Override
            public void onResponse(Call<OrganizationChart> call, Response<OrganizationChart> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.setOnLoadFirstOrganizationListener(response.body());
                }
            }

            @Override
            public void onFailure(Call<OrganizationChart> call, Throwable t) {
                Log.d("rrr", t.getMessage());
            }
        });
    }
}
