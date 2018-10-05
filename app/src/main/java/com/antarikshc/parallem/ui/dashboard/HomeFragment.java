package com.antarikshc.parallem.ui.dashboard;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import com.antarikshc.parallem.ui.adapters.ExploreRecyclerAdapter;


public class HomeFragment extends Fragment {

    private static final String LOG_TAG = HomeFragment.class.getSimpleName();

    // Global params
    private FragmentHomeBinding binding;
    private DashboardViewModel viewModel;
    private RecyclerView exploreUserList;
    private ExploreRecyclerAdapter adapter;

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

        setupViewModel();

        setupRecyclerViewAdapter();

    }

    /**
     * Setup and Initialize RecyclerView and RecyclerViewAdapter
     */
    private void setupRecyclerViewAdapter() {

        exploreUserList = binding.recyclerHomeExplore;

        // Initialize Adapter
        adapter = new ExploreRecyclerAdapter(getActivity());

        exploreUserList.setLayoutManager(new LinearLayoutManager(getActivity()));
        exploreUserList.setAdapter(adapter);

    }

    /**
     * Initiate ViewModel to start fetching data
     */
    private void setupViewModel() {

        DashboardViewModelFactory factory = InjectorUtils.provideDashboardViewModelFactory(getActivity().getApplicationContext());
        viewModel = ViewModelProviders.of(getActivity(), factory).get(DashboardViewModel.class);

        Log.i(LOG_TAG, "Getting Users from ViewModel");
        viewModel.getExploreUsers().observe(HomeFragment.this, new Observer<User[]>() {
            @Override
            public void onChanged(@Nullable User[] users) {
                assert users != null;

                Log.i(LOG_TAG, users.length + " Users Received");
                if (users.length > 0) {
                    adapter.setData(users);
                }
            }
        });

    }

}
