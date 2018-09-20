package com.antarikshc.parallem.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.antarikshc.parallem.R;
import com.antarikshc.parallem.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    // Global Params
    private ActivitySignUpBinding binding;
    private String firstName;
    private String lastName;
    private String userEmail;
    private String mobileNumber;
    private String userPassword;
    private String userConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Bind the layout
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

    }

    /**
     * OnClick Button SignUp - Validate form and Call API
     *
     * @param view
     */
    public void hitSignUpAPI(View view) {

        // Retrieve data from views
        firstName = binding.editSignupFirstname.getText().toString();
        lastName = binding.editSignupLastname.getText().toString();
        userEmail = binding.editSignupEmail.getText().toString();
        mobileNumber = binding.editSignupIsd.getText().toString() +
                binding.editSignupMobile.getText().toString();
        userPassword = binding.editSignupPassword.getText().toString();
        userConfirmPassword = binding.editSignupCnfPassword.getText().toString();

        if (validateForm()) {

            //TODO: Call Sign Up API

        }
    }

    /**
     * Validate form before Calling Login API
     *
     * @return Boolean validation
     */
    private Boolean validateForm() {

        // Clear all errors
        binding.editSignupFirstnameLayout.setError(null);
        binding.editSignupLastnameLayout.setError(null);
        binding.editSignupEmailLayout.setError(null);
        binding.editSignupMobileLayout.setError(null);
        binding.editSignupPasswordLayout.setError(null);
        binding.editSignupCnfPasswordLayout.setError(null);

        Boolean validation = true;

        if (firstName.isEmpty()) {
            binding.editSignupFirstnameLayout.setError("*Required");
            validation = false;
        }

        if (lastName.isEmpty()) {
            binding.editSignupLastnameLayout.setError("*Required");
            validation = false;
        }

        if (mobileNumber.length() < 10) {
            binding.editSignupMobileLayout.setError("*Requires at least 10 digit");
            validation = false;
        }

        if (userEmail.isEmpty()) {
            binding.editSignupEmailLayout.setError("*Required");
            validation = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            binding.editSignupEmailLayout.setError("Input Valid Email");
            validation = false;
        }

        if (userPassword.isEmpty()) {
            binding.editSignupPasswordLayout.setError("*Required");
            validation = false;
        } else if (userPassword.length() < 8) {
            binding.editSignupPasswordLayout.setError("*Requires at least 8 characters");
            validation = false;
        }

        if (userConfirmPassword.isEmpty()) {
            binding.editSignupCnfPasswordLayout.setError("*Required");
            validation = false;
        } else if (userConfirmPassword.length() < 8) {
            binding.editSignupCnfPasswordLayout.setError("*Requires at least 8 characters");
            validation = false;
        } else if (!userPassword.equals(userConfirmPassword)) {
            binding.editSignupCnfPasswordLayout.setError("Passwords do not match!");
            validation = false;
        }

        return validation;

    }

    public void backButton(View view) {
        onBackPressed();
    }

    /**
     * Boilerplate: Tap outside to hide soft input manager
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof TextInputEditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
