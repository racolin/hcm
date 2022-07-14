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
import com.banvie.hcm.api.RetrofitApi;
import com.banvie.hcm.dialog.FilterAndRemoveToolDialog;
import com.banvie.hcm.R;
import com.banvie.hcm.Support;
import com.banvie.hcm.adapter.NoticeAdapter;
import com.banvie.hcm.adapter.SliderAdapter;
import com.banvie.hcm.listener.OnClickAddOrRemoveToolListener;
import com.banvie.hcm.listener.OnLoadedNoticesListener;
import com.banvie.hcm.model.policy.Notice;
import com.banvie.hcm.model.Tool;
import com.banvie.hcm.type.ToolsType;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements
        OnClickAddOrRemoveToolListener, OnLoadedNoticesListener {
    List<Tool> tools;
    RecyclerView rv_tools;
    ToolAdapter adapter_tools;
    NoticeAdapter adapter_notices;

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

        View v = LayoutInflater.from(getContext()).inflate(R.layout.tool_fragment, null);
        ((TextView) v.findViewById(R.id.tv_title)).setText(R.string.bv_tools);
        tools = getTools();
        adapter_tools = new ToolAdapter(getContext(), getToolShown(), ToolsType.HIGH_LIGHT);
        rv_tools = v.findViewById(R.id.rv_tools);
        rv_tools.setAdapter(adapter_tools);
        rv_tools.setLayoutManager(new GridLayoutManager(getContext(), 4));
        FrameLayout layout = view.findViewById(R.id.fg_tools);
        layout.addView(v);

        handler = new Handler(Looper.myLooper());
        getData();

        rv_notices = view.findViewById(R.id.rv_notices);
        adapter_notices = new NoticeAdapter(getContext(), new ArrayList<>());
        rv_notices.setAdapter(adapter_notices);

        rv_notices.setLayoutManager(new GridLayoutManager(
                getContext(), 2
        ));

        vp2_slider = view.findViewById(R.id.vp2_slider);

        vp2_slider.setAdapter(new SliderAdapter(getContext(), sliders));
        ibt_filter = view.findViewById(R.id.ibt_filter);

        RetrofitApi.callNotices(this);
    }

    private void initListener() {
        ibt_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new FilterAndRemoveToolDialog(getContext(), HomeFragment.this, getToolShown(), getHiddenTool());
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
        if (tool.isShow()) {
            adapter_tools.addTool(tool);
        } else {
            adapter_tools.removeTool(tool);
        }
    }

    private void getData() {
        sliders = new ArrayList<>();
        sliders.add(Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.slider_1)));
        sliders.add(Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.slider_2)));
    }

    private List<Tool> getHiddenTool() {
        List<Tool> hides = new ArrayList<>();
        for (Tool tool : tools) {
            if (tool.isHide()) {
                hides.add(tool);
            }
        }
        return hides;
    }

    private List<Tool> getToolShown() {
        List<Tool> shows = new ArrayList<>();
        for (Tool tool : tools) {
            if (tool.isShow()) {
                shows.add(tool);
            }
        }
        return shows;
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
                tool.setHide();
            }
        }
        return tools;
    }

    @Override
    public void setOnLoadedNotices(List<Notice> notices) {
        adapter_notices.setNotices(notices);
        adapter_notices.notifyDataSetChanged();
    }

    @Override
    public void setOnLoadImageListener(byte[] image, int i) {
        adapter_notices.getNotices().get(i).setImage(image);
        adapter_notices.notifyItemChanged(i);
    }
}
