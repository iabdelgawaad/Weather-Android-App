package com.insta2apps.ibrahim.weatherapp.view.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.insta2apps.ibrahim.weatherapp.R;
import com.insta2apps.ibrahim.weatherapp.view.home.model.Country;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ibrahim AbdelGawad on 1/30/2018.
 */

public class CityViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    @BindView(R.id.thumbnail)
    ImageView image;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.remove)
    ImageView remove;


    CityViewHolder(View view, Context context) {
        super(view);
        ButterKnife.bind(this, view);
        this.context = context;
    }

    public void bind(final Country item, final CityAdapter.OnItemClickListener listener) {
        title.setText(item.getName());
        try {
            Glide.with(context).load(R.drawable.ic_search).into(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onItemClick(item);
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onRemoveIconClick(item);
            }
        });
    }
}
