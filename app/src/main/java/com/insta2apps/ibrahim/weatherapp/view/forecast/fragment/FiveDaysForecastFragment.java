package com.insta2apps.ibrahim.weatherapp.view.forecast.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.insta2apps.ibrahim.weatherapp.R;
import com.insta2apps.ibrahim.weatherapp.view.activity.MainActivity;
import com.insta2apps.ibrahim.weatherapp.view.base.BaseFragment;
import com.insta2apps.ibrahim.weatherapp.view.base.Constants;
import com.insta2apps.ibrahim.weatherapp.view.forecast.ForecastView;
import com.insta2apps.ibrahim.weatherapp.view.forecast.presenter.FiveDaysForecastPresenter;
import com.insta2apps.ibrahim.weatherapp.view.forecast.presenter.FiveDaysForecastPresenterImp;
import com.insta2apps.ibrahim.weatherapp.view.util.NetworkConnectionUtil;

/**
 * Created by Ibrahim AbdelGawad on 2/1/2018.
 */

public class FiveDaysForecastFragment extends BaseFragment<FiveDaysForecastPresenter> implements ForecastView {

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
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).setAppBarVisibility(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_forecast, container, false);

        return view;
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
        super.showLoading();
    }

    @Override
    public void showContent() {
        super.showContent();
    }

    @Override
    public void showError(int errorMessage) {

    }

    @Override
    public boolean isConnected() {
        return NetworkConnectionUtil.isNetworkAvailable(mContext);
    }
}
