package com.antarikshc.parallem.ui.team;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.antarikshc.parallem.R;
import com.antarikshc.parallem.databinding.ActivityTeamProfileBinding;
import com.antarikshc.parallem.models.team.Member;
import com.antarikshc.parallem.models.team.Team;
import com.antarikshc.parallem.ui.adapters.SkillRecyclerAdapter;
import com.antarikshc.parallem.ui.adapters.TeamMemberRecyclerAdapter;
import com.antarikshc.parallem.util.Master;
import com.antarikshc.parallem.util.ParallemApp;
import com.antarikshc.parallem.util.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.List;

public class TeamProfileActivity extends AppCompatActivity {

    private final static String LOG_TAG = TeamProfileActivity.class.getSimpleName();

    // Global params
    private ActivityTeamProfileBinding binding;
    private Gson gson;
    private RequestQueue requestQueue;
    private Team team;
    private RecyclerView skillList;
    private SkillRecyclerAdapter skillAdapter;
    private RecyclerView membersList;
    private TeamMemberRecyclerAdapter membersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_team_profile);

        // Get Json String from Intent
        Intent intent = getIntent();
        String jsonString = intent.getStringExtra("json_string");

        // Create GsonBuilder with Expose Annotation
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        // Get Volley Singleton Request Queue
        requestQueue = VolleySingleton.getInstance(this).getRequestQueue();

        setupAdapter();

        // Use GSON to convert string back to Team object
        if (!jsonString.isEmpty()) {

            team = gson.fromJson(jsonString, Team.class);

            hookDataToViews();

            skillAdapter.setData(team.getSkills());

            fetchTeamMembers();
        }
    }

    private void fetchTeamMembers() {

        JsonArrayRequest teamMemberRequest = new JsonArrayRequest(
                Request.Method.GET,
                Master.getTeamMembers(team.getId()),
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i(LOG_TAG, "Volley response received");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(LOG_TAG, "Volley Error occurred: " + error);
                    }
                }
        ) {
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {

                Log.i(LOG_TAG, "Parsing network response");

                try {
                    // Parse response data into String
                    String responseString = new String(response.data, "UTF-8");
                    JSONArray usersArray = new JSONArray(responseString);

                    // Parse JSON Array
                    final List<Member> members = gson.fromJson(usersArray.toString(),
                            new TypeToken<List<Member>>() {
                            }.getType());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            membersAdapter.setData(members);
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }

                return super.parseNetworkResponse(response);
            }
        };

        // Queue the API Call
        requestQueue.add(teamMemberRequest);

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

        // Team Members Adapter
        membersList = binding.recyclerTeamMembers;
        membersAdapter = new TeamMemberRecyclerAdapter(this, null);
        membersList.setLayoutManager(new LinearLayoutManager(this));
        membersList.setNestedScrollingEnabled(false);
        membersList.setAdapter(membersAdapter);
    }
}
