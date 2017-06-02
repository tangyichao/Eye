package com.tang.eye;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.view.RxView;
import com.tang.eye.model.Daily;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class VideoDescActivity extends AppCompatActivity {

    @BindView(R.id.iv_daily)
    ImageView mIvDaily;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.civ_author_icon)
    CircleImageView mCivAuthorIcon;
    @BindView(R.id.tv_author_name)
    TextView mTvAuthorName;
    @BindView(R.id.tv_author_desc)
    TextView mTvAuthorDesc;
    @BindView(R.id.cv_author)
    CardView mCvAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_video_desc);
        ButterKnife.bind(this);
        Daily.ItemListBean itemListBean = (Daily.ItemListBean) getIntent().getSerializableExtra("ItemList");
        Glide.with(this).load(itemListBean.getData().getCover().getFeed()).into(mIvDaily);
        mTvTitle.setText(itemListBean.getData().getTitle());
        int duration = itemListBean.getData().getDuration();
        int minute = duration / 60;//Minute second
        int second = duration % 60;
        mTvTime.setText("#" + itemListBean.getData().getCategory() + " / " + minute + "' " + second + "''");
        mTvDesc.setText(itemListBean.getData().getDescription());
        if(itemListBean.getData().getAuthor()!=null){
            Glide.with(this).load(itemListBean.getData().getAuthor().getIcon()).into(mCivAuthorIcon);
            mTvAuthorName.setText(itemListBean.getData().getAuthor().getName());
            mTvAuthorDesc.setText(itemListBean.getData().getAuthor().getDescription());
        }else{
            mCvAuthor.setVisibility(View.GONE);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RxView.clicks(fab)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        Log.i("debug", "点击了");
                    }
                });


    }


}
