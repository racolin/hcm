package com.banvie.hcm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.banvie.hcm.R;
import com.banvie.hcm.Support;
import com.banvie.hcm.model.notification_sound.NotifySoundContainer;

import java.util.List;

public class NotifySoundAdapter extends RecyclerView.Adapter<NotifySoundAdapter.NotifySoundHolder> {

    List<NotifySoundContainer> containers;
    Context context;

    public NotifySoundAdapter(Context context, List<NotifySoundContainer> containers) {
        this.context = context;
        this.containers = containers;
    }

    @NonNull
    @Override
    public NotifySoundHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotifySoundHolder(LayoutInflater.from(context)
                .inflate(R.layout.notification_sound_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotifySoundHolder holder, int position) {
        holder.tv_title.setText(Support.convertAllUpper(containers.get(position).moduleName));
        holder.rv_items.setAdapter(new NotifySoundItemAdapter(context, containers.get(position).listNotifiSetting));
        holder.rv_items.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public int getItemCount() {
        return containers.size();
    }

    public class NotifySoundHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        RecyclerView rv_items;
        public NotifySoundHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            rv_items = itemView.findViewById(R.id.rv_items);
        }
    }
}
