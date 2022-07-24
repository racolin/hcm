package com.banvie.hcm.api;

import com.banvie.hcm.config.Environment;
import com.banvie.hcm.model.RefreshToken;
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
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    Moshi moshi = new Moshi.Builder().build();

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new CustomInterceptor()) // This is used to add ApplicationInterceptor.
//            .addNetworkInterceptor(new CustomInterceptor())
            .authenticator(new CustomAuthenticator())
            .build();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl(Environment.environment)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiService.class);

    @POST("accountapp/v1.0/auth")
    Call<Token> login(@Body UserParam param);

    @GET("mytimeapp/v1.0/policies")
    Call<PolicyContainer> getPolicy();

    @GET(Environment.environment + "fileapp/store/file/get")
    Call<ResponseBody> getImage(@Query("subPath") String subPath);

    @POST("scheduleapp/v1.0/notification-mobile/list")
    Call<NotificationContainer> getNotifications(@Body NotificationParam param);

    @GET("mytimeapp/v1.0/status-check-in-out")
    Call<CheckOutContainer> checkOut();

    @POST("accountapp/v1.0/auth/refresh")
    Call<Token> refreshToken(@Body RefreshToken refreshToken);

    @PUT("scheduleapp/v1.0/notification-mobile")
    Call<Void> readNotification(@Body List<String> notificationIds);

    @HTTP(method = "DELETE", path = "scheduleapp/v1.0/notification-mobile", hasBody = true)
    Call<Void> removeNotification(@Body List<String> notificationIds);

    @PUT("scheduleapp/v1.0/notification-mobile/users")
    Call<Void> readNotifications(@Body List<String> userIds);

    @GET("accountapp/v1.0/employees/{userId}")
    Call<SummaryContainer> getUserSummary(@Path("userId") String userId);

    @GET("accountapp/v1.0/info/employees/duration/{userId}")
    Call<EmployeeDurationContainer> getEmployeeDuration(@Path("userId") String userId);

    @GET("accountapp/v1.0/info/employees/education/{userId}")
    Call<EducationContainer> getEducation(@Path("userId") String userId);

    @GET("accountapp/v1.0/info/employees/duration/{userId}")
    Call<ShuiContainer> getShui(@Path("userId") String userId);

    @GET("accountapp/v1.0/info/employees/individual/{userId}")
    Call<IndividualContainer> getIndividual(@Path("userId") String userId);

    @GET("scheduleapp/v1.0/notify/list-setting")
    Call<List<NotifySoundContainer>> getNotifySound();

    @GET("accountapp/v1.0/employees")
    Call<EmployeeContainer> getEmployees(@Query("page") int page, @Query("search") String search, @Query("isMore") boolean isMore);

    @GET("accountapp/v1.0/org-chart")
    Call<OrganizationChart> getOrganizationChart();

    @GET("accountapp/v1.0/org-chart/{userId}")
    Call<OrganizationChart> getOrganizationChart(@Path("userId") String userId);
}
