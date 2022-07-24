package com.banvie.hcm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.ImageButton;
import android.widget.Toast;

import com.banvie.hcm.api.Constant;
import com.banvie.hcm.api.RetrofitApi;
import com.banvie.hcm.dialog.CheckInOutDialog;
import com.banvie.hcm.fragment.HomeFragment;
import com.banvie.hcm.fragment.MessageFragment;
import com.banvie.hcm.fragment.NotificationFragment;
import com.banvie.hcm.fragment.SettingFragment;
import com.banvie.hcm.listener.OnLoadNotificationsNumberListener;
import com.banvie.hcm.model.UserInformation;
import com.banvie.hcm.param.NotificationParam;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.Base64;

public class HomeActivity extends AppCompatActivity implements OnLoadNotificationsNumberListener {

    private ImageButton ibt_checkout;
    private BottomNavigationView nv_bottom;
    private boolean flag_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fg_body, new HomeFragment(), HomeFragment.class.getName())
                    .addToBackStack(HomeFragment.class.getName()).commit();
        }

        Base64.Decoder decoder = Base64.getUrlDecoder();
        Moshi moshi = new Moshi.Builder().build();
        try {
            Constant.ACCESS_TOKEN = getSharedPreferences("token", MODE_PRIVATE).getString("access_token", "");
            if (Constant.ACCESS_TOKEN.equals("")) {
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(new Intent());
                finish();
            }
            Constant.userInformation = moshi.adapter(UserInformation.class).fromJson(new String(decoder.decode(Constant.ACCESS_TOKEN.split("\\.")[1])));
        } catch (IOException e) {
            e.printStackTrace();
        }

        initUI();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        flag_out = false;
    }

    private void initUI() {

        getWindow().setStatusBarColor(getColor(R.color.white));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().getInsetsController().setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
        }
        else {
            getWindow().getDecorView()
                    .setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility()
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }


        nv_bottom = findViewById(R.id.nv_bottom);
        ibt_checkout = findViewById(R.id.ibt_checkout);


        nv_bottom.getOrCreateBadge(R.id.nv_notification).setBackgroundColor(getColor(R.color.red));

        RetrofitApi.getNotifications(
                new NotificationParam(Constant.userInformation.userId,
                        0, null, 0, 15), this);
    }

    private void initListener() {

        ibt_checkout.setOnClickListener((view) -> {
                showCheckOutDialog();
        });

        nv_bottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                flag_out = false;
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.nv_home:
                        if (!(manager.findFragmentById(R.id.fg_body) instanceof HomeFragment)) {
                            HomeFragment home = (HomeFragment) manager.findFragmentByTag(HomeFragment.class.getName());
                            transaction.remove(home);
                            transaction.replace(R.id.fg_body, home, home.getClass().getName());
                        }
                        break;
                    case R.id.nv_message:
                        if (!(manager.findFragmentById(R.id.fg_body) instanceof MessageFragment)) {
                            MessageFragment mess = (MessageFragment) manager.findFragmentByTag(MessageFragment.class.getName());
                            if (mess != null) {
                                transaction.remove(mess);
                                transaction.replace(R.id.fg_body, mess, mess.getClass().getName());
                            } else {
                                transaction.replace(R.id.fg_body, new MessageFragment(), MessageFragment.class.getName());
                            }
                            transaction.addToBackStack(MessageFragment.class.getName());
                        }
                        break;
                    case R.id.nv_checkout:
                        showCheckOutDialog();
                        break;
                    case R.id.nv_notification:
                        if (!(manager.findFragmentById(R.id.fg_body) instanceof NotificationFragment)) {
                            NotificationFragment notify = (NotificationFragment) manager.findFragmentByTag(NotificationFragment.class.getName());
                            if (notify != null) {
                                transaction.remove(notify);
                                transaction.replace(R.id.fg_body, notify, notify.getClass().getName());
                            } else {
                                transaction.replace(R.id.fg_body, new NotificationFragment(HomeActivity.this), NotificationFragment.class.getName());
                            }
                            transaction.addToBackStack(NotificationFragment.class.getName());
                        }
                        break;
                    case R.id.nv_setting:
                        if (!(manager.findFragmentById(R.id.fg_body) instanceof SettingFragment)) {
                            SettingFragment setting = (SettingFragment) manager.findFragmentByTag(SettingFragment.class.getName());
                            if (setting != null) {
                                transaction.remove(setting);
                                transaction.replace(R.id.fg_body, setting, setting.getClass().getName());
                            } else {
                                transaction.replace(R.id.fg_body, new SettingFragment(), SettingFragment.class.getName());
                            }
                            transaction.addToBackStack(SettingFragment.class.getName());
                        }
                        break;
                }
                transaction.commit();
                return true;
            }
        });

    }

    private void showCheckOutDialog() {
        Dialog dialog = new CheckInOutDialog(HomeActivity.this);
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if (flag_out) {
            finish();
        } else {
            FragmentManager manager = getSupportFragmentManager();
            if (!(manager.findFragmentById(R.id.fg_body) instanceof HomeFragment)) {
                FragmentTransaction transaction = manager.beginTransaction();
                HomeFragment home = (HomeFragment) manager.findFragmentByTag(HomeFragment.class.getName());
                transaction.remove(home);
                transaction.replace(R.id.fg_body, home, home.getClass().getName()).commit();
                Toast.makeText(this, R.string.press_to_back, Toast.LENGTH_SHORT).show();
                nv_bottom.setSelectedItemId(R.id.nv_home);
                flag_out = true;
            }
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void setOnNotificationsNumberListener(int i) {
        nv_bottom.getOrCreateBadge(R.id.nv_notification).setNumber(i);
    }
}