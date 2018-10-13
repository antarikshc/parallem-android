package com.antarikshc.parallem.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.antarikshc.parallem.R;

public class UserProfileActivity extends AppCompatActivity {

    private static final String LOG_TAG = UserProfileActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Intent intent = getIntent();

        if (intent != null) {
            String userJsonString = intent.getStringExtra("json_string");
            Log.i(LOG_TAG, "User: " + userJsonString);
        }

    }
}
