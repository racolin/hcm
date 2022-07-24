package com.banvie.hcm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.banvie.hcm.adapter.ProfileAdapter;
import com.banvie.hcm.api.Constant;
import com.banvie.hcm.api.RetrofitApi;
import com.banvie.hcm.listener.OnLoadProfileListener;
import com.banvie.hcm.model.education.Education;
import com.banvie.hcm.model.employee_duration.EmployeeDuration;
import com.banvie.hcm.model.shui.Shui;
import com.banvie.hcm.model.summary.Summary;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements OnLoadProfileListener {

    RecyclerView rv_summary, rv_employee, rv_education, rv_shui;
    ProfileAdapter summary_adapter, employee_adapter, education_adapter, shui_adapter;
    List<String> summary_names, summary_values, employee_names, employee_values,
                education_names, education_values, shui_names, shui_values;

    TextView tv_full_name, tv_id, tv_token_value;
    CardView card;

    LinearLayout employee, education, shui, token;
    RelativeLayout access_token_layout;
    ImageButton ibt_copy;

    ImageView iv_shui_roll, iv_education_roll, iv_employee_roll, iv_token_roll;
    boolean show_shui = false, show_employee = false, show_education = false, show_token = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initUI();
        initListener();
    }

    private void setupToolbar() {
        

        Toolbar toolbar = findViewById(R.id.tb);

        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Profile");

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    void initUI() {

        setupToolbar();

        setUp();

        employee = findViewById(R.id.employee);
        education = findViewById(R.id.education);
        shui = findViewById(R.id.shui);
        token = findViewById(R.id.token);
        access_token_layout = findViewById(R.id.access_token_layout);

        iv_employee_roll = findViewById(R.id.iv_employee_roll);
        iv_education_roll = findViewById(R.id.iv_education_roll);
        iv_shui_roll = findViewById(R.id.iv_shui_roll);
        iv_token_roll = findViewById(R.id.iv_token_roll);

        card = findViewById(R.id.card);

        tv_full_name = findViewById(R.id.tv_full_name);
        tv_id = findViewById(R.id.tv_id);
        tv_token_value = findViewById(R.id.tv_token_value);
        tv_token_value.setText(Constant.ACCESS_TOKEN);

        ibt_copy = findViewById(R.id.ibt_copy);

        rv_summary = findViewById(R.id.rv_summary);
        summary_adapter = new ProfileAdapter(this, summary_names, summary_values);
        rv_summary.setAdapter(summary_adapter);
        rv_summary.setLayoutManager(new LinearLayoutManager(this));
        rv_summary.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        rv_education = findViewById(R.id.rv_education);
        education_adapter = new ProfileAdapter(this, education_names, education_values);
        rv_education.setAdapter(education_adapter);
        rv_education.setLayoutManager(new LinearLayoutManager(this));
        rv_education.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        rv_employee = findViewById(R.id.rv_employee);
        employee_adapter = new ProfileAdapter(this, employee_names, employee_values);
        rv_employee.setAdapter(employee_adapter);
        rv_employee.setLayoutManager(new LinearLayoutManager(this));
        rv_employee.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        rv_shui = findViewById(R.id.rv_shui);
        shui_adapter = new ProfileAdapter(this, shui_names, shui_values);
        rv_shui.setAdapter(shui_adapter);
        rv_shui.setLayoutManager(new LinearLayoutManager(this));
        rv_shui.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        RetrofitApi.getUserSummary(this);
        RetrofitApi.getEmployeeDuration(this);
        RetrofitApi.getEducation(this);
        RetrofitApi.getShui(this);
    }

    void initListener() {
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rv_employee.setVisibility(show_employee ? View.GONE : View.VISIBLE);
                iv_employee_roll.startAnimation(AnimationUtils
                        .loadAnimation(ProfileActivity.this,
                                show_employee ? R.anim.roll_up : R.anim.roll_down));
                show_employee = !show_employee;
            }
        });
        education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rv_education.setVisibility(show_education ? View.GONE : View.VISIBLE);
                iv_education_roll.startAnimation(AnimationUtils
                        .loadAnimation(ProfileActivity.this,
                                show_education ? R.anim.roll_up : R.anim.roll_down));
                show_education = !show_education;
            }
        });
        shui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rv_shui.setVisibility(show_shui ? View.GONE : View.VISIBLE);
                iv_shui_roll.startAnimation(AnimationUtils
                        .loadAnimation(ProfileActivity.this,
                                show_shui ? R.anim.roll_up : R.anim.roll_down));
                show_shui = !show_shui;
            }
        });
        token.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                access_token_layout.setVisibility(show_token ? View.GONE : View.VISIBLE);
                iv_token_roll.startAnimation(AnimationUtils
                        .loadAnimation(ProfileActivity.this,
                                show_token ? R.anim.roll_up : R.anim.roll_down));
                show_token = !show_token;
            }
        });

    }

    private void setUp() {
        summary_names = new ArrayList<>();
        summary_values = new ArrayList<>();
        String[] list = {"Organization", "Job title", "Job level", "Direct report", "Office", "Date of birth",
                "Company email", "Personal email", "Phone number", "Identity card number", "Issue on", "Issue at", "Bank account list"};
        for (String s : list) {
            summary_names.add(s);
            summary_values.add("");
        }
        employee_names = new ArrayList<>();
        employee_values = new ArrayList<>();
        list = new String[]{"Onboarding date", "Probation dates", "Official date", "Termination date", "Probation contract number",
                "Labour contract number", "labour contract date", "Indefinite-term contract", "Indefinite-term contract date",
                "Resignation agreement", "Resignation agreement date", "Emergency contract list"};
        for (String s : list) {
            employee_names.add(s);
            employee_values.add("");
        }
        education_names = new ArrayList<>();
        education_values = new ArrayList<>();
//        list = new String[]{"Organization", "Job title", "Job level", "Direct report", "Office", "Date of birth",
//                "Company email", "Personal email", "Phone number", "Identity card number", "Issue on", "Issue at", "Bank account list"};
//        for (String s : list) {
//            summary_names.add(s);
//            summary_values.add("");
//        }
        shui_names = new ArrayList<>();
        shui_values = new ArrayList<>();
        list = new String[]{"Tax identification number", "Social insurance number", "Social insurance place",
                "Family health care package number", "Health insurance number", "Health care insurance list"};
        for (String s : list) {
            shui_names.add(s);
            shui_values.add("");
        }
    }

    @Override
    public void setOnSummaryListener(Summary summary) {
        tv_full_name.setText(summary.fullName.equals("") ? "" : summary.fullName);
        tv_id.setText(summary.cif);
        summary_values.set(0, summary.organization.name);
        summary_values.set(1, summary.jobTitle.name);
        summary_values.set(2, "");
        summary_values.set(3, summary.directReport.name);
        summary_values.set(4, "");
        summary_values.set(5, summary.birthDay);
        summary_values.set(6, summary.companyEmail);
        summary_values.set(7, summary.personalEmail);
        summary_values.set(8, summary.phone);
        summary_values.set(9, "");
        summary_values.set(10, "");
        summary_values.set(11, "");
        summary_values.set(12, "");
        summary_adapter.notifyDataSetChanged();
    }

    @Override
    public void setOnEmployeeDurationListener(EmployeeDuration employeeDuration) {
        employee_values.set(0, employeeDuration.onboardDate);
        employee_values.set(6, employeeDuration.labourContractDate);
        employee_values.set(2, employeeDuration.officialStartDate);
//        employee_values.set(11, employeeDuration.emergencyContacts);
        employee_values.set(5, employeeDuration.labourContractNumber);
        employee_adapter.notifyDataSetChanged();
    }

    @Override
    public void setOnEducationListener(Education education) {

    }

    @Override
    public void setOnShuiListener(Shui shui) {

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