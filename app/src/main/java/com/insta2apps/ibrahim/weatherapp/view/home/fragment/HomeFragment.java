package com.insta2apps.ibrahim.weatherapp.view.home.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.insta2apps.ibrahim.weatherapp.R;
import com.insta2apps.ibrahim.weatherapp.view.activity.MainActivity;
import com.insta2apps.ibrahim.weatherapp.view.base.BaseFragment;
import com.insta2apps.ibrahim.weatherapp.view.forecast.fragment.FiveDaysForecastFragment;
import com.insta2apps.ibrahim.weatherapp.view.home.HomeView;
import com.insta2apps.ibrahim.weatherapp.view.home.adapter.CityAdapter;
import com.insta2apps.ibrahim.weatherapp.view.home.model.Country;
import com.insta2apps.ibrahim.weatherapp.view.home.presenter.HomePresenter;
import com.insta2apps.ibrahim.weatherapp.view.home.presenter.HomePresenterImp;
import com.insta2apps.ibrahim.weatherapp.view.util.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements CityAdapter.OnItemClickListener, HomeView {

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private CityAdapter cityAdapter;
    private List<Country> countryList = new ArrayList<>();

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
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPresenter().init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.cities_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        countryList.clear();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    public void onItemClick(Country country) {
        Toast.makeText(getActivity(), "On item Click ", Toast.LENGTH_SHORT).show();
        if (country != null)
            getPresenter().onItemClick(country);
    }

    @Override
    public void onRemoveIconClick(Country country) {
        Toast.makeText(getActivity(), "On remove Click ", Toast.LENGTH_SHORT).show();

        if (country != null)
            getPresenter().remove(country.getId());
    }

    @Override
    public void showCountryList(List<Country> shirtModelArrayList) {
        Country country1 = new Country();
        country1.setName("Cairo");
        country1.setId(1);
        countryList.add(country1);

        Country country2 = new Country();
        country2.setName("Cairo");
        country2.setId(1);
        countryList.add(country2);

        Country country3 = new Country();
        country3.setName("Cairo");
        country3.setId(1);
        countryList.add(country3);

        Country country4 = new Country();
        country4.setName("Cairo");
        country4.setId(1);
        countryList.add(country4);

        Country country = new Country();
        country.setName("Cairo");
        country.setId(1);
        countryList.add(country);

        cityAdapter = new CityAdapter(getActivity(), countryList, this);
        recyclerView.setAdapter(cityAdapter);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void openItemDetail(Country country) {
        if (getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).replaceFragment(FiveDaysForecastFragment.newInstance(country.getId()));
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
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
        super.showLoading();
    }

    @Override
    public void showContent() {
        super.showContent();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (countryList != null)
            countryList.clear();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).setAppBarVisibility(true);
    }
}
