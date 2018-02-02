package com.insta2apps.ibrahim.weatherapp.view.home.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ibrahim AbdelGawad on 2/2/2018.
 */

public class CountryListModel {
    public List<Country> getCountryArrayList() {
        return countryArrayList;
    }

    public void setCountryArrayList(ArrayList<Country> countryArrayList) {
        this.countryArrayList = countryArrayList;
    }

    List<Country> countryArrayList;
}
