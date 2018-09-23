package com.antarikshc.parallem.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.antarikshc.parallem.R;
import com.antarikshc.parallem.databinding.ActivitySignUpBinding;
import com.antarikshc.parallem.util.Master;
import com.antarikshc.parallem.util.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    private static final String LOG_TAG = SignUpActivity.class.getSimpleName();

    // Global Params
    private ActivitySignUpBinding binding;
    private RequestQueue requestQueue;
    private String firstName;
    private String lastName;
    private String userEmail;
    private String mobileNumber;
    private String userPassword;
    private String userConfirmPassword;
    // Params for Email checking
    private Boolean isEmailExist = true;
    private String lastCheckedEmail = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Bind the layout
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

        // Get Singleton Volley Request Queue Instance
        requestQueue = VolleySingleton.getInstance(this).getRequestQueue();

        // Set up Email EditText focus listener to check email uniqueness
        setupEmailFocusListener();

    }

    /**
     * OnClick Button SignUp - Validate form and Call API
     *
     * @param view SignUp Button View
     */
    public void hitSignUpAPI(View view) {

        // Retrieve data from views
        firstName = binding.editSignupFirstname.getText().toString();
        lastName = binding.editSignupLastname.getText().toString();
        userEmail = binding.editSignupEmail.getText().toString();
        mobileNumber = binding.editSignupIsd.getText().toString() +
                binding.editSignupMobile.getText().toString();
        userPassword = binding.editSignupPassword.getText().toString();
        userConfirmPassword = binding.editSignupCnfPassword.getText().toString();

        if (validateForm() && isEmailExist) {

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
                    Method.POST,
                    Master.getSignUpEndpoint(),
                    userObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i(LOG_TAG, "Volley Response received: " + response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i(LOG_TAG, "Volley Error occurred: " + error.toString());
                            Toast.makeText(SignUpActivity.this, "Couldn't SignUp", Toast.LENGTH_SHORT).show();
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

            if (statusCode == 200) {

                if (resObject.has("data")) {
                    data = resObject.getJSONObject("data");

                    // Retrieve user_id
                    userID = data.getString("user_id");

                    // Create toast with user_id
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SignUpActivity.this, "SignUp Successful\n" + userID,
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            } else if (statusCode == 500) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SignUpActivity.this, "Couldn't SignUp",
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
                Method.POST,
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

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    binding.editSignupEmail
                                            .setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_warning, 0);
                                }
                            });
                        } else {
                            Log.i(LOG_TAG, "Email doesn't exist.");
                            isEmailExist = false; // Update Global param

                            runOnUiThread(new Runnable() {
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

    public void backButton(View view) {
        onBackPressed();
    }

    /**
     * Boilerplate: Tap outside to hide soft input manager
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof TextInputEditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
