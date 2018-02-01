package com.insta2apps.ibrahim.weatherapp.view;

import android.app.Activity;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.insta2apps.ibrahim.weatherapp.R;
import com.insta2apps.ibrahim.weatherapp.view.base.BaseActivity;
import com.insta2apps.ibrahim.weatherapp.view.home.fragment.HomeFragment;
import com.insta2apps.ibrahim.weatherapp.view.util.FragmentUtil;

public class MainActivity extends BaseActivity implements HomeFragment.OnFragmentInteractionListener {

    AutoCompleteTextView autoCompleteTextView;
    private static final String[] COUNTRIES = new String[] {
            "Belgium", "France", "Google", "Germany", "Spain" , "Spain", "Spain", "Spain",
            "Spain", "Spain", "Spain", "Spain", "Spain", "Spain", "Spain", "Spain", "Spain"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setHomeTitle(R.id.inc_toolbar, R.id.tv_title, getString(R.string.app_name), R.color.blue, R.color.black);

        //search
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        autoCompleteTextView  = (AutoCompleteTextView)
                findViewById(R.id.countries_list);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        //

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null) {
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

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
