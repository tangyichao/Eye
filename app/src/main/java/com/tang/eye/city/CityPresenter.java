package com.tang.eye.city;

import android.content.Context;

/**
 * Created by tangyc on 2017/6/9.
 */

public class CityPresenter implements ICityPresenter {
    private CityModel cityModel;
    public CityPresenter(ICityView cityView)
    {
        cityModel=new CityModel(cityView);

    }
    @Override
    public void load(Context context){
        cityModel.load(context);
    }
}
