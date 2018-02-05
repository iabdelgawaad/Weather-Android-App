package com.insta2apps.ibrahim.weatherapp.view.home.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.insta2apps.ibrahim.weatherapp.R;
import com.insta2apps.ibrahim.weatherapp.source.database.entity.City;
import com.insta2apps.ibrahim.weatherapp.view.activity.MainActivity;
import com.insta2apps.ibrahim.weatherapp.view.base.BaseFragment;
import com.insta2apps.ibrahim.weatherapp.view.forecast.fragment.FiveDaysForecastFragment;
import com.insta2apps.ibrahim.weatherapp.view.home.HomeView;
import com.insta2apps.ibrahim.weatherapp.view.home.adapter.HomeAdapter;
import com.insta2apps.ibrahim.weatherapp.view.home.model.Country;
import com.insta2apps.ibrahim.weatherapp.view.home.presenter.HomePresenter;
import com.insta2apps.ibrahim.weatherapp.view.home.presenter.HomePresenterImp;
import com.insta2apps.ibrahim.weatherapp.view.util.GridSpacingItemDecoration;
import com.insta2apps.ibrahim.weatherapp.view.util.NetworkConnectionUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeAdapter.OnItemClickListener, HomeView, MainActivity.HomeFragmentCommunicator {

    @BindView(R.id.cities_recycler_view)
    RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    @BindView(R.id.progressBar)
    ProgressBar loadingProgressBar;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    @BindView(R.id.error_txt_cause)
    TextView txtError;

    private List<City> cityList = new ArrayList<>();

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public HomePresenter getPresenter() {
        return new HomePresenterImp(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter(getPresenter());
        ((MainActivity) getActivity()).setHomeFragmentCommunicator(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getPresenter().init();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).setAppBarVisibility(true);

        if (cityList != null && cityList.size() > 0) {
            cityList.clear();
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    public void onItemClick(City country) {
        if (country != null)
            getPresenter().onItemClick(country);
    }

    @Override
    public void onRemoveIconClick(City country) {

        if (country != null) {
            City city = new City();
            city.setName(country.getName());
            getPresenter().remove(city);
        }
    }

    @Override
    public void showCountryList(List<City> countryList1) {
        this.cityList = countryList1;
        homeAdapter = new HomeAdapter(getActivity(), cityList, this);
        recyclerView.setAdapter(homeAdapter);
        homeAdapter.notifyDataSetChanged();
    }

    @Override
    public void openItemDetail(City country) {
        if (getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).replaceFragment(FiveDaysForecastFragment.newInstance(country.getName() + ""));
    }

    @Override
    public void requestLocationPermission() {
        if (getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).setupLocationService();
    }

    @Override
    public void updateSearchContent(List<Country> countryList) {
        if (getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).updateSearchContent(countryList);
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void showLoading() {
        loadingProgressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showContent() {
        if (getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).showContent();
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
    public void onStop() {
        super.onStop();

        if (cityList != null)
            cityList.clear();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (cityList != null && cityList.size() > 0)
            cityList.clear();
    }

    @Override
    public void isLocationGranted(boolean isLocationGranted, Location location) {
        getPresenter().getSearchCityData(location);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (cityList != null && cityList.size() > 0) {
            cityList.clear();
        }
    }
}
