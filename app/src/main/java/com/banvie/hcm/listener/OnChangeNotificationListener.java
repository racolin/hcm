package com.banvie.hcm.listener;

import java.util.List;

public interface OnChangeNotificationListener {
    void removeNotification(List<String> ids);
    void readNotification(List<String> ids);
    void readNotifications();

    void onRemoveNotification(List<String> ids);
    void onReadNotification(List<String> ids);
    void onReadNotifications();
}
