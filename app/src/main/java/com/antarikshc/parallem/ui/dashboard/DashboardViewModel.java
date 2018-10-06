package com.antarikshc.parallem.ui.dashboard;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.antarikshc.parallem.data.ParallemRepository;
import com.antarikshc.parallem.models.user.User;

public class DashboardViewModel extends ViewModel {

    private static final String LOG_TAG = DashboardViewModel.class.getSimpleName();

    private final ParallemRepository mRepository;
    private final LiveData<User[]> mUsers;
    private final LiveData<User> mProfile;

    public DashboardViewModel(ParallemRepository repository) {
        mRepository = repository;

        Log.i(LOG_TAG, "Getting Users from Repository");
        mUsers = mRepository.getExploreUsers();
        mProfile = mRepository.getProfileDetails();

    }

    // Return the Explore users
    public LiveData<User[]> getExploreUsers() {
        return mUsers;
    }

    // Return the details of single user (Profile)
    public LiveData<User> getProfileDetails() {
        return mProfile;
    }
}
