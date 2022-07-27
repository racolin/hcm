package com.banvie.hcm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.banvie.hcm.api.ApiService;
import com.banvie.hcm.api.RetrofitApi;
import com.banvie.hcm.listener.OnLoadImageListener;
import com.banvie.hcm.model.employee.Employee;
import com.banvie.hcm.model.employee.EmployeeContainer;
import com.banvie.hcm.model.employee.EmployeeInformation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeActivity extends AppCompatActivity
        implements OnLoadImageListener {

    String cif;
    EmployeeInformation employee;
    ImageView iv_avatar;

    TextView tv_phone, tv_email, tv_cif, tv_dob, tv_gender, tv_name, tv_nation,
            tv_married, tv_team, tv_office, tv_join, tv_avatar, tv_type, tv_position;

    ImageButton ibt_org, ibt_chat, ibt_mess, ibt_call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        if (getIntent().hasExtra("cif")) {
            ApiService.apiService.getEmployee(0, getIntent().getStringExtra("cif"), true).enqueue(new Callback<EmployeeContainer<EmployeeInformation>>() {
                @Override
                public void onResponse(Call<EmployeeContainer<EmployeeInformation>> call, Response<EmployeeContainer<EmployeeInformation>> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().data.items.size() == 1) {
                        employee = response.body().data.items.get(0);
                        RetrofitApi.getImage(employee.image, -1, EmployeeActivity.this);
                        setEmployeeInformation();
                    }
                }

                @Override
                public void onFailure(Call<EmployeeContainer<EmployeeInformation>> call, Throwable t) {

                }
            });
        }

        initUI();
        initListener();
    }

    private void initUI() {
        setupToolbar();

        tv_cif = findViewById(R.id.tv_cif);
        tv_phone = findViewById(R.id.tv_phone);
        tv_email = findViewById(R.id.tv_email);
        tv_dob = findViewById(R.id.tv_dob);
        tv_gender = findViewById(R.id.tv_gender);
        tv_married = findViewById(R.id.tv_married);
        tv_team = findViewById(R.id.tv_team);
        tv_office = findViewById(R.id.tv_office);
        tv_join = findViewById(R.id.tv_join);
        tv_name = findViewById(R.id.tv_name);
        tv_type = findViewById(R.id.tv_type);
        tv_position = findViewById(R.id.tv_position);
        tv_nation = findViewById(R.id.tv_nation);

        iv_avatar = findViewById(R.id.iv_avatar);
        tv_avatar = findViewById(R.id.tv_avatar);

        if (employee != null) {
            setEmployeeInformation();
        }

        ibt_call = findViewById(R.id.ibt_call);
        ibt_chat = findViewById(R.id.ibt_chat);
        ibt_mess = findViewById(R.id.ibt_mess);
        ibt_org = findViewById(R.id.ibt_org);
    }

    private void setEmployeeInformation() {
        tv_cif.setText(employee.cif);
        tv_phone.setText(employee.phone);
        tv_email.setText(employee.companyEmail);
        tv_dob.setText(employee.birthday);
        tv_gender.setText(employee.gender);
        tv_married.setText(employee.maritalStatus);
        tv_team.setText(employee.jobTitle.name);
//        tv_office.setText(employee.jobTitle.name);
        tv_join.setText(employee.stringOnboardDate);
        tv_name.setText(employee.fullName);
        tv_type.setText(employee.jobTitle.name);
        tv_position.setText(employee.jobTitle.name);
        tv_nation.setText(employee.nationality);
        if (!employee.image.equals("") && employee.image_bytes != null) {
            tv_avatar.setText("");
            iv_avatar.setImageBitmap(BitmapFactory.decodeByteArray(employee.image_bytes, 0, employee.image_bytes.length));
        } else {
            tv_avatar.setText(employee.username.substring(0, 1).toUpperCase());
        }
    }

    private void initListener() {
        ibt_org.setOnClickListener((view) -> {
            Intent intent = new Intent(this, OrganizationActivity.class);
            intent.putExtra("id", employee.id);
            startActivity(intent);
        });

        ibt_chat.setOnClickListener((view) -> {

        });

        ibt_mess.setOnClickListener((view) -> {

        });

        ibt_call.setOnClickListener((view) -> {

        });
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tb);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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

    @Override
    public void setOnLoadImageListener(byte[] image, int i) {
        tv_avatar.setText("");
        iv_avatar.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
    }
}