package com.antarikshc.parallem.util;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class ParallemMessagingService extends FirebaseMessagingService {

    private final static String LOG_TAG = ParallemMessagingService.class.getSimpleName();

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        Log.i(LOG_TAG, "OneTimeToken: " + token);

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.i(LOG_TAG, "Message: " + remoteMessage.getData().toString());

    }
}
