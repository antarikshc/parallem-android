package com.antarikshc.parallem.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
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
import com.antarikshc.parallem.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    // Views
    private TextInputEditText editEmail;
    private TextInputLayout editEmailLayout;
    private TextInputEditText editPassword;
    private TextInputLayout editPasswordLayout;

    // Global params
    private ActivityLoginBinding binding;
    private String userEmail;
    private String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        //initializeViews();

    }

    /**
     * OnClick Button Login - Validate form and Call API
     * @param view
     */
    public void hitLoginAPI(View view) {

        // Retrieve data from views
        userEmail = binding.editLoginEmail.getText().toString();
        userPassword = binding.editLoginPassword.getText().toString();

        if (validateForm()) {

            // TODO: Call Login API

        }
    }

    /**
     * Validate form before Calling Login API
     *
     * @return Boolean validation
     */
    private Boolean validateForm() {

        // Clear all errors
        binding.editLoginEmailLayout.setError(null);
        binding.editLoginPasswordLayout.setError(null);

        Boolean validation = true;

        if (userEmail.isEmpty()) {
            binding.editLoginEmailLayout.setError("*Required");
            validation = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            binding.editLoginEmailLayout.setError("Input Valid Email");
            validation = false;
        }

        if (userPassword.isEmpty()) {
            binding.editLoginPasswordLayout.setError("*Required");
            validation = false;
        }

        return validation;

    }

    /**
     * Boilerplate to Find and initialize all views
     */
    private void initializeViews() {
        editEmail = findViewById(R.id.edit_login_email);
        editEmail.requestFocus();
        editPassword = findViewById(R.id.edit_login_password);
        editEmailLayout = findViewById(R.id.edit_login_email_layout);
        editPasswordLayout = findViewById(R.id.edit_login_password_layout);
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
