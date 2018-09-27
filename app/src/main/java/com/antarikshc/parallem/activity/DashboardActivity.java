package com.antarikshc.parallem.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.antarikshc.parallem.R;

public class DashboardActivity extends AppCompatActivity {

    // Views
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        setupToolbar();
    }

    /**
     * Set Title and Text Color on Toolbar
     */
    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar_dashboard);
        toolbar.setTitle(R.string.title_dashboard_parallem);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }
}
