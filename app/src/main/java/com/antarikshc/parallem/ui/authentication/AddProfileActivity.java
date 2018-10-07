package com.antarikshc.parallem.ui.authentication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.antarikshc.parallem.R;

public class AddProfileActivity extends AppCompatActivity {

    // Global params
    private static FragmentManager fragmentManager;
    private PersonalDetailsFragment personalDetailsFragment;
    private CareerDetailsFragment careerDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);

        personalDetailsFragment = new PersonalDetailsFragment();
        careerDetailsFragment = new CareerDetailsFragment();

        setupFragmentManager();

    }

    /**
     * Setup Fragment Manager and Transactions
     * Add to backstack
     */
    private void setupFragmentManager() {
        // Retrieve FragmentManager instance
        fragmentManager = getSupportFragmentManager();

        // Keep PersonalDetailsFragment attached by default
        attachFragment(personalDetailsFragment);
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
            fragmentTransaction.replace(R.id.frame_add_profile, fragment, backStateName);
            fragmentTransaction.addToBackStack(backStateName);
            fragmentTransaction.commit();
        } else {
            fragmentManager.popBackStack(backStateName, 0);
        }
    }
}
