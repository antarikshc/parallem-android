package com.antarikshc.parallem.ui.authentication;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.antarikshc.parallem.data.ParallemRepository;
import com.antarikshc.parallem.models.Skill;
import com.antarikshc.parallem.ui.dashboard.DashboardViewModel;

public class AddProfileViewModel extends ViewModel {

    private static final String LOG_TAG = DashboardViewModel.class.getSimpleName();

    private final ParallemRepository mRepository;
    private final LiveData<Skill[]> mSkills;

    public AddProfileViewModel(ParallemRepository mRepository) {
        this.mRepository = mRepository;
        mSkills = mRepository.getAllSkills();
    }

    // Return the List of all Skills
    public LiveData<Skill[]> getAllSkills() {
        return mSkills;
    }
}
