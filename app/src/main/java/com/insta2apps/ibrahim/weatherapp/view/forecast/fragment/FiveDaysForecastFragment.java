package com.insta2apps.ibrahim.weatherapp.view.forecast.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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

    private ProgressBar loadingProgressBar;
    private LinearLayout errorLayout;
    private TextView txtError;
    private RecyclerView recyclerView;

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
        if (getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).setAppBarVisibility(false);
        getPresenter().init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.forecast_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        loadingProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        errorLayout = (LinearLayout) view.findViewById(R.id.error_layout);
        txtError = (TextView) view.findViewById(R.id.error_txt_cause);

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
}
