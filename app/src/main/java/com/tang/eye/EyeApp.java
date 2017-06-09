package com.tang.eye;

import android.app.Application;
import android.app.Instrumentation;

import com.squareup.leakcanary.LeakCanary;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
        try {
            attachContext();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public NetComponent getNetComponent() {
        return mNetComponent;
    }

    public static void attachContext() throws Exception{
        // 先获取到当前的ActivityThread对象

        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
        currentActivityThreadMethod.setAccessible(true);
        Object currentActivityThread = currentActivityThreadMethod.invoke(null);

        // 拿到原始的 mInstrumentation字段
        Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
        mInstrumentationField.setAccessible(true);
        Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(currentActivityThread);

        // 创建代理对象
        Instrumentation evilInstrumentation = new EvilInstrumentation(mInstrumentation);

        // 偷梁换柱
        mInstrumentationField.set(currentActivityThread, evilInstrumentation);
    }
}
