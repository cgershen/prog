package com.unam.alex.pumaride.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * Created by alex on 31/10/16.
 */
public class Statics {
    private static Statics ourInstance = new Statics();

    public static Statics getInstance() {
        return ourInstance;
    }

    private Statics() {
    }

    public static final String PREFERENCES = "preferencias";
    public static final String VERSION_CODE ="VERSION_CODE";
    public static final String NOTIFICATIONS = "NOTIFICATIONS";
    public static final String GCM_SENDER_ID = "513259354042";
    public static final String GCM_SERVER_KEY = "AIzaSyCS2el2ANbFYo2L9Or49qYSSLVLrcIG_9c";
    public static String SERVER_BASE_URL = "http://pumaride.codingo.mx/";
    public static String AUXILIAR_SERVER_BASE_URL = "http://35.162.215.204:8000/";
    public static String CHAT_SERVER_BASE_URL = "http://184.173.94.0:12439/";
    public static String GOOGLE_API_REVERSE_GEOCODE = "https://maps.googleapis.com/maps/api/geocode/";
    public static String GCM_SERVER_URL = "http://gcm.codingo.mx/";
    public static final String URL_SERVER_REGISTRATION = "http://gcm.codingo.mx/register.php";
    public static String getDeviceId(Context c) {
        TelephonyManager tm = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }
    public static String getDeviceGmail(Context c) {
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(c).getAccounts();
        String possibleEmail = "";
        for (Account account : accounts) {
            if (account.name.endsWith("@gmail.com")) {
                possibleEmail = account.name;
            }
        }
        return possibleEmail;
    }

}
