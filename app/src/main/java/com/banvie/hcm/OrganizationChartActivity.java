package com.banvie.hcm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.banvie.hcm.adapter.OrganizationChartAdapter;
import com.banvie.hcm.api.ApiService;
import com.banvie.hcm.api.Constant;
import com.banvie.hcm.listener.OnLoadItemOrganizationListener;
import com.banvie.hcm.model.organization_chart.OrganizationChart;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrganizationChartActivity extends AppCompatActivity
        implements OnLoadItemOrganizationListener
{

    RecyclerView rv_bot;

    OrganizationChartAdapter adapter;
    LinearLayout top, top_1;
    RelativeLayout layout, layout_1;
    ImageView iv_avatar, iv_avatar_1;
    ImageButton ibt_info, ibt_info_1;
    TextView tv_avatar, tv_avatar_1, tv_type, tv_type_1, tv_name, tv_name_1;
    String id, id_1;

    Disposable disposable;

    String userId;

    ObservableOnSubscribe<OrganizationChart> subscribe = new ObservableOnSubscribe<OrganizationChart>() {
        @Override
        public void subscribe(@NonNull ObservableEmitter<OrganizationChart> emitter) throws Throwable {
            OrganizationChart chart = ApiService.apiService.getOrganizationChart(userId).execute().body();
            if (chart.profile.image != null && !chart.profile.image.equals("")) {
                chart.image_bytes = Support.reduceImage(ApiService.apiService.getImage(chart.profile.image).execute().body().bytes());
                OrganizationChart c = chart;
                if (chart.descendants.size() == 1) {
                    c = c.descendants.get(0);
                    if (c.profile.image != null && !c.profile.image.equals("")) {
                        c.image_bytes = Support.reduceImage(ApiService.apiService.getImage(c.profile.image).execute().body().bytes());
                    }
                }
            }
            emitter.onNext(chart);
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
            top.setVisibility(View.VISIBLE);
            ((GradientDrawable) layout.getBackground().mutate())
                    .setStroke(1, getColor(R.color.stroke));
            ((GradientDrawable) layout_1.getBackground().mutate())
                    .setStroke(1, getColor(R.color.stroke));

            tv_name.setText(organizationChart.fullName);
            tv_type.setText(organizationChart.jobTitle.name);
//            if (organizationChart.image_bytes != null) {
//                tv_avatar.setText("");
//                iv_avatar.setImageBitmap(
//                        BitmapFactory
//                                .decodeByteArray(
//                                        organizationChart.image_bytes,
//                                        0,
//                                        organizationChart.image_bytes.length));
//            } else {
                tv_avatar.setText(organizationChart.username.substring(0, 1).toUpperCase());
//            }
            if (organizationChart.id.equals(userId)) {
                GradientDrawable background = (GradientDrawable) layout.getBackground();
                background.mutate();
                background.setStroke(2, getColor(R.color.yellow));
            }
            id = organizationChart.id;
//            Log.d("rrrxxx0", id);
            if (organizationChart.descendants.size() == 1) {
                top_1.setVisibility(View.VISIBLE);
                organizationChart = organizationChart.descendants.get(0);
                if (organizationChart.image_bytes != null) {
                    tv_avatar_1.setText("");
                    iv_avatar_1.setImageBitmap(
                            BitmapFactory
                                    .decodeByteArray(
                                            organizationChart.image_bytes,
                                            0,
                                            organizationChart.image_bytes.length));
                } else {
                    tv_avatar_1.setText(organizationChart.username.substring(0, 1));
                }
                if (organizationChart.id.equals(userId)) {
                    GradientDrawable background = (GradientDrawable) layout_1.getBackground();
                    background.mutate();
//                    Log.d("ssssssrrr", id_1);
                    background.setStroke(2, getColor(R.color.yellow));
                }
                id_1 = organizationChart.id;
                tv_name_1.setText(organizationChart.fullName);
                tv_type_1.setText(organizationChart.jobTitle.name);
//                Log.d("rrrxxx1", id_1);
            } else {
                top_1.setVisibility(View.GONE);
            }
            adapter.organizations.clear();
            adapter.organizations.addAll(organizationChart.descendants);
            OrganizationChart o = new OrganizationChart();
            o.id = userId;
            adapter.focus = organizationChart.descendants.indexOf(o);
            adapter.notifyDataSetChanged();


            final int len = organizationChart.descendants.size();
            OrganizationChart chart = organizationChart;
            for (int i = 0; i < len; i++) {
                if (chart.descendants.get(i).profile.image != null && !chart.descendants.get(i).profile.image.equals("")) {
                    int finalI = i;
                    ApiService.apiService.getImage(chart.descendants.get(i).profile.image).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                adapter.organizations.get(finalI).image_bytes = response.body().bytes();
                                adapter.notifyItemChanged(finalI);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (finalI == len - 1) {
                                adapter.isLoad = false;
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }
            }
        }

        @Override
        public void onError(@NonNull Throwable e) {

        }

        @Override
        public void onComplete() {
//            adapter.isLoad = false;
            disposable.dispose();
        }
    };

    Observable<OrganizationChart> observable = Observable.create(subscribe);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_chart);

        userId = getIntent().getStringExtra("id");
        userId = userId == null ? Constant.userInformation.userId : userId;

        initUI();
        initListener();

    }


    private void setupToolbar() {

        Toolbar toolbar = findViewById(R.id.tb);

        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText(R.string.organization_chart);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initUI() {
        setupToolbar();

        layout = findViewById(R.id.layout);
        top = findViewById(R.id.top);
        iv_avatar = findViewById(R.id.iv_avatar);
        ibt_info = findViewById(R.id.ibt_info);
        tv_avatar = findViewById(R.id.tv_avatar);
        tv_type = findViewById(R.id.tv_type);
        tv_name = findViewById(R.id.tv_name);

        layout_1 = findViewById(R.id.layout_1);
        top_1 = findViewById(R.id.top_1);
        iv_avatar_1 = findViewById(R.id.iv_avatar_1);
        ibt_info_1 = findViewById(R.id.ibt_info_1);
        tv_avatar_1 = findViewById(R.id.tv_avatar_1);
        tv_type_1 = findViewById(R.id.tv_type_1);
        tv_name_1 = findViewById(R.id.tv_name_1);

        rv_bot = findViewById(R.id.rv_bot);
        adapter = new OrganizationChartAdapter(this, this, new ArrayList<>(), -1);
        rv_bot.setAdapter(adapter);
        rv_bot.setLayoutManager(new LinearLayoutManager(this));

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void initListener() {
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = adapter.focus;
                adapter.focus = -1;
                adapter.notifyItemChanged(i);

                userId = id;

                observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(observer);
            }
        });
        top_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userId = id_1;

                observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(observer);
            }
        });
    }

    @Override
    public void setOnLoadImageListener(byte[] image, int i) {

    }

    @Override
    public void _setOnClickItemOrganizationListener(int position) {

        int i = adapter.focus;
        adapter.focus = -1;
        adapter.notifyItemChanged(i);

        userId = adapter.organizations.get(position).id;

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    @Override
    public void setOnClickItemOrganizationListener(int position, boolean isManager) {

    }

    @Override
    public void setOnLoadItemOrganizationListener(OrganizationChart organization) {

    }

    @Override
    public void setOnLoadFirstOrganizationListener(OrganizationChart organizations) {

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