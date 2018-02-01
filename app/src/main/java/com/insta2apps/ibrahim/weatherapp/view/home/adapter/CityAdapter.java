package com.insta2apps.ibrahim.weatherapp.view.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.insta2apps.ibrahim.weatherapp.R;
import com.insta2apps.ibrahim.weatherapp.view.home.model.Country;

import java.util.List;

/**
 * Created by Ibrahim AbdelGawad on 1/30/2018.
 */

public class CityAdapter extends RecyclerView.Adapter<CityViewHolder> {
    private Context mContext;
    private List<Country> countryModelList;

    public interface OnItemClickListener {
        void onItemClick(Country country);
        void onRemoveIconClick(Country country);
    }

    private final OnItemClickListener listener;

    public CityAdapter(Context mContext, List<Country> countryModelList, OnItemClickListener listener) {
        this.mContext = mContext;
        this.countryModelList = countryModelList;
        this.listener = listener;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_card_item, parent, false);
        return new CityViewHolder(itemView, mContext);
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        holder.bind(countryModelList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return countryModelList.size();
    }
}
