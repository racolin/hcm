package com.banvie.hcm.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.banvie.hcm.listener.OnClickToolListener;
import com.banvie.hcm.R;
import com.banvie.hcm.Support;
import com.banvie.hcm.adapter.NoticeAdapter;
import com.banvie.hcm.adapter.SliderAdapter;
import com.banvie.hcm.model.Notice;
import com.banvie.hcm.model.Tool;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnClickToolListener {
    List<byte[]> sliders;
    RecyclerView rv_notices;
    Handler handler;
    ViewPager2 vp2_slider;
    ImageButton ibt_filter;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (vp2_slider.getCurrentItem() == vp2_slider.getAdapter().getItemCount() - 1) {
                vp2_slider.setCurrentItem(0);
            } else {
                vp2_slider.setCurrentItem(vp2_slider.getCurrentItem() + 1);
            }
        }
    };

    public HomeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initUI(view);
        initListener();
        return view;
    }

    private void initUI(View view) {
        FragmentManager manager = getChildFragmentManager();
        manager.beginTransaction()
                .add(R.id.fg_tools, new ToolFragment(getTools(), getString(R.string.bv_tools), 4))
                .commit();

        handler = new Handler(Looper.myLooper());
        getData();

        rv_notices = view.findViewById(R.id.rv_notices);
        rv_notices.setAdapter(new NoticeAdapter(getContext(), getNotices()));
        rv_notices.setLayoutManager(new GridLayoutManager(
                getContext(), 2
        ));

        vp2_slider = view.findViewById(R.id.vp2_slider);

        vp2_slider.setAdapter(new SliderAdapter(getContext(), sliders));
        ibt_filter = view.findViewById(R.id.ibt_filter);
    }

    private void initListener() {
        ibt_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        vp2_slider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 4000);
            }
        });
    }

    private void getData() {
        sliders = new ArrayList<>();
        sliders.add(Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.slider_1)));
        sliders.add(Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.slider_2)));
    }

    private List<Notice> getNotices() {
        List<Notice> notices = new ArrayList<>();
        notices.add(new Notice("Announcement of new employee on boarding",
                Support.convertStringToDate("Mar 23, 2021 15:35", "MMM dd, yyyy HH:mm"),
                Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.iv_notice))));
        notices.add(new Notice("Announcement of new employee on boarding",
                Support.convertStringToDate("Mar 23, 2021 15:35", "MMM dd, yyyy HH:mm"),
                Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.iv_notice))));
        notices.add(new Notice("Announcement of new employee on boarding",
                Support.convertStringToDate("Mar 23, 2021 15:35", "MMM dd, yyyy HH:mm"),
                Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.iv_notice))));
        notices.add(new Notice("Announcement of new employee on boarding",
                Support.convertStringToDate("Mar 23, 2021 15:35", "MMM dd, yyyy HH:mm"),
                Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.iv_notice))));
        notices.add(new Notice("Announcement of new employee on boarding",
                Support.convertStringToDate("Mar 23, 2021 15:35", "MMM dd, yyyy HH:mm"),
                Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.iv_notice))));
        notices.add(new Notice("Announcement of new employee on boarding",
                Support.convertStringToDate("Mar 23, 2021 15:35", "MMM dd, yyyy HH:mm"),
                Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.iv_notice))));
        notices.add(new Notice("Announcement of new employee on boarding",
                Support.convertStringToDate("Mar 23, 2021 15:35", "MMM dd, yyyy HH:mm"),
                Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.iv_notice))));
        return notices;
    }

    private List<Tool> getTools() {
        List<Tool> tools = new ArrayList<>();
        tools.add(new Tool(getString(R.string.working_time), Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.working_time))));
        tools.add(new Tool(getString(R.string.bv_calendar), Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.bv_calendar))));
        tools.add(new Tool(getString(R.string.payslip), Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.payslip))));
        tools.add(new Tool(getString(R.string.seat_map), Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.seat_map))));
        tools.add(new Tool(getString(R.string.employees), Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.employees))));
        tools.add(new Tool(getString(R.string.organization_chart), Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.organization_charts))));
        tools.add(new Tool(getString(R.string.knowledge_base), Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.knowledge_base))));
        tools.add(new Tool(getString(R.string.more_tools), Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.more_tools))));
        SharedPreferences preferences = getContext().getSharedPreferences("tools_status", Context.MODE_PRIVATE);
        for (Tool tool : tools) {
            if (preferences.getBoolean(tool.getName(), false)) {
                tool.unsetShow();
            }
        }
        return tools;
    }

    @Override
    public void setOnClickMore() {

    }
}
