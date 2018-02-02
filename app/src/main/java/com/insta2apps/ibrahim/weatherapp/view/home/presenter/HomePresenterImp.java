package com.insta2apps.ibrahim.weatherapp.view.home.presenter;

import android.location.Location;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.insta2apps.ibrahim.weatherapp.WeatherApplication;
import com.insta2apps.ibrahim.weatherapp.view.activity.MainActivity;
import com.insta2apps.ibrahim.weatherapp.view.home.HomeView;
import com.insta2apps.ibrahim.weatherapp.view.home.fragment.HomeFragment;
import com.insta2apps.ibrahim.weatherapp.view.home.model.Country;
import com.insta2apps.ibrahim.weatherapp.view.util.FileUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.insta2apps.ibrahim.weatherapp.view.base.Constants.IS_LOCATION_REQUESTED;

/**
 * Created by Ibrahim AbdelGawad on 1/30/2018.
 */

public class HomePresenterImp extends HomePresenter {
    private static final String CityFile = "city.list.json";

    public HomePresenterImp(HomeView homeView) {
        attachView(homeView);
    }

    @Override
    public void remove(int cityId) {

    }

    @Override
    public void onItemClick(Country country) {
        getView().openItemDetail(country);
    }

    @Override
    public void getSearchCityData(Location location) {
        getView().showLoading();
        if (location != null) {
            //get current location weather
        } else {
            //get england weather
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getView().showContent();
                getView().showCountryList(getCountryObject());
            }
        }, 2000);
    }

    @Override
    public void init() {
        super.init();
        if (getView() == null) return;

        if (getView() instanceof HomeFragment) {
            boolean isLocationRequested = ((MainActivity) ((HomeFragment) getView()).getActivity()).sharedPreferenceManager.getBoolean
                    (IS_LOCATION_REQUESTED, false);
            if (isLocationRequested) {
                getSearchCityData(null);
            } else {
                getView().requestLocationPermission();
            }
        }
    }

    public List<Country> getCountryObject() {

        List<Country> theList = null;
        try {
            Type collectionType = new TypeToken<Collection<Country>>() {
            }.getType();
            Collection<Country> enums = new Gson().fromJson(FileUtils
                    .readFileFromAssetsWithStringResult(WeatherApplication.getInstance(), CityFile), collectionType);
            theList = new ArrayList(enums);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return theList;

    }

}
