package com.unam.alex.pumaride.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.unam.alex.pumaride.MainActivity;
import com.unam.alex.pumaride.R;

/**
 * Created by alex on 1/11/16.
 */
public class FCMMessageReceiverService {

}/*extends FirebaseMessagingService {
    @Override
    protected Intent zzae(Intent intent) {
        return null;
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.w("fcm", "received notification");
        sendNotification(remoteMessage.getNotification().getTitle());
    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(messageBody)
                .setAutoCancel(false)
                .setSound(defaultSoundUri);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1, notificationBuilder.build());
    }

}*/