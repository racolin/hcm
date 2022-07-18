package com.banvie.hcm.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.banvie.hcm.R;
import com.banvie.hcm.type.ToolsType;
import com.banvie.hcm.adapter.ToolAdapter;
import com.banvie.hcm.model.Tool;

import java.util.List;

public class ToolFragment extends Fragment {

    RecyclerView rv_tools;
    List<Tool> tools;
    String title;
    int columns;

    public ToolFragment() {

    }

    public ToolFragment(List<Tool> tools, String title, int columns) {
        this.tools = tools;
        this.columns = columns;
        this.title = title;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_tool, container, false);
        ((TextView) view.findViewById(R.id.tv_title)).setText(title);
        rv_tools = view.findViewById(R.id.rv);
        rv_tools.setAdapter(new ToolAdapter(getContext(), tools, ToolsType.HIGH_LIGHT));
        rv_tools.setLayoutManager(new GridLayoutManager(getContext(), columns));
        return view;
    }
}
