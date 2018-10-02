package com.antarikshc.parallem.ui.dashboard;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.antarikshc.parallem.R;

public class DashboardActivity extends AppCompatActivity {

    public static final String LOG_TAG = DashboardActivity.class.getSimpleName();

    // Views
    private Toolbar toolbar;
    private BottomNavigationView bottomNav;

    // Global params
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private HomeFragment homeFragment;
    private ProjectsFragment projectFragment;
    private TeamsFragment teamsFragment;
    private NotificationsFragment notificationsFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        homeFragment = new HomeFragment();
        projectFragment = new ProjectsFragment();
        teamsFragment = new TeamsFragment();
        notificationsFragment = new NotificationsFragment();
        profileFragment = new ProfileFragment();

        setupFragmentManager();

        setupToolbar();

        setupBottomNavListener();
    }

    /**
     * Setup Bottom Navigation Item Selected listener to
     * switch between fragments
     */
    private void setupBottomNavListener() {

        bottomNav = findViewById(R.id.bottom_nav_dashboard);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.bottom_nav_item_home:
                        if (!menuItem.isChecked()) {
                            attachFragment(homeFragment);
                            menuItem.setChecked(true);
                        }
                        return true;

                    case R.id.bottom_nav_item_projects:
                        if (!menuItem.isChecked()) {
                            attachFragment(projectFragment);
                            menuItem.setChecked(true);
                        }
                        return true;

                    case R.id.bottom_nav_item_teams:
                        if (!menuItem.isChecked()) {
                            attachFragment(teamsFragment);
                            menuItem.setChecked(true);
                        }
                        return true;

                    case R.id.bottom_nav_item_alerts:
                        if (!menuItem.isChecked()) {
                            attachFragment(notificationsFragment);
                            menuItem.setChecked(true);
                        }
                        return true;

                    case R.id.bottom_nav_item_profile:
                        if (!menuItem.isChecked()) {
                            attachFragment(profileFragment);
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
     * Setup Fragment Manager and Transactions
     * Add HomeFragment to backstack
     */
    private void setupFragmentManager() {
        // Retrieve FragmentManager instance
        fragmentManager = getSupportFragmentManager();
        // Begin Fragment Transaction
        fragmentTransaction = fragmentManager.beginTransaction();

        // Keep HomeFragment attached by default
        attachFragment(homeFragment);
    }

    private void attachFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getSimpleName();

        Fragment fragmentFromBackStack = getSupportFragmentManager().findFragmentByTag(backStateName);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        if (fragmentFromBackStack == null) {
            // Fragment not in back stack, create it.
            fragmentTransaction.replace(R.id.frame_dash_main, fragment, backStateName);
            fragmentTransaction.addToBackStack(backStateName);
            fragmentTransaction.commit();
        } else {
            fragmentTransaction.replace(R.id.frame_dash_main, fragmentFromBackStack, backStateName);
            fragmentTransaction.commit();
        }
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
