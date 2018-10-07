package com.antarikshc.parallem.ui.authentication;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antarikshc.parallem.R;
import com.antarikshc.parallem.databinding.FragmentPersonalDetailsBinding;


public class PersonalDetailsFragment extends Fragment {

    // Global params
    private FragmentPersonalDetailsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate Fragment layout with data binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_personal_details, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onClickListeners();
    }

    /**
     * Setup all the Button onClick here
     */
    private void onClickListeners() {

        // Button - Continue
        binding.btnPersonalDetailsContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddProfileActivity.attachFragment(new CareerDetailsFragment());
            }
        });

        // Button - Skip
        binding.btnPersonalDetailsSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProfileActivity.attachFragment(new CareerDetailsFragment());
            }
        });
    }

}
