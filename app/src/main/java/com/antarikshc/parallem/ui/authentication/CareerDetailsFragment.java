package com.antarikshc.parallem.ui.authentication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.antarikshc.parallem.R;
import com.antarikshc.parallem.data.InjectorUtils;
import com.antarikshc.parallem.databinding.FragmentCareerDetailsBinding;
import com.antarikshc.parallem.models.Skill;
import com.antarikshc.parallem.models.user.Certification;
import com.antarikshc.parallem.models.user.Experience;
import com.antarikshc.parallem.models.user.User;
import com.antarikshc.parallem.models.user.UserProject;
import com.antarikshc.parallem.ui.adapters.CertificationRecyclerAdapter;
import com.antarikshc.parallem.ui.adapters.ExperienceRecyclerAdapter;
import com.antarikshc.parallem.ui.adapters.SkillRecyclerAdapter;
import com.antarikshc.parallem.ui.adapters.UserProjectRecyclerAdapter;
import com.antarikshc.parallem.util.SkillHelper;

import java.util.ArrayList;
import java.util.List;

public class CareerDetailsFragment extends Fragment {

    private static final String LOG_TAG = CareerDetailsFragment.class.getSimpleName();

    // Global params
    private FragmentCareerDetailsBinding binding;
    private AddProfileViewModel viewModel;
    private Skill[] mSkills;
    private String[] mSkillNameArray;

    private User user;
    private List<Experience> experiences = new ArrayList<Experience>();
    private List<UserProject> projects = new ArrayList<UserProject>();
    private List<Certification> certifications = new ArrayList<Certification>();
    private List<Skill> userSkills = new ArrayList<Skill>();

    private RecyclerView experienceList;
    private ExperienceRecyclerAdapter expAdapter;
    private RecyclerView userProjectList;
    private UserProjectRecyclerAdapter userProjectAdapter;
    private RecyclerView certificateList;
    private CertificationRecyclerAdapter certificationAdapter;
    private RecyclerView skillList;
    private SkillRecyclerAdapter skillAdapter;

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

        setupAdapter();

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
                Log.i(LOG_TAG, skills.length + " userSkills received");

                if (skills.length > 0) {
                    // Save values in Global params
                    mSkills = skills;
                    mSkillNameArray = SkillHelper.getSkillNameArray(skills);
                }

            }
        });

        Log.i(LOG_TAG, "Getting User from ViewModel");
        // This will essentially return the Updated User from PersonalDetailsFragment
        user = viewModel.getUser();
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
        final TextInputEditText expTitle = mView.findViewById(R.id.edit_dialog_experience_title);
        final TextInputEditText expCompany = mView.findViewById(R.id.edit_dialog_experience_company);
        final TextInputLayout layoutTitle = mView.findViewById(R.id.til_dialog_experience_title);
        final TextInputLayout layoutCompany = mView.findViewById(R.id.til_dialog_experience_company);
        Button btnSave = mView.findViewById(R.id.btn_dialog_experience_save);
        Button btnCancel = mView.findViewById(R.id.btn_dialog_experience_cancel);

        // Set view and create AlertDialog
        mBuilder.setView(mView);
        final AlertDialog alertDialog = mBuilder.create();

        // OnClickListeners for Buttons
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = expTitle.getText().toString();
                String company = expCompany.getText().toString();

                // Clear previous errors
                layoutTitle.setError(null);
                layoutCompany.setError(null);

                // Validation of Forms
                Boolean validation = true;
                if (title.isEmpty()) {
                    layoutTitle.setError("*Required");
                    validation = false;
                }
                if (company.isEmpty()) {
                    layoutCompany.setError("*Required");
                    validation = false;
                }

                if (validation) {

                    // Create Experience object and add to Array of Experiences
                    Experience exp = new Experience(company, title, null, null);
                    experiences.add(exp);

                    // Update adapter with data
                    expAdapter.setData(experiences);

                    alertDialog.dismiss();
                }

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
        final TextInputEditText projectName = mView.findViewById(R.id.edit_dialog_project_name);
        final TextInputEditText projectDesc = mView.findViewById(R.id.edit_dialog_project_desc);
        final TextInputLayout layoutName = mView.findViewById(R.id.til_dialog_project_name);
        final TextInputLayout layoutDesc = mView.findViewById(R.id.til_dialog_project_desc);
        Button btnSave = mView.findViewById(R.id.btn_dialog_project_save);
        Button btnCancel = mView.findViewById(R.id.btn_dialog_project_cancel);

        // Set view and create AlertDialog
        mBuilder.setView(mView);
        final AlertDialog alertDialog = mBuilder.create();

        // OnClickListeners for Buttons
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = projectName.getText().toString();
                String desc = projectDesc.getText().toString();

                // Clear previous errors
                layoutName.setError(null);
                layoutDesc.setError(null);

                // Validation of Forms
                Boolean validation = true;
                if (name.isEmpty()) {
                    layoutName.setError("*Required");
                    validation = false;
                }
                if (desc.isEmpty()) {
                    layoutDesc.setError("*Required");
                    validation = false;
                }

                if (validation) {

                    // Create UserProject object and add to Array of UserProjects
                    UserProject userProject = new UserProject(name, desc);
                    projects.add(userProject);

                    // Update adapter with data
                    userProjectAdapter.setData(projects);

                    alertDialog.dismiss();
                }

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
        final TextInputEditText certName = mView.findViewById(R.id.edit_dialog_certificate_name);
        final TextInputEditText certAuthority = mView.findViewById(R.id.edit_dialog_certificate_authority);
        final TextInputLayout layoutName = mView.findViewById(R.id.til_dialog_certificate_name);
        final TextInputLayout layoutAuthority = mView.findViewById(R.id.til_dialog_certificate_authority);
        Button btnSave = mView.findViewById(R.id.btn_dialog_certificate_save);
        Button btnCancel = mView.findViewById(R.id.btn_dialog_certificate_cancel);

        // Set view and create AlertDialog
        mBuilder.setView(mView);
        final AlertDialog alertDialog = mBuilder.create();

        // OnClickListeners for Buttons
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = certName.getText().toString();
                String authority = certAuthority.getText().toString();

                // Clear previous errors
                layoutName.setError(null);
                layoutAuthority.setError(null);

                // Validation of Forms
                Boolean validation = true;
                if (name.isEmpty()) {
                    layoutName.setError("*Required");
                    validation = false;
                }
                if (authority.isEmpty()) {
                    layoutAuthority.setError("*Required");
                    validation = false;
                }

                if (validation) {

                    // Create UserProject object and add to Array of UserProjects
                    Certification certificate = new Certification(name, authority);
                    certifications.add(certificate);

                    // Update adapter with data
                    certificationAdapter.setData(certifications);

                    alertDialog.dismiss();
                }
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
        final AutoCompleteTextView autoTextView = mView.findViewById(R.id.auto_complete_text_skill);
        final TextInputLayout layoutSkill = mView.findViewById(R.id.til_dialog_skill_name);
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

                String name = autoTextView.getText().toString();

                // Clear previous errors
                layoutSkill.setError(null);

                // Validation of Forms
                Boolean validation = true;
                if (name.isEmpty()) {
                    layoutSkill.setError("*Required");
                    validation = false;
                }

                if (validation) {

                    // Check if the Skill name is associated with an ID
                    Integer skillId = SkillHelper.getSkillId(mSkills, name);

                    if (skillId == 404) {
                        Toast.makeText(getActivity(), "Skill not associated!", Toast.LENGTH_SHORT).show();
                    } else {

                        // Create Skill object and add to Array of Skills
                        Skill skill = new Skill(skillId, name);
                        userSkills.add(skill);

                        // Update adapter with data
                        skillAdapter.setData(userSkills);

                        alertDialog.dismiss();
                    }
                }
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
     * Boilerplate setup for RecyclerViewAdapter
     */
    private void setupAdapter() {

        // Experience Adapter
        experienceList = binding.recyclerCareerExperience;
        expAdapter = new ExperienceRecyclerAdapter(getActivity());
        experienceList.setLayoutManager(new LinearLayoutManager(getActivity()));
        experienceList.setNestedScrollingEnabled(false);
        experienceList.setAdapter(expAdapter);

        // User Project Adapter
        userProjectList = binding.recyclerCareerProjects;
        userProjectAdapter = new UserProjectRecyclerAdapter(getActivity());
        userProjectList.setLayoutManager(new LinearLayoutManager(getActivity()));
        userProjectList.setNestedScrollingEnabled(false);
        userProjectList.setAdapter(userProjectAdapter);

        // Certification Adapter
        certificateList = binding.recyclerCareerCertifications;
        certificationAdapter = new CertificationRecyclerAdapter(getActivity());
        certificateList.setLayoutManager(new LinearLayoutManager(getActivity()));
        certificateList.setNestedScrollingEnabled(false);
        certificateList.setAdapter(certificationAdapter);

        // Skill Adapter
        skillList = binding.recyclerCareerSkills;
        skillAdapter = new SkillRecyclerAdapter(getActivity());
        skillList.setLayoutManager(new LinearLayoutManager(getActivity()));
        skillList.setNestedScrollingEnabled(false);
        skillList.setAdapter(skillAdapter);
    }
}
