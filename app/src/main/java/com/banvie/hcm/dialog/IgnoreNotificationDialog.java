package com.banvie.hcm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.banvie.hcm.R;

public class IgnoreNotificationDialog extends Dialog {

    Button btn_no, btn_yes;
    RadioGroup rdg_time;

    public IgnoreNotificationDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_yes_no);
        getWindow().setBackgroundDrawableResource(R.drawable.round_32);
        initUI();
        initListener();
    }


    private void initUI() {
        btn_no = findViewById(R.id.btn_no);
        btn_no.setText(R.string.no);
        btn_yes = findViewById(R.id.btn_yes);
        btn_yes.setText(R.string.yes);
        FrameLayout layout_content = findViewById(R.id.layout_content);
        LayoutInflater.from(getContext()).inflate(R.layout.content_ignore_notification, layout_content, true);
        rdg_time = findViewById(R.id.rdg_time);

        ((TextView) findViewById(R.id.tv_title)).setText(R.string.turn_off_notify);
    }

    private void initListener() {
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 0;
                switch (rdg_time.getCheckedRadioButtonId()) {
                    case R.id.rd_15:
                        i = 1;
                        break;
                    case R.id.rd_30:
                        i = 2;
                        break;
                    case R.id.rd_1h:
                        i = 3;
                        break;
                    case R.id.rd_4h:
                        i = 4;
                        break;
                }
                setIgnore(i);
                dismiss();
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void setIgnore(int i) {
        Log.d("rrr", i + "");
    }
}
