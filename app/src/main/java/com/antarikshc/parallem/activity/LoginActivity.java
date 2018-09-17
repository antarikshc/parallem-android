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

public class LoginActivity extends AppCompatActivity {

    // Views
    private TextInputEditText editEmail;
    private TextInputLayout editEmailLayout;
    private TextInputEditText editPassword;
    private TextInputLayout editPasswordLayout;

    // Global params
    private String userEmail;
    private String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();

    }

    public void backButton(View view) {
        onBackPressed();
    }

    /**
     * OnClick Button Login - Validate form and Call API
     *
     * @param view
     */
    public void hitLoginAPI(View view) {

        // Retrieve data from views
        userEmail = editEmail.getText().toString();
        userPassword = editPassword.getText().toString();

        // Clear all errors
        editEmailLayout.setError(null);
        editPasswordLayout.setError(null);

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

        Boolean validation = true;

        if (userEmail.isEmpty()) {
            editEmailLayout.setError("*Required");
            validation = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            editEmailLayout.setError("Input Valid Email");
            validation = false;
        }

        if (userPassword.isEmpty()) {
            editPasswordLayout.setError("*Required");
            validation = false;
        }

        return validation;

    }

    /**
     * Boilerplate to Find and initialize all views
     */
    private void initializeViews() {
        editEmail = findViewById(R.id.edit_email);
        editEmail.requestFocus();
        editPassword = findViewById(R.id.edit_password);
        editEmailLayout = findViewById(R.id.edit_email_layout);
        editPasswordLayout = findViewById(R.id.edit_password_layout);
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
