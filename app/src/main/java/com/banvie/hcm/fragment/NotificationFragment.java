package com.banvie.hcm.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
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
import com.banvie.hcm.dialog.IgnoreNotificationDialog;
import com.banvie.hcm.listener.OnLoadNotificationsNumberListener;
import com.banvie.hcm.param.NotificationParam;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class NotificationFragment extends Fragment {

    NotificationTabAdapter adapter;
    Button btn_all, btn_unread;
    ImageButton ibt_ignore_notify, ibt_setting_notify;
    ViewPager2 vp2_notifications;

    OnLoadNotificationsNumberListener listener;
    NotificationParam paramAll = new NotificationParam(Constant.userInformation.userId, 0, "true", 0,15);
    NotificationParam paramUnread = new NotificationParam(Constant.userInformation.userId, 0, "false", 0,15);

    public NotificationFragment() {
    }

    public NotificationFragment(OnLoadNotificationsNumberListener listener) {
        this.listener = listener;
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
        adapter = new NotificationTabAdapter(this, listener, paramAll);
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
                setOff(btn_unread);
                setOn(btn_all);
                adapter = new NotificationTabAdapter(NotificationFragment.this, listener, paramAll);
                vp2_notifications.setAdapter(adapter);
            }
        });

        btn_unread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOff(btn_all);
                setOn(btn_unread);
                adapter = new NotificationTabAdapter(NotificationFragment.this, listener, paramUnread);
                vp2_notifications.setAdapter(adapter);
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

    private void setOn(Button btn) {
        btn.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(R.color.blue)));
        btn.setTextColor(getContext().getColor(R.color.white));
    }

    private void setOff(Button btn) {
        btn.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(R.color.background_notify)));
        btn.setTextColor(getContext().getColor(R.color.black));
    }
}
