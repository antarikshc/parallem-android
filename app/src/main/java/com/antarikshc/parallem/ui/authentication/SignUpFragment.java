package com.antarikshc.parallem.ui.authentication;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.antarikshc.parallem.R;
import com.antarikshc.parallem.data.InjectorUtils;
import com.antarikshc.parallem.databinding.FragmentSignUpBinding;
import com.antarikshc.parallem.models.user.User;
import com.antarikshc.parallem.util.Master;
import com.antarikshc.parallem.util.ParallemApp;
import com.antarikshc.parallem.util.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpFragment extends Fragment {

    private final static String LOG_TAG = SignUpFragment.class.getSimpleName();

    // Global Params
    private FragmentSignUpBinding binding;
    private AuthenticationViewModel viewModel;
    private RequestQueue requestQueue;
    private User user;
    private String firstName;
    private String lastName;
    private String userEmail;
    private String mobileNumber;
    private String userPassword;
    private String userConfirmPassword;
    // Params for Email checking
    private Boolean isEmailExist = true;
    private String lastCheckedEmail = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate Fragment layout with data binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViewModel();

        setupEmailFocusListener();

        onClickListeners();

        // Get Singleton Volley Request Queue Instance
        requestQueue = VolleySingleton.getInstance(getActivity()).getRequestQueue();
    }

    /**
     * Initiate ViewModel to start fetching data
     */
    private void setupViewModel() {

        AuthenticationViewModelFactory factory = InjectorUtils.provideAddProfileViewModelFactory(getActivity().getApplicationContext());
        viewModel = ViewModelProviders.of(getActivity(), factory).get(AuthenticationViewModel.class);

    }

    /**
     * Set all on click listeners here rather than in Activity
     */
    private void onClickListeners() {

        // Button Sign Up
        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hitSignUpAPI();
            }
        });

        // Button - Skip
        binding.btnSignupSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthenticationActivity.attachFragment(new PersonalDetailsFragment());
            }
        });

    }


    /**
     * Validate form and Call SignUp API
     */
    public void hitSignUpAPI() {

        // Retrieve data from views
        firstName = binding.editSignupFirstname.getText().toString();
        lastName = binding.editSignupLastname.getText().toString();
        userEmail = binding.editSignupEmail.getText().toString();
        mobileNumber = binding.editSignupIsd.getText().toString() +
                binding.editSignupMobile.getText().toString();
        userPassword = binding.editSignupPassword.getText().toString();
        userConfirmPassword = binding.editSignupCnfPassword.getText().toString();

        if (validateForm() && !isEmailExist) {

            // Create User object to store in ViewModel
            user = new User(null, firstName + " " + lastName, userEmail, mobileNumber, null, null,
                    null, null, null, null, null, null, null,
                    null, null);

            // Create JsonObject for POST Method
            JSONObject userObject = new JSONObject();
            try {

                userObject.put("name", firstName + " " + lastName);
                userObject.put("email", userEmail);
                userObject.put("mobile", mobileNumber);
                userObject.put("password", userPassword);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest signUpRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    Master.getSignUpEndpoint(),
                    userObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i(LOG_TAG, "Volley Response received: " + response.toString());

                            AuthenticationActivity.attachFragment(new PersonalDetailsFragment());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i(LOG_TAG, "Volley Error occurred: " + error.toString());
                            Toast.makeText(getActivity(), "Couldn't SignUp", Toast.LENGTH_SHORT).show();
                        }
                    }) {

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

                    Log.i(LOG_TAG, "Volley Parsing network response");

                    parseJson(response);

                    return super.parseNetworkResponse(response);
                }
            };

            // Make the request to backoff after 1 retry
            // Set the timeout to 0
            signUpRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // Queue the request
            requestQueue.add(signUpRequest);

        }
    }

    /**
     * Parsing the Response received from API
     *
     * @param response : Contains the JSONObject in String format
     */
    private void parseJson(NetworkResponse response) {
        try {

            // Params
            Integer statusCode;
            String responseString;
            JSONObject resObject;
            String message;
            final String userID;
            JSONObject data;

            // Retrieve status code
            statusCode = response.statusCode;

            // Parse response data into String
            responseString = new String(response.data, "UTF-8");

            // Create JsonObject for data
            resObject = new JSONObject(responseString);

            // Retrieve message to show Toast
            if (resObject.has("message")) {
                message = resObject.getString("message");
                Log.i(LOG_TAG, message);
            }

            if (statusCode == 201) {

                if (resObject.has("data")) {
                    data = resObject.getJSONObject("data");

                    // Retrieve user_id
                    userID = data.getString("user_id");

                    // Update user object with Id and update ViewModel data
                    user.set_id(userID);
                    viewModel.updateUser(user);

                    ParallemApp.saveUserId(userID);

                    // Create toast with user_id
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AuthenticationActivity.attachFragment(new PersonalDetailsFragment());
                        }
                    });


                }

            } else if (statusCode == 500) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Couldn't SignUp",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Setup FocusListener on EditText to call checkMailAPI
     * Clear requestQueue to prevent memory hogging
     */
    private void setupEmailFocusListener() {

        binding.editSignupEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                final String currentEmail = binding.editSignupEmail.getText().toString();

                // Remove validation drawable as the EditText is empty
                if (!hasFocus && currentEmail.isEmpty()) {
                    binding.editSignupEmail
                            .setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }

                // Conditions to prevent unnecessary requests
                if (!hasFocus && !currentEmail.isEmpty() &&
                        !(currentEmail.equals(lastCheckedEmail))) {

                    // Clear requestQueue to any previous check email requests
                    requestQueue.cancelAll(new RequestQueue.RequestFilter() {
                        @Override
                        public boolean apply(Request<?> request) {
                            return true;
                        }
                    });

                    // Call the check mail API
                    hitCheckMailAPI(currentEmail);
                }

            }
        });

    }

    /**
     * Hit Check Email API and update Email EditText
     *
     * @param currentEmail Text retrieved from View
     */
    private void hitCheckMailAPI(final String currentEmail) {

        // Create JSONObject for request body
        JSONObject emailObject = new JSONObject();

        try {
            emailObject.put("email", currentEmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a Volley request to check email endpoint
        JsonObjectRequest emailCheckRequest = new JsonObjectRequest(
                Request.Method.POST,
                Master.getEmailCheckEndpoint(),
                emailObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(LOG_TAG, "Volley Response received: " + response.toString());
                        lastCheckedEmail = currentEmail;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(LOG_TAG, "Volley Error occurred: " + error.toString());
                        lastCheckedEmail = currentEmail;
                    }
                }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

                Log.i(LOG_TAG, "Volley: Parsing network response");

                Integer statusCode;
                String responseString;
                JSONObject resObject;
                Boolean isExist;

                try {
                    // Retrieve status code
                    statusCode = response.statusCode;

                    // Parse response data into String
                    responseString = new String(response.data, "UTF-8");

                    // Create JsonObject for data
                    resObject = new JSONObject(responseString);

                    if (statusCode == 200) {
                        // Retrieve whether email exist
                        isExist = resObject.getBoolean("isExist");

                        if (isExist) {
                            Log.i(LOG_TAG, "Email exist already");
                            isEmailExist = true; // Update Global param

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    binding.editSignupEmail
                                            .setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_warning, 0);
                                    Toast.makeText(getActivity(), "Email already exist!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Log.i(LOG_TAG, "Email doesn't exist.");
                            isEmailExist = false; // Update Global param

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    binding.editSignupEmail
                                            .setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_valid, 0);
                                }
                            });
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return super.parseNetworkResponse(response);
            }
        };

        // Make the request to backoff after 1 retry
        // Set the timeout to 0
        emailCheckRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(emailCheckRequest);

    }

    /**
     * Validate form before Calling Login API
     *
     * @return Boolean validation
     */
    private Boolean validateForm() {

        // Clear all errors
        binding.editSignupFirstnameLayout.setError(null);
        binding.editSignupLastnameLayout.setError(null);
        binding.editSignupEmailLayout.setError(null);
        binding.editSignupMobileLayout.setError(null);
        binding.editSignupPasswordLayout.setError(null);
        binding.editSignupCnfPasswordLayout.setError(null);

        Boolean validation = true;

        if (firstName.isEmpty()) {
            binding.editSignupFirstnameLayout.setError("*Required");
            validation = false;
        }

        if (lastName.isEmpty()) {
            binding.editSignupLastnameLayout.setError("*Required");
            validation = false;
        }

        if (mobileNumber.length() < 10) {
            binding.editSignupMobileLayout.setError("*Requires at least 10 digit");
            validation = false;
        }

        if (userEmail.isEmpty()) {
            binding.editSignupEmailLayout.setError("*Required");
            validation = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            binding.editSignupEmailLayout.setError("Input Valid Email");
            validation = false;
        }

        if (userPassword.isEmpty()) {
            binding.editSignupPasswordLayout.setError("*Required");
            validation = false;
        } else if (userPassword.length() < 8) {
            binding.editSignupPasswordLayout.setError("*Requires at least 8 characters");
            validation = false;
        }

        if (userConfirmPassword.isEmpty()) {
            binding.editSignupCnfPasswordLayout.setError("*Required");
            validation = false;
        } else if (userConfirmPassword.length() < 8) {
            binding.editSignupCnfPasswordLayout.setError("*Requires at least 8 characters");
            validation = false;
        } else if (!userPassword.equals(userConfirmPassword)) {
            binding.editSignupCnfPasswordLayout.setError("Passwords do not match!");
            validation = false;
        }

        return validation;

    }

}
