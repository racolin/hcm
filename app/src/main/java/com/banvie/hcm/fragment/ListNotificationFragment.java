package com.banvie.hcm.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.banvie.hcm.R;
import com.banvie.hcm.adapter.NotificationAdapter;
import com.banvie.hcm.api.RetrofitApi;
import com.banvie.hcm.listener.OnChangeNotificationListener;
import com.banvie.hcm.listener.OnLoadNotificationsListener;
import com.banvie.hcm.listener.OnLoadNotificationsNumberListener;
import com.banvie.hcm.model.notification.Notification;
import com.banvie.hcm.model.notification.NotificationContainer;
import com.banvie.hcm.param.NotificationParam;

import java.util.ArrayList;
import java.util.List;

public class ListNotificationFragment extends Fragment
        implements OnLoadNotificationsListener {

    private RecyclerView rv;
    private NotificationAdapter adapter;
    private OnLoadNotificationsNumberListener listener;
    private Button btn_mark;
    private boolean loading;
    NotificationParam param;
    OnChangeNotificationListener option;

    boolean hasNext;

    public ListNotificationFragment() {
        adapter = new NotificationAdapter(getContext(), null, new ArrayList<>());
    }

    public ListNotificationFragment(Context context, OnChangeNotificationListener option, OnLoadNotificationsNumberListener listener, NotificationParam param) {
        this.listener = listener;
        this.param = param;
        this.option = option;
//        context ???
        adapter = new NotificationAdapter(context, option, new ArrayList<>());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loading = true;
        hasNext = true;
        loadNotificationListener();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_notifications, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUI(view);
        initListener();
        super.onViewCreated(view, savedInstanceState);
    }

    private void initUI(View view) {
        btn_mark = view.findViewById(R.id.btn_mark);

        if (param.requestType == 0) {
            view.findViewById(R.id.btn_request).setVisibility(View.GONE);
        }

        rv = view.findViewById(R.id.rv);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initListener() {

        btn_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                option.readNotifications();
            }
        });

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (! recyclerView.canScrollVertically(1)){
                    loadNotificationListener();
                }
            }
        });
    }

    public void removeNotification(List<String> ids) {
        for (String id : ids) {
            Notification n = new Notification();
            n.notificationId = id;
            int i = adapter.notifications.indexOf(n);
            if (i != -1) {
                adapter.notifications.remove(i);
                adapter.notifyItemRemoved(i);
            }
        }
    }

    public void readNotification(List<String> ids) {
        if (param.statusRead.equals("false")) {
            removeNotification(ids);
            return;
        }
        for (String id : ids) {
            Notification n = new Notification();
            n.notificationId = id;
            int i = adapter.notifications.indexOf(n);
            if (i != -1 && adapter.notifications.get(i).read == false) {
                adapter.notifications.get(i).read = true;
                adapter.notifyItemChanged(i);
            }
        }
    }

    public void readNotifications() {
        int len = adapter.notifications.size();
        for (int i = 0; i < len; i++) {
            if (adapter.notifications.get(i).read == false) {
                adapter.notifications.get(i).read = true;
                adapter.notifyItemChanged(i);
            }
        }
    }

    @Override
    public void setOnLoadNotificationsListener(NotificationContainer container) {
        param.pageNumber = container.data.data.page;
        hasNext = container.data.data.hasNext;
        List<Notification> ns = container.data.data.items;

        int i = adapter.notifications.size();
        int c = ns.size();
        adapter.notifications.addAll(ns);

        listener.setOnNotificationsNumberListener(container.unReadCount);

        adapter.notifyItemRangeInserted(i, c);

        loading = false;
    }

    @Override
    public void loadNotificationListener() {
        if (hasNext) {
            RetrofitApi.getNotifications(param, this);
        }
    }
}
