package com.insta2apps.ibrahim.weatherapp.view.home.presenter;

import android.location.Location;

import com.insta2apps.ibrahim.weatherapp.view.home.HomeView;
import com.insta2apps.ibrahim.weatherapp.view.base.BasePresenter;
import com.insta2apps.ibrahim.weatherapp.view.home.model.Country;

/**
 * Created by Ibrahim AbdelGawad on 1/30/2018.
 */

public abstract class HomePresenter extends BasePresenter<HomeView> {
    public abstract void remove(int cityId);
    public abstract void onItemClick(Country country);
    public abstract void getSearchCityData(Location location);
}
