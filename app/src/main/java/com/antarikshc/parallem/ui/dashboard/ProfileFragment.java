package com.antarikshc.parallem.ui.dashboard;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antarikshc.parallem.R;
import com.antarikshc.parallem.data.InjectorUtils;
import com.antarikshc.parallem.databinding.FragmentProfileBinding;
import com.antarikshc.parallem.models.user.User;
import com.antarikshc.parallem.ui.adapters.CertificationRecyclerAdapter;
import com.antarikshc.parallem.ui.adapters.ExperienceRecyclerAdapter;
import com.antarikshc.parallem.ui.adapters.SkillRecyclerAdapter;
import com.antarikshc.parallem.ui.adapters.UserProjectRecyclerAdapter;
import com.antarikshc.parallem.ui.authentication.AuthenticationActivity;
import com.antarikshc.parallem.util.API;
import com.antarikshc.parallem.util.ParallemApp;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    private static final String LOG_TAG = ProfileFragment.class.getSimpleName();

    // Global params
    private FragmentProfileBinding binding;
    private DashboardViewModel viewModel;
    private RecyclerView experienceList;
    private ExperienceRecyclerAdapter expAdapter;
    private RecyclerView userProjectList;
    private UserProjectRecyclerAdapter userProjectAdapter;
    private RecyclerView certificateList;
    private CertificationRecyclerAdapter certificationAdapter;
    private RecyclerView skillList;
    private SkillRecyclerAdapter skillAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate Fragment layout with data binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupAdapter();

        setupViewModel();

        onClickListeners();
    }

    /**
     * Boilerplate setup for RecyclerViewAdapter
     */
    private void setupAdapter() {

        // Experience Adapter
        experienceList = binding.recyclerProfileExperience;
        expAdapter = new ExperienceRecyclerAdapter(getActivity());
        experienceList.setLayoutManager(new LinearLayoutManager(getActivity()));
        experienceList.setNestedScrollingEnabled(false);
        experienceList.setAdapter(expAdapter);

        // User Project Adapter
        userProjectList = binding.recyclerProfileProjects;
        userProjectAdapter = new UserProjectRecyclerAdapter(getActivity());
        userProjectList.setLayoutManager(new LinearLayoutManager(getActivity()));
        userProjectList.setNestedScrollingEnabled(false);
        userProjectList.setAdapter(userProjectAdapter);

        // Certification Adapter
        certificateList = binding.recyclerProfileCertifications;
        certificationAdapter = new CertificationRecyclerAdapter(getActivity());
        certificateList.setLayoutManager(new LinearLayoutManager(getActivity()));
        certificateList.setNestedScrollingEnabled(false);
        certificateList.setAdapter(certificationAdapter);

        // Skill Adapter
        skillList = binding.recyclerProfileSkills;
        skillAdapter = new SkillRecyclerAdapter(getActivity());
        skillList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        skillList.setNestedScrollingEnabled(false);
        skillList.setAdapter(skillAdapter);
    }


    /**
     * Initiate ViewModel to start fetching data
     */
    private void setupViewModel() {

        DashboardViewModelFactory factory = InjectorUtils.provideDashboardViewModelFactory(getActivity().getApplicationContext());
        viewModel = ViewModelProviders.of(getActivity(), factory).get(DashboardViewModel.class);

        Log.i(LOG_TAG, "Getting Profile details from ViewModel");
        viewModel.getProfileDetails().observe(ProfileFragment.this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                assert user != null;

                // Load image with Picasso and set to ImageView
                Picasso.get()
                        .load(API.getProfileImageUrl(user.getProfileImage()))
                        .into(binding.imgProfilePicture);

                // Represent data in Toolbar
                binding.txtProfileName.setText(user.getName());
                binding.txtProfileHeadline.setText(user.getHeadline());
                String locationEmail = user.getLocation() + " " + getString(R.string.char_bullet)
                        + " " + user.getEmail();
                binding.txtProfileLocEmail.setText(locationEmail);

                // Send data to adapter to update them
                expAdapter.setData(user.getExperiences());
                userProjectAdapter.setData(user.getUserProjects());
                certificationAdapter.setData(user.getCertifications());
                skillAdapter.setData(user.getSkills());

            }
        });

    }

    /**
     * Setup all the Button onClick here
     */
    private void onClickListeners() {

        // Button Logout
        binding.btnProfileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    /**
     * This clears the SharedPrefs and Finishes the activity
     */
    private void logoutUser() {

        ParallemApp.removeAll();

        Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
        startActivity(intent);

        getActivity().finish();

    }
}
