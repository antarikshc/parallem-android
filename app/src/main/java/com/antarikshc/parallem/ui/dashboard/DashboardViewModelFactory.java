package com.antarikshc.parallem.ui.dashboard;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.antarikshc.parallem.data.ParallemRepository;

public class DashboardViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final ParallemRepository mRepository;

    public DashboardViewModelFactory(ParallemRepository repository) {
        this.mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        // No inspection unchecked
        return super.create(modelClass);
    }
}
