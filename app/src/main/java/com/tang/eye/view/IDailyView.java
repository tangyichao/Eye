package com.tang.eye.view;

import com.tang.eye.model.Daily;

/**
 * Created by tangyc on 2017/5/9.
 */

public interface IDailyView {
    void initDaily();
    void loadDaily(Daily daily);
    void errorDaily();
}
