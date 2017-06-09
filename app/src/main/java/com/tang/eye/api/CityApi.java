package com.tang.eye.api;

import com.tang.eye.city.CityList;
import com.tang.eye.model.Daily;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by tangyc on 2017/6/9.
 */

public interface CityApi {
    @GET("Showtime/HotCitiesByCinema.api")
    Observable<CityList> getCityList();
}
