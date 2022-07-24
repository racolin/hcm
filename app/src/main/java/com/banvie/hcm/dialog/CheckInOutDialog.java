package com.banvie.hcm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;

import com.banvie.hcm.R;
import com.banvie.hcm.Support;
import com.banvie.hcm.api.RetrofitApi;
import com.banvie.hcm.listener.OnCheckOutListener;
import com.banvie.hcm.model.checkout.CheckOutContainer;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckInOutDialog extends Dialog implements OnCheckOutListener {

    Button btn_no, btn_yes;

    public CheckInOutDialog(@NonNull Context context) {
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
        LayoutInflater.from(getContext()).inflate(R.layout.content_checkout, layout_content, true);
        String s = getContext().getString(R.string.checkout) + " <strong>" + Support.convertDateToString(new Date(), "HH'h'mm\''") + "</strong>";
        ((TextView) findViewById(R.id.tv_content)).setText(HtmlCompat.fromHtml(s, HtmlCompat.FROM_HTML_MODE_COMPACT));
        ((TextView) findViewById(R.id.tv_title)).setText(R.string.checkinout_title);
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
        RetrofitApi.checkOut(this);
    }

    @Override
    public void setOnCheckOutListener(boolean b) {
        if (b) {
            Toast.makeText(getContext(), R.string.checkout_success, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), R.string.checkout_not_success, Toast.LENGTH_SHORT).show();
        }
    }
}
