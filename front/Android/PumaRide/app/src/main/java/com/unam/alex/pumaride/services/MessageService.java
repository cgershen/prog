package com.unam.alex.pumaride.services;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.unam.alex.pumaride.MessageActivity;
import com.unam.alex.pumaride.R;
import com.unam.alex.pumaride.models.Match;
import com.unam.alex.pumaride.models.Message;
import com.unam.alex.pumaride.utils.Statics;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MessageService extends Service {

    Realm realm = null;
    private NotificationManager mNotificationManager;
    PendingIntent contentIntent;
    LocalBroadcastManager broadcaster;
    int id = 10;
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(Statics.CHAT_SERVER_BASE_URL);
        } catch (URISyntaxException e) {}
    }
    public MessageService() {
    }
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... objects) {
            String result = (String) objects[0];

            sendResult(result);
        }
    };
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Initialize Realm
        SharedPreferences sp = getSharedPreferences("pumaride", Activity.MODE_PRIVATE);
        id = sp.getInt("userid",1);
        broadcaster = LocalBroadcastManager.getInstance(this);
        mSocket.on("chat"+id, onNewMessage);
        mSocket.connect();
        return Service.START_NOT_STICKY;
    }
    public void notifyMessage(ArrayList<Message> messages){

        Intent i = new Intent(getApplicationContext(), MessageActivity.class);
        contentIntent = PendingIntent.getActivity(getApplicationContext(),0,i,0);
        int id=1;
        mNotificationManager = (NotificationManager) getApplicationContext().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                getApplicationContext()).setSmallIcon(R.drawable.ic_add_alert_black_24dp)
                .setContentTitle("PumarRide")
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Prueba"))
                .setContentText("Pruebas");
        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("PumaRide:");
        for (Message m:messages){
            String name = "Usuario";
            try {
                Match match = (Match) realm.where(Match.class).equalTo("id", m.getUser_id()).findFirst();
                name = match.getName();
                MessageActivity.id2 = match.getId();
            }catch(Exception e) {

            }
                inboxStyle.addLine(name+":"+m.getMessage());
        }
        mBuilder.setStyle(inboxStyle);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(id, mBuilder.build());
    }
    static final public String MESSAGE_RESULT = "com.unam.alex.pumarde.MessageService.REQUEST_PROCESSED";

    static final public String MESSAGE = "com.unam.alex.pumarde.MSG";
    static final public String ACTION="action";

    public void sendResult(final String message) {

        if(!MessageActivity.active) {
            Handler mainHandler = new Handler(getApplicationContext().getMainLooper());

            Runnable myRunnable = new Runnable() {
                @Override
                public void run() {
                    Realm.init(getBaseContext());
                    // Get a Realm instance for this thread
                    realm = Realm.getDefaultInstance();
                    Message m  = new Gson().fromJson(message,Message.class);
                    //reload message and alert
                    m.setId(getNextKey());
                    m.setReaded(false);
                    realm.beginTransaction();
                    Message realmMessage = realm.copyToRealm(m);
                    realm.commitTransaction();
                    int maxid = 0;
                    try{
                        maxid = getMaxMessageByUserId(m.getUser_id())-4;
                    }catch(Exception e){

                    }
                    final RealmResults<Message> messages = realm.where(Message.class).equalTo("readed",false).findAllSorted("id");
                    ArrayList<Message> messages_ = new ArrayList<>(messages);
                    notifyMessage(messages_);
                } // This is your code
            };
            mainHandler.post(myRunnable);




        }else {
            Intent intent = new Intent(MESSAGE_RESULT);
            if (message != null) {
                intent.putExtra(MESSAGE, message);
            }
            broadcaster.sendBroadcast(intent);
        }
    }
    int getMaxMessageByUserId(int userId){
        SharedPreferences sp = getSharedPreferences("pumaride", Activity.MODE_PRIVATE);
        id = sp.getInt("userid",1);
        int v = realm.where(Message.class).equalTo("user_id",userId).equalTo("user_id",id).max("id").intValue();
        return v;
    }
    public int getNextKey()
    {
        return realm.where(Message.class).max("id").intValue() + 1;
    }
}
