package com.antarikshc.parallem.ui.dashboard;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.antarikshc.parallem.data.ParallemRepository;
import com.antarikshc.parallem.models.User;

public class DashboardViewModel extends ViewModel {

    private static final String LOG_TAG = DashboardViewModel.class.getSimpleName();

    private final ParallemRepository mRepository;
    private final LiveData<User[]> mUsers;

    public DashboardViewModel(ParallemRepository repository) {
        mRepository = repository;

        Log.i(LOG_TAG, "Getting Users from Repository");
        mUsers = mRepository.getUsers();
    }

    // Return the users
    public LiveData<User[]> getUsers() {
        return mUsers;
    }
}
