package com.insta2apps.ibrahim.weatherapp.source.network;

import com.insta2apps.ibrahim.weatherapp.view.forecast.model.FiveDaysForeCastModel;
import com.insta2apps.ibrahim.weatherapp.view.home.model.Country;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ibrahim AbdelGawad on 2/3/2018.
 */

public interface ApiService {
    @GET("2.5/forecast?")
    Observable<FiveDaysForeCastModel> getCountryById
            (@Query("id") String cityId,
             @Query("appid") String apiKey,
             @Query("units") String units);

    @GET("2.5/forecast?")
    Observable<Country> getCountryByGeographicCoordinates(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("appid") String apiKey,
            @Query("units") String units);

    @GET("2.5/find?")
    Observable<FiveDaysForeCastModel> searchByCityName
            (@Query("q") String cityName,
             @Query("type") String searchType,
             @Query("appid") String apiKey);

    @GET("2.5/forecast?")
    Observable<FiveDaysForeCastModel> getCountryByCityName
            (@Query("q") String cityName,
             @Query("appid") String apiKey,
             @Query("units") String units);
}
