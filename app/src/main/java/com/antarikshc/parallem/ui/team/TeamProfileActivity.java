package com.antarikshc.parallem.ui.team;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.antarikshc.parallem.R;
import com.antarikshc.parallem.databinding.ActivityTeamProfileBinding;
import com.antarikshc.parallem.models.team.Member;
import com.antarikshc.parallem.models.team.Team;
import com.antarikshc.parallem.ui.adapters.SkillRecyclerAdapter;
import com.antarikshc.parallem.util.ParallemApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class TeamProfileActivity extends AppCompatActivity {

    // Global params
    private ActivityTeamProfileBinding binding;
    private Team team;
    private Gson gson;
    private RecyclerView skillList;
    private SkillRecyclerAdapter skillAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_team_profile);

        // Get Json String from Intent
        Intent intent = getIntent();
        String jsonString = intent.getStringExtra("json_string");

        // Create GsonBuilder with Expose Annotation
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        setupAdapter();

        // Use GSON to convert string back to Team object
        if (!jsonString.isEmpty()) {

            team = gson.fromJson(jsonString, Team.class);

            hookDataToViews();

            skillAdapter.setData(team.getSkills());
        }
    }

    /**
     * Represent data onto the views
     */
    private void hookDataToViews() {

        if (ParallemApp.getTeamId().equals(team.getId())) {
            binding.btnTeamRequestJoin.setVisibility(View.GONE);
        } else {
            binding.btnTeamRequestJoin.setVisibility(View.VISIBLE);
        }

        binding.txtTeamName.setText(team.getName());
        binding.txtTeamDesc.setText(team.getDesc());

        List<Member> members = team.getMembers();

        String capacityText = members.size() + "/" + team.getCapacity();
        binding.txtTeamCapacity.setText(capacityText);

    }

    private void setupAdapter() {

        // Skill Adapter
        skillList = binding.recyclerTeamSkills;
        skillAdapter = new SkillRecyclerAdapter(this);
        skillList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        skillList.setNestedScrollingEnabled(false);
        skillList.setAdapter(skillAdapter);
    }
}
