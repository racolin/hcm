package com.banvie.hcm.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banvie.hcm.EmployeeActivity;
import com.banvie.hcm.R;
import com.banvie.hcm.Support;
import com.banvie.hcm.model.employee.Employee;

import org.w3c.dom.Text;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeHolder> {

    Context context;
    public List<Employee> employees;

    public EmployeeAdapter(Context context, List<Employee> employees) {
        this.context = context;
        this.employees = employees;
    }

    @NonNull
    @Override
    public EmployeeAdapter.EmployeeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EmployeeHolder(LayoutInflater.from(context).inflate(R.layout.employee_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeHolder holder, int position) {
        final int i = position;
        holder.tv_name.setText(employees.get(position).fullName);
        holder.tv_type.setText(employees.get(position).jobTitle.name);
        if (employees.get(position).image_bytes == null) {
            holder.tv_avatar.setText(Support.getFirstTextOfName(employees.get(position).firstName));
        } else {
            holder.tv_avatar.setBackground(new BitmapDrawable(context.getResources(),
                    BitmapFactory.decodeByteArray(employees.get(position).image_bytes, 0, employees.get(position).image_bytes.length)));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EmployeeActivity.class);
                intent.putExtra("employee", employees.get(i));
                context.startActivity(intent);
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
