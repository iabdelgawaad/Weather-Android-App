package com.insta2apps.ibrahim.weatherapp.view.forecast.presenter;

import android.os.Handler;

import com.insta2apps.ibrahim.weatherapp.view.forecast.ForecastView;

/**
 * Created by Ibrahim AbdelGawad on 2/1/2018.
 */

public class FiveDaysForecastPresenterImp extends FiveDaysForecastPresenter {

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
            }
        }, 2000);
    }
}
