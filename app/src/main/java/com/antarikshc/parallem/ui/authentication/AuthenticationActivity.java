package com.antarikshc.parallem.ui.authentication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.antarikshc.parallem.R;
import com.antarikshc.parallem.data.InjectorUtils;
import com.antarikshc.parallem.models.Skill;
import com.antarikshc.parallem.ui.dashboard.DashboardActivity;
import com.antarikshc.parallem.util.ParallemApp;

public class AuthenticationActivity extends AppCompatActivity {

    private static final String LOG_TAG = AuthenticationActivity.class.getSimpleName();

    // Global params
    private static FragmentManager fragmentManager;
    private AuthenticationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        if (ParallemApp.isUserIdExist()) {
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }

        setupFragmentManager();

        setupViewModel();
    }

    /**
     * Method to avoid fragment recreation
     *
     * @param fragment Pass the Fragment to be launched
     */
    public static void attachFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getSimpleName();

        Fragment fragmentFromBackStack = fragmentManager.findFragmentByTag(backStateName);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);

        if (fragmentFromBackStack == null) {
            // Fragment not in back stack, create it.
            fragmentTransaction.replace(R.id.frame_authentication_main, fragment, backStateName);
            fragmentTransaction.addToBackStack(backStateName);
            fragmentTransaction.commit();
        } else {
            fragmentManager.popBackStack(backStateName, 0);
        }
    }

    /**
     * This ViewModel will start fetching data of Current User Signed up
     * and List of skills, both of which are used in the 2 attached fragments
     */
    private void setupViewModel() {

        AuthenticationViewModelFactory factory = InjectorUtils.provideAddProfileViewModelFactory(getApplicationContext());
        viewModel = ViewModelProviders.of(this, factory).get(AuthenticationViewModel.class);

        // No need to explicitly fetch User data
        // as it's being fetched as soon as the ViewModel is instantiated

        Log.i(LOG_TAG, "Getting Skills from ViewModel");
        viewModel.getAllSkills().observe(this, new Observer<Skill[]>() {
            @Override
            public void onChanged(@Nullable Skill[] skills) {
                Log.i(LOG_TAG, skills.length + " skills received");
                // Do not do anything here.
                // This is just to initiate fetching skills on the first fragment
                // Recollect the data on Second Fragment
            }
        });

    }

    /**
     * Setup Fragment Manager and Transactions
     * Add to backstack
     */
    private void setupFragmentManager() {
        // Retrieve FragmentManager instance
        fragmentManager = getSupportFragmentManager();

        // Keep PersonalDetailsFragment attached by default
        attachFragment(new IntroFragment());
    }

    public void onBackButton(View view) {
        fragmentManager.popBackStack();
    }
}
