package com.unam.alex.pumaride;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.gcm.GcmListenerService;
import com.google.gson.Gson;
import com.unam.alex.pumaride.models.Match;
import com.unam.alex.pumaride.models.Message;
import com.unam.alex.pumaride.models.Route;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MyGcmListenerService extends GcmListenerService {

    LocalBroadcastManager broadcaster;
    String TAG = getClass().getName();
    int countNotification = 0;
    int id = 10;
    Match match;
    Realm realm = null;
    private NotificationManager mNotificationManager;
    PendingIntent contentIntent;
    static final public String MESSAGE_RESULT = "com.unam.alex.pumarde.MessageService.REQUEST_PROCESSED";

    static final public String MESSAGE = "com.unam.alex.pumarde.MSG";
    static final public String ACTION="action";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        String msg = data.getString("message");
        SharedPreferences sp = getSharedPreferences("pumaride", Activity.MODE_PRIVATE);
        id = sp.getInt("userid",1);
        broadcaster = LocalBroadcastManager.getInstance(this);
        //NotificationHelper.generateNotification(getApplicationContext(), msg, "Mensaje", true);
        final Message m  = new Gson().fromJson(msg,Message.class);
        int value = m.getMessage().indexOf("@code");
        int value2 = m.getMessage().indexOf("#code");
        if(value==-1){
            if(value2==-1) {
                sendResult(msg);
            }else{

                Handler mainHandler = new Handler(getApplicationContext().getMainLooper());
                Runnable myRunnable = new Runnable() {
                    @Override
                    public void run() {
                        Realm.init(getBaseContext());
                        // Get a Realm instance for this thread
                        realm = Realm.getDefaultInstance();
                        RealmObject obj = realm.where(Match.class).equalTo("id",m.getUser_id()).findFirst();
                        RealmObject obj2 = realm.where(Route.class).equalTo("match.id",m.getUser_id()).findFirst();
                        realm.beginTransaction();
                        obj2.deleteFromRealm();
                        obj.deleteFromRealm();
                        realm.commitTransaction();
                        if(MainTabActivity.active){
                            Intent intent = new Intent(MESSAGE_RESULT);
                            intent.putExtra(MESSAGE, "reload_fragments");
                            broadcaster.sendBroadcast(intent);
                        }
                    } // This is your code
                };
                mainHandler.post(myRunnable);
            }
        }else{
            if(MapsActivity.active){
                Intent intent = new Intent(MESSAGE_RESULT);
                intent.putExtra(MESSAGE, msg);
                broadcaster.sendBroadcast(intent);
            }
        }
    }
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

            Handler mainHandler = new Handler(getApplicationContext().getMainLooper());
            Runnable myRunnable = new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MESSAGE_RESULT);
                    if (message != null) {
                        intent.putExtra(MESSAGE, message);
                    }
                    broadcaster.sendBroadcast(intent);
                } // This is your code
            };
            mainHandler.post(myRunnable);
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
        int result = 1;
        try{
            result = realm.where(Message.class).max("id").intValue() + 1;
        }catch(Exception e){

        }
        return result;
    }
    public void notifyMessage(ArrayList<Message> messages){

        Intent i = new Intent(getApplicationContext(), MessageActivity.class);
        contentIntent = PendingIntent.getActivity(getApplicationContext(),0,i,0);
        int id=1;
        mNotificationManager = (NotificationManager) getApplicationContext().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                getApplicationContext()).setSmallIcon(R.drawable.ic_pumaride_alert)
                .setContentTitle("PumarRide")
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Mensaje"))
                .setContentText("Mensaje");
        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("PumaRide:");
        for (Message m:messages){
            String name = "Usuario";
            try {
                match = (Match) realm.where(Match.class).equalTo("id", m.getUser_id()).findFirst();
                name = match.getFirst_name();
                MessageActivity.id2 = match.getId();
            }catch(Exception e) {

            }
            inboxStyle.addLine(name+":"+m.getMessage());
        }
        mBuilder.setStyle(inboxStyle);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(id, mBuilder.build());
    }
}
