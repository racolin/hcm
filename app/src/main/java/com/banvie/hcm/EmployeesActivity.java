package com.banvie.hcm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.banvie.hcm.adapter.EmployeeAdapter;
import com.banvie.hcm.api.RetrofitApi;
import com.banvie.hcm.listener.OnLoadEmployeeListener;
import com.banvie.hcm.model.employee.EmployeeContainer;
import com.banvie.hcm.param.EmployeeParam;

import java.util.ArrayList;

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

        loadEmployeesListener(new EmployeeParam(0, "", true));
    }

    private void loadEmployeesListener(EmployeeParam param) {
        if (hasNext && !loading) {
            loading = true;
            RetrofitApi.getEmployees(param, EmployeesActivity.this);
        }
    }

    private void initListener() {
        ibt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!loading) {
                    adapter = new EmployeeAdapter(EmployeesActivity.this, new ArrayList<>());
                    rv_employees.setAdapter(adapter);
                    tv_count.setText("0");
                    page = 0;
                    total = 0;
                    hasNext = true;
                    loadEmployeesListener(new EmployeeParam(0, edt_search.getText().toString(), true));
                }
            }
        });

        rv_employees.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (hasNext && !rv_employees.canScrollVertically(1) && !loading) {
//                    loadEmployeesListener()new EmployeeParam(0, "", true);
                }
            }
        });
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
            tv_count.setText(String.valueOf(total));
            adapter = new EmployeeAdapter(this, container.data.items);
            rv_employees.setAdapter(adapter);
            rv_employees.setLayoutManager(new LinearLayoutManager(this));
        } else {
            int i = adapter.employees.size();
            int len = container.data.items.size();
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