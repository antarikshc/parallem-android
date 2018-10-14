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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antarikshc.parallem.R;
import com.antarikshc.parallem.data.InjectorUtils;
import com.antarikshc.parallem.databinding.FragmentHomeBinding;
import com.antarikshc.parallem.models.user.User;
import com.antarikshc.parallem.ui.UserProfileActivity;
import com.antarikshc.parallem.ui.adapters.CustomItemClickListener;
import com.antarikshc.parallem.ui.adapters.ExploreRecyclerAdapter;
import com.antarikshc.parallem.util.Master;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment {

    private static final String LOG_TAG = HomeFragment.class.getSimpleName();

    // Global params
    private FragmentHomeBinding binding;
    private DashboardViewModel viewModel;
    private User[] mUsers;
    private Gson gson;
    private RecyclerView exploreUserList;
    private ExploreRecyclerAdapter exploreAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate Fragment layout with data binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupExploreAdapter();

        setupViewModel();

        // Create GsonBuilder with Expose Annotation
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    }

    /**
     * Setup and Initialize RecyclerView and RecyclerViewAdapter
     */
    private void setupExploreAdapter() {

        exploreUserList = binding.recyclerHomeExplore;

        // Initialize Adapter
        exploreAdapter = new ExploreRecyclerAdapter(getActivity(), new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                openUserProfile(position);
            }
        });

        exploreUserList.setLayoutManager(new LinearLayoutManager(getActivity()));
        exploreUserList.setAdapter(exploreAdapter);

    }

    /**
     * Retrieve User object, convert to String and
     * Pass through Intent
     */
    private void openUserProfile(Integer position) {

        // Retrieve single User which we want to open
        User user = mUsers[position];

        String jsonString = gson.toJson(user);

        Intent intent = new Intent(getActivity(), UserProfileActivity.class);
        intent.putExtra("json_string", jsonString);
        startActivity(intent);
    }

    /**
     * Initiate ViewModel to start fetching data
     */
    private void setupViewModel() {

        DashboardViewModelFactory factory = InjectorUtils.provideDashboardViewModelFactory(getActivity().getApplicationContext());
        viewModel = ViewModelProviders.of(getActivity(), factory).get(DashboardViewModel.class);

        Log.i(LOG_TAG, "Getting Top Weekly Developer from ViewModel");
        viewModel.getTopWeeklyDev().observe(HomeFragment.this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                assert user != null;
                hookDataToWeeklyCard(user);
            }
        });

        Log.i(LOG_TAG, "Getting Users from ViewModel");
        viewModel.getExploreUsers().observe(HomeFragment.this, new Observer<User[]>() {
            @Override
            public void onChanged(@Nullable User[] users) {
                assert users != null;

                Log.i(LOG_TAG, users.length + " Users Received");
                if (users.length > 0) {
                    mUsers = users;
                    exploreAdapter.setData(users);
                }
            }
        });

    }

    /**
     * This represent data onto the Weekly Developer card
     *
     * @param user Object of TWD
     */
    private void hookDataToWeeklyCard(User user) {

        // Load image with Picasso and set to ImageView
        Picasso.get()
                .load(Master.getProfileImageUrl(user.getProfileImage()))
                .into(binding.imgTwdProfile);

        binding.txtTwdName.setText(user.getName());
        binding.txtTwdHeadline.setText(user.getHeadline());

    }

}
