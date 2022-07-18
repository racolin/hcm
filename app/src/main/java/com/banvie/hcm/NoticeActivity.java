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

import com.banvie.hcm.model.policy.Policy;

public class NoticeActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        initUI();
    }

    private void initUI() {
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Policy policy = (Policy) getIntent().getSerializableExtra("notice");

        Toolbar toolbar = findViewById(R.id.tb);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Policy");

        ((TextView) findViewById(R.id.tv_content))
                .setText(HtmlCompat.fromHtml(policy.getLongDescription(), HtmlCompat.FROM_HTML_MODE_COMPACT));

        ((TextView) findViewById(R.id.tv_title))
                .setText(policy.getTopic());

        ((TextView) findViewById(R.id.tv_time))
                .setText(policy.getTimeString("MMM dd, yyyy HH:mm"));

        ((ImageView) findViewById(R.id.iv_notice))
                .setImageBitmap(BitmapFactory.decodeByteArray(policy.getImage(), 0, policy.getImage().length));
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