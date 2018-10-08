package com.antarikshc.parallem.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.antarikshc.parallem.data.network.NetworkDataSource;
import com.antarikshc.parallem.models.Skill;
import com.antarikshc.parallem.models.user.User;

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

    // Retrieve Profile details from Data Source
    public LiveData<User> getProfileDetails() {
        Log.i(LOG_TAG, "Getting Profile details from Data Source");
        return mNetworkDataSource.getProfileDetails();
    }

    // Retrieve Users from Data Source
    public LiveData<User[]> getExploreUsers() {
        Log.i(LOG_TAG, "Getting Explore Users from Data Source");
        return mNetworkDataSource.getExploreUsers();
    }


    // Retrieve Skilsl from Data Source
    public LiveData<Skill[]> getAllSkills() {
        Log.i(LOG_TAG, "Getting Skills from Data Source");
        return mNetworkDataSource.getAllSkills();
    }

}
