package com.insta2apps.ibrahim.weatherapp.view.forecast.presenter;

import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.insta2apps.ibrahim.weatherapp.WeatherApplication;
import com.insta2apps.ibrahim.weatherapp.view.forecast.ForecastView;
import com.insta2apps.ibrahim.weatherapp.view.forecast.model.FiveDaysForeCastModel;
import com.insta2apps.ibrahim.weatherapp.view.forecast.model.List;
import com.insta2apps.ibrahim.weatherapp.view.util.FileUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Ibrahim AbdelGawad on 2/1/2018.
 */

public class FiveDaysForecastPresenterImp extends FiveDaysForecastPresenter {

    private static final String CityFile = "selected.city.json";

    public FiveDaysForecastPresenterImp(ForecastView view) {
        attachView(view);
    }

    @Override
    public void init() {
        super.init();
        if (getView() == null) return;
        getView().showLoading();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getView().showContent();
                getView().showCityForecast(getCityObject());
                getView().showFiveDaysWeatherList(getCityObject().getList());
            }
        }, 2000);
    }

    public FiveDaysForeCastModel getCityObject() {

        FiveDaysForeCastModel fiveDaysForeCastModel = null;
        try {

            fiveDaysForeCastModel = new Gson().fromJson(FileUtils
                    .readFileFromAssetsWithStringResult(WeatherApplication.getInstance(), CityFile), FiveDaysForeCastModel.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return fiveDaysForeCastModel;

    }
}
