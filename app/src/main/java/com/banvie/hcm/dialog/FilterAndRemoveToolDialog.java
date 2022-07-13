package com.banvie.hcm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.banvie.hcm.R;
import com.banvie.hcm.adapter.ToolAdapter;
import com.banvie.hcm.listener.OnClickAddOrRemoveToolListener;
import com.banvie.hcm.model.Tool;
import com.banvie.hcm.type.ToolsType;

import java.util.List;

public class FilterAndRemoveToolDialog extends Dialog implements OnClickAddOrRemoveToolListener {

    List<Tool> showing, hiding;
    RecyclerView rv_tools_show, rv_tools_hide;
    ToolAdapter adapter_show, adapter_hide;
    OnClickAddOrRemoveToolListener listener;

    public FilterAndRemoveToolDialog(@NonNull Context context, OnClickAddOrRemoveToolListener listener,
                                     List<Tool> showing, List<Tool> hiding) {
        super(context);
        this.listener = listener;
        this.showing =showing;
        this.hiding = hiding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_filter_tools);

        initUI();
        initListener();
    }

    private void initUI() {
        LinearLayout layout = findViewById(R.id.layout);

        View show = LayoutInflater.from(getContext()).inflate(R.layout.tool_fragment, null);
        ((TextView) show.findViewById(R.id.tv_title)).setText(R.string.showing_tools);
        rv_tools_show = show.findViewById(R.id.rv_tools);
        adapter_show = new ToolAdapter(getContext(), this, showing, ToolsType.SHOWING);
        rv_tools_show.setAdapter(adapter_show);
        rv_tools_show.setLayoutManager(new GridLayoutManager(getContext(), 3));
        layout.addView(show);

        View hide = LayoutInflater.from(getContext()).inflate(R.layout.tool_fragment, null);
        ((TextView) hide.findViewById(R.id.tv_title)).setText(R.string.hidden_tools);
        rv_tools_hide = hide.findViewById(R.id.rv_tools);
        adapter_hide = new ToolAdapter(getContext(), this, hiding, ToolsType.HIDING);
        rv_tools_hide.setAdapter(adapter_hide);
        rv_tools_hide.setLayoutManager(new GridLayoutManager(getContext(), 3));
        layout.addView(hide);

//
//        ((TextView) findViewById(R.id.tv_title_show)).setText(R.string.showing_tools);
//
//        ((TextView) findViewById(R.id.tv_title_hide)).setText(R.string.hidden_tools);
//
//        rv_tools_show = findViewById(R.id.rv_tools_show);
//        rv_tools_show.setAdapter(new ToolAdapter(getContext(), showing, ToolsType.SHOWING));
//        rv_tools_show.setLayoutManager(new GridLayoutManager(getContext(), 3));
//
//        rv_tools_hide = findViewById(R.id.rv_tools_hide);
//        rv_tools_hide.setAdapter(new ToolAdapter(getContext(), hiding, ToolsType.HIDING));
//        rv_tools_hide.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

    private void initListener() {

    }

    @Override
    public void setOnClickAddOrRemoveTool(Tool tool) {
        if (tool.isShow()) {
            adapter_show.addTool(tool);
        } else {
            adapter_hide.addTool(tool);
        }
        listener.setOnClickAddOrRemoveTool(tool);
    }
}
