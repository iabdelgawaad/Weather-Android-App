package com.insta2apps.ibrahim.weatherapp.view.forecast.presenter;

import com.google.gson.Gson;
import com.insta2apps.ibrahim.weatherapp.R;
import com.insta2apps.ibrahim.weatherapp.WeatherApplication;
import com.insta2apps.ibrahim.weatherapp.domain.error.ErrorHandler;
import com.insta2apps.ibrahim.weatherapp.domain.error.ErrorModel;
import com.insta2apps.ibrahim.weatherapp.source.network.ApiService;
import com.insta2apps.ibrahim.weatherapp.source.network.NetworkManager;
import com.insta2apps.ibrahim.weatherapp.view.base.Constants;
import com.insta2apps.ibrahim.weatherapp.view.forecast.ForecastView;
import com.insta2apps.ibrahim.weatherapp.view.forecast.model.FiveDaysForeCastModel;
import com.insta2apps.ibrahim.weatherapp.view.util.FileUtils;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
    }

    private void getCityByNameService(String cityName) {
        NetworkManager networkManager = new NetworkManager(WeatherApplication.getInstance());
        ApiService service = networkManager.service();

        service.getCountryByCityName(cityName, Constants.WEATHER_API_KEY , Constants.TEMP_UNIT).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<FiveDaysForeCastModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(FiveDaysForeCastModel fiveDaysForeCastModel) {
                if (fiveDaysForeCastModel != null) {
                    getView().showContent();
                    getView().showCityForecast(fiveDaysForeCastModel);
                    getView().showFiveDaysWeatherList(fiveDaysForeCastModel.getList());
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

    @Override
    public void getCityById(String cityId, boolean isLoading) {

    }

    @Override
    public void getCityByName(String cityName, boolean isLoading) {
        if (getView() == null) return;
        if (isLoading)
            getView().showLoading();
        getCityByNameService(cityName);
    }

    //Just to simulate the server...
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
