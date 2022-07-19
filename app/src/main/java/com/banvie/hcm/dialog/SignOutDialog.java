package com.banvie.hcm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.banvie.hcm.LoginActivity;
import com.banvie.hcm.R;
import com.banvie.hcm.api.Constant;
import com.banvie.hcm.api.RetrofitApi;
import com.banvie.hcm.model.checkout.CheckOut;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        btn_yes = findViewById(R.id.btn_yes);
        ((TextView) findViewById(R.id.tv_content)).setText(R.string.sign_out_content);
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
