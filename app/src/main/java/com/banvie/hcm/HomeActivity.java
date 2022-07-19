package com.banvie.hcm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.banvie.hcm.api.Constant;
import com.banvie.hcm.api.RetrofitApi;
import com.banvie.hcm.dialog.CheckOutDialog;
import com.banvie.hcm.fragment.HomeFragment;
import com.banvie.hcm.fragment.MessageFragment;
import com.banvie.hcm.fragment.NotificationFragment;
import com.banvie.hcm.fragment.SettingFragment;
import com.banvie.hcm.listener.OnLoadNotificationsListener;
import com.banvie.hcm.listener.OnLoadNotificationsNumberListener;
import com.banvie.hcm.model.UserInformation;
import com.banvie.hcm.model.notification.Notification;
import com.banvie.hcm.model.notification.NotificationContainer;
import com.banvie.hcm.param.NotificationParam;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements OnLoadNotificationsNumberListener {

    ImageButton ibt_checkout;
    BottomNavigationView nv_bottom;
    List<Notification> notifications;
    NotificationFragment notificationFragment;
//    BottomSheetBehavior sheetBehavior;
//    LinearLayout option_bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Base64.Decoder decoder = Base64.getUrlDecoder();
        Moshi moshi = new Moshi.Builder().build();
        try {
            Constant.userInformation = moshi.adapter(UserInformation.class).fromJson(new String(decoder.decode(Constant.ACCESS_TOKEN.split("\\.")[1])));
        } catch (IOException e) {
            e.printStackTrace();
        }

        initUI();
        initListener();
    }

    private void initUI() {
        notifications = new ArrayList<>();
        notificationFragment = new NotificationFragment(notifications, HomeActivity.this);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        nv_bottom = findViewById(R.id.nv_bottom);
        ibt_checkout = findViewById(R.id.ibt_checkout);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.fg_body, new HomeFragment()).commit();

        nv_bottom.getOrCreateBadge(R.id.nv_notification).setBackgroundColor(getColor(R.color.red));

//        option_bottom = findViewById(R.id.option_bottom);
//
//        sheetBehavior = BottomSheetBehavior.from(option_bottom);
//
//        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//
//            }
//        });
//
        RetrofitApi.getNotifications(
                new NotificationParam(Constant.userInformation.getUserId(),
                        0, null, 0, 15), this);
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
                        transaction.replace(R.id.fg_body, notificationFragment);
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

    @Override
    public void setOnNotificationsNumberListener(int i) {
        nv_bottom.getOrCreateBadge(R.id.nv_notification).setNumber(i);
    }
}