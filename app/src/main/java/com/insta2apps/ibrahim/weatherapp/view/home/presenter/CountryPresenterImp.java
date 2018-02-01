package com.insta2apps.ibrahim.weatherapp.view.home.presenter;

import com.insta2apps.ibrahim.weatherapp.view.home.HomeView;
import com.insta2apps.ibrahim.weatherapp.view.home.model.Country;

import java.util.ArrayList;

/**
 * Created by Ibrahim AbdelGawad on 1/30/2018.
 */

public class CountryPresenterImp extends CountryPresenter {
    HomeView homeView;

    public CountryPresenterImp(HomeView homeView) {
        this.homeView = homeView;
    }

    @Override
    public void populate() {
        homeView.showCountryList(new ArrayList<Country>());
    }

    @Override
    public void remove(int cityId) {

    }

    @Override
    public void onItemClick(Country country) {
        homeView.openItemDetail(country);
    }
}
