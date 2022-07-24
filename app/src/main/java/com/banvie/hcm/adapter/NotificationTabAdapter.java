package com.banvie.hcm.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.banvie.hcm.fragment.ListNotificationFragment;
import com.banvie.hcm.listener.OnLoadNotificationsListener;
import com.banvie.hcm.listener.OnLoadNotificationsNumberListener;
import com.banvie.hcm.model.notification.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationTabAdapter extends FragmentStateAdapter {

    List<Notification> notifications, requestNotifications;
    ListNotificationFragment all, request;
    OnLoadNotificationsNumberListener listener;

    public NotificationTabAdapter(@NonNull Fragment fragment, OnLoadNotificationsListener loadListener, OnLoadNotificationsNumberListener listener, List<Notification> notifications) {
        super(fragment);
        this.notifications = notifications;
        requestNotifications = getRequestNotifications();
        all = new ListNotificationFragment(fragment.getContext(), loadListener, notifications, false);
        request = new ListNotificationFragment(fragment.getContext(), loadListener, requestNotifications, true);
        this.listener = listener;
    }

    public void setNotifications(List<Notification> ns) {
        notifications = ns;
//        notifications.clear();
//        notifications.addAll(ns);
        requestNotifications = getRequestNotifications();
        all.setNotifications(notifications);
        request.setNotifications(requestNotifications);
    }

    public void removeNotification(int i) {
        all.removeNotification(i);
        if (i == -1) {
            request.setNotifications(new ArrayList<>());
        } else {
            int j = requestNotifications.indexOf(notifications.get(i));
            if (j != -1) {
                request.removeNotification(j);
            }
        }
    }

    public void updateNotification(int i) {
        all.updateNotification(i);
        if (i == -1) {
//            requestNotifications.clear();
//            requestNotifications.addAll(getRequestNotifications());
//            request.updateNotification(i);
            request.setNotifications(getRequestNotifications());
        } else {
            int j = requestNotifications.indexOf(notifications.get(i));
            if (j != -1) {
                request.updateNotification(j);
            }
        }
    }

    private List<Notification> getRequestNotifications() {
        List<Notification> notifications = new ArrayList<>();
        for (Notification notification : this.notifications) {
            if (notification.type == 6 || notification.type == 8) {
                notifications.add(notification);
            }
        }
        return notifications;
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
}
