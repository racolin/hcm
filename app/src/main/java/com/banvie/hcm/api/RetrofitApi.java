package com.banvie.hcm.api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.banvie.hcm.listener.OnChangeNotificationListener;
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
import com.banvie.hcm.model.employee.Employee;
import com.banvie.hcm.model.employee.EmployeeContainer;
import com.banvie.hcm.model.employee_duration.EmployeeDurationContainer;
import com.banvie.hcm.model.individual.IndividualContainer;
import com.banvie.hcm.model.notification.NotificationContainer;
import com.banvie.hcm.model.notification_sound.NotifySoundContainer;
import com.banvie.hcm.model.organization_chart.OrganizationChart;
import com.banvie.hcm.model.shui.ShuiContainer;
import com.banvie.hcm.model.summary.SummaryContainer;
import com.banvie.hcm.param.EmployeeParam;
import com.banvie.hcm.param.NotificationParam;
import com.banvie.hcm.param.PolicyParam;
import com.banvie.hcm.param.UserParam;
import com.banvie.hcm.model.policy.Policy;
import com.banvie.hcm.model.policy.PolicyContainer;

import java.io.ByteArrayOutputStream;
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
                Log.d("debug:login", t.getMessage());
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
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<PolicyContainer> call, Throwable t) {
                Log.d("debug:callNotices", t.getMessage());
            }
        });
    }

    static public void callNotices(PolicyParam param, OnLoadedNoticesListener listener) {
        ApiService.apiService.getPolicy(param).enqueue(new Callback<PolicyContainer>() {
            @Override
            public void onResponse(Call<PolicyContainer> call, Response<PolicyContainer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Policy> policies = response.body().data.items;
                    listener.setOnLoadedNotices(policies);
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<PolicyContainer> call, Throwable t) {
                Log.d("debug:callNotices", t.getMessage());
            }
        });
    }

    static public void getNotifications(NotificationParam param, OnLoadNotificationsListener listener) {
        ApiService.apiService.getNotifications(param).enqueue(new Callback<NotificationContainer>() {
            @Override
            public void onResponse(Call<NotificationContainer> call, Response<NotificationContainer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.setOnLoadNotificationsListener(response.body());
                } else {

                }
            }

            @Override
            public void onFailure(Call<NotificationContainer> call, Throwable t) {
                Log.d("debug:getNotifications", t.getMessage());
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
                            if (bitmap != null) {
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, (int) ((5000.0 /  len) * 100), bos);
                                image = bos.toByteArray();
                            } else {
                                listener.setOnLoadImageListener(image, i);
                                return;
                            }
                        }
                        listener.setOnLoadImageListener(image, i);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("debug:getImage", t.getMessage());
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
                Log.d("debug:getNotifications", t.getMessage());
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
                        Log.d("debug:getUserSummary", t.getMessage());
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
                        Log.d("debug:getEmployeeDuration", t.getMessage());
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
                        Log.d("debug:getEducation", t.getMessage());
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
                        Log.d("debug:getShui", t.getMessage());
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
                        Log.d("debug:getIndividual", t.getMessage());
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
                Log.d("debug:getNotifySound", t.getMessage());
            }
        });
    }

    static public void readNotification(OnChangeNotificationListener listener, List<String> notifications) {
        ApiService.apiService.readNotification(notifications).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    listener.onReadNotification(notifications);
                } else {

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("debug:readNotification", t.getMessage());
            }
        });
    }

    static public void removeNotification(OnChangeNotificationListener listener, List<String> notifications) {
        ApiService.apiService.removeNotification(notifications).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    listener.onRemoveNotification(notifications);
                } else {

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("debug:removeNotification", t.getMessage());
            }
        });
    }

    static public void readNotifications(OnChangeNotificationListener listener, List<String> userIds) {
        ApiService.apiService.readNotifications(userIds).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    listener.onReadNotifications();
                } else {

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("debug:readNotifications", t.getMessage());
            }
        });

    }

    static public void getEmployees(EmployeeParam param, OnLoadEmployeeListener listener) {
        ApiService.apiService.getEmployees(param.page, param.search, param.isMore).enqueue(new Callback<EmployeeContainer<Employee>>() {
            @Override
            public void onResponse(Call<EmployeeContainer<Employee>> call, Response<EmployeeContainer<Employee>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    EmployeeContainer container = response.body();
                    listener.setOnLoadEmployeeListener(container);
                } else {

                }
            }

            @Override
            public void onFailure(Call<EmployeeContainer<Employee>> call, Throwable t) {
                Log.d("debug:getEmployees", t.getMessage());

            }
        });
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
