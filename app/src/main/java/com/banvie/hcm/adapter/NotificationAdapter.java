package com.banvie.hcm.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import com.banvie.hcm.api.ApiService;
import com.banvie.hcm.bottom_sheet.OptionNotificationBottomSheet;
import com.banvie.hcm.listener.OnChangeNotificationListener;
import com.banvie.hcm.listener.OnLoadNotificationsListener;
import com.banvie.hcm.model.notification.Notification;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {

    public List<Notification> notifications;
    Context context;
    OnChangeNotificationListener listener;

    public NotificationAdapter(Context context, OnChangeNotificationListener listener, List<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationHolder(LayoutInflater.from(context).inflate(R.layout.notification, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
//        initUI
        final  int i = position;
        holder.tv_time.setText(Support.convertToTimeAgo(notifications.get(position).sendDate));
        holder.tv_title.setText(HtmlCompat.fromHtml(notifications.get(position).shortContent, HtmlCompat.FROM_HTML_MODE_COMPACT));

        if (notifications.get(position).image == null || notifications.get(position).image.equals("")) {
            holder.iv_notification.setImageResource(R.drawable.logo);
            holder.iv_notification.setBackgroundResource(R.color.dark);
        } else {
            byte[] img = notifications.get(holder.getAdapterPosition()).image_bytes;
            if (img == null) {
                holder.iv_notification.setImageResource(R.drawable.logo);
                holder.iv_notification.setBackgroundResource(R.color.dark);
                ApiService.apiService.getImage(notifications.get(holder.getAdapterPosition()).image).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            notifications.get(i).image_bytes = Support.reduceImage(response.body().bytes());
                            holder.iv_notification.setImageBitmap(
                                    (BitmapFactory.decodeByteArray(notifications.get(i).image_bytes,
                                            0, notifications.get(i).image_bytes.length)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            } else {
                holder.iv_notification.setImageBitmap(
                        BitmapFactory.decodeByteArray(
                                notifications.get(holder.getAdapterPosition()).image_bytes,
                                0, notifications.get(holder.getAdapterPosition()).image_bytes.length));
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
                OptionNotificationBottomSheet sheet = new OptionNotificationBottomSheet(
                        notifications.get(holder.getAdapterPosition()), listener);
                sheet.show(((AppCompatActivity) context).getSupportFragmentManager(), sheet.getTag());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!notifications.get(holder.getAdapterPosition()).read) {
                    listener.readNotification(Arrays.asList(
                            notifications.get(holder.getAdapterPosition()).notificationId));
                }
            }
        });
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
