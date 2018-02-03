package com.insta2apps.ibrahim.weatherapp;

import android.support.multidex.MultiDexApplication;

/**
 * Created by Ibrahim AbdelGawad on 1/28/2018.
 */

public class WeatherApplication extends MultiDexApplication {
    private static WeatherApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static WeatherApplication getInstance() {
        return instance;
    }
}
