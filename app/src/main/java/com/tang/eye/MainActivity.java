package com.tang.eye;

import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ObbInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.tang.eye.adapter.DailyAdapter;
import com.tang.eye.city.CityActivity;
import com.tang.eye.model.Daily;
import com.tang.eye.presenter.DailyPresenter;
import com.tang.eye.view.IDailyView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IDailyView {

    @BindView(R.id.rv_daily)
    RecyclerView mRvDaily;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;
    @BindView(R.id.pb_daily)
    ProgressBar mPbDaily;
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.container)
    LinearLayout container;
    Fragment fragment;
    private DailyPresenter mDailyPresenter;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    // showDateDialog();
                    return true;
                case R.id.navigation_find:
                    mRvDaily.setVisibility(View.GONE);
                    mPbDaily.setVisibility(View.GONE);
                    fragment = DashboardFragment.newInstance("dashboard");
                    android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.add(R.id.fragment, fragment);
                    transaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    Intent cityIntent=new Intent();
                    cityIntent.setClass(MainActivity.this, CityActivity.class);
                    startActivity(cityIntent);
                    return true;
                case R.id.navigation_my:
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this, MainActivity.class);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                    
                    return true;
            }
            return false;
        }


    };


    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ((EyeApp) getApplication()).getNetComponent().inject(this);

         fragment =  getFragmentManager()
                .findFragmentById(R.id.fragment);



        mDailyPresenter = new DailyPresenter(this);
        mDailyPresenter.loadDaily(String.valueOf(1));
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvDaily.setLayoutManager(linearLayoutManager);
        mRvDaily.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void initDaily() {
        mPbDaily.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadDaily(Daily daily) {
        mPbDaily.setVisibility(View.GONE);
        DailyAdapter dailyAdapter = new DailyAdapter(this, daily);
        mRvDaily.setAdapter(dailyAdapter);
        dailyAdapter.setOnItemClickListener(new DailyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int type, Object itemListBean) {
                if (type == 2) {

                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, VideoDescActivity.class);
                    intent.putExtra("ItemList", (Daily.ItemListBean)itemListBean);
                    startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, view, "shareAnim").toBundle());
                }

            }
        });

    }

    @Override
    public void errorDaily() {
        mPbDaily.setVisibility(View.GONE);
    }
    public void onHind(){
        mNavigation.setVisibility(View.GONE);
    }
    public void onVisiable(){
        mNavigation.setVisibility(View.VISIBLE);
    }
}
