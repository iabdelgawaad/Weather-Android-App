package com.insta2apps.ibrahim.weatherapp.view.home.presenter;

import android.os.Handler;

import com.insta2apps.ibrahim.weatherapp.view.home.HomeView;
import com.insta2apps.ibrahim.weatherapp.view.home.model.Country;

import java.util.ArrayList;

/**
 * Created by Ibrahim AbdelGawad on 1/30/2018.
 */

public class HomePresenterImp extends HomePresenter {

    public HomePresenterImp(HomeView homeView) {
        attachView(homeView);
    }

    @Override
    public void populate() {
    }

    @Override
    public void remove(int cityId) {

    }

    @Override
    public void onItemClick(Country country) {
        getView().openItemDetail(country);
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
                getView().showCountryList(new ArrayList<Country>());
            }
        }, 2000);
    }
}
