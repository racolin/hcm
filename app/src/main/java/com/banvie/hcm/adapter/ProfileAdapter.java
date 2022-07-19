package com.banvie.hcm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banvie.hcm.R;

import java.util.Dictionary;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileHolder> {

    Context context;
    List<String> names, values;

    public ProfileAdapter(Context context, List<String> names, List<String> values) {
        this.context = context;
        this.names = names;
        this.values = values;
    }


    @NonNull
    @Override
    public ProfileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProfileHolder(LayoutInflater.from(context).inflate(R.layout.item_profile, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileHolder holder, int position) {
        holder.tv_name.setText(names.get(position));
        holder.tv_value.setText(values.get(position));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ProfileHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_value;

        public ProfileHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_value = itemView.findViewById(R.id.tv_value);
        }
    }
}
