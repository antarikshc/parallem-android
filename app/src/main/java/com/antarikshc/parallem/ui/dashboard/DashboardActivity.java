package com.antarikshc.parallem.ui.dashboard;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.antarikshc.parallem.R;

public class DashboardActivity extends AppCompatActivity {

    public static final String LOG_TAG = DashboardActivity.class.getSimpleName();

    // Views
    private Toolbar toolbar;
    private BottomNavigationView bottomNav;

    // Global params
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Begin Fragment Transaction
        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        // Keep HomeFragment attached by default
        fragmentTransaction.add(R.id.frame_dash_main, new HomeFragment());
        fragmentTransaction.addToBackStack("home");
        fragmentTransaction.commit();

        setupToolbar();

        setupBottomNavListner();
    }

    private void setupBottomNavListner() {

        bottomNav = findViewById(R.id.bottom_nav_dashboard);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.bottom_nav_item_home:
                        if (!menuItem.isChecked()) {
                            Log.i(LOG_TAG, "Bottom Nav Item: Home");
                            menuItem.setChecked(true);
                        }
                        return true;

                    case R.id.bottom_nav_item_projects:
                        if (!menuItem.isChecked()) {
                            Log.i(LOG_TAG, "Bottom Nav Item: Projects");
                            menuItem.setChecked(true);
                        }
                        return true;

                    case R.id.bottom_nav_item_teams:
                        if (!menuItem.isChecked()) {
                            Log.i(LOG_TAG, "Bottom Nav Item: Teams");
                            menuItem.setChecked(true);
                        }
                        return true;

                    case R.id.bottom_nav_item_alerts:
                        if (!menuItem.isChecked()) {
                            Log.i(LOG_TAG, "Bottom Nav Item: Alerts");
                            menuItem.setChecked(true);
                        }
                        return true;

                    case R.id.bottom_nav_item_profile:
                        if (!menuItem.isChecked()) {
                            Log.i(LOG_TAG, "Bottom Nav Item: Profiles");
                            menuItem.setChecked(true);
                        }
                        return true;

                    default:
                        return false;

                }
            }
        });

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
