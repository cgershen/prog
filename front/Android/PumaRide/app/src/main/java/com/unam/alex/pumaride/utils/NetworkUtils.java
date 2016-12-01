package com.unam.alex.pumaride.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by alex on 24/06/16.
 */
public class NetworkUtils {
    public final static int NO_INTERNET_CONECTION = 0;
    public final static int WIFI_MODE = 1;
    public final static int MOBILE_MODE = 2;

    private static NetworkUtils ourInstance = new NetworkUtils();

    public static NetworkUtils getInstance() {
        return ourInstance;
    }

    private NetworkUtils() {
    }
    public static boolean isNetworkAvailable(Context context){
        if(getConectionStatus(context)>0)
            return true;
        return false;
    }
    public static boolean isWifiAvailable(Context context){
        if(getConectionStatus(context)==WIFI_MODE)
            return true;
        return false;
    }
    public static boolean isMobileAvailable(Context context){
        if(getConectionStatus(context)==MOBILE_MODE)
            return true;
        return false;
    }
    public static int getConectionStatus(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                //Toast.makeText(context, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
                return WIFI_MODE;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                //Toast.makeText(context, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
                return MOBILE_MODE;
            }
        }
        return NO_INTERNET_CONECTION;
    }
}
