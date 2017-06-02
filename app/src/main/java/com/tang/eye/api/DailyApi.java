package com.tang.eye.api;

import com.tang.eye.model.Daily;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by tangyc on 2017/5/9.
 */
public interface DailyApi {
    @GET("v4/tabs/selected")
    Observable<Daily> getDaily();
}
