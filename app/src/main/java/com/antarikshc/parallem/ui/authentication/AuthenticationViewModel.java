package com.antarikshc.parallem.ui.authentication;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.antarikshc.parallem.data.ParallemRepository;
import com.antarikshc.parallem.models.Skill;
import com.antarikshc.parallem.models.user.User;
import com.antarikshc.parallem.ui.dashboard.DashboardViewModel;

public class AuthenticationViewModel extends ViewModel {

    private static final String LOG_TAG = DashboardViewModel.class.getSimpleName();

    private final ParallemRepository mRepository;
    private final LiveData<Skill[]> mSkills;
    private User mUser;

    public AuthenticationViewModel(ParallemRepository mRepository) {
        this.mRepository = mRepository;
        mSkills = mRepository.getAllSkills();
        mRepository.getProfileDetails().observeForever(new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                mUser = user;
            }
        });
    }

    // Return the List of all Skills
    public LiveData<Skill[]> getAllSkills() {
        return mSkills;
    }

    // Update User
    public void updateUser(User user) {
        mUser = user;
    }

    // Retrieve user
    public User getUser() {
        return mUser;
    }
}
