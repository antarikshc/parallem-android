package com.antarikshc.parallem.ui.dashboard;

import android.arch.lifecycle.ViewModel;

import com.antarikshc.parallem.data.ParallemRepository;

public class DashboardViewModel extends ViewModel {

    private final ParallemRepository mRepository;

    public DashboardViewModel(ParallemRepository repository) {
        mRepository = repository;
        //TODO: Query data from NetworkDataSource
    }
}
