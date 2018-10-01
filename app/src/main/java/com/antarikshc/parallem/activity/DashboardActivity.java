package com.antarikshc.parallem.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.antarikshc.parallem.R;
import com.antarikshc.parallem.fragment.HomeFragment;

public class DashboardActivity extends AppCompatActivity {

    // Views
    private Toolbar toolbar;

    // Global params
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_dash_main, new HomeFragment());
        fragmentTransaction.addToBackStack("sign_up_phone");
        fragmentTransaction.commit();

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
