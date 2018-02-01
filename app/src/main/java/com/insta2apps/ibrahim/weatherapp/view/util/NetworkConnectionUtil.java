package com.insta2apps.ibrahim.weatherapp.view.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Ibrahim AbdelGawad on 2/1/2018.
 */

public class NetworkConnectionUtil {
    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
        } else {
            return true;
        }
    }
}
