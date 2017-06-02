package com.tang.eye.model;

import com.tang.eye.ApiManager;
import com.tang.eye.api.DailyApi;
import com.tang.eye.view.IDailyView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tangyc on 2017/5/9.
 */

public class DailyModel implements com.tang.eye.model.IDailyModel {
    private IDailyView dailyView;
    public DailyModel(IDailyView dailyView)
    {
            this.dailyView=dailyView;
    }
    @Override
    public void loadDailyModel(String num) {
        dailyView.initDaily();
        ApiManager.getRetrofit().create(DailyApi.class).getDaily()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Daily>() {
                    @Override
                    public void accept(@NonNull Daily daily) throws Exception {
                        dailyView.loadDaily(daily);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dailyView.errorDaily();

                    }
                });

    }


}
