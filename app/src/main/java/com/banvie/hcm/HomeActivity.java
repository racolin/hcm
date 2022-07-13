package com.banvie.hcm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.banvie.hcm.dialog.CheckOutDialog;
import com.banvie.hcm.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.time.LocalDateTime;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    ImageButton ibt_checkout;
    BottomNavigationView nv_bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUI();
        initListener();
    }

    private void initUI() {
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        nv_bottom = findViewById(R.id.nv_bottom);
        ibt_checkout = findViewById(R.id.ibt_checkout);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.fg_body, new HomeFragment()).commit();

        nv_bottom.getOrCreateBadge(R.id.nv_notification).setBackgroundColor(getColor(R.color.red));
        nv_bottom.getOrCreateBadge(R.id.nv_notification).setNumber(5);

        LocalDateTime time = LocalDateTime.now();
        int hour = time.getHour();
        int welcome = R.string.evening;
        if (hour < 12) {
            welcome = R.string.morning;
        } else if (hour < 18) {
            welcome = R.string.afternoon;
        }
        ((TextView) findViewById(R.id.tv_welcome)).setText(welcome);
    }

    private void initListener() {

        ibt_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCheckOutDialog();
            }
        });

        nv_bottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.nv_home:
                        transaction.replace(R.id.fg_body, new HomeFragment());
                        break;
                    case R.id.nv_message:
                        transaction.replace(R.id.fg_body, new MessageFragment());
                        break;
                    case R.id.nv_checkout:
                        showCheckOutDialog();
                        break;
                    case R.id.nv_notification:
                        transaction.replace(R.id.fg_body, new NotificationFragment());
                        break;
                    case R.id.nv_setting:
                        transaction.replace(R.id.fg_body, new SettingFragment());
                        break;
                }
                transaction.commit();
                return true;
            }
        });
    }

    private void showCheckOutDialog() {
        Dialog dialog = new CheckOutDialog(HomeActivity.this);
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.findFragmentById(R.id.fg_body) instanceof HomeFragment) {
            finish();
        } else {
            manager.beginTransaction()
                    .replace(R.id.fg_body, new HomeFragment()).commit();
            nv_bottom.setSelectedItemId(R.id.nv_home);
            Toast.makeText(this, R.string.press_to_back, Toast.LENGTH_SHORT).show();
            return;
        }
        super.onBackPressed();
    }
}