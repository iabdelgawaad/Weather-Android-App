package com.insta2apps.ibrahim.weatherapp.view.forecast.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.insta2apps.ibrahim.weatherapp.R;
import com.insta2apps.ibrahim.weatherapp.view.activity.MainActivity;
import com.insta2apps.ibrahim.weatherapp.view.base.BaseFragment;
import com.insta2apps.ibrahim.weatherapp.view.base.Constants;
import com.insta2apps.ibrahim.weatherapp.view.forecast.ForecastView;
import com.insta2apps.ibrahim.weatherapp.view.forecast.adapter.CityAdapter;
import com.insta2apps.ibrahim.weatherapp.view.forecast.model.FiveDaysForeCastModel;
import com.insta2apps.ibrahim.weatherapp.view.forecast.model.List;
import com.insta2apps.ibrahim.weatherapp.view.forecast.presenter.FiveDaysForecastPresenter;
import com.insta2apps.ibrahim.weatherapp.view.forecast.presenter.FiveDaysForecastPresenterImp;
import com.insta2apps.ibrahim.weatherapp.view.util.NetworkConnectionUtil;

import butterknife.BindView;

import static com.insta2apps.ibrahim.weatherapp.view.base.Constants.WEATHER_IMAGE_EXTENSION;
import static com.insta2apps.ibrahim.weatherapp.view.base.Constants.WEATHER_IMAGE_URL;

/**
 * Created by Ibrahim AbdelGawad on 2/1/2018.
 */

public class FiveDaysForecastFragment extends BaseFragment<FiveDaysForecastPresenter> implements ForecastView, CityAdapter.OnCityClickListener {

    @BindView(R.id.forecast_recycler_view)
    RecyclerView recyclerView;
    private CityAdapter cityAdapter;
    @BindView(R.id.city_name_text_view)
    TextView cityName;
    @BindView(R.id.temperature_text_view)
    TextView temperatureTextView;
    @BindView(R.id.weather_condition_text_view)
    TextView weatherCondition;
    @BindView(R.id.progressBar)
    ProgressBar loadingProgressBar;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    @BindView(R.id.error_txt_cause)
    TextView txtError;
    @BindView(R.id.humidity_text_view)
    TextView humidityTextView;
    @BindView(R.id.wind_speed_text_view)
    TextView windSpeedTextView;
    @BindView(R.id.weather_image_view)
    ImageView todayWeatherImage;

    public FiveDaysForecastFragment() {
    }

    public static FiveDaysForecastFragment newInstance(int cityId) {
        FiveDaysForecastFragment fragment = new FiveDaysForecastFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.CITY_ID, cityId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter(getPresenter());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if (getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).setAppBarVisibility(false);
        getPresenter().init();
    }

    @Override
    public FiveDaysForecastPresenter getPresenter() {
        return new FiveDaysForecastPresenterImp(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_forecast;
    }

    @Override
    public void showLoading() {
        loadingProgressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showContent() {
        recyclerView.setVisibility(View.VISIBLE);
        loadingProgressBar.setVisibility(View.INVISIBLE);
        errorLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(int errorMessage) {
        errorLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        loadingProgressBar.setVisibility(View.GONE);
        txtError.setText(getString(errorMessage));
    }

    @Override
    public boolean isConnected() {
        return NetworkConnectionUtil.isNetworkAvailable(mContext);
    }

    @Override
    public void showCityForecast(FiveDaysForeCastModel fiveDaysForeCastModel) {
        cityName.setText(fiveDaysForeCastModel.getCity().getName());
        temperatureTextView.setText(fiveDaysForeCastModel.getList().get(0).getMain().getTemp() + "");
        weatherCondition.setText(fiveDaysForeCastModel.getList().get(0).getWeather().get(0).getDescription());
        humidityTextView.setText(fiveDaysForeCastModel.getList().get(0).getMain().getHumidity() + "");
        windSpeedTextView.setText(fiveDaysForeCastModel.getList().get(0).getWind().getSpeed() + "");
        try {
            Glide.with(getActivity()).load(WEATHER_IMAGE_URL + fiveDaysForeCastModel.getList()
                    .get(0).getWeather().get(0).getIcon() + WEATHER_IMAGE_EXTENSION).into(todayWeatherImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showFiveDaysWeatherList(java.util.List weatherList) {
        cityAdapter = new CityAdapter(getActivity(), weatherList, this);
        recyclerView.setAdapter(cityAdapter);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(List city) {

    }
}
