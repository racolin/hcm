package com.banvie.hcm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.banvie.hcm.api.ApiService;
import com.banvie.hcm.model.employee.Employee;
import com.banvie.hcm.model.employee.EmployeeContainer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeActivity extends AppCompatActivity  {

    String cif;
    Employee employee;
    ImageView iv_avatar;

    TextView tv_phone, tv_email, tv_cif, tv_dob, tv_gender, tv_name,
            tv_married, tv_team, tv_office, tv_join, tv_avatar, tv_type;

    ImageButton ibt_org, ibt_chat, ibt_mess, ibt_call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        cif = getIntent().getStringExtra("cif");

        if (cif == null && !cif.equals("")) {
            ApiService.apiService.getEmployees(0, cif, true).enqueue(new Callback<EmployeeContainer>() {
                @Override
                public void onResponse(Call<EmployeeContainer> call, Response<EmployeeContainer> response) {
                    if (response.isSuccessful() && response.body() != null) {

                    }
                }

                @Override
                public void onFailure(Call<EmployeeContainer> call, Throwable t) {

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

        tv_cif.setText(employee.cif);
//        tv_phone.setText(employee.phone);
//        tv_email.setText(employee.companyEmail);
//        tv_dob.setText(employee.birthDay);
//        tv_gender.setText(employee.gender);
//        tv_married.setText(employee.);
        tv_team.setText(employee.jobTitle.name);
        tv_name.setText(employee.fullName);
        tv_type.setText(employee.jobTitle.name);

        ibt_call = findViewById(R.id.ibt_call);
        ibt_chat = findViewById(R.id.ibt_chat);
        ibt_mess = findViewById(R.id.ibt_mess);
        ibt_org = findViewById(R.id.ibt_org);

        iv_avatar = findViewById(R.id.iv_avatar);
        tv_avatar = findViewById(R.id.tv_avatar);
        if (employee.image.equals("")) {
            tv_avatar.setText(Support.getFirstTextOfName(employee.fullName));
        } else {
            iv_avatar.setImageBitmap(BitmapFactory.decodeByteArray(employee.image_bytes, 0, employee.image_bytes.length));
        }
    }

    private void initListener() {

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
}