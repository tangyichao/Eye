package com.tang.eye.city;

import android.content.Context;
import android.content.SharedPreferences;

import com.tang.eye.ApiManager;
import com.tang.eye.C;
import com.tang.eye.api.CityApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tangyc on 2017/6/9.
 */

public class CityModel{
    private ICityView cityview;
    public CityModel(ICityView cityView) {
        this.cityview=cityView;
    }

    public void load(Context context) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, "city", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
         final DaoSession daoSession = daoMaster.newSession();
        SharedPreferences sp=context.getSharedPreferences("config",Context.MODE_PRIVATE);
       final SharedPreferences.Editor editor= sp.edit();
        if(!sp.getBoolean("is_save_city",false)){
            ApiManager.getRetrofit(C.MTIME_API).create(CityApi.class).getCityList()
                    .map(new Function<CityList, List<CityList.PBean>>() {
                        @Override
                        public List<CityList.PBean> apply(@NonNull CityList cityList) throws Exception {
                            List<CityList.PBean> list = cityList.getP();
                            Collections.sort(list, new Comparator<CityList.PBean>() {
                                @Override
                                public int compare(CityList.PBean o1, CityList.PBean o2) {
                                    return o1.getPinyinFull().compareToIgnoreCase(o2.getPinyinFull());
                                }
                            });
                            for(int i=0;i<list.size();i++)
                            {
                                CityList.PBean pBean=list.get(i);
                                City city=new City();
                                city.setCount(pBean.getCount());
                                city.setCityId(pBean.getId());
                                city.setN(pBean.getN());
                                city.setPinyinFull(pBean.getPinyinFull());
                                city.setPinyinShort(pBean.getPinyinShort());
                                daoSession.getCityDao().insert(city);
                            }
                            return list;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<CityList.PBean>>() {
                        @Override
                        public void accept(@NonNull List<CityList.PBean> list) throws Exception {
                            cityview.loadCityList(list);
                            editor.putBoolean("is_save_city",true);
                            editor.apply();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            cityview.loadError();
                            editor.putBoolean("is_save_city",false);
                            editor.apply();
                        }
                    });
        }else{
          List<City> list= daoSession.getCityDao().queryBuilder().build().list();
            List<CityList.PBean> pBeanList=new ArrayList<>();
            for(int i=0;i<list.size();i++)
            {
                City city=list.get(i);
                CityList.PBean pBean=new CityList.PBean();
                pBean.setCount(city.getCount());
                pBean.setId(city.getCityId());
                pBean.setN(city.getN());
                pBean.setPinyinFull(city.getPinyinFull());
                pBean.setPinyinShort(city.getPinyinShort());
                pBeanList.add(pBean);
            }
            cityview.loadCityList(pBeanList);
        }


    }
}
