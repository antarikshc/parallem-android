package com.antarikshc.parallem.ui.authentication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.antarikshc.parallem.R;
import com.antarikshc.parallem.data.InjectorUtils;
import com.antarikshc.parallem.databinding.FragmentCareerDetailsBinding;
import com.antarikshc.parallem.models.Skill;
import com.antarikshc.parallem.util.SkillHelper;

public class CareerDetailsFragment extends Fragment {

    private static final String LOG_TAG = CareerDetailsFragment.class.getSimpleName();

    // Global params
    private FragmentCareerDetailsBinding binding;
    private AddProfileViewModel viewModel;
    private Skill[] mSkills;
    private String[] mSkillNameArray;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate Fragment layout with data binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_career_details, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onClickListeners();

        setupViewModel();
    }

    /**
     * Initiate ViewModel to start fetching data
     */
    private void setupViewModel() {

        AddProfileViewModelFactory factory = InjectorUtils.provideAddProfileViewModelFactory(getActivity().getApplicationContext());
        viewModel = ViewModelProviders.of(getActivity(), factory).get(AddProfileViewModel.class);

        Log.i(LOG_TAG, "Getting Skills from ViewModel");
        viewModel.getAllSkills().observe(CareerDetailsFragment.this, new Observer<Skill[]>() {
            @Override
            public void onChanged(@Nullable Skill[] skills) {
                assert skills != null;
                Log.i(LOG_TAG, skills.length + " skills received");

                if (skills.length > 0) {
                    // Save values in Global params
                    mSkills = skills;
                    mSkillNameArray = SkillHelper.getSkillNameArray(skills);
                }

            }
        });

    }

    /**
     * Setup all the Button onClick here
     */
    private void onClickListeners() {

        // Button - Add Experience
        binding.imgCareerExpAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createExperienceDialog();
            }
        });

        // Button - Add Project
        binding.imgCareerProjectsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProjectDialog();
            }
        });

        // Button - Add Certificate
        binding.imgCareerCertAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCertificateDialog();
            }
        });

        // Button - Add Skill
        binding.imgCareerSkillAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSkillDialog();
            }
        });
    }

    /**
     * AlertDialog for adding Experience
     */
    private void createExperienceDialog() {

        // Build AlertDialog and inflate views
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity(), R.style.DialogRoundedCorners);
        View mView = getLayoutInflater().inflate(R.layout.dialog_experience, null);

        // Bind views from Dialog layout
        TextInputEditText expTitle = mView.findViewById(R.id.edit_dialog_experience_title);
        TextInputEditText expCompany = mView.findViewById(R.id.edit_dialog_experience_company);
        Button btnSave = mView.findViewById(R.id.btn_dialog_experience_save);
        Button btnCancel = mView.findViewById(R.id.btn_dialog_experience_cancel);

        // Set view and create AlertDialog
        mBuilder.setView(mView);
        final AlertDialog alertDialog = mBuilder.create();

        // OnClickListeners for Buttons
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    /**
     * AlertDialog for adding Project
     */
    private void createProjectDialog() {

        // Build AlertDialog and inflate views
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity(), R.style.DialogRoundedCorners);
        View mView = getLayoutInflater().inflate(R.layout.dialog_project, null);

        // Bind views from Dialog layout
        TextInputEditText projectName = mView.findViewById(R.id.edit_dialog_project_name);
        TextInputEditText projectDesc = mView.findViewById(R.id.edit_dialog_project_desc);
        Button btnSave = mView.findViewById(R.id.btn_dialog_project_save);
        Button btnCancel = mView.findViewById(R.id.btn_dialog_project_cancel);

        // Set view and create AlertDialog
        mBuilder.setView(mView);
        final AlertDialog alertDialog = mBuilder.create();

        // OnClickListeners for Buttons
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    /**
     * AlertDialog for adding Certificate
     */
    private void createCertificateDialog() {

        // Build AlertDialog and inflate views
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity(), R.style.DialogRoundedCorners);
        View mView = getLayoutInflater().inflate(R.layout.dialog_certificate, null);

        // Bind views from Dialog layout
        TextInputEditText certName = mView.findViewById(R.id.edit_dialog_certificate_name);
        TextInputEditText certAuthority = mView.findViewById(R.id.edit_dialog_certificate_authority);
        Button btnSave = mView.findViewById(R.id.btn_dialog_certificate_save);
        Button btnCancel = mView.findViewById(R.id.btn_dialog_certificate_cancel);

        // Set view and create AlertDialog
        mBuilder.setView(mView);
        final AlertDialog alertDialog = mBuilder.create();

        // OnClickListeners for Buttons
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    /**
     * AlertDialog for adding Certificate
     */
    private void createSkillDialog() {

        // Build AlertDialog and inflate views
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity(), R.style.DialogRoundedCorners);
        View mView = getLayoutInflater().inflate(R.layout.dialog_skill, null);

        // Bind views from Dialog layout
        AutoCompleteTextView autoTextView = mView.findViewById(R.id.auto_complete_text_skill);
        Button btnSave = mView.findViewById(R.id.btn_dialog_skill_save);
        Button btnCancel = mView.findViewById(R.id.btn_dialog_skill_cancel);

        // Creating instance of ArrayAdapter for AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.select_dialog_item, mSkillNameArray);
        autoTextView.setAdapter(adapter);

        // Set view and create AlertDialog
        mBuilder.setView(mView);
        final AlertDialog alertDialog = mBuilder.create();

        // OnClickListeners for Buttons
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}
