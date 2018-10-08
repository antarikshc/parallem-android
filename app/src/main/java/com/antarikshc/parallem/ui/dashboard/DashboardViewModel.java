package com.antarikshc.parallem.ui.dashboard;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.antarikshc.parallem.data.ParallemRepository;
import com.antarikshc.parallem.models.Skill;
import com.antarikshc.parallem.models.user.User;

public class DashboardViewModel extends ViewModel {

    private static final String LOG_TAG = DashboardViewModel.class.getSimpleName();

    private final ParallemRepository mRepository;
    private final LiveData<User[]> mUsers;
    private final LiveData<User> mProfile;
    private final LiveData<Skill[]> mSkills;

    public DashboardViewModel(ParallemRepository repository) {
        mRepository = repository;

        Log.i(LOG_TAG, "Getting Users from Repository");
        mUsers = mRepository.getExploreUsers();
        mProfile = mRepository.getProfileDetails();
        mSkills = mRepository.getAllSkills();

    }

    // Return the Explore users
    public LiveData<User[]> getExploreUsers() {
        return mUsers;
    }

    // Return the details of single user (Profile)
    public LiveData<User> getProfileDetails() {
        return mProfile;
    }

    // Return the List of all Skills
    public LiveData<Skill[]> getAllSkills() {
        return mSkills;
    }
}
