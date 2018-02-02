package com.insta2apps.ibrahim.weatherapp.view.forecast;

import com.insta2apps.ibrahim.weatherapp.view.base.BasePresenter;
import com.insta2apps.ibrahim.weatherapp.view.forecast.model.FiveDaysForeCastModel;
import com.insta2apps.ibrahim.weatherapp.view.forecast.model.List;

import java.util.ArrayList;

/**
 * Created by Ibrahim AbdelGawad on 2/1/2018.
 */

public interface ForecastView extends BasePresenter.View {
    void showCityForecast(FiveDaysForeCastModel fiveDaysForeCastModel);
    void showFiveDaysWeatherList(java.util.List<List> weatherList);
}
