package com.antarikshc.parallem.data;

import android.util.Log;

import com.antarikshc.parallem.data.network.NetworkDataSource;

public class ParallemRepository {

    private static final String LOG_TAG = ParallemRepository.class.getSimpleName();
    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static ParallemRepository sInstance;
    // Global params
    private final NetworkDataSource mNetworkDataSource;

    private ParallemRepository(NetworkDataSource networkDataSource) {
        mNetworkDataSource = networkDataSource;

        //TODO: Fetch data from DataSource
    }

    public synchronized static ParallemRepository getInstance(NetworkDataSource networkDataSource) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new ParallemRepository(networkDataSource);
                Log.d(LOG_TAG, "Initiated new repository");
            }
        }

        return sInstance;
    }
}
