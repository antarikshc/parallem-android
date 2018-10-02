package com.antarikshc.parallem.ui.dashboard;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antarikshc.parallem.R;
import com.antarikshc.parallem.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    // Global params
    private FragmentNotificationsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate Fragment layout with data binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notifications, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
