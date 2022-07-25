package com.banvie.hcm.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.banvie.hcm.adapter.ToolAdapter;
import com.banvie.hcm.api.Constant;
import com.banvie.hcm.api.RetrofitApi;
import com.banvie.hcm.dialog.FilterAndRemoveToolDialog;
import com.banvie.hcm.R;
import com.banvie.hcm.Support;
import com.banvie.hcm.adapter.NoticeAdapter;
import com.banvie.hcm.adapter.SliderAdapter;
import com.banvie.hcm.listener.OnClickAddOrRemoveToolListener;
import com.banvie.hcm.listener.OnLoadedNoticesListener;
import com.banvie.hcm.model.notification.Notification;
import com.banvie.hcm.model.policy.Policy;
import com.banvie.hcm.model.Tool;
import com.banvie.hcm.type.ToolId;
import com.banvie.hcm.type.ToolsType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements
        OnClickAddOrRemoveToolListener, OnLoadedNoticesListener {
    List<byte[]> sliders;
    List<Tool> tools;
    List<Policy> policies;

    RecyclerView rv_tools;
    ToolAdapter adapter_tools;
    NoticeAdapter adapter_notices;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sliders = getSliders();
        tools = getTools();
        policies = new ArrayList<>();

        RetrofitApi.callNotices(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUI(view);
        initListener();
        super.onViewCreated(view, savedInstanceState);
    }

    private void initUI(View view) {

        ((TextView) view.findViewById(R.id.tv_welcome)).setText(getGoodId());
        ((TextView) view.findViewById(R.id.tv_full_name)).setText(Constant.userInformation.getFullName());
        adapter_tools = new ToolAdapter(getContext(), tools, ToolsType.HIGH_LIGHT);

        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_tool, null);
        ((TextView) v.findViewById(R.id.tv_title)).setText(R.string.bv_tools);
        rv_tools = v.findViewById(R.id.rv);
        rv_tools.setAdapter(adapter_tools);
        rv_tools.setLayoutManager(new GridLayoutManager(getContext(), 4));
        FrameLayout layout = view.findViewById(R.id.fg_tools);
        layout.addView(v);

        handler = new Handler(Looper.myLooper());

        rv_notices = view.findViewById(R.id.rv_notices);
        adapter_notices = new NoticeAdapter(getContext(), policies);
        rv_notices.setAdapter(adapter_notices);
        rv_notices.setLayoutManager(new GridLayoutManager(getContext(), 2));

        vp2_slider = view.findViewById(R.id.vp2_slider);
        vp2_slider.setAdapter(new SliderAdapter(getContext(), sliders));

        ibt_filter = view.findViewById(R.id.ibt_filter);
    }

    private void initListener() {
        ibt_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new FilterAndRemoveToolDialog(getContext(), HomeFragment.this, tools);
                dialog.show();
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

    @Override
    public void setOnClickAddOrRemoveTool(Tool tool) {
        if (tool.isShow) {
            adapter_tools.addTool(tool);
        } else {
            adapter_tools.removeTool(tool);
        }
    }

    private int getGoodId() {
        LocalDateTime time = LocalDateTime.now();
        int hour = time.getHour();
        int welcome = R.string.evening;
        if (hour < 12) {
            welcome = R.string.morning;
        } else if (hour < 18) {
            welcome = R.string.afternoon;
        }
        return welcome;
    }

    private List<byte[]> getSliders() {
        List<byte[]> ss = new ArrayList<>();
        ss.add(Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.slider_1)));
        ss.add(Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.slider_2)));
        return ss;
    }

    private List<Tool> getTools() {
        List<Tool> tools = new ArrayList<>();
        tools.add(new Tool(ToolId.MY_TIME, getString(R.string.working_time), Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.working_time))));
        tools.add(new Tool(ToolId.CALENDAR_BV, getString(R.string.bv_calendar), Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.bv_calendar))));
        tools.add(new Tool(ToolId.PAYSLIP, getString(R.string.payslip), Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.payslip))));
        tools.add(new Tool(ToolId.SEAT_MAP, getString(R.string.seat_map), Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.seat_map))));
        tools.add(new Tool(ToolId.EMPLOYEES, getString(R.string.employees), Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.employees))));
        tools.add(new Tool(ToolId.ORGANIZATION_CHART, getString(R.string.organization_chart), Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.organization_charts))));
        tools.add(new Tool(ToolId.KNOWLEDGE_BASE, getString(R.string.knowledge_base), Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.knowledge_base))));
        tools.add(new Tool(ToolId.MORE_TOOL, getString(R.string.more_tools), Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.more_tools))));
        SharedPreferences preferences = getContext().getSharedPreferences("tools_status", Context.MODE_PRIVATE);
        for (Tool tool : tools) {
            if (preferences.getBoolean(tool.name, false)) {
                tool.isShow = false;
            }
        }
        return tools;
    }

    @Override
    public void setOnLoadedNotices(List<Policy> policies) {
        this.policies.clear();
        this.policies.addAll(policies);
        if (null != adapter_notices) {
            adapter_notices.notifyDataSetChanged();
        }
    }

    @Override
    public void setOnLoadImageListener(byte[] image, int i) {
        policies.get(i).image = image;
        if (null != adapter_notices) {
            adapter_notices.notifyItemChanged(i);
        }
    }
}
