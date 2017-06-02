package com.tang.eye;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tang.eye.adapter.DailyAdapter;
import com.tang.eye.model.Daily;
import com.tang.eye.presenter.DailyPresenter;
import com.tang.eye.view.IDailyView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by tangyc on 2017/6/1.
 */

public class DashboardFragment extends Fragment implements IDailyView {
    @BindView(R.id.rv_daily)
    RecyclerView mRvDaily;

    Unbinder unbinder;
    private DailyPresenter mDailyPresenter;
    public static DashboardFragment newInstance(String name) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        DashboardFragment dashboardFragment = new DashboardFragment();
        dashboardFragment.setArguments(bundle);
        return dashboardFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("debug","onCreate");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("debug","onCreateView");
        View view = inflater.inflate(R.layout.fragment_bashboard, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle=getArguments();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mDailyPresenter = new DailyPresenter(this);
        mDailyPresenter.loadDaily(String.valueOf(1));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRvDaily.setLayoutManager(linearLayoutManager);
        mRvDaily.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void initDaily() {

    }

    @Override
    public void loadDaily(Daily daily) {
        DailyAdapter dailyAdapter = new DailyAdapter(getActivity(), daily);
        mRvDaily.setAdapter(dailyAdapter);
        mRvDaily.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //屏幕中最后一个可见子项的position
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                //当前屏幕所看到的子项个数
                int visibleItemCount = layoutManager.getChildCount();
                //当前RecyclerView的所有子项个数
                int totalItemCount = layoutManager.getItemCount();
                //RecyclerView的滑动状态
                int state = recyclerView.getScrollState();
                if(visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == RecyclerView.SCROLL_STATE_IDLE){
                    Log.i("debug","底部"+"newState"+newState+"state"+state);
                    ((MainActivity)getActivity()).onVisiable();
                }else if(state == RecyclerView.SCROLL_STATE_IDLE){
                    Log.i("debug","底部");
                    ((MainActivity)getActivity()).onVisiable();
                }else{
                    ((MainActivity)getActivity()).onHind();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.i("debug","dx=="+dx+"dy=="+dy);
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public void errorDaily() {

    }
}
