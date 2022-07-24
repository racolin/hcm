package com.banvie.hcm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;

import com.banvie.hcm.LoginActivity;
import com.banvie.hcm.R;
import com.banvie.hcm.Support;

import java.util.Date;

public class SignOutDialog extends Dialog {

    Button btn_no, btn_yes;

    public SignOutDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_yes_no);

        initUI();
        initListener();
    }

    private void initUI() {
        btn_no = findViewById(R.id.btn_no);
        btn_no.setText(R.string.no);
        btn_yes = findViewById(R.id.btn_yes);
        btn_yes.setText(R.string.yes);
        FrameLayout layout_content = findViewById(R.id.layout_content);
        LayoutInflater.from(getContext()).inflate(R.layout.content_checkout, layout_content, true);
        ((TextView) findViewById(R.id.tv_content)).setText(R.string.sign_out_content);
        ((TextView) findViewById(R.id.tv_title)).setText(R.string.notification_content);
    }

    private void initListener() {
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
                dismiss();
            }
        });
    }
    void signOut() {
        SharedPreferences.Editor editor = getContext().getSharedPreferences("token", Context.MODE_PRIVATE).edit();
        editor.putString("access_token", "");
        editor.putString("token_type", "");
        editor.putString("refresh_token", "");
        editor.putLong("expired", 0);
        editor.commit();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getContext().startActivity(intent);
        dismiss();
    }
}
