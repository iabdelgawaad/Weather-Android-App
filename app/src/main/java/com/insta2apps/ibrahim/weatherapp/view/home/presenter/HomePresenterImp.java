package com.insta2apps.ibrahim.weatherapp.view.home.presenter;

import android.location.Location;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.insta2apps.ibrahim.weatherapp.R;
import com.insta2apps.ibrahim.weatherapp.WeatherApplication;
import com.insta2apps.ibrahim.weatherapp.source.database.AppDatabase;
import com.insta2apps.ibrahim.weatherapp.source.database.DatabaseInitializer;
import com.insta2apps.ibrahim.weatherapp.source.database.entity.City;
import com.insta2apps.ibrahim.weatherapp.source.network.error.ErrorHandler;
import com.insta2apps.ibrahim.weatherapp.source.network.error.ErrorModel;
import com.insta2apps.ibrahim.weatherapp.source.network.ApiService;
import com.insta2apps.ibrahim.weatherapp.source.network.NetworkManager;
import com.insta2apps.ibrahim.weatherapp.view.activity.MainActivity;
import com.insta2apps.ibrahim.weatherapp.view.base.Constants;
import com.insta2apps.ibrahim.weatherapp.view.forecast.model.FiveDaysForeCastModel;
import com.insta2apps.ibrahim.weatherapp.view.home.HomeView;
import com.insta2apps.ibrahim.weatherapp.view.home.fragment.HomeFragment;
import com.insta2apps.ibrahim.weatherapp.view.home.model.Country;
import com.insta2apps.ibrahim.weatherapp.view.util.FileUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.insta2apps.ibrahim.weatherapp.view.base.Constants.IS_LOCATION_REQUESTED;

/**
 * Created by Ibrahim AbdelGawad on 1/30/2018.
 */

public class HomePresenterImp extends HomePresenter implements DatabaseInitializer.OnDatabaseCrudOperation {
    private static final String CityFile = "city.list.json";
    private static DatabaseInitializer databaseInitializer;

    public HomePresenterImp(HomeView homeView) {
        attachView(homeView);
    }

    @Override
    public void remove(City city) {
        databaseInitializer.removeAsync(AppDatabase.getAppDatabase(WeatherApplication.getInstance()) , city);
    }

    @Override
    public void onItemClick(City country) {
        getView().openItemDetail(country);
    }

    @Override
    public void getSearchCityData(Location location) {
        getView().showLoading();
        if (location != null) {
            //get current location weather
            getCountryByGeographicCoordinatesService(location.getLatitude() + "",
                    location.getLongitude() + "");
        } else {
            //get england weather
            getCountryByGeographicCoordinatesService(Constants.ENGLAND_LAT,
                    Constants.ENGLAND_LON);
        }
        new GetSearchData().execute();
    }

    @Override
    public void init() {
        super.init();
        if (getView() == null) return;
        if (getView() instanceof HomeFragment) {
            getView().showLoading();
            databaseInitializer = new DatabaseInitializer(this);
            boolean isLocationRequested = ((MainActivity) ((HomeFragment) getView()).getActivity()).sharedPreferenceManager.getBoolean
                    (IS_LOCATION_REQUESTED, false);
            if (isLocationRequested) {
                new GetSearchData().execute();
            } else {
                getView().requestLocationPermission();
            }
        }
    }

    private class GetSearchData extends AsyncTask<Void, Void, List<Country>> {
        @Override
        protected List<Country> doInBackground(Void... voids) {
            return getSearchCountyList();
        }

        @Override
        protected void onPostExecute(List<Country> countryList) {
            getView().showContent();
            getView().updateSearchContent(countryList);
            getHomeScreenAddedCities();
        }
    }

    private void getCountryByGeographicCoordinatesService(String lat, String lon) {
        NetworkManager networkManager = new NetworkManager(WeatherApplication.getInstance());
        ApiService service = networkManager.service();

        service.getCountryByGeographicCoordinates(lat, lon, Constants.WEATHER_API_KEY, Constants.TEMP_UNIT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Country>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Country country) {
                        if (getView() == null) return;
                        if (country != null && country.getId() != null) {
                           City city = new City();
                           city.setName(country.getName());
                            //add to DB
                            databaseInitializer.addAsync(AppDatabase.getAppDatabase(WeatherApplication.getInstance()) , city);
                        } else {
                            //API: lat,lon not working..
                            //Load england as a default city
                            getCityByNameService(Constants.DEFAULT_CIT_NAME);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() == null) {
                            return;
                        }
                        int strRes;
                        ErrorModel errorModel = ErrorHandler.getErrorModel(e);
                        switch (errorModel.getErrorType()) {
                            case ErrorModel.ErrorType.NETWORK_ERROR:
                                if (!getView().isConnected()) {
                                    strRes = R.string.error_msg_no_internet;
                                } else {
                                    strRes = R.string.error_msg_timeout;
                                }
                                break;

                            default:
                                strRes = R.string.error_msg_unknown;
                                break;
                        }
                        getView().showError(strRes);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getCityByNameService(String cityName) {
        NetworkManager networkManager = new NetworkManager(WeatherApplication.getInstance());
        ApiService service = networkManager.service();

        service.getCountryByCityName(cityName, Constants.WEATHER_API_KEY, Constants.TEMP_UNIT).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<FiveDaysForeCastModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(FiveDaysForeCastModel fiveDaysForeCastModel) {
                if (fiveDaysForeCastModel != null) {
                    //add to DB
                    City city = new City();
                    city.setName(fiveDaysForeCastModel.getCity().getName());
                    databaseInitializer.addAsync(AppDatabase.getAppDatabase(WeatherApplication.getInstance()) , city);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (getView() == null) {
                    return;
                }
                int strRes;
                ErrorModel errorModel = ErrorHandler.getErrorModel(e);
                switch (errorModel.getErrorType()) {
                    case ErrorModel.ErrorType.NETWORK_ERROR:
                        if (!getView().isConnected()) {
                            strRes = R.string.error_msg_no_internet;
                        } else {
                            strRes = R.string.error_msg_timeout;
                        }
                        break;

                    default:
                        strRes = R.string.error_msg_unknown;
                        break;
                }
                getView().showError(strRes);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void getHomeScreenAddedCities()
    {
        databaseInitializer.populateAsync(AppDatabase.getAppDatabase(WeatherApplication.getInstance()));
    }
    //Get data from local json file {Bulk}
    public List<Country> getSearchCountyList() {
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

    @Override
    public void getSelectedCities(List<City> cityList) {
        getView().showCountryList(cityList);
    }
}
