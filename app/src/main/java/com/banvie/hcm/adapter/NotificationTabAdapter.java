package com.banvie.hcm.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.banvie.hcm.api.ApiService;
import com.banvie.hcm.api.Constant;
import com.banvie.hcm.api.RetrofitApi;
import com.banvie.hcm.fragment.ListNotificationFragment;
import com.banvie.hcm.listener.OnChangeNotificationListener;
import com.banvie.hcm.listener.OnLoadNotificationsNumberListener;
import com.banvie.hcm.model.notification.Notification;
import com.banvie.hcm.param.NotificationParam;

import java.util.Arrays;
import java.util.List;

public class NotificationTabAdapter extends FragmentStateAdapter
        implements OnChangeNotificationListener {

    OnLoadNotificationsNumberListener listener;
    NotificationParam param;
    Context context;
    ListNotificationFragment request, all;

    public NotificationTabAdapter(@NonNull Fragment fragment, OnLoadNotificationsNumberListener listener, NotificationParam param) {
        super(fragment);
        this.param = param;
        this.listener = listener;
        context = fragment.getContext();
        all = new ListNotificationFragment(context, this, listener,
                new NotificationParam(param.userId, 0, param.statusRead, param.pageNumber, param.pageSize));
        request = new ListNotificationFragment(context, this, listener,
                new NotificationParam(param.userId, 1, param.statusRead, param.pageNumber, param.pageSize));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return all;
            case 1:
                return request;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public void removeNotification(List<String> ids) {
        RetrofitApi.removeNotification(this, ids);
    }

    @Override
    public void readNotification(List<String> ids) {
        RetrofitApi.readNotification(this, ids);
    }

    @Override
    public void readNotifications() {
        RetrofitApi.readNotifications(this, Arrays.asList(param.userId));
    }

    @Override
    public void onRemoveNotification(List<String> ids) {
        request.removeNotification(ids);
        all.removeNotification(ids);
        updateNumberOfNotification();
    }

    @Override
    public void onReadNotification(List<String> ids) {
        request.readNotification(ids);
        all.readNotification(ids);
        updateNumberOfNotification();
    }

    @Override
    public void onReadNotifications() {
        request.readNotifications();
        all.readNotifications();
        updateNumberOfNotification();
    }

    private void updateNumberOfNotification() {
        RetrofitApi.getNotifications(
                new NotificationParam(Constant.userInformation.userId,
                        0, null, 0, 15), listener);
    }
}
