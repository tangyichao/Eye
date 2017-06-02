package com.tang.eye.presenter;

import com.tang.eye.view.IDailyView;
import com.tang.eye.model.DailyModel;

/**
 * Created by tangyc on 2017/5/9.
 */

public class DailyPresenter implements IDailyPresenter {
    private DailyModel dailyModel;
    public DailyPresenter(IDailyView dailyView){
        dailyModel=new DailyModel(dailyView);
    }
    @Override
    public void loadDaily(String num) {
        dailyModel.loadDailyModel(num);
    }
}
