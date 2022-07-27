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
import com.banvie.hcm.api.Constant;
import com.banvie.hcm.listener.OnClickItemOrganizationListener;
import com.banvie.hcm.model.organization_chart.OrganizationChart;
import com.banvie.hcm.model.summary.Organization;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OrganizationActivity extends AppCompatActivity
        implements OnClickItemOrganizationListener {

    RecyclerView rv_top, rv_bot;
    OrganizationAdapter adapter_top, adapter_bot;

    Disposable disposable;
    String userId;

    ObservableOnSubscribe<OrganizationChart> subscribeInit = new ObservableOnSubscribe<OrganizationChart>() {
        @Override
        public void subscribe(@NonNull ObservableEmitter<OrganizationChart> emitter) throws Throwable {
            OrganizationChart chart = ApiService.apiService.getOrganizationChart(userId).execute().body();

            while (chart.descendants.size() == 1) {
                emitter.onNext(chart);
                chart = chart.descendants.get(0);
            }

            emitter.onNext(chart);

            for (OrganizationChart c : chart.descendants) {
                emitter.onNext(c);
            }

            emitter.onComplete();
        }
    };

    ObservableOnSubscribe<OrganizationChart> subscribeMore = new ObservableOnSubscribe<OrganizationChart>() {
        @Override
        public void subscribe(@NonNull ObservableEmitter<OrganizationChart> emitter) throws Throwable {
            OrganizationChart chart = ApiService.apiService.getOrganizationChart(userId).execute().body();

            while (chart.descendants.size() == 1) {
                chart = chart.descendants.get(0);
            }

//            Kiểm tra xem có đúng là người manager ở cuối có phải là user hay không.
//            Nếu phải thì  ta cần xóa apdater_bot
//            Nếu không thì ta không làm gì
            if (chart.id.equals(userId)) {

                emitter.onNext(chart);

                for (OrganizationChart c : chart.descendants) {
                    emitter.onNext(c);
                }
            }

            emitter.onComplete();
        }
    };

    Observer<OrganizationChart> observer = new Observer<OrganizationChart>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {
            disposable = d;
        }

        @Override
        public void onNext(@NonNull OrganizationChart organizationChart) {
            if (organizationChart.descendants.size() != 0) {
                adapter_top.organizations.add(organizationChart);
                if (organizationChart.id.equals(userId)) {
                    adapter_bot = new OrganizationAdapter(OrganizationActivity.this, OrganizationActivity.this, new ArrayList<>(), -1, false);
                    rv_bot.setAdapter(adapter_bot);
                    rv_bot.setLayoutManager(new LinearLayoutManager(OrganizationActivity.this));
//                    unFocus();
//                    adapter_top.focus = adapter_top.organizations.size() - 1;
                }
                adapter_top.notifyItemInserted(adapter_top.organizations.size() - 1);
            } else {
                adapter_bot.organizations.add(organizationChart);
                if (organizationChart.id.equals(userId)) {
//                    unFocus();
//                    adapter_bot.focus = adapter_bot.organizations.size() - 1;
                }
                adapter_bot.notifyItemInserted(adapter_bot.organizations.size() - 1);
            }

        }

        @Override
        public void onError(@NonNull Throwable e) {
            disposable.dispose();
        }

        @Override
        public void onComplete() {
            adapter_top.isLoad = false;
            adapter_bot.isLoad = false;

            disposable.dispose();
        }
    };

//    private void unFocus() {
//        int i = adapter_top.focus;
//        adapter_top.focus = -1;
//        if (i != -1) {
//            adapter_top.notifyItemChanged(i);
//        }
//
//        i = adapter_bot.focus;
//        adapter_bot.focus = -1;
//        if (i != -1) {
//            adapter_bot.notifyItemChanged(i);
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);

        initUI();
        initListener();
    }

    private void initUI() {
        setupToolbar();

        if (getIntent().hasExtra("id")) {
            userId = getIntent().getStringExtra("id");
        } else {
            userId = Constant.userInformation.userId;
        }

        rv_top = findViewById(R.id.rv_top);
        rv_bot = findViewById(R.id.rv_bot);

        adapter_top = new OrganizationAdapter(this, this, new ArrayList<>(), -1, true);
        adapter_bot = new OrganizationAdapter(this, this, new ArrayList<>(), -1,false);

        rv_top.setAdapter(adapter_top);
        rv_bot.setAdapter(adapter_bot);
        rv_top.setLayoutManager(new LinearLayoutManager(this));
        rv_bot.setLayoutManager(new LinearLayoutManager(this));

        loadInit();
    }

    private void loadMore() {
        Observable<OrganizationChart> observable = Observable.create(subscribeMore);

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

//    Load org khi vừa mới click vào
    private void loadInit() {

        Observable<OrganizationChart> observable = Observable.create(subscribeInit);

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
    public void setOnClickItemOrganizationListener(int position, boolean isManager) {
        adapter_bot.isLoad = true;
        adapter_top.isLoad = true;
//        unFocus();
        if (isManager) {
            userId = adapter_top.organizations.get(position).id;
            int len = adapter_top.organizations.size();

            for (int i = len - 1; i >= position; i--) {
                adapter_top.organizations.remove(i);
            }
            adapter_top.notifyItemRangeRemoved(position,len - position + 1);

            adapter_bot = new OrganizationAdapter(this, this, new ArrayList<>(), -1, false);
            rv_bot.setAdapter(adapter_bot);
            rv_bot.setLayoutManager(new LinearLayoutManager(this));
        }
        else {
            userId = adapter_bot.organizations.get(position).id;
        }
        loadMore();
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