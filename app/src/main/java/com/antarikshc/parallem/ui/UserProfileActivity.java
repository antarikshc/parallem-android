package com.antarikshc.parallem.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.antarikshc.parallem.R;
import com.antarikshc.parallem.databinding.ActivityUserProfileBinding;
import com.antarikshc.parallem.models.user.User;
import com.antarikshc.parallem.ui.adapters.CertificationRecyclerAdapter;
import com.antarikshc.parallem.ui.adapters.ExperienceRecyclerAdapter;
import com.antarikshc.parallem.ui.adapters.SkillRecyclerAdapter;
import com.antarikshc.parallem.ui.adapters.UserProjectRecyclerAdapter;
import com.antarikshc.parallem.util.API;
import com.antarikshc.parallem.util.ParallemApp;
import com.antarikshc.parallem.util.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class UserProfileActivity extends AppCompatActivity {

    private static final String LOG_TAG = UserProfileActivity.class.getSimpleName();

    // Global params
    private ActivityUserProfileBinding binding;
    private Gson gson;
    private RequestQueue requestQueue;
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

        // For some reason Material Buttons are refusing to attach Drawable through XML
        binding.btnUserCollabRequest.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                R.drawable.ic_send, 0);

        Intent intent = getIntent();

        // Create GsonBuilder with Expose Annotation
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        // Get Singleton Volley Request Queue Instance
        requestQueue = VolleySingleton.getInstance(this).getRequestQueue();

        setupAdapter();

        if (intent != null) {

            // Create User object from JSON String passed through intent
            userJsonString = intent.getStringExtra("json_string");
            user = gson.fromJson(userJsonString, User.class);

            hookDataToViews();
        }

    }

    /**
     * Creates User object from String
     * Pass data onto views
     */
    private void hookDataToViews() {

        // Load image with Picasso and set to ImageView
        Picasso.get()
                .load(API.getProfileImageUrl(user.getProfileImage()))
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
     * On Click for Sending Collab request
     *
     * @param view Request to Collab button
     */
    public void sendCollabRequest(View view) {

        if (ParallemApp.isTeamIdExist()) {

            binding.progressBarUserProfile.setVisibility(View.VISIBLE);

            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("team_id", ParallemApp.getTeamId());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest collabRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    API.getCollabRequestEndpoint(user.get_id()),
                    jsonObject,

                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i(LOG_TAG, "HTTP Response received for CollabRequest");
                            binding.progressBarUserProfile.setVisibility(View.GONE);

                            Toast.makeText(UserProfileActivity.this, "Collab request has been sent!", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i(LOG_TAG, "HTTP Error occurred: " + error);
                            binding.progressBarUserProfile.setVisibility(View.GONE);

                            Toast.makeText(UserProfileActivity.this, "Couldn't send collab request :(", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
            );

            // Queue the API Call
            requestQueue.add(collabRequest);

        } else {

            Toast.makeText(this, "Please first create a team!", Toast.LENGTH_LONG).show();

        }

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
