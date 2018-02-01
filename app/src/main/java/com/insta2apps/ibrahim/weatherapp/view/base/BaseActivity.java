package com.insta2apps.ibrahim.weatherapp.view.base;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by Ibrahim AbdelGawad on 1/28/2018.
 */

public class BaseActivity extends AppCompatActivity implements BasePresenter.View{

    public void setTitle(int toolbarId,int titleId, String title, int btnDrawable, int colorBg, int titleColor){
        Toolbar toolbar = (Toolbar) findViewById(toolbarId);
        toolbar.setBackgroundResource(colorBg);
        setSupportActionBar(toolbar);
        TextView pageTitle = (TextView) toolbar.findViewById(titleId);
        pageTitle.setText(title);
        pageTitle.setTextColor(getResources().getColor(titleColor));
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(btnDrawable);

    }

    public void setHomeTitle(int toolbarId,int titleId, String title, int colorBg, int titleColor){
        Toolbar toolbar = (Toolbar) findViewById(toolbarId);
        toolbar.setBackgroundResource(colorBg);
        setSupportActionBar(toolbar);
        TextView pageTitle = (TextView) toolbar.findViewById(titleId);
        pageTitle.setText(title);
        pageTitle.setTextColor(getResources().getColor(titleColor));
        getSupportActionBar().setTitle("");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showContent() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
