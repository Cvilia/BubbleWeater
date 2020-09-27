package com.cvilia.bubbleweather;


import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.cvilia.bubbleweather.base.BaseApplication;
import com.cvilia.bubbleweather.sql.DaoSession;

import java.util.List;

/**
 * author: lzy
 * date: 2020/8/17
 * describe：描述
 */
public class WeatherApplication extends BaseApplication {

    private static DaoSession mDaosession;

    @Override
    public void onCreate() {
        super.onCreate();
        initGreenDao();
    }

    private void initGreenDao() {

    }

    public static DaoSession getDaoSessionInstance() {
        return mDaosession;
    }
}
