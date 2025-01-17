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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.antarikshc.parallem.R;
import com.antarikshc.parallem.data.InjectorUtils;
import com.antarikshc.parallem.databinding.FragmentNotificationsBinding;
import com.antarikshc.parallem.models.user.User;
import com.antarikshc.parallem.ui.adapters.NotificationAdapter;
import com.antarikshc.parallem.util.VolleySingleton;

public class NotificationsFragment extends Fragment {

    private static final String LOG_TAG = NotificationsFragment.class.getSimpleName();

    // Global params
    private FragmentNotificationsBinding binding;
    private RequestQueue requestQueue;
    private DashboardViewModel viewModel;
    private RecyclerView notificationList;
    private NotificationAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate Fragment layout with data binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notifications, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get Singleton Volley Request Queue Instance
        requestQueue = VolleySingleton.getInstance(getActivity()).getRequestQueue();

        setupNotificationAdapter();

        setupViewModel();
    }

    /**
     * Setup and Initialize RecyclerView and RecyclerViewAdapter
     */
    private void setupNotificationAdapter() {

        notificationList = binding.recyclerNotification;

        // Initialize Adapter
        adapter = new NotificationAdapter(getActivity(), requestQueue);

        notificationList.setLayoutManager(new LinearLayoutManager(getActivity()));
        notificationList.setAdapter(adapter);

    }

    /**
     * Initiate ViewModel to start fetching data
     */
    private void setupViewModel() {

        DashboardViewModelFactory factory = InjectorUtils.provideDashboardViewModelFactory(getActivity().getApplicationContext());
        viewModel = ViewModelProviders.of(getActivity(), factory).get(DashboardViewModel.class);

        viewModel.getProfileDetails().observe(NotificationsFragment.this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                assert user != null;

                if (user.getNotifications().size() > 0) {
                    adapter.setData(user.getNotifications());
                }

            }
        });

    }
}