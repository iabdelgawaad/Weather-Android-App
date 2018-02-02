package com.insta2apps.ibrahim.weatherapp.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.insta2apps.ibrahim.weatherapp.R;
import com.insta2apps.ibrahim.weatherapp.view.base.BaseActivity;
import com.insta2apps.ibrahim.weatherapp.view.home.fragment.HomeFragment;
import com.insta2apps.ibrahim.weatherapp.view.home.model.Country;
import com.insta2apps.ibrahim.weatherapp.view.util.FragmentUtil;
import com.insta2apps.ibrahim.weatherapp.view.util.PermissionsUtil;
import com.insta2apps.ibrahim.weatherapp.view.util.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, LocationListener {

    AutoCompleteTextView autoCompleteTextView;
    GoogleApiClient mGoogleApiClient;
    HomeFragmentCommunicator homeFragmentCommunicator;
    private static final int GPS_REQUEST = 100;
    public SharedPreferenceManager sharedPreferenceManager;

    public interface HomeFragmentCommunicator {
        void isLocationGranted(boolean isLocationGranted, Location location);
    }

    private static final String[] COUNTRIES = new String[]{
            "Belgium", "France", "Google", "Germany", "Spain", "Spain", "Spain", "Spain",
            "Spain", "Spain", "Spain", "Spain", "Spain", "Spain", "Spain", "Spain", "Spain"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setHomeTitle(R.id.inc_toolbar, R.id.tv_title, getString(R.string.app_name), R.color.blue, R.color.white);
        sharedPreferenceManager = new SharedPreferenceManager(this);
        //search
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        autoCompleteTextView = (AutoCompleteTextView)
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
        autoCompleteTextView.setVisibility(View.GONE);
        //
        initHome();
    }

    public void initHome() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            FragmentUtil.replaceFragment(
                    getSupportFragmentManager(), R.id.fragment_container, HomeFragment.newInstance(), null);
        }
    }

    public void pushFragment(Fragment fragment) {
        FragmentUtil.addFragment(
                getSupportFragmentManager(), R.id.fragment_container, fragment, fragment.getClass().getName());
    }

    public void replaceFragment(Fragment fragment) {
        FragmentUtil.replaceFragment(
                getSupportFragmentManager(), R.id.fragment_container, fragment, fragment.getClass().getName());
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void updateSearchContent(List<Country> countryList) {
        List<String> stringList = new ArrayList<>();

        for (Country country : countryList) {
            stringList.add(country.getName());
        }

        //search
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, stringList);
        autoCompleteTextView = (AutoCompleteTextView)
                findViewById(R.id.countries_list);

        autoCompleteTextView.setAdapter(adapter);
    }

    // Location Serivce Section
    public void setupLocationService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionsUtil.checkLocationPermission(this)) {
                buildGoogleApiClient();
            }
        } else {
            buildGoogleApiClient();
        }
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        checkGPSEnabled();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionsUtil.REQUEST_LOCATION_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED)
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                } else {
                    //set England as the default city
                    homeFragmentCommunicator.isLocationGranted(false, null);
                }
            }
        }
        return;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        // To help preserve the device’s battery life, you’ll typically want to use
        // removeLocationUpdates to suspend location updates when your app is no longer
        // visible onscreen//
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
        homeFragmentCommunicator.isLocationGranted(true, location);
        Toast.makeText(this, location.getLatitude() + " WORKS " + location.getLongitude() + "", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showContent() {
        if (autoCompleteTextView != null)
            autoCompleteTextView.setVisibility(View.VISIBLE);
    }

    public void setHomeFragmentCommunicator(HomeFragmentCommunicator homeFragmentCommunicator) {
        this.homeFragmentCommunicator = homeFragmentCommunicator;
    }

    private void checkGPSEnabled() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
        else
        {
            startLocationUpdates();
        }
    }

    private void buildAlertMessageNoGps() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.gps_alert_dialog_message))
                .setCancelable(false)
                .setPositiveButton(R.string.gps_alert_dialog_yes_txt, new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), GPS_REQUEST);
                    }
                })
                .setNegativeButton(R.string.gps_alert_dialog_no_button, new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                        homeFragmentCommunicator.isLocationGranted(false,null);
                    }
                });
        final AlertDialog alert = builder.create();

        //Set dialog button color
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor
                        (MainActivity.this, R.color.blue));
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor
                        (MainActivity.this, R.color.blue));
            }
        });
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GPS_REQUEST && resultCode == 0) {
            String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if (provider != null) {
                startLocationUpdates();
                homeFragmentCommunicator.isLocationGranted(true, null);
            } else {
                //Users did not switch on the GPS
                homeFragmentCommunicator.isLocationGranted(false, null);
            }
        }
    }

    private void startLocationUpdates() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(2000);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Retrieve the user’s last known location//
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    locationRequest, this);
        }
    }
}
