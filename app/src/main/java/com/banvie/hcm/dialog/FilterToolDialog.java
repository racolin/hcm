package com.banvie.hcm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.banvie.hcm.R;
import com.banvie.hcm.model.Tool;

import java.util.List;

public class FilterToolDialog extends Dialog {

    List<Tool> showing, hiding;

    public FilterToolDialog(@NonNull Context context, List<Tool> showing, List<Tool> hiding) {
        super(context);
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

    }

    private void initListener() {

    }
}
