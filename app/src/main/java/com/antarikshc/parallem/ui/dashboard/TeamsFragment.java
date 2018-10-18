package com.antarikshc.parallem.ui.dashboard;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.antarikshc.parallem.R;
import com.antarikshc.parallem.data.InjectorUtils;
import com.antarikshc.parallem.databinding.FragmentTeamsBinding;
import com.antarikshc.parallem.models.team.Team;
import com.antarikshc.parallem.ui.team.TeamProfileActivity;
import com.antarikshc.parallem.util.API;
import com.antarikshc.parallem.util.ParallemApp;
import com.antarikshc.parallem.util.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class TeamsFragment extends Fragment {

    private static final String LOG_TAG = TeamsFragment.class.getSimpleName();

    // Global params
    private FragmentTeamsBinding binding;
    private DashboardViewModel viewModel;
    private Gson gson;
    private RequestQueue requestQueue;
    private Team userTeam;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate Fragment layout with data binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_teams, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onClickListeners();

        setupViewModel();

        // Create GsonBuilder with Expose Annotation
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        // Get Singleton Volley Request Queue Instance
        requestQueue = VolleySingleton.getInstance(getActivity()).getRequestQueue();
    }

    /**
     * Initiate ViewModel to start fetching data
     */
    private void setupViewModel() {

        DashboardViewModelFactory factory = InjectorUtils.provideDashboardViewModelFactory(getActivity().getApplicationContext());
        viewModel = ViewModelProviders.of(getActivity(), factory).get(DashboardViewModel.class);

        Log.i(LOG_TAG, "Getting User's team profile from ViewModel");
        viewModel.getUserTeam().observe(TeamsFragment.this, new Observer<Team>() {
            @Override
            public void onChanged(@Nullable Team team) {
                assert team != null;
                userTeam = team;

                // Make YourTeam card visible and present data
                binding.cardUserTeamTeam.setVisibility(View.VISIBLE);
                // Hide the FAB Button to create a team
                binding.fabTeamsCreate.hide();

                binding.txtUserTeamName.setText(team.getName());
                binding.txtUserTeamDesc.setText(team.getDesc());
            }
        });

    }

    public void createTeamDialog() {

        // Build AlertDialog and inflate views
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity(), R.style.DialogRoundedCorners);
        View mView = getLayoutInflater().inflate(R.layout.dialog_create_team, null);

        // Bind views from Dialog layout
        final TextInputEditText editName = mView.findViewById(R.id.edit_create_team_name);
        final TextInputEditText editDesc = mView.findViewById(R.id.edit_create_team_desc);
        final TextInputEditText editCapacity = mView.findViewById(R.id.edit_create_team_capacity);
        final TextInputLayout layoutName = mView.findViewById(R.id.til_create_team_name);
        final TextInputLayout layoutDesc = mView.findViewById(R.id.til_create_team_desc);
        final TextInputLayout layoutCapacity = mView.findViewById(R.id.til_create_team_capacity);
        Button btnSave = mView.findViewById(R.id.btn_create_team_save);
        Button btnCancel = mView.findViewById(R.id.btn_create_team_cancel);

        // Set view and create AlertDialog
        mBuilder.setView(mView);
        final AlertDialog alertDialog = mBuilder.create();

        // OnClickListeners for Buttons
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hitCreateTeamAPI(
                        editName.getText().toString(),
                        editDesc.getText().toString(),
                        Integer.parseInt(editCapacity.getText().toString()));

                alertDialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();

    }

    private void hitCreateTeamAPI(String name, String desc, Integer capacity) {

        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("name", name);
            requestObject.put("desc", desc);
            requestObject.put("capacity", capacity);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Volley request to Register FCM Token
        JsonObjectRequest createTeamRequest = new JsonObjectRequest(
                Request.Method.POST,
                API.getCreateTeamEndpoint(ParallemApp.getUserId()),
                requestObject,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(LOG_TAG, "HTTP Response received for CreateTeamRequest");
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(LOG_TAG, "HTTP Error occurred: " + error);
                    }
                }
        ) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

                // Retrieve status code
                Integer statusCode = response.statusCode;

                try {
                    if (statusCode == 201) {

                        // Parse response data into String
                        String responseString = new String(response.data, "UTF-8");

                        // Create JSONObject from the responseString
                        JSONObject responseObject = new JSONObject(responseString);

                        String teamId = (responseObject.getJSONObject("data")).getString("team_id");

                        ParallemApp.saveTeamId(teamId);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return super.parseNetworkResponse(response);
            }
        };

        // Queue the API Call
        requestQueue.add(createTeamRequest);


    }

    /**
     * Setup all the Button onClick here
     */
    private void onClickListeners() {

        binding.cardUserTeamTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String jsonString = gson.toJson(userTeam);

                Intent intent = new Intent(getActivity(), TeamProfileActivity.class);
                intent.putExtra("json_string", jsonString);
                startActivity(intent);

            }
        });

        binding.fabTeamsCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTeamDialog();
            }
        });

    }
}
