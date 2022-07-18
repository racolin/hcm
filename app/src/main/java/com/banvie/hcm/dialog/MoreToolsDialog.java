package com.banvie.hcm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.banvie.hcm.R;
import com.banvie.hcm.Support;
import com.banvie.hcm.adapter.ToolAdapter;
import com.banvie.hcm.type.ToolsType;
import com.banvie.hcm.model.Tool;

import java.util.ArrayList;
import java.util.List;

public class MoreToolsDialog extends Dialog {
    List<Tool> tools;

    public MoreToolsDialog(@NonNull Context context, List<Tool> tools) {
        super(context);
        this.tools = getTools();
    }

    private List<Tool> getTools() {
        List<Tool> tools = new ArrayList<>();
        tools.add(new Tool(getContext().getString(R.string.working_time), Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.working_time))));
        tools.add(new Tool(getContext().getString(R.string.bv_calendar), Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.bv_calendar))));
        tools.add(new Tool(getContext().getString(R.string.payslip), Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.payslip))));
        tools.add(new Tool(getContext().getString(R.string.seat_map), Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.seat_map))));
        tools.add(new Tool(getContext().getString(R.string.employees), Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.employees))));
        tools.add(new Tool(getContext().getString(R.string.organization_chart), Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.organization_charts))));
        tools.add(new Tool(getContext().getString(R.string.knowledge_base), Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.knowledge_base))));
        tools.add(new Tool(getContext().getString(R.string.more_tools), Support.convertDrawableToBytes(getContext().getDrawable(R.drawable.more_tools))));
        SharedPreferences preferences = getContext().getSharedPreferences("tools_status", Context.MODE_PRIVATE);
        for (Tool tool : tools) {
            if (preferences.getBoolean(tool.getName(), false)) {
                tool.setHide();
            }
        }

        tools.get(1).setHide();
        tools.get(2).setHide();
        return tools;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);

        initUI();
        initListener();
    }

    private void initUI() {
        RecyclerView rv_tools = findViewById(R.id.rv);
        rv_tools.setAdapter(new ToolAdapter(getContext(), tools, ToolsType.ALL));
        rv_tools.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

    private void initListener() {

    }
}
