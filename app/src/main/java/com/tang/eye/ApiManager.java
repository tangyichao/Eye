package com.tang.eye;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tangyc on 2017/6/1.
 */

public class ApiManager {
    public static Retrofit getRetrofit(String url){
        OkHttpClient client=new OkHttpClient().newBuilder().readTimeout(20, TimeUnit.SECONDS).build();
        Retrofit retrofit=new Retrofit.Builder().baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).build();
        return  retrofit;
    }
}
