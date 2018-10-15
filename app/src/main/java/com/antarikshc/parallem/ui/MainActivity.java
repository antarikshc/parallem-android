package com.antarikshc.parallem.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.antarikshc.parallem.R;
import com.antarikshc.parallem.ui.authentication.AuthenticationActivity;
import com.antarikshc.parallem.ui.authentication.LoginActivity;
import com.antarikshc.parallem.ui.authentication.SignUpActivity;
import com.antarikshc.parallem.ui.dashboard.DashboardActivity;
import com.antarikshc.parallem.ui.team.TeamProfileActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void goToSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void goToDashboard(View view) {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

    public void goToAddProfile(View view) {
        Intent intent = new Intent(this, AuthenticationActivity.class);
        startActivity(intent);
    }

    public void goToTeamProfile(View view) {
        Intent intent = new Intent(this, TeamProfileActivity.class);
        startActivity(intent);
    }
}
