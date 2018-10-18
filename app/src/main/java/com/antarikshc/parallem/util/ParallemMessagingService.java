package com.antarikshc.parallem.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.antarikshc.parallem.R;
import com.antarikshc.parallem.ui.dashboard.DashboardActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class ParallemMessagingService extends FirebaseMessagingService {

    private final static String LOG_TAG = ParallemMessagingService.class.getSimpleName();

    // Global params
    private Notification notification;
    private NotificationManager manager;

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title = "New collab request";
        String forId = remoteMessage.getData().get("for");
        String teamId = remoteMessage.getData().get("team_id");
        String message = remoteMessage.getData().get("message");

        // Show Notification for the Messages received only for the logged in user
        if (forId.equals(ParallemApp.getUserId())) {
            Log.i(LOG_TAG, "Message: " + message);

            // Create persistent notification and channel
            createNotification(title, message);

        }

    }

    private void createNotification(String title, String message) {

        String CHANNEL_ID = getResources().getString(R.string.notification_channel_id);

        Intent notificationIntent = new Intent(this, DashboardActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setColorized(false)
                .setContentIntent(pendingIntent)
                .build();


        // If Oreo+ then create Notification Channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel serviceChannel = new NotificationChannel(CHANNEL_ID,
                    "Collab Request",
                    NotificationManager.IMPORTANCE_DEFAULT);

            manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
            manager.notify(1, notification);

        } else {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(1, notification);
        }

    }
}
