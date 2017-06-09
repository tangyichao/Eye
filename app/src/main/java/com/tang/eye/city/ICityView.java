package com.tang.eye.city;

import java.util.List;

/**
 * Created by tangyc on 2017/6/9.
 */

public interface ICityView  {
    void loadCityList(List<CityList.PBean> list);
    void loadError();
}
