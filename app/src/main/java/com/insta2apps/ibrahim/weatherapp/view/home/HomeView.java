package com.insta2apps.ibrahim.weatherapp.view.home;

import com.insta2apps.ibrahim.weatherapp.source.database.entity.City;
import com.insta2apps.ibrahim.weatherapp.view.base.BasePresenter;
import com.insta2apps.ibrahim.weatherapp.view.home.model.Country;
import com.insta2apps.ibrahim.weatherapp.view.home.model.CountryListModel;

import java.util.List;

/**
 * Created by Ibrahim AbdelGawad on 1/30/2018.
 */

public interface HomeView extends BasePresenter.View {
    void showCountryList(List<City> countryList);
    void openItemDetail(City country);
    void requestLocationPermission();
    void updateSearchContent(List<Country> countryList);
}