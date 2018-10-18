package com.antarikshc.parallem.ui.dashboard;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.antarikshc.parallem.R;
import com.antarikshc.parallem.util.API;
import com.antarikshc.parallem.util.ParallemApp;
import com.antarikshc.parallem.util.VolleySingleton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

public class DashboardActivity extends AppCompatActivity {

    public static final String LOG_TAG = DashboardActivity.class.getSimpleName();

    // Views
    private Toolbar toolbar;
    private ImageView leftArc;
    private ImageView rightArc;
    private BottomNavigationView bottomNav;

    // Global params
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private RequestQueue requestQueue;
    private HomeFragment homeFragment;
    private ProjectsFragment projectFragment;
    private TeamsFragment teamsFragment;
    private NotificationsFragment notificationsFragment;
    private ProfileFragment profileFragment;
    private Boolean isToolbarHidden = false;

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

        // Get Singleton Volley Request Queue Instance
        requestQueue = VolleySingleton.getInstance(this).getRequestQueue();

        registerFcmToken();
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
                            showToolbar();

                            attachFragment(homeFragment);
                            menuItem.setChecked(true);
                        }
                        return true;

                    case R.id.bottom_nav_item_projects:
                        if (!menuItem.isChecked()) {
                            showToolbar();

                            attachFragment(projectFragment);
                            menuItem.setChecked(true);
                        }
                        return true;

                    case R.id.bottom_nav_item_teams:
                        if (!menuItem.isChecked()) {
                            showToolbar();

                            attachFragment(teamsFragment);
                            menuItem.setChecked(true);
                        }
                        return true;

                    case R.id.bottom_nav_item_alerts:
                        if (!menuItem.isChecked()) {
                            showToolbar();

                            attachFragment(notificationsFragment);
                            menuItem.setChecked(true);
                        }
                        return true;

                    case R.id.bottom_nav_item_profile:
                        if (!menuItem.isChecked()) {
                            hideToolbar();

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

        // Keep HomeFragment attached by default
        attachFragment(homeFragment);
    }

    /**
     * Method to avoid fragment recreation
     *
     * @param fragment Pass the Fragment to be launched
     */
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
            fragmentManager.popBackStack(backStateName, 0);
        }
    }

    /**
     * Retrieve and Register FCM token on to the backend server
     */
    private void registerFcmToken() {


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Firebase Instance", "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        final String token = task.getResult().getToken();

                        JSONObject tokenObject = new JSONObject();
                        try {
                            tokenObject.put("token", token);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (!ParallemApp.isFcmTokenExist()) {

                            // Volley request to Register FCM Token
                            JsonObjectRequest tokenRequest = new JsonObjectRequest(
                                    Request.Method.POST,
                                    API.getTokenEndpoint(ParallemApp.getUserId()),
                                    tokenObject,

                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Log.i(LOG_TAG, "HTTP Response received for tokenRequest");
                                        }
                                    },

                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.i(LOG_TAG, "HTTP Error occurred: " + error);
                                        }
                                    }
                            ) {
                                @Override
                                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

                                    // Retrieve status code
                                    Integer statusCode = response.statusCode;

                                    try {
                                        if (statusCode == 201) {

                                            ParallemApp.saveFcmToken(token);

                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    return super.parseNetworkResponse(response);
                                }
                            };

                            // Queue the API Call
                            requestQueue.add(tokenRequest);
                        }

                    }
                });

    }

    /**
     * Toggle Toolbar
     */
    private void showToolbar() {
        if (!(toolbar.getVisibility() == View.VISIBLE)) {
            toolbar.setVisibility(View.VISIBLE);
            leftArc.setVisibility(View.VISIBLE);
            rightArc.setVisibility(View.VISIBLE);
            isToolbarHidden = false;
        }
    }

    private void hideToolbar() {
        if (!(toolbar.getVisibility() == View.GONE)) {
            toolbar.setVisibility(View.GONE);
            leftArc.setVisibility(View.GONE);
            rightArc.setVisibility(View.GONE);
            isToolbarHidden = true;
        }
    }

    @Override
    public void onBackPressed() {

        if (isToolbarHidden) {
            showToolbar();
        }

        // If no fragments are left in backstack, finish the activity
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
            finish();
        } else {
            getFragmentManager().popBackStack();
        }

    }

    /**
     * Set Title and Text Color on Toolbar
     */
    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar_dashboard);
        leftArc = findViewById(R.id.img_arc_left);
        rightArc = findViewById(R.id.img_arc_right);

        toolbar.setTitle(R.string.title_dashboard_parallem);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }
}
