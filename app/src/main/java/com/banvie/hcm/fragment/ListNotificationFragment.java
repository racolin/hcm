package com.banvie.hcm.fragment;

import android.content.Context;
import android.os.Bundle;
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
import com.banvie.hcm.listener.OnLoadNotificationsListener;
import com.banvie.hcm.model.notification.Notification;
import com.banvie.hcm.type.ReloadMode;

import java.util.ArrayList;
import java.util.List;

public class ListNotificationFragment extends Fragment {

    private List<Notification> notifications;
    private boolean showRequest;

    private RecyclerView rv;
    private NotificationAdapter adapter;
    private OnLoadNotificationsListener listener;
    private Button btn_mark;

    public ListNotificationFragment() {
        notifications = new ArrayList<>();
        adapter = new NotificationAdapter(getContext(), null, notifications);
    }

    public ListNotificationFragment(Context context, OnLoadNotificationsListener listener, List<Notification> notifications, boolean showRequest) {
        this.notifications = notifications;
        this.listener = listener;
        adapter = new NotificationAdapter(context, listener, notifications);
        this.showRequest = showRequest;
    }

    public void updateNotification(int i) {
        if (i == -1) {
            adapter.ntfs_loaded.clear();
            fit();
            adapter.notifyDataSetChanged();
        } else {
            adapter.notifyItemChanged(i);
        }
    }

    public void removeNotification(int i) {
        if (i == -1) {
            adapter.ntfs_loaded.clear();
            fit();
//            adapter.
        } else {
            adapter.ntfs_loaded.remove(i);
            notifications.remove(i);
            adapter.notifyItemRemoved(i);
        }
    }

    public void setNotifications(List<Notification> ns) {
        notifications = ns;
        adapter.notifications = notifications;
        adapter.ntfs_loaded.clear();
        fit();
        adapter.notifyDataSetChanged();
    }

    private void fit() {
        int l_1 = adapter.notifications.size();
        int l_2 = adapter.ntfs_loaded.size();
        if (l_1 > l_2) {
            for (int i = 0; i < l_1- l_2; i++) {
                adapter.ntfs_loaded.add(ReloadMode.INITIAL);
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_notifications, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initIU(view);
        initListener();
        super.onViewCreated(view, savedInstanceState);
    }

    private void initIU(View view) {
        btn_mark = view.findViewById(R.id.btn_mark);

        if (!showRequest) {
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
                listener.setOnReadNotificationsListener();
            }
        });

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (! recyclerView.canScrollVertically(1)){
                    loadMore();
                }
            }
        });
    }

    private void loadMore() {
        listener.loadNotificationListener();
    }
}
