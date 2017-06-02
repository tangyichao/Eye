package com.tang.eye;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import dagger.internal.DaggerCollections;

/**
 * Created by tangyc on 2017/6/1.
 */

public class EyeApp extends Application {
    private NetComponent mNetComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        mNetComponent=DaggerNetComponent.builder().appModule(new AppModule(this)).netModule(new NetModule("http://baobab.kaiyanapp.com/api/")).build();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
    }
    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
