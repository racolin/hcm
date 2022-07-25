package com.banvie.hcm.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banvie.hcm.R;
import com.banvie.hcm.Support;
import com.banvie.hcm.api.ApiService;
import com.banvie.hcm.model.employee.Em;
import com.banvie.hcm.model.employee.Employee;
import com.banvie.hcm.type.ReloadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeHolder> {

    Context context;
    public List<Em> employees;
    public List<ReloadMode> employees_loaded;

    public EmployeeAdapter(Context context, List<Em> employees) {
        this.context = context;
        this.employees = employees;
        employees_loaded = new ArrayList<>();
        fit();
    }

    private void fit() {
        int l_1 = employees.size();
        int l_2 = employees_loaded.size();
        if (l_1 > l_2) {
            for (int i = 0; i < l_1- l_2; i++) {
                employees_loaded.add(ReloadMode.INITIAL);
            }
        }
    }

    @NonNull
    @Override
    public EmployeeAdapter.EmployeeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EmployeeHolder(LayoutInflater.from(context).inflate(R.layout.employee_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeHolder holder, int position) {
//        initUI
        final int i = position;
//        Log.d("xxx", i + "=" + employees.size() + "=");
//        if (employees_loaded.get(i) == ReloadMode.INITIAL) {
//            holder.tv_name.setText(employees.get(position).fullName);
//            holder.tv_type.setText(employees.get(position).jobTitle.name);
//            employees_loaded.set(i, ReloadMode.TEXT_LOADED);
//        }
//
//        if (employees_loaded.get(i) != ReloadMode.IMAGE_LOADED) {
//            byte[] img = employees.get(position).image_bytes;
//            holder.tv_name.setText(employees.get(position).fullName);
//            holder.tv_type.setText(employees.get(position).jobTitle.name);
//            if (img == null) {
//                holder.tv_avatar.setText(employees.get(position).username.substring(0, 1));
//                holder.tv_avatar.setBackgroundColor(context.getColor(R.color.blue));
//            } else {
//                holder.tv_avatar.setText("");
//                holder.tv_avatar.setBackground(new BitmapDrawable(context.getResources(),
//                        BitmapFactory.decodeByteArray(img, 0, img.length)));
//                employees_loaded.set(i, ReloadMode.IMAGE_LOADED);
//            }
//        }

        holder.tv_name.setText(employees.get(position).fullName);
        holder.tv_type.setText(employees.get(position).jobTitle.name);

        if (employees.get(i).image.equals("")) {
            holder.tv_avatar.setText(employees.get(position).username.substring(0, 1));
            holder.tv_avatar.setBackgroundColor(context.getColor(R.color.blue));
        } else {
            byte[] img = employees.get(position).image_bytes;
            if (img == null) {
                holder.tv_avatar.setText(employees.get(position).username.substring(0, 1));
                holder.tv_avatar.setBackgroundColor(context.getColor(R.color.blue));
                ApiService.apiService.getImage(employees.get(i).image).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            employees.get(i).image_bytes = Support.reduceImage(response.body().bytes());
                            holder.tv_avatar.setText("");
                            holder.tv_avatar.setBackground(new BitmapDrawable(context.getResources(),
                                    BitmapFactory.decodeByteArray(employees.get(i).image_bytes, 0, employees.get(i).image_bytes.length)));
                            employees_loaded.set(i, ReloadMode.IMAGE_LOADED);
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
                holder.tv_avatar.setBackground(new BitmapDrawable(context.getResources(),
                        BitmapFactory.decodeByteArray(img, 0, img.length)));
                employees_loaded.set(i, ReloadMode.IMAGE_LOADED);
            }
        }

//        initListener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, EmployeeActivity.class);
//                intent.putExtra("employee", employees.get(i));
//                intent.putExtra("employeeId", employees.get(i).id);
//                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public class EmployeeHolder extends RecyclerView.ViewHolder {

        TextView tv_avatar, tv_name, tv_type;

        public EmployeeHolder(@NonNull View itemView) {
            super(itemView);

            tv_avatar = itemView.findViewById(R.id.tv_avatar);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_type = itemView.findViewById(R.id.tv_type);
        }
    }

}
