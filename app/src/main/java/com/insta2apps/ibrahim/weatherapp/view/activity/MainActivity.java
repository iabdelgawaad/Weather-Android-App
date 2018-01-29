package com.insta2apps.ibrahim.weatherapp.view.activity;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.insta2apps.ibrahim.weatherapp.R;
import com.insta2apps.ibrahim.weatherapp.view.base.BaseActivity;
import com.insta2apps.ibrahim.weatherapp.view.fragment.HomeFragment;
import com.insta2apps.ibrahim.weatherapp.view.util.FragmentUtil;

public class MainActivity extends BaseActivity implements HomeFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setHomeTitle(R.id.inc_toolbar, R.id.tv_title, getString(R.string.app_name), R.color.purple, R.color.white);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            //Push fragment
            pushFragment(HomeFragment.newInstance());
        }
    }

    public void pushFragment(Fragment fragment){
        FragmentUtil.addFragment(
                getSupportFragmentManager(), R.id.fragment_container, fragment, fragment.getClass().getName());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
