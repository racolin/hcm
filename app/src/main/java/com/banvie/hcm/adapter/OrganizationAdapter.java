package com.banvie.hcm.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banvie.hcm.EmployeeActivity;
import com.banvie.hcm.R;
import com.banvie.hcm.Support;
import com.banvie.hcm.api.ApiService;
import com.banvie.hcm.listener.OnClickItemOrganizationListener;
import com.banvie.hcm.listener.OnLoadItemOrganizationListener;
import com.banvie.hcm.model.organization_chart.OrganizationChart;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrganizationAdapter extends RecyclerView.Adapter<OrganizationAdapter.OrganizationHolder> {

    Context context;
    OnClickItemOrganizationListener listener;
    public List<OrganizationChart> organizations;
    boolean isManager;
    public boolean isLoad = false;
//    public int focus;

    public OrganizationAdapter(Context context, OnClickItemOrganizationListener listener, List<OrganizationChart> organizations, int focus, boolean isManager) {
        this.context = context;
        this.organizations = organizations;
        this.isManager = isManager;
        this.listener = listener;
//        this.focus = focus;
    }

    @NonNull
    @Override
    public OrganizationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrganizationHolder(LayoutInflater
                .from(context)
                .inflate(isManager ? R.layout.bottom_manager : R.layout.bottom_employee, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizationHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizationHolder holder, int position) {
        final int i = position;
        holder.tv_name.setText(organizations.get(position).fullName);
        holder.tv_type.setText(organizations.get(position).jobTitle.name);
        if (organizations.get(position).profile.image.equals("")) {
            holder.tv_avatar.setText(organizations.get(position).username.substring(0, 1).toUpperCase());
            holder.tv_avatar.setBackgroundColor(context.getColor(R.color.blue));
        } else {
            if (organizations.get(position).image_bytes == null) {
                holder.tv_avatar.setText(organizations.get(position).username.substring(0, 1).toUpperCase());
                holder.tv_avatar.setBackgroundColor(context.getColor(R.color.blue));
                ApiService.apiService.getImage(organizations.get(i).profile.image)
                        .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            organizations.get(i).image_bytes = Support.reduceImage(response.body().bytes());
                            holder.tv_avatar.setText("");
                            holder.tv_avatar.setBackground(new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(
                                    organizations.get(i).image_bytes, 0,
                                    organizations.get(i).image_bytes.length)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            } else {
                holder.tv_avatar.setText("");
                holder.tv_avatar.setBackground(new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(
                        organizations.get(i).image_bytes, 0,
                        organizations.get(i).image_bytes.length)));
            }
        }

        if (!isManager && (position == 0 || position == organizations.size() - 1)) {
            holder.layout.setBackgroundResource(position == 0 ? R.drawable.top_stroke : R.drawable.bot_stroke);
        }

//        if (position == focus) {
//            ((GradientDrawable) holder.layout.getBackground().mutate()).setStroke(2, context.getColor(R.color.yellow));
//        }

        holder.ibt_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EmployeeActivity.class);
                intent.putExtra("cif", organizations.get(holder.getAdapterPosition()).code);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isLoad
//                        && focus != holder.getAdapterPosition()
                        && (!isManager || (isManager && holder.getAdapterPosition() != organizations.size() - 1))) {
                    isLoad = true;
                    listener.setOnClickItemOrganizationListener(holder.getAdapterPosition(), isManager);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return organizations == null ? 0 : organizations.size();
    }

    public class OrganizationHolder extends RecyclerView.ViewHolder {

        RelativeLayout layout;
//        ImageView iv_avatar;
        ImageButton ibt_info;
        TextView tv_name, tv_type, tv_avatar;

        public OrganizationHolder(@NonNull View itemView) {
            super(itemView);
//            iv_avatar = itemView.findViewById(R.id.iv_avatar);
            tv_avatar = itemView.findViewById(R.id.tv_avatar);
            ibt_info = itemView.findViewById(R.id.ibt_info);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_type = itemView.findViewById(R.id.tv_type);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
