package com.antarikshc.parallem.ui.dashboard;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antarikshc.parallem.R;
import com.antarikshc.parallem.data.InjectorUtils;
import com.antarikshc.parallem.databinding.FragmentTeamsBinding;
import com.antarikshc.parallem.models.team.Team;
import com.antarikshc.parallem.ui.team.TeamProfileActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TeamsFragment extends Fragment {

    private static final String LOG_TAG = TeamsFragment.class.getSimpleName();

    // Global params
    private FragmentTeamsBinding binding;
    private DashboardViewModel viewModel;
    private Gson gson;
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

    }
}
