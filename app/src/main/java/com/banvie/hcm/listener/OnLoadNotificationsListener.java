package com.banvie.hcm.listener;

import com.banvie.hcm.model.notification.Notification;

import java.util.List;

public interface OnLoadNotificationsListener extends OnLoadImageListener{
    void setOnLoadNotificationsListener(List<Notification> notifications);
}
