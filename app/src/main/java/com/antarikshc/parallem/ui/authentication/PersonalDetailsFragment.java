package com.antarikshc.parallem.ui.authentication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antarikshc.parallem.R;
import com.antarikshc.parallem.data.InjectorUtils;
import com.antarikshc.parallem.databinding.FragmentPersonalDetailsBinding;
import com.antarikshc.parallem.models.Skill;
import com.antarikshc.parallem.models.user.ProfileAvatar;
import com.antarikshc.parallem.ui.adapters.AvatarRecyclerAdapter;
import com.antarikshc.parallem.ui.adapters.CustomItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class PersonalDetailsFragment extends Fragment {

    private static final String LOG_TAG = PersonalDetailsFragment.class.getSimpleName();

    // Global params
    private FragmentPersonalDetailsBinding binding;
    private AddProfileViewModel viewModel;
    private RecyclerView avatarList;
    private AvatarRecyclerAdapter avatarAdapter;
    private List<ProfileAvatar> avatars;
    private Integer previouslySelectedAvatar = 0;
    private Integer currentSelectedAvatar = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate Fragment layout with data binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_personal_details, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onClickListeners();

        setupAvatarAdapter();

        setupViewModel();
    }

    /**
     * Initiate ViewModel to start fetching data
     */
    private void setupViewModel() {

        AddProfileViewModelFactory factory = InjectorUtils.provideAddProfileViewModelFactory(getActivity().getApplicationContext());
        viewModel = ViewModelProviders.of(getActivity(), factory).get(AddProfileViewModel.class);

        Log.i(LOG_TAG, "Getting Skills from ViewModel");
        viewModel.getAllSkills().observe(PersonalDetailsFragment.this, new Observer<Skill[]>() {
            @Override
            public void onChanged(@Nullable Skill[] skills) {
                Log.i(LOG_TAG, skills.length + " skills received");
                // Do not do anything here.
                // This is just to initiate fetching skills on the first fragment
                // Recollect the data on Second Fragment
            }
        });

    }

    /**
     * Setup Profile Avatar RecyclerViewAdapter
     */
    private void setupAvatarAdapter() {

        // Find the Recycler View Adapter
        avatarList = binding.recyclerPersonalAvatar;

        // Initialize adapter with CustomItemClickListener
        avatarAdapter = new AvatarRecyclerAdapter(getContext(), new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                previouslySelectedAvatar = currentSelectedAvatar;
                currentSelectedAvatar = position;
                setSelectedBackground(previouslySelectedAvatar, position);
            }
        });

        // Set GridLayout as LayoutManager
        avatarList.setLayoutManager(new GridLayoutManager(getContext(), 4));

        avatarList.setAdapter(avatarAdapter);

        avatars = new ArrayList<ProfileAvatar>();
        for (int i = 1; i <= 22; i++) {
            // Temp: Hardcoding 22 as we 22 image illustrations

            avatars.add(new ProfileAvatar(i, false));

        }
        avatarAdapter.setData(avatars);

    }

    /**
     * Clear previous selection and set new selected background
     *
     * @param previousPosition to clear selected background
     * @param position         to set selected background
     */
    private void setSelectedBackground(int previousPosition, int position) {

        avatars.get(previousPosition).setSelected(false);

        avatars.get(position).setSelected(true);

        avatarAdapter.setData(avatars);

    }

    /**
     * Setup all the Button onClick here
     */
    private void onClickListeners() {

        // Button - Continue
        binding.btnPersonalDetailsContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddProfileActivity.attachFragment(new CareerDetailsFragment());
            }
        });

        // Button - Skip
        binding.btnPersonalDetailsSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProfileActivity.attachFragment(new CareerDetailsFragment());
            }
        });
    }

}
