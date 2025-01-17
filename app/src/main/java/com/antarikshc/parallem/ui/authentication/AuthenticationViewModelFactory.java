package com.antarikshc.parallem.ui.authentication;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.antarikshc.parallem.data.ParallemRepository;

public class AuthenticationViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final ParallemRepository mRepository;

    public AuthenticationViewModelFactory(ParallemRepository mRepository) {
        this.mRepository = mRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        // No inspection unchecked
        return (T) new AuthenticationViewModel(mRepository);
    }
}
