package com.banvie.hcm.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banvie.hcm.R;
import com.banvie.hcm.api.RetrofitApi;
import com.banvie.hcm.model.policy.Notice;

import java.util.List;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeHolder> {

    List<Notice> notices;
    Context context;
    Disposable disposable;

    public NoticeAdapter(Context context, List<Notice> notices) {
        this.context = context;
        this.notices = notices;
    }

    public void update(List<Notice> notices) {
        this.notices = notices;
        notifyDataSetChanged();
    }

    public List<Notice> getNotices() {
        return notices;
    }

    public void setNotices(List<Notice> notices) {
        this.notices = notices;
    }

    @NonNull
    @Override
    public NoticeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoticeHolder(LayoutInflater.from(context).inflate(R.layout.notice, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeHolder holder, int position) {
        holder.tv_title.setText(notices.get(position).getTopic());
        holder.tv_time.setText(notices.get(position).getTimeString("MMM dd, yyyy HH:mm"));
        byte[] image = notices.get(position).getImage();
        if (image != null) {
            holder.iv_notice.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
        }
    }

    @Override
    public int getItemCount() {
        return notices.size();
    }

    public class NoticeHolder extends RecyclerView.ViewHolder {

        ImageView iv_notice;
        TextView tv_title, tv_time;

        public NoticeHolder(@NonNull View itemView) {
            super(itemView);
            iv_notice = itemView.findViewById(R.id.iv_notice);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_time = itemView.findViewById(R.id.tv_time);
        }
    }
}
