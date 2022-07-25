package com.banvie.hcm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.banvie.hcm.adapter.OrganizationAdapter;
import com.banvie.hcm.api.ApiService;
import com.banvie.hcm.listener.OnLoadItemOrganizationListener;
import com.banvie.hcm.model.organization_chart.OrganizationChart;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OrganizationActivity extends AppCompatActivity implements OnLoadItemOrganizationListener {

    RecyclerView rv_top, rv_bot;
    List<OrganizationChart> top, bot;
    OrganizationAdapter adapter_top, adapter_bot;
    Disposable disposable;
    String m;

    ObservableOnSubscribe<OrganizationChart> start = new ObservableOnSubscribe<OrganizationChart>() {
        @Override
        public void subscribe(@NonNull ObservableEmitter<OrganizationChart> emitter) throws Throwable {
            OrganizationChart chart = ApiService.apiService.getOrganizationChart().execute().body();

            while (chart.descendants.size() == 1) {
                if (chart != null && !chart.profile.image.equals("")) {

                    chart.image_bytes = ApiService.apiService.getImage(chart.profile.image).execute().body().bytes();
                }
                emitter.onNext(chart);
                chart = chart.descendants.get(0);
            }
            if (chart != null && !chart.profile.image.equals("")) {

                chart.image_bytes = ApiService.apiService.getImage(chart.profile.image).execute().body().bytes();
            }
            emitter.onNext(chart);
            for (OrganizationChart c : chart.descendants) {
                if (c != null && !c.profile.image.equals("")) {

                    c.image_bytes = ApiService.apiService.getImage(c.profile.image).execute().body().bytes();
                }
                emitter.onNext(c);
            }
        }
    };

    ObservableOnSubscribe<OrganizationChart> more = new ObservableOnSubscribe<OrganizationChart>() {
        @Override
        public void subscribe(@NonNull ObservableEmitter<OrganizationChart> emitter) throws Throwable {
            OrganizationChart chart = ApiService.apiService.getOrganizationChart(m).execute().body();

            chart = chart.descendants.get(0);

            int i = top.indexOf(chart);

            for (OrganizationChart c : chart.descendants) {
                if (c != null && !c.profile.image.equals("")) {

                    c.image_bytes = ApiService.apiService.getImage(c.profile.image).execute().body().bytes();
                }
                emitter.onNext(c);
            }
        }
    };

    Observer<OrganizationChart> observer = new Observer<OrganizationChart>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {
            disposable = d;
        }

        @Override
        public void onNext(@NonNull OrganizationChart organizationChart) {
            Log.d("rrr", organizationChart.fullName);
            if (organizationChart.descendants.size() != 0) {
                top.add(organizationChart);
                adapter_top.notifyItemInserted(top.size() - 1);
            } else {
                bot.add(organizationChart);
                adapter_bot.notifyItemInserted(bot.size() - 1);
            }
        }

        @Override
        public void onError(@NonNull Throwable e) {

        }

        @Override
        public void onComplete() {
                disposable.dispose();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);

        initUI();
        initListener();
    }

    private void initUI() {
        setupToolbar();
        top = new ArrayList<>();
        bot = new ArrayList<>();
        rv_bot = findViewById(R.id.rv_bot);
        rv_top = findViewById(R.id.rv_top);

        adapter_bot = new OrganizationAdapter(this, this, bot, -1,false);
        adapter_top = new OrganizationAdapter(this, this, top, 0, true);

        rv_bot.setAdapter(adapter_bot);
        rv_top.setAdapter(adapter_top);
        rv_bot.setLayoutManager(new LinearLayoutManager(this));
        rv_top.setLayoutManager(new LinearLayoutManager(this));

        loadFirst();
    }

    private void loadMore() {
        Observable<OrganizationChart> observable = Observable.create(more);

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void loadFirst() {

        Observable<OrganizationChart> observable = Observable.create(start);

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }

    private void initListener() {

    }

    private void setupToolbar() {
        

        Toolbar toolbar = findViewById(R.id.tb);

        setSupportActionBar(toolbar);

        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText(R.string.organization_chart);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void _setOnClickItemOrganizationListener(int position) {

    }

    @Override
    public void setOnClickItemOrganizationListener(int position, boolean isManager) {
        if (isManager) {
            m = top.get(position).id;
            top.subList(position + 1, top.size()).clear();
            adapter_top.notifyDataSetChanged();
        } else {
            m = bot.get(position).id;
            top.add(bot.get(position));
            adapter_top.notifyItemChanged(top.size() - 1);
        }
        bot.clear();
        adapter_bot.notifyDataSetChanged();
        loadMore();
        //        adapter_bot.isLoad = true;
//        adapter_top.isLoad = true;
//        int focus_top = adapter_top.focus;
//
//        if (focus_top != -1) {
//            adapter_top.focus = -1;
//            adapter_top.notifyItemChanged(focus_top);
//        }
//        int focus_bot = adapter_bot.focus;
//        if (focus_bot != -1) {
//            adapter_bot.focus = -1;
//            adapter_bot.notifyItemChanged(focus_bot);
//        }
//
//        if (!isManager) {
//            int f = adapter_top.focus = top.size();
//            top.add(bot.get(position));
//            adapter_top.notifyItemChanged(f);
//        } else {
//            int f = adapter_bot.focus = -1;
//            adapter_bot.notifyItemChanged(f);
//
//            int len = top.size();
//            for (int i = position + 1; i < len; i++) {
//                top.remove(i);
//            }
//
//            adapter_top.notifyItemRangeRemoved(position + 1, len - position - 1);
//        }
//
//
////        setOnLoadItemOrganizationListener(getEmployees(4));
    }

    @Override
    public void setOnLoadItemOrganizationListener(OrganizationChart organization) {
        bot.clear();
//        bot.addAll(employees);
        adapter_bot.notifyDataSetChanged();
        adapter_bot.isLoad = false;
        adapter_top.isLoad = false;
    }

    @Override
    public void setOnLoadFirstOrganizationListener(OrganizationChart organization) {

        List<OrganizationChart> orgs = organization.descendants;
        OrganizationChart org = organization;
        top.add(org);
        while (orgs.size() == 1) {
            org = org.descendants.get(0);
            top.add(org);
            orgs = org.descendants;
        }
        bot.addAll(orgs);
        adapter_bot.notifyDataSetChanged();
        adapter_top.notifyDataSetChanged();
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

    @Override
    public void setOnLoadImageListener(byte[] image, int i) {

    }
}