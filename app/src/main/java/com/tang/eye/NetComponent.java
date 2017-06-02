package com.tang.eye;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tangyc on 2017/6/1.
 */
@Singleton
@Component(modules={AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(MainActivity activity);
}
