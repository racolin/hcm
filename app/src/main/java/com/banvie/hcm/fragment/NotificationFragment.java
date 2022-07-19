package com.banvie.hcm.fragment;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.banvie.hcm.R;
import com.banvie.hcm.adapter.NotificationTabAdapter;
import com.banvie.hcm.api.Constant;
import com.banvie.hcm.api.RetrofitApi;
import com.banvie.hcm.dialog.IgnoreNotificationDialog;
import com.banvie.hcm.listener.OnLoadNotificationsListener;
import com.banvie.hcm.listener.OnLoadNotificationsNumberListener;
import com.banvie.hcm.model.notification.Notification;
import com.banvie.hcm.model.notification.NotificationContainer;
import com.banvie.hcm.param.NotificationParam;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment implements OnLoadNotificationsListener {

    List<Notification> notifications, unreadNotifications;
    OnLoadNotificationsNumberListener listener;
    NotificationTabAdapter adapter;
    Button btn_all, btn_unread;
    ImageButton ibt_ignore_notify;

    public NotificationFragment() {
        notifications = new ArrayList<>();
        unreadNotifications = new ArrayList<>();
    }

    public NotificationFragment(List<Notification> notifications, OnLoadNotificationsNumberListener listener) {
        this.listener = listener;
        this.notifications = notifications;
        unreadNotifications = getUnreadNotifications();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notification, container, false);

        TabLayout tab = v.findViewById(R.id.tab);

        ViewPager2 vp2_notifications = v.findViewById(R.id.vp2_notifications);

        adapter = new NotificationTabAdapter(this, listener, notifications);

        vp2_notifications.setAdapter(adapter);

        vp2_notifications.setSaveEnabled(false);

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

        ibt_ignore_notify = v.findViewById(R.id.ibt_ignore_notify);

        btn_all = v.findViewById(R.id.btn_all);

        btn_unread = v.findViewById(R.id.btn_unread);


        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter = new NotificationTabAdapter(NotificationFragment.this, listener, notifications);

                vp2_notifications.setAdapter(adapter);

                btn_unread.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(R.color.background_notify)));
                btn_unread.setTextColor(getContext().getColor(R.color.black));

                btn_all.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(R.color.blue)));
                btn_all.setTextColor(getContext().getColor(R.color.white));
            }
        });

        btn_unread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter = new NotificationTabAdapter(NotificationFragment.this, listener, unreadNotifications);

                vp2_notifications.setAdapter(adapter);

                btn_unread.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(R.color.blue)));
                btn_unread.setTextColor(getContext().getColor(R.color.white));

                btn_all.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(R.color.background_notify)));
                btn_all.setTextColor(getContext().getColor(R.color.black));
            }
        });

        ibt_ignore_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new IgnoreNotificationDialog(getContext());
                dialog.show();
            }
        });

        RetrofitApi.getNotifications(new NotificationParam(
                Constant.userInformation.getUserId(),
                0, null, 0, 15), this);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

    @Override
    public void setOnLoadImageListener(byte[] image, int i) {
        notifications.get(i).setImage_bytes(image);
        adapter.updateNotification(i);
    }

    @Override
    public void setOnLoadNotificationsListener(NotificationContainer container) {
        List<Notification> ns = container.getData().getData().getItems();
        this.notifications.clear();
        this.notifications.addAll(ns);
        this.unreadNotifications.clear();
        for (Notification n : ns) {
            if (!n.isRead()) {
                unreadNotifications.add(n);
            }
        }
        listener.setOnNotificationsNumberListener(container.getUnReadCount());
        adapter.updateNotification(-1);
    }
}
