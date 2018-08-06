package com.unam.alex.pumaride;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.unam.alex.pumaride.utils.Statics;

import java.net.URISyntaxException;

/**
 * Created by alex on 13/12/16.
 */
public class Application extends android.app.Application {

    public static final boolean DEBUG = true;
    public static Application application;

    private static Context context;

    public static String    packageName;
    public static Resources resources;
    public static Socket CHAT_SOCKET;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //@formatter:off
        resources   = this.getResources();
        context     = getApplicationContext();
        packageName = getPackageName();
        //@formatter:on

        IO.Options opts = new IO.Options();
        opts.forceNew = true;
        opts.reconnection = true;

        try {
            CHAT_SOCKET = IO.socket(Statics.CHAT_SERVER_BASE_URL, opts);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Log.e("SOCKET.IO ", e.getMessage());
        }

    }

    public static Context getContext() {
        return context;
    }

    public Socket getSocket() {
        return CHAT_SOCKET;
    }
}