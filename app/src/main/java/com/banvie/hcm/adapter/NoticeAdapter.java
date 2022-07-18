package com.banvie.hcm.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banvie.hcm.NoticeActivity;
import com.banvie.hcm.R;
import com.banvie.hcm.model.policy.Policy;

import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeHolder> {

    List<Policy> policies;
    Context context;
    Disposable disposable;

    public NoticeAdapter(Context context, List<Policy> policies) {
        this.context = context;
        this.policies = policies;
    }

    public void update(List<Policy> policies) {
        this.policies = policies;
        notifyDataSetChanged();
    }

    public List<Policy> getNotices() {
        return policies;
    }

    public void setNotices(List<Policy> policies) {
        this.policies = policies;
    }

    @NonNull
    @Override
    public NoticeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoticeHolder(LayoutInflater.from(context).inflate(R.layout.notice, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeHolder holder, int position) {
        holder.tv_title.setText(policies.get(position).getTopic());
        holder.tv_time.setText(policies.get(position).getTimeString("MMM dd, yyyy HH:mm"));
        byte[] image = policies.get(position).getImage();
        if (image != null) {
            holder.iv_notice.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
        }
        final int i = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NoticeActivity.class);
                intent.putExtra("notice", policies.get(i));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return policies.size();
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
