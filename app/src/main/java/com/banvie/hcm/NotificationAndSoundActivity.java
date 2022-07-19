package com.banvie.hcm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.banvie.hcm.adapter.NotifySoundAdapter;
import com.banvie.hcm.api.RetrofitApi;
import com.banvie.hcm.listener.OnLoadNotifySoundListener;
import com.banvie.hcm.model.notification_sound.NotifySound;
import com.banvie.hcm.model.notification_sound.NotifySoundContainer;
import com.banvie.hcm.model.policy.Policy;

import java.util.ArrayList;
import java.util.List;

public class NotificationAndSoundActivity extends AppCompatActivity
        implements OnLoadNotifySoundListener {

    RecyclerView rv_ns;
    NotifySoundAdapter adapter;
    List<NotifySoundContainer> containers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_and_sound);

        initUI();
        initListener();
    }

    private void setupToolbar() {
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Toolbar toolbar = findViewById(R.id.tb);

        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText(R.string.notify_sounds);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initUI() {
        setupToolbar();

        containers = new ArrayList<>();

        adapter = new NotifySoundAdapter(this, containers);

        rv_ns = findViewById(R.id.rv_ns);
        rv_ns.setAdapter(adapter);
        rv_ns.setLayoutManager(new LinearLayoutManager(this));

        RetrofitApi.getNotifySound(this);
    }

    private void initListener() {

    }

    @Override
    public void setOnLoadNotifySoundListener(List<NotifySoundContainer> containers) {
        this.containers.clear();
        this.containers.addAll(containers);
        adapter.notifyDataSetChanged();
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