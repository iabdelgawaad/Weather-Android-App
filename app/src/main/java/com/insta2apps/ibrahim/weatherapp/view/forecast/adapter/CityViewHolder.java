package com.insta2apps.ibrahim.weatherapp.view.forecast.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.insta2apps.ibrahim.weatherapp.R;
import com.insta2apps.ibrahim.weatherapp.view.forecast.model.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.insta2apps.ibrahim.weatherapp.view.base.Constants.WEATHER_IMAGE_EXTENSION;
import static com.insta2apps.ibrahim.weatherapp.view.base.Constants.WEATHER_IMAGE_URL;

/**
 * Created by Ibrahim AbdelGawad on 2/1/2018.
 */

public class CityViewHolder extends RecyclerView.ViewHolder {
    private Context context;
    @BindView(R.id.day_text_view)
    TextView dayTextView;
    @BindView(R.id.weather_image_view)
    ImageView weatherImage;
    @BindView(R.id.temperature_text_view)
    TextView temperatureText;

    public CityViewHolder(View itemView, Context context) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = context;
    }

    public void bind(final List item, final CityAdapter.OnCityClickListener listener) {
        dayTextView.setText(String.valueOf(item.getMain().getPressure()));
        temperatureText.setText(String.valueOf(item.getMain().getTemp()));
        try {
            Glide.with(context).load(WEATHER_IMAGE_URL + item.getWeather().get(0).getIcon() +
                    WEATHER_IMAGE_EXTENSION).into(weatherImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onItemClick(item);
            }
        });
    }
}
