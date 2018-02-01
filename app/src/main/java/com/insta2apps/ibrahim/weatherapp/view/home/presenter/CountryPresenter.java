package com.insta2apps.ibrahim.weatherapp.view.home.presenter;

import com.insta2apps.ibrahim.weatherapp.view.home.HomeView;
import com.insta2apps.ibrahim.weatherapp.view.base.BasePresenter;

/**
 * Created by Ibrahim AbdelGawad on 1/30/2018.
 */

public abstract class CountryPresenter extends BasePresenter<HomeView>{
    public abstract void populate();
    public abstract void remove(int cityId);
}
