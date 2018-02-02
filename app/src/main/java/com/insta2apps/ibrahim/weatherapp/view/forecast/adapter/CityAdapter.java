package com.insta2apps.ibrahim.weatherapp.view.forecast.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.insta2apps.ibrahim.weatherapp.R;
import com.insta2apps.ibrahim.weatherapp.view.forecast.model.List;
import com.insta2apps.ibrahim.weatherapp.view.home.adapter.HomeAdapter;
import com.insta2apps.ibrahim.weatherapp.view.home.model.Country;

/**
 * Created by Ibrahim AbdelGawad on 2/1/2018.
 */

public class CityAdapter extends RecyclerView.Adapter<CityViewHolder> {

    private Context mContext;
    private java.util.List<List> weatherList;
    private OnCityClickListener listener;

    public interface OnCityClickListener {
        void onItemClick(List city);
    }

    public CityAdapter(Context mContext, java.util.List<List> weatherList, OnCityClickListener listener)
    {
        this.mContext = mContext;
        this.weatherList = weatherList;
        this.listener = listener;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_forecast, parent, false);
        return new CityViewHolder(itemView, mContext);
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        holder.bind(weatherList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }
}
