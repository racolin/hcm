package com.banvie.hcm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.banvie.hcm.adapter.EmployeeAdapter;
import com.banvie.hcm.api.ApiService;
import com.banvie.hcm.api.RetrofitApi;
import com.banvie.hcm.listener.OnLoadEmployeeListener;
import com.banvie.hcm.model.employee.Em;
import com.banvie.hcm.model.employee.Employee;
import com.banvie.hcm.model.employee.EmployeeContainer;
import com.banvie.hcm.param.EmployeeParam;
import com.banvie.hcm.type.ReloadMode;

import java.util.ArrayList;
import java.util.List;

public class EmployeesActivity extends AppCompatActivity implements OnLoadEmployeeListener {

    RecyclerView rv_employees;
    EmployeeAdapter adapter;
    ImageButton ibt_search;
    TextView tv_count;
    EditText edt_search;

    int page, totalPage, size, total;
    boolean hasNext, loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);

        initUI();
        initListener();
    }

    private void setupToolbar() {

        Toolbar toolbar = findViewById(R.id.tb);

        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText(R.string.employees);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initUI() {
        page = 0;
        total = 0;
        hasNext = true;
        loading = false;

        setupToolbar();

        edt_search = findViewById(R.id.edt_search);
        ibt_search = findViewById(R.id.ibt_search);
        tv_count = findViewById(R.id.tv_count);

        adapter = new EmployeeAdapter(this, new ArrayList<>());
        rv_employees = findViewById(R.id.rv_employees);
        rv_employees.setAdapter(adapter);
        rv_employees.setLayoutManager(new LinearLayoutManager(this));

        loadEmployeesListener();
    }

    private void loadEmployeesListener() {
        if (hasNext) {
            loading = true;
            RetrofitApi.getEmployees(new EmployeeParam(page, edt_search.getText().toString(), true), EmployeesActivity.this);
        }
    }

    private void initListener() {
        ibt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.employees.clear();
                page = 0;
                total = 0;
                hasNext = true;
                loading = true;
                loadEmployeesListener();
            }
        });

        rv_employees.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (hasNext && !rv_employees.canScrollVertically(1) && !loading) {
                    loadEmployeesListener();
                }
            }
        });
    }

    @Override
    public void setOnLoadImageListener(byte[] image, int i) {
        adapter.employees.get(i).image_bytes = image;
        adapter.notifyItemChanged(i);
    }

//  Được gọi khi tìm kiếm nhân viên hoặc khi load thêm nhân viên
    @Override
    public void setOnLoadEmployeeListener(EmployeeContainer container) {
        size = container.data.size;
        totalPage = container.data.totalPages;
        total = container.data.totalElements;
        hasNext = container.data.hasNext;
        page = container.data.page;

        if (page == 1) {
//            tv_count.setText(String.valueOf(total));
////            employees.clear();
////            employees.addAll(container.data.items);
//            adapter.employees_loaded.clear();
//            int len = container.data.items.size();
//            for (int j = 0; j < len; j++) {
//                adapter.employees_loaded.add(ReloadMode.INITIAL);
//            }
//            adapter.notifyDataSetChanged();
            adapter = new EmployeeAdapter(this, container.data.items);
            rv_employees.setAdapter(adapter);
            rv_employees.setLayoutManager(new LinearLayoutManager(this));
        } else {
            int i = adapter.employees.size();
            int len = container.data.items.size();
            for (int j = 0; j < len; j++) {
                adapter.employees_loaded.add(ReloadMode.INITIAL);
            }
            adapter.employees.addAll(container.data.items);
            adapter.notifyItemRangeInserted(i, len);
        }

        loading = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

        }

        return true;
    }
}