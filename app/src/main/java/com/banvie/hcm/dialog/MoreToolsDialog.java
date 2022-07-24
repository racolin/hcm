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
import com.banvie.hcm.type.ToolId;
import com.banvie.hcm.type.ToolsType;
import com.banvie.hcm.model.Tool;

import java.util.ArrayList;
import java.util.List;

public class MoreToolsDialog extends Dialog {
    List<Tool> tools;

    public MoreToolsDialog(@NonNull Context context, List<Tool> tools) {
        super(context);
        this.tools = tools;
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
