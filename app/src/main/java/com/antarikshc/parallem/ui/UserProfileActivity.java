package com.antarikshc.parallem.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.antarikshc.parallem.R;
import com.antarikshc.parallem.databinding.ActivityUserProfileBinding;
import com.antarikshc.parallem.models.user.User;
import com.antarikshc.parallem.ui.adapters.CertificationRecyclerAdapter;
import com.antarikshc.parallem.ui.adapters.ExperienceRecyclerAdapter;
import com.antarikshc.parallem.ui.adapters.SkillRecyclerAdapter;
import com.antarikshc.parallem.ui.adapters.UserProjectRecyclerAdapter;
import com.antarikshc.parallem.util.Master;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

public class UserProfileActivity extends AppCompatActivity {

    private static final String LOG_TAG = UserProfileActivity.class.getSimpleName();

    // Global params
    private ActivityUserProfileBinding binding;
    private Gson gson;
    private String userJsonString;
    private User user;
    private RecyclerView experienceList;
    private ExperienceRecyclerAdapter expAdapter;
    private RecyclerView userProjectList;
    private UserProjectRecyclerAdapter userProjectAdapter;
    private RecyclerView certificateList;
    private CertificationRecyclerAdapter certificationAdapter;
    private RecyclerView skillList;
    private SkillRecyclerAdapter skillAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Bind the layout
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile);

        Intent intent = getIntent();

        if (intent != null) {
            userJsonString = intent.getStringExtra("json_string");
        }

        // Create GsonBuilder with Expose Annotation
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        setupAdapter();

        hookDataToViews();

    }

    /**
     * Creates User object from String
     * Pass data onto views
     */
    private void hookDataToViews() {

        // Convert String to Object
        user = gson.fromJson(userJsonString, User.class);

        // Load image with Picasso and set to ImageView
        Picasso.get()
                .load(Master.getProfileImageUrl(user.getProfileImage()))
                .into(binding.imgUserPicture);

        // Represent data in Toolbar
        binding.txtUserName.setText(user.getName());
        binding.txtUserHeadline.setText(user.getHeadline());
        String locationEmail = user.getLocation() + " " + getString(R.string.char_bullet)
                + " " + user.getEmail();
        binding.txtUserLocEmail.setText(locationEmail);

        // Send data to adapter to update them
        expAdapter.setData(user.getExperiences());
        userProjectAdapter.setData(user.getUserProjects());
        certificationAdapter.setData(user.getCertifications());
        skillAdapter.setData(user.getSkills());

    }

    /**
     * Boilerplate setup for RecyclerViewAdapter
     */
    private void setupAdapter() {

        // Experience Adapter
        experienceList = binding.recyclerUserExperience;
        expAdapter = new ExperienceRecyclerAdapter(this);
        experienceList.setLayoutManager(new LinearLayoutManager(this));
        experienceList.setNestedScrollingEnabled(false);
        experienceList.setAdapter(expAdapter);

        // User Project Adapter
        userProjectList = binding.recyclerUserProjects;
        userProjectAdapter = new UserProjectRecyclerAdapter(this);
        userProjectList.setLayoutManager(new LinearLayoutManager(this));
        userProjectList.setNestedScrollingEnabled(false);
        userProjectList.setAdapter(userProjectAdapter);

        // Certification Adapter
        certificateList = binding.recyclerUserCertifications;
        certificationAdapter = new CertificationRecyclerAdapter(this);
        certificateList.setLayoutManager(new LinearLayoutManager(this));
        certificateList.setNestedScrollingEnabled(false);
        certificateList.setAdapter(certificationAdapter);

        // Skill Adapter
        skillList = binding.recyclerUserSkills;
        skillAdapter = new SkillRecyclerAdapter(this);
        skillList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        skillList.setNestedScrollingEnabled(false);
        skillList.setAdapter(skillAdapter);
    }


}
