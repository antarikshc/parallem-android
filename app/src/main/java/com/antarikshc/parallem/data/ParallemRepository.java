package com.antarikshc.parallem.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.antarikshc.parallem.data.network.NetworkDataSource;
import com.antarikshc.parallem.models.User;

public class ParallemRepository {

    private static final String LOG_TAG = ParallemRepository.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static ParallemRepository sInstance;

    // Global params
    private final NetworkDataSource mNetworkDataSource;

    private ParallemRepository(NetworkDataSource networkDataSource) {
        mNetworkDataSource = networkDataSource;
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

    // Retrieve Users from Data Source
    public LiveData<User[]> getUsers() {
        Log.i(LOG_TAG, "Getting Users from Data Source");
        return mNetworkDataSource.getUsers();
    }
}
