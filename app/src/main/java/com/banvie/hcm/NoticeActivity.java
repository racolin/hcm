package com.banvie.hcm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.banvie.hcm.api.ApiService;
import com.banvie.hcm.model.policy.Policy;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        initUI();
    }

    private void setupToolbar() {
        

        Toolbar toolbar = findViewById(R.id.tb);

        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText(R.string.policy);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initUI() {

        Policy policy = (Policy) getIntent().getSerializableExtra("notice");

        setupToolbar();

        ((TextView) findViewById(R.id.tv_content))
                .setText(HtmlCompat.fromHtml(policy.longDescription, HtmlCompat.FROM_HTML_MODE_COMPACT));

        ((TextView) findViewById(R.id.tv_title))
                .setText(policy.topic);

        ((TextView) findViewById(R.id.tv_time))
                .setText(policy.getTimeString("MMM dd, yyyy HH:mm"));

        ApiService.apiService.getImage(policy.thumbnail).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        policy.image = response.body().bytes();
                        ((ImageView) findViewById(R.id.iv_notice))
                                .setImageBitmap(BitmapFactory.decodeByteArray(policy.image, 0, policy.image.length));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private Policy getData() {
        return (Policy) getIntent().getSerializableExtra("notice");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

        }

        return true;
    }
}