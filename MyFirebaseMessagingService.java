package com.example.sakthivel.retrofitapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle("FCM Notification");
        notificationBuilder.setContentTitle(remoteMessage.getNotification().getBody());
        notificationBuilder.setAutoCancel(false);
        notificationBuilder.setSmallIcon(R.drawable.ic_favorite_true);
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBuilder.build());

    }


}