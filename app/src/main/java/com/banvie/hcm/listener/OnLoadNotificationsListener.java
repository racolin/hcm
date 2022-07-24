package com.banvie.hcm.listener;

import com.banvie.hcm.model.notification.Notification;
import com.banvie.hcm.model.notification.NotificationContainer;

import java.util.List;

public interface OnLoadNotificationsListener extends OnLoadImageListener, OnLoadMoreNotificationListener{
    void setOnLoadNotificationsListener(NotificationContainer container);
    void setOnReadNotificationListener(Notification notification);
    void setOnRemoveNotificationListener(Notification notification);
    void updateNotificationRead(List<String> notification);
    void updateNotificationRemove(List<String> notification);
    void setOnReadNotificationsListener();
}
