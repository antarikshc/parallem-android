package com.antarikshc.parallem.data.network;

import android.content.Context;
import android.util.Log;

public class NetworkDataSource {

    private static final String LOG_TAG = NetworkDataSource.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static NetworkDataSource sInstance;
    private final Context mContext;

    private NetworkDataSource(Context context) {
        mContext = context;
    }

    /**
     * Get the singleton for this class
     */
    public static NetworkDataSource getInstance(Context context) {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new NetworkDataSource(context.getApplicationContext());
                Log.d(LOG_TAG, "Made new network data source");
            }
        }

        return sInstance;
    }

    //TODO: Queue all data retrieval tasks from network in here


}
