package com.banvie.hcm.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.banvie.hcm.fragment.ListNotificationFragment;
import com.banvie.hcm.listener.OnLoadNotificationsNumberListener;
import com.banvie.hcm.model.notification.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationTabAdapter extends FragmentStateAdapter {

    List<Notification> notifications, requestNotifications;
    ListNotificationFragment all, request;
    OnLoadNotificationsNumberListener listener;

    public NotificationTabAdapter(@NonNull Fragment fragment, OnLoadNotificationsNumberListener listener, List<Notification> notifications) {
        super(fragment);
        this.notifications = notifications;
        requestNotifications = getRequestNotifications();
        all = new ListNotificationFragment(fragment.getContext(), notifications, false);
        request = new ListNotificationFragment(fragment.getContext(), requestNotifications, true);
        this.listener = listener;
    }

    private List<Notification> getRequestNotifications() {
        List<Notification> notifications = new ArrayList<>();
        for (Notification notification : notifications) {
            if (!notification.isRead()) {
                notifications.add(notification);
            }
        }
        return notifications;
    }

    public void updateNotification(int i) {
        all.updateNotification(i);
        if (i == -1) {
            request.updateNotification(i);
        } else {
            int j = requestNotifications.indexOf(notifications.get(i));
            if (j != -1) {
                request.updateNotification(j);
            }
        }
    }

    public void setImageNotification(byte[] image, int i) {
        notifications.get(i).setImage_bytes(image);
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
}
