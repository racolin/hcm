package com.banvie.hcm.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.banvie.hcm.fragment.ListNotificationFragment;
import com.banvie.hcm.model.notification.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationTabAdapter extends FragmentStateAdapter {

    List<Notification> notifications, unreadNotifications;
    ListNotificationFragment all, unread;

    public NotificationTabAdapter(@NonNull Fragment fragment, List<Notification> notifications) {
        super(fragment);
        this.notifications = notifications;
        unreadNotifications = getUnreadNotifications();
        all = new ListNotificationFragment(notifications);
        unread = new ListNotificationFragment(unreadNotifications);
    }

    private List<Notification> getUnreadNotifications() {
        List<Notification> notifications = new ArrayList<>();
        for (Notification notification : notifications) {
            if (!notification.isRead()) {
                notifications.add(notification);
            }
        }
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
        notifyDataSetChanged();
    }

    public void updateNotification(int i) {
        all.updateNotification(i);
        if (i == -1) {
            unread.updateNotification(i);
        } else {
            int j = unreadNotifications.indexOf(notifications.get(i));
            if (j != -1) {
                unread.updateNotification(j);
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
                return unread;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
