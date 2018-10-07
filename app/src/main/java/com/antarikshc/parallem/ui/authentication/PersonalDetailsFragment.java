package com.antarikshc.parallem.ui.authentication;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antarikshc.parallem.R;
import com.antarikshc.parallem.databinding.FragmentPersonalDetailsBinding;
import com.antarikshc.parallem.models.user.ProfileAvatar;
import com.antarikshc.parallem.ui.adapters.AvatarRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;


public class PersonalDetailsFragment extends Fragment {

    // Global params
    private FragmentPersonalDetailsBinding binding;
    private RecyclerView avatarList;
    private AvatarRecyclerAdapter avatarAdapter;

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

        setupAvatarAdapter();
    }

    /**
     * Setup Profile Avatar RecyclerViewAdapter
     */
    private void setupAvatarAdapter() {

        // Find the Recycler View Adapter
        avatarList = binding.recyclerPersonalAvatar;

        // Initialize adapter
        avatarAdapter = new AvatarRecyclerAdapter();

        // Set GridLayout as LayoutManager
        avatarList.setLayoutManager(new GridLayoutManager(getContext(), 4));

        avatarList.setAdapter(avatarAdapter);

        List<ProfileAvatar> avatars = new ArrayList<ProfileAvatar>();
        for (int i = 1; i <= 22; i++) {
            // Temp: Hardcoding 22 as we 22 image illustrations

            avatars.add(new ProfileAvatar(i, false));

        }
        avatarAdapter.setData(avatars);


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
