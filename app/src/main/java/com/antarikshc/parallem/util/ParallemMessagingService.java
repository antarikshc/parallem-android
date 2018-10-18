package com.antarikshc.parallem.util;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class ParallemMessagingService extends FirebaseMessagingService {

    private final static String LOG_TAG = ParallemMessagingService.class.getSimpleName();

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String forId = remoteMessage.getData().get("for");
        String teamId = remoteMessage.getData().get("team_id");
        String message = remoteMessage.getData().get("message");

        // Show Notification for the Messages received only for the logged in user
        if (forId.equals(ParallemApp.getUserId())) {
            Log.i(LOG_TAG, "Message: " + message);
        }

    }
}
