package com.antarikshc.parallem.ui.authentication;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.antarikshc.parallem.R;
import com.antarikshc.parallem.databinding.FragmentCareerDetailsBinding;

public class CareerDetailsFragment extends Fragment {

    // Global params
    private FragmentCareerDetailsBinding binding;

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
}
