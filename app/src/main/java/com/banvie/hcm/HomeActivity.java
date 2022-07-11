package com.banvie.hcm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {

    ViewPager2 vp2_slider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initUI();
        initListener();
    }

    private void initUI() {
        vp2_slider = findViewById(R.id.vp2_slider);

//        vp2_slider.set
    }

    private void initListener() {

    }
}