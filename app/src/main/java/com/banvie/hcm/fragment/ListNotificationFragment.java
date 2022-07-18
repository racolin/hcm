package com.banvie.hcm.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.banvie.hcm.R;
import com.banvie.hcm.adapter.NotificationAdapter;
import com.banvie.hcm.model.notification.Notification;

import java.util.ArrayList;
import java.util.List;

public class ListNotificationFragment extends Fragment {

    RecyclerView rv;
    NotificationAdapter adapter;
    List<Notification> notifications;

    public ListNotificationFragment() {
        notifications = new ArrayList<>();
    }

    public ListNotificationFragment(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public void updateNotification(int i) {
        if (i == -1) {
            adapter.notifyDataSetChanged();
        } else {
            adapter.notifyItemChanged(i);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view, container, false);
        adapter = new NotificationAdapter(getContext(), notifications);
        rv = view.findViewById(R.id.rv);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}
