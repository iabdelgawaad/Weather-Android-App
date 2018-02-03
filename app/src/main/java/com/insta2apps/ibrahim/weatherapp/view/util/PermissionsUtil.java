package com.insta2apps.ibrahim.weatherapp.view.util;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.insta2apps.ibrahim.weatherapp.R;

import java.util.ArrayList;

/**
 * Created by Ibrahim AbdelGawad on 2/2/2018.
 */

public class PermissionsUtil {
    public static final int REQUEST_LOCATION_PERMISSION = 200;

    public static boolean checkLocationPermission(final Activity activity) {
        ArrayList<String> permissionsNeeded = new ArrayList<>();

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                final AlertDialog alert = new AlertDialog.Builder(activity)
                        .setTitle(activity.getString(R.string.dialog_location_permission_title))
                        .setMessage(activity.getString(R.string.dialog_location_permission_message))
                        .setCancelable(false)
                        .setPositiveButton(activity.getString(R.string.ok_text), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(activity,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION
                                );
                            }
                        })
                        .create();

                //Set dialog button color
                alert.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        alert.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor
                                (activity, R.color.blue));
                        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor
                                (activity, R.color.blue));
                    }
                });
                alert.show();
                return false;

            } else {
                permissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
        }
        if (!permissionsNeeded.isEmpty()) {
            requestPermission(activity, permissionsNeeded.toArray(new String[permissionsNeeded.size()]), REQUEST_LOCATION_PERMISSION);
            return false;
        }
        return true;
    }

    private static void requestPermission(Activity activity, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }
}
