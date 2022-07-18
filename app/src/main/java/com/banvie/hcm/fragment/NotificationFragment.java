package com.banvie.hcm.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.banvie.hcm.R;
import com.banvie.hcm.adapter.NotificationTabAdapter;
import com.banvie.hcm.api.Constant;
import com.banvie.hcm.api.RetrofitApi;
import com.banvie.hcm.listener.OnLoadNotificationsListener;
import com.banvie.hcm.model.notification.Notification;
import com.banvie.hcm.param.NotificationParam;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    List<Notification> notifications, unreadNotifications;
    OnLoadNotificationsListener listener;
    NotificationTabAdapter adapter;
    Button btn_all, btn_unread;

    public NotificationFragment() {
        notifications = new ArrayList<>();
        unreadNotifications = new ArrayList<>();
    }

    public NotificationFragment(List<Notification> notifications, OnLoadNotificationsListener listener) {
        this.listener = listener;
        this.notifications = notifications;
        unreadNotifications = getUnreadNotifications();
    }

    public void updateNotification(int i) {
        adapter.updateNotification(-1);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notification, container, false);

        TabLayout tab = v.findViewById(R.id.tab);

        ViewPager2 vp2_notifications = v.findViewById(R.id.vp2_notifications);

        adapter = new NotificationTabAdapter(this, notifications);

        unreadNotifications = getUnreadNotifications();

        vp2_notifications.setAdapter(adapter);

        new TabLayoutMediator(tab, vp2_notifications, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText(R.string.new_notify);
                        break;
                    case 1:
                        tab.setText(R.string.request_notify);
                        break;
                }
            }
        }).attach();

        btn_all = v.findViewById(R.id.btn_all);
        btn_unread = v.findViewById(R.id.btn_unread);

        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setNotifications(unreadNotifications);
            }
        });

        btn_unread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setNotifications(notifications);
            }
        });

        return v;
    }

    private List<Notification> getUnreadNotifications() {
        List<Notification> ns = new ArrayList<>();
        for (Notification notification : notifications) {
            if (!notification.isRead()) {
                ns.add(notification);
            }
        }
        return ns;
    }
}
