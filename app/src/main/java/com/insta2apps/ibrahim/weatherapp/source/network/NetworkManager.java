package com.insta2apps.ibrahim.weatherapp.source.network;

import android.content.Context;

import com.insta2apps.ibrahim.weatherapp.view.base.Constants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ibrahim AbdelGawad on 2/3/2018.
 */

public class NetworkManager {
    private Retrofit retrofit;
    private ApiService apiService;
    private Context context;

    public NetworkManager(Context context) {
        this.context = context;
        initRetrofit();
    }

    private void initRetrofit() {
        OkHttpClient.Builder httpClient = new OkHttpClient
                .Builder();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();
    }

    public ApiService service() {
        if (apiService == null) {
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }

}
