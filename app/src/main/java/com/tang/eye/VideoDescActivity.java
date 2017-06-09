package com.tang.eye;

import android.animation.Animator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.tang.eye.model.Daily;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.centerX;

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
    @BindView(R.id.ctl)
    CollapsingToolbarLayout mCtl;
    @BindView(R.id.cl)
    CoordinatorLayout mCl;
    @BindView(R.id.cv_title_time)
    CardView mCvTitleTime;
    @BindView(R.id.cv_desc)
    CardView mCvDesc;

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
        setSupportActionBar(toolbar);
        Object itemListBean = getIntent().getSerializableExtra("ItemList");
        if (itemListBean instanceof Daily.ItemListBean) {
            //  mCl.setBackgroundColor(C.bgColor);
            mCtl.setTitle(((Daily.ItemListBean) itemListBean).getData().getTitle());
            Glide.with(this).load(((Daily.ItemListBean) itemListBean).getData().getCover().getFeed()).into(mIvDaily);

            mTvTitle.setText(((Daily.ItemListBean) itemListBean).getData().getTitle());
            int duration = ((Daily.ItemListBean) itemListBean).getData().getDuration();
            int minute = duration / 60;//Minute second
            int second = duration % 60;
            mTvTime.setText("#" + ((Daily.ItemListBean) itemListBean).getData().getCategory() + " / " + minute + "' " + second + "''");
            mTvDesc.setText(((Daily.ItemListBean) itemListBean).getData().getDescription());
            if (((Daily.ItemListBean) itemListBean).getData().getAuthor() != null) {
                Glide.with(this).load(((Daily.ItemListBean) itemListBean).getData().getAuthor().getIcon()).into(mCivAuthorIcon);
                mTvAuthorName.setText(((Daily.ItemListBean) itemListBean).getData().getAuthor().getName());
                mTvAuthorDesc.setText(((Daily.ItemListBean) itemListBean).getData().getAuthor().getDescription());
            } else {
                mCvAuthor.setVisibility(View.GONE);
            }
        } else if (itemListBean instanceof Daily.ItemListBean.DataBean.ItemListBeanX) {
            Glide.with(this).load(((Daily.ItemListBean.DataBean.ItemListBeanX) itemListBean).getData().getCover().getFeed()).into(mIvDaily);
            mTvTitle.setText(((Daily.ItemListBean.DataBean.ItemListBeanX) itemListBean).getData().getTitle());
            int duration = ((Daily.ItemListBean.DataBean.ItemListBeanX) itemListBean).getData().getDuration();
            int minute = duration / 60;//Minute second
            int second = duration % 60;
            mTvTime.setText("#" + ((Daily.ItemListBean.DataBean.ItemListBeanX) itemListBean).getData().getCategory() + " / " + minute + "' " + second + "''");
            mTvDesc.setText(((Daily.ItemListBean.DataBean.ItemListBeanX) itemListBean).getData().getDescription());
            if (((Daily.ItemListBean.DataBean.ItemListBeanX) itemListBean).getData().getAuthor() != null) {
                Glide.with(this).load(((Daily.ItemListBean.DataBean.ItemListBeanX) itemListBean).getData().getAuthor().getIcon()).into(mCivAuthorIcon);
                mTvAuthorName.setText(((Daily.ItemListBean.DataBean.ItemListBeanX) itemListBean).getData().getAuthor().getName());
                mTvAuthorDesc.setText(((Daily.ItemListBean.DataBean.ItemListBeanX) itemListBean).getData().getAuthor().getDescription());
            } else {
                mCvAuthor.setVisibility(View.GONE);
            }
//            Animation anim = AnimationUtils.makeInChildBottomAnimation(this);
//            anim.setDuration(1000);
//            mCvAuthor.startAnimation(anim);
//            mCvDesc.startAnimation(anim);
//            mCvTitleTime.startAnimation(anim);
            int centerX=(mCvDesc.getRight()+mCvDesc.getLeft())/2;
            int centerY=(mCvDesc.getBottom()+mCvDesc.getTop())/2;
            int startRadius=0;
            int endRadius= (int) Math.sqrt(mCvDesc.getWidth()*mCvDesc.getWidth()+mCvDesc.getHeight()*mCvDesc.getHeight());
            Animator animator= ViewAnimationUtils.createCircularReveal(mCvDesc,centerX,centerY,startRadius,endRadius);
            animator.setDuration(4000);
            animator.start();
//            mCvAuthor.animate().alpha(1).setStartDelay(100).start();
//            mCvTitleTime.animate().alpha(1).setStartDelay(100).start();
//            mCvDesc.animate().alpha(1).setStartDelay(100).start();

        }


    }


}
