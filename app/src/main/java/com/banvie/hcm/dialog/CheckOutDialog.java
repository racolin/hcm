package com.banvie.hcm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.banvie.hcm.R;
import com.banvie.hcm.Support;
import com.banvie.hcm.api.RetrofitApi;
import com.banvie.hcm.model.checkout.CheckOut;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutDialog extends Dialog {

    TextView tv_time;
    Button btn_no, btn_yes;

    public CheckOutDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_checkout);

        initUI();
        initListener();
    }

    private void initUI() {
        btn_no = findViewById(R.id.btn_no);
        btn_yes = findViewById(R.id.btn_yes);
        tv_time = findViewById(R.id.tv_time);
        tv_time.setText(Support.convertDateToString(new Date(), "HH'h'mm\''"));
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
                checkout();
                dismiss();
            }
        });
    }
    void checkout() {
        RetrofitApi.checkOut(new Callback<CheckOut>() {
            @Override
            public void onResponse(Call<CheckOut> call, Response<CheckOut> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), R.string.checkout_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), R.string.checkout_not_success, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CheckOut> call, Throwable t) {

            }
        });
    }
}
