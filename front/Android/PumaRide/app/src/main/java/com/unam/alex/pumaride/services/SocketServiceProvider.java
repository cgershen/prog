package com.unam.alex.pumaride.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.unam.alex.pumaride.Application;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.internal.Utils;
import io.realm.Realm;

public class SocketServiceProvider extends Service {
    private Application signalApplication;

    public static SocketServiceProvider instance = null;

    public static boolean isInstanceCreated() {
        return instance == null ? false : true;
    }

    private final IBinder myBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public SocketServiceProvider getService() {
            return SocketServiceProvider.this;
        }
    }

    public void IsBendable() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public void onCreate() {
        if (isInstanceCreated()) {
            return;
        }
        super.onCreate();

        signalApplication = (Application) getApplication();

        if (signalApplication.getSocket() == null)
            signalApplication.CHAT_SOCKET = signalApplication.getSocket();

        signalApplication.getSocket().on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        signalApplication.getSocket().on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        signalApplication.getSocket().on(Socket.EVENT_CONNECT, onConnect);

        //@formatter:off
        signalApplication.getSocket().on("chat2" , message);
        //@formatter:on

        //EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isInstanceCreated()) {
            return 0;
        }
        super.onStartCommand(intent, flags, startId);
        connectConnection();
        return START_STICKY;
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    //EventBus.getDefault().post(new EventChangeChatServerStateEvent(EventChangeChatServerStateEvent.chatServerState.connectedToSocket));
                }
            });
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    //EventBus.getDefault().post(
                      //      new EventChangeChatServerStateEvent(EventChangeChatServerStateEvent.chatServerState.disconnectedFromSocket)
                    //);
                }
            });
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    //EventBus.getDefault().post(
                      //      new EventChangeChatServerStateEvent(EventChangeChatServerStateEvent.chatServerState.flashConnectionIcon)
                    //);
                }
            });
        }
    };

    private Emitter.Listener message = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            String result = (String) args[0];
            Toast.makeText(SocketServiceProvider.this, ""+result, Toast.LENGTH_SHORT).show();
        }
    };

    private void connectConnection() {
        instance = this;
        signalApplication.getSocket().connect();
    }

    private void disconnectConnection() {
        instance = null;
        signalApplication.getSocket().disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


        signalApplication.getSocket().off(Socket.EVENT_CONNECT, onConnect);
        signalApplication.getSocket().off(Socket.EVENT_DISCONNECT, onDisconnect);
        signalApplication.getSocket().off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        signalApplication.getSocket().off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);

        //@formatter:off
        signalApplication.getSocket().off("chat2", message);
        //@formatter:on

        disconnectConnection();
    }

}
