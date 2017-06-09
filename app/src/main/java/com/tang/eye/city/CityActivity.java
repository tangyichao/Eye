package com.tang.eye.city;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.tang.eye.R;
import com.tang.eye.adapter.RecyclerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tangyc on 2017/6/9.
 */

public class CityActivity extends AppCompatActivity implements ICityView {
    @BindView(R.id.recycler)
    FastScrollRecyclerView  recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        ButterKnife.bind(this);

        CityPresenter presenter=new CityPresenter(this);
        presenter.load(this);

    }

    @Override
    public void loadCityList( List<CityList.PBean>  list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerAdapter(list));
    }

    @Override
    public void loadError() {

    }
}
