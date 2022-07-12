package com.banvie.hcm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

//    ViewPager2 vp2_slider;
    RecyclerView rv_slider;
    List<byte[]> sliders;
    int current = 0;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            if (vp2_slider.getCurrentItem() == vp2_slider.getAdapter().getItemCount() - 1) {
//                vp2_slider.setCurrentItem(0);
//            } else {
//                vp2_slider.setCurrentItem(vp2_slider.getCurrentItem() + 1);
//            }

            while (true) {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (current > sliders.size()) {
                    current = 0;
                    runOnUiThread(() -> {
                        rv_slider.setTop(current);
                    });
                }
                Log.d("rrr", current + "");
                current++;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getData();
        initUI();
        initListener();
    }

    private void initUI() {
//        vp2_slider = findViewById(R.id.vp2_slider);
//
//        vp2_slider.setAdapter(new SliderAdapter(this, sliders));
//
//            vp2_slider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                handler.removeCallbacks(runnable);
//                handler.postDelayed(runnable, 4000);
//            }
//        });

        rv_slider = findViewById(R.id.rv_slider);
        rv_slider.setAdapter(new SliderAdapter(this, sliders));
        rv_slider.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_slider.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        new Thread(runnable).start();
    }

    private void initListener() {

    }

    private void getData() {
        sliders = new ArrayList<>();
        sliders.add(Support.convertDrawableToBytes(getDrawable(R.drawable.slider_1)));
        sliders.add(Support.convertDrawableToBytes(getDrawable(R.drawable.slider_2)));
    }


}