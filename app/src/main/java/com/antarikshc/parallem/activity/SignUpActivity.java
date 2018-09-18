package com.antarikshc.parallem.activity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.antarikshc.parallem.R;

public class SignUpActivity extends AppCompatActivity {

    // Views
    private TextInputEditText editFirstName;
    private TextInputLayout layoutFirstName;
    private TextInputEditText editLastName;
    private TextInputLayout layoutLastName;
    private TextInputEditText editEmail;
    private TextInputLayout layoutEmail;
    private TextInputEditText editISD;
    private TextInputEditText editMobile;
    private TextInputLayout layoutMobile;
    private TextInputEditText editPassword;
    private TextInputLayout layoutPassword;
    private TextInputEditText editConfirmPassword;
    private TextInputLayout layoutConfirmPassword;

    // Global Params
    private String firstName;
    private String lastName;
    private String userEmail;
    private String mobileNumber;
    private String userPassword;
    private String userConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initializeViews();

    }

    /**
     * OnClick Button SignUp - Validate form and Call API
     *
     * @param view
     */
    public void hitSignUpAPI(View view) {

        // Retrieve data from views
        firstName = editFirstName.getText().toString();
        lastName = editLastName.getText().toString();
        userEmail = editEmail.getText().toString();
        mobileNumber = editISD.getText().toString() + editMobile.getText().toString();
        userPassword = editPassword.getText().toString();
        userConfirmPassword = editConfirmPassword.getText().toString();

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
        layoutFirstName.setError(null);
        layoutLastName.setError(null);
        layoutEmail.setError(null);
        layoutMobile.setError(null);
        layoutPassword.setError(null);
        layoutConfirmPassword.setError(null);

        Boolean validation = true;

        if (firstName.isEmpty()) {
            layoutFirstName.setError("*Required");
            validation = false;
        }

        if (lastName.isEmpty()) {
            layoutLastName.setError("*Required");
            validation = false;
        }

        if (mobileNumber.length() < 10) {
            layoutMobile.setError("*Requires at least 10 digit");
            validation = false;
        }

        if (userEmail.isEmpty()) {
            layoutEmail.setError("*Required");
            validation = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            layoutEmail.setError("Input Valid Email");
            validation = false;
        }

        if (userPassword.isEmpty()) {
            layoutPassword.setError("*Required");
            validation = false;
        } else if (userPassword.length() < 8) {
            layoutPassword.setError("*Requires at least 8 characters");
            validation = false;
        }

        if (userConfirmPassword.isEmpty()) {
            layoutConfirmPassword.setError("*Required");
            validation = false;
        } else if (userConfirmPassword.length() < 8) {
            layoutConfirmPassword.setError("*Requires at least 8 characters");
            validation = false;
        } else if (!userPassword.equals(userConfirmPassword)) {
            layoutConfirmPassword.setError("Passwords do not match!");
            validation = false;
        }

        return validation;

    }

    /**
     * Boilerplate to Find and initialize all views
     */
    private void initializeViews() {
        editFirstName = findViewById(R.id.edit_signup_firstname);
        editFirstName.requestFocus();
        layoutFirstName = findViewById(R.id.edit_signup_firstname_layout);
        editLastName = findViewById(R.id.edit_signup_lastname);
        layoutLastName = findViewById(R.id.edit_signup_lastname_layout);
        editEmail = findViewById(R.id.edit_signup_email);
        layoutEmail = findViewById(R.id.edit_signup_email_layout);
        editISD = findViewById(R.id.edit_signup_isd);
        editMobile = findViewById(R.id.edit_signup_mobile);
        layoutMobile = findViewById(R.id.edit_signup_mobile_layout);
        editPassword = findViewById(R.id.edit_signup_password);
        layoutPassword = findViewById(R.id.edit_signup_password_layout);
        editConfirmPassword = findViewById(R.id.edit_signup_cnf_password);
        layoutConfirmPassword = findViewById(R.id.edit_signup_cnf_password_layout);
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
