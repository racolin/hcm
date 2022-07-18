package com.banvie.hcm.listener;

import com.banvie.hcm.model.notification.Notification;
import com.banvie.hcm.model.notification.NotificationContainer;

import java.util.List;

public interface OnLoadNotificationsListener extends OnLoadImageListener{
    void setOnLoadNotificationsListener(NotificationContainer container);
}
