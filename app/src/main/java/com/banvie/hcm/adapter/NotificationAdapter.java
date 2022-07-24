package com.banvie.hcm.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.banvie.hcm.R;
import com.banvie.hcm.Support;
import com.banvie.hcm.bottom_sheet.OptionNotificationBottomSheet;
import com.banvie.hcm.listener.OnLoadNotificationsListener;
import com.banvie.hcm.model.notification.Notification;
import com.banvie.hcm.type.ReloadMode;
import com.banvie.hcm.type.ToolId;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {

    public List<Notification> notifications;
    public List<ReloadMode> ntfs_loaded;
    Context context;
    OnLoadNotificationsListener listener;

    public NotificationAdapter(Context context, OnLoadNotificationsListener listener, List<Notification> notifications) {
        this.context = context;
        this.listener = listener;
        this.notifications = notifications;
        ntfs_loaded = new ArrayList<>();
        fit();
    }

    private void fit() {
        int l_1 = notifications.size();
        int l_2 = ntfs_loaded.size();
        if (l_1 > l_2) {
            for (int i = 0; i < l_1- l_2; i++) {
                ntfs_loaded.add(ReloadMode.INITIAL);
            }
        }
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationHolder(LayoutInflater.from(context).inflate(R.layout.notification, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
//        initUI
        Log.d("rrrrrxx", "---" + position);
        final int i = position;
        if (ntfs_loaded.get(i) == ReloadMode.INITIAL) {
            Log.d("rrrrrxx", "hello");
            ntfs_loaded.set(i, ReloadMode.TEXT_LOADED);
            holder.tv_time.setText(Support.convertToTimeAgo(notifications.get(position).sendDate));
            holder.tv_title.setText(HtmlCompat.fromHtml(notifications.get(position).shortContent, HtmlCompat.FROM_HTML_MODE_COMPACT));
        }

        if (ntfs_loaded.get(i) != ReloadMode.IMAGE_LOADED) {
            Log.d("rrrrrxx", "holle");
            byte[] img = notifications.get(position).image_bytes;
            if (img != null) {
                ntfs_loaded.set(i, ReloadMode.IMAGE_LOADED);
                holder.iv_notification.setImageBitmap(BitmapFactory.decodeByteArray(img, 0, img.length));
            } else {
                holder.iv_notification.setImageResource(R.drawable.logo);
                holder.iv_notification.setBackgroundResource(R.color.dark);
            }
        }

        if (notifications.get(position).read) {
            holder.itemView.setBackgroundResource(R.color.white);
        } else {
            holder.itemView.setBackgroundResource(R.color.blue_light);
        }

//        initListener
        holder.ibt_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OptionNotificationBottomSheet sheet = new OptionNotificationBottomSheet(notifications.get(i), listener);
                sheet.show(((AppCompatActivity) context).getSupportFragmentManager(), sheet.getTag());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!notifications.get(i).read) {
                    listener.setOnReadNotificationListener(notifications.get(i));
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class NotificationHolder extends RecyclerView.ViewHolder {

        ImageView iv_notification;
        TextView tv_title, tv_time;
        ImageButton ibt_option;

        public NotificationHolder(@NonNull View itemView) {
            super(itemView);

            iv_notification = itemView.findViewById(R.id.iv_notification);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_title = itemView.findViewById(R.id.tv_title);
            ibt_option = itemView.findViewById(R.id.ibt_option);
        }
    }
}
