package com.antarikshc.parallem.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.antarikshc.parallem.R;

public class LoginActivity extends AppCompatActivity {

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
     * Boilerplate to Find and initialize all views
     */
    private void initializeViews() {

    }

}
