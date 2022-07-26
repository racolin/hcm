package com.banvie.hcm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.banvie.hcm.R;
import com.banvie.hcm.Support;
import com.banvie.hcm.model.notification_sound.NotifySound;

import java.util.List;

public class NotifySoundItemAdapter extends RecyclerView.Adapter<NotifySoundItemAdapter.NotifySoundItemHolder> {

    List<NotifySound> notifySounds;
    Context context;

    public NotifySoundItemAdapter(Context context, List<NotifySound> notifySounds) {
        this.context = context;
        this.notifySounds = notifySounds;
    }

    @NonNull
    @Override
    public NotifySoundItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotifySoundItemHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotifySoundItemHolder holder, int position) {
        holder.swt_title.setText(Support.convertFirstWordUpper(notifySounds.get(position).title));
        holder.swt_title.setChecked(notifySounds.get(position).notifyOnMobile);
        holder.swt_sound.setChecked(notifySounds.get(position).soundNotifyOnMobile);
    }

    @Override
    public int getItemCount() {
        return notifySounds.size();
    }

    public class NotifySoundItemHolder extends RecyclerView.ViewHolder {

        SwitchCompat swt_sound, swt_title;

        public NotifySoundItemHolder(@NonNull View itemView) {
            super(itemView);

            swt_sound = itemView.findViewById(R.id.swt_sound);
            swt_title = itemView.findViewById(R.id.swt_title);
        }
    }
}
