package com.banvie.hcm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.banvie.hcm.dialog.CheckOutDialog;
import com.banvie.hcm.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
    }

    private void initListener() {

        ibt_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new CheckOutDialog(HomeActivity.this);
                dialog.show();
            }
        });
    }
}