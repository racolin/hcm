package com.banvie.hcm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.banvie.hcm.LoginActivity;
import com.banvie.hcm.R;

public class IgnoreNotificationDialog extends Dialog {

    Button btn_save, btn_close;
    RadioGroup rdg_time;

    public IgnoreNotificationDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_time_notification);

        initUI();
        initListener();
    }

    private void initUI() {
        btn_close = findViewById(R.id.btn_close);
        btn_save = findViewById(R.id.btn_save);
        rdg_time = findViewById(R.id.rdg_time);
    }

    private void initListener() {
        btn_save.setOnClickListener(new View.OnClickListener() {
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

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void setIgnore(int i) {
        Log.d("rrr", i + "");
    }

    void signOut() {
        SharedPreferences.Editor editor = getContext().getSharedPreferences("token", Context.MODE_PRIVATE).edit();
        editor.remove("access_token").remove("token_type").remove("refresh_token").remove("expired");
        editor.commit();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getContext().startActivity(intent);
        dismiss();
    }
}
