package com.insta2apps.ibrahim.weatherapp.view.forecast.presenter;

import com.insta2apps.ibrahim.weatherapp.view.base.BasePresenter;
import com.insta2apps.ibrahim.weatherapp.view.forecast.ForecastView;

/**
 * Created by Ibrahim AbdelGawad on 2/1/2018.
 */

public abstract class FiveDaysForecastPresenter extends BasePresenter<ForecastView> {
    public abstract void getCityById(String cityId , boolean isLoading);
    public abstract void getCityByName(String cityName , boolean isLoading);
}
