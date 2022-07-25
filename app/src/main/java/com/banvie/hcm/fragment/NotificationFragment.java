package com.banvie.hcm.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.banvie.hcm.NotificationAndSoundActivity;
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
import java.util.Arrays;
import java.util.List;

public class NotificationFragment extends Fragment
        implements OnLoadNotificationsListener {

    List<Notification> notifications, unreadNotifications;
    NotificationTabAdapter adapter;
    Button btn_all, btn_unread;
    ImageButton ibt_ignore_notify, ibt_setting_notify;
    ViewPager2 vp2_notifications;

    OnLoadNotificationsNumberListener listener;

    int page, totalPage, size, total;
    boolean hasNext;

    public NotificationFragment() {
    }

    public NotificationFragment(OnLoadNotificationsNumberListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hasNext = true;
        page = 0;
        size = 15;
        total = 0;
        notifications = new ArrayList<>();
        unreadNotifications = new ArrayList<>();
        loadNotificationListener();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notification, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUI(view);
        initListener();
        super.onViewCreated(view, savedInstanceState);
    }

    private void initUI(View v) {

        TabLayout tab = v.findViewById(R.id.tab);
        vp2_notifications = v.findViewById(R.id.vp2_notifications);
        adapter = new NotificationTabAdapter(this, this, listener, notifications);
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
        vp2_notifications.setSaveEnabled(false);

        ibt_ignore_notify = v.findViewById(R.id.ibt_ignore_notify);
        ibt_setting_notify = v.findViewById(R.id.ibt_setting_notify);

        btn_all = v.findViewById(R.id.btn_all);
        btn_unread = v.findViewById(R.id.btn_unread);
    }

    private void initListener() {

        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setNotifications(notifications);
                setAllOn();
                setUnreadOff();
            }
        });

        btn_unread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setNotifications(unreadNotifications);
                setAllOff();
                setUnreadOn();
            }
        });

        ibt_ignore_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new IgnoreNotificationDialog(getContext());
                dialog.show();
            }
        });

        ibt_setting_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NotificationAndSoundActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setAllOn() {
        btn_all.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(R.color.blue)));
        btn_all.setTextColor(getContext().getColor(R.color.white));
    }

    private void setUnreadOff() {
        btn_unread.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(R.color.background_notify)));
        btn_unread.setTextColor(getContext().getColor(R.color.black));
    }

    private void setUnreadOn() {
        btn_unread.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(R.color.blue)));
        btn_unread.setTextColor(getContext().getColor(R.color.white));
    }

    private void setAllOff() {
        btn_all.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(R.color.background_notify)));
        btn_all.setTextColor(getContext().getColor(R.color.black));
    }

    private List<Notification> getUnreadNotifications() {
        List<Notification> ns = new ArrayList<>();
        for (Notification notification : notifications) {
            if (!notification.read) {
                ns.add(notification);
            }
        }
        return ns;
    }

    @Override
    public void setOnLoadImageListener(byte[] image, int i) {
        notifications.get(i).image_bytes = image;

//      Lần load đầu tiên có thể là adapter chưa được khởi tạo
//      Nếu chưa được khởi tọa thì không thể cập nhật notification
        if (adapter != null) {
            adapter.updateNotification(i);
        }
    }

    @Override
    public void updateNotificationRead(List<String> ids) {
        if (ids == null) {
            int len = notifications.size();
            for (int i = 0; i < len; i++) {
                if (!notifications.get(i).read) {
                    notifications.get(i).read = true;
                    adapter.updateNotification(i);
                }
            }
        } else {
            for (String id : ids) {
                Notification n = new Notification();
                n.notificationId = id;
                int k = notifications.indexOf(n);
                if (k != -1) {
                    notifications.get(k).read = true;
                    adapter.updateNotification(k);
                }
            }
        }

        RetrofitApi.getNotifications(new NotificationParam(
                Constant.userInformation.userId,
                0, null, 0, 15), listener);
    }

    @Override
    public void updateNotificationRemove(List<String> ids) {
        if (ids != null) {
            for (String id : ids) {
                Notification n = new Notification();
                n.notificationId = id;
                int k = notifications.indexOf(n);
                if (k != -1) {
                    adapter.removeNotification(k);
                }
            }
        }

        RetrofitApi.getNotifications(new NotificationParam(
                Constant.userInformation.userId,
                0, null, 0, 15),  listener);
    }

    @Override
    public void setOnLoadNotificationsListener(NotificationContainer container) {

        page = container.data.data.page;
        totalPage = container.data.data.totalPages;
        size = container.data.data.size;
        total = container.data.data.totalElements;
        hasNext = container.data.data.hasNext;
        List<Notification> ns = container.data.data.items;

        this.notifications.addAll(ns);
        for (Notification n : ns) {
            if (!n.read) {
                unreadNotifications.add(n);
            }
        }

        listener.setOnNotificationsNumberListener(container.unReadCount);

//        load -1 là load tất cả trong khi chỉ cần load từ 1 đoạn mà thôi
        adapter.updateNotification(-1);
    }

    @Override
    public void setOnReadNotificationListener(Notification notification) {
        RetrofitApi.readNotification(this, Arrays.asList(notification.notificationId));
    }

    @Override
    public void setOnReadNotificationsListener() {
        RetrofitApi.readNotifications(this, Arrays.asList(Constant.userInformation.userId));
    }

    @Override
    public void setOnRemoveNotificationListener(Notification notification) {
        RetrofitApi.removeNotification(this, Arrays.asList(notification.notificationId));
    }

    @Override
    public void loadNotificationListener() {
        if (hasNext) {
            RetrofitApi.getNotifications(new NotificationParam(
                    Constant.userInformation.userId,
                    0, null, page, size), this);
        }
    }
}
