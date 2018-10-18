package com.antarikshc.parallem.ui.authentication;

import android.content.Intent;
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

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.antarikshc.parallem.R;
import com.antarikshc.parallem.databinding.FragmentLoginBinding;
import com.antarikshc.parallem.ui.dashboard.DashboardActivity;
import com.antarikshc.parallem.util.Master;
import com.antarikshc.parallem.util.ParallemApp;
import com.antarikshc.parallem.util.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginFragment extends Fragment {

    private static final String LOG_TAG = LoginFragment.class.getSimpleName();

    // Global params
    private FragmentLoginBinding binding;
    private String userEmail;
    private String userPassword;
    private RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate Fragment layout with data binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Request focus on EditText on Startup
        binding.editLoginEmail.requestFocus();

        onClickListeners();

        // Get Singleton Volley Request Queue Instance
        requestQueue = VolleySingleton.getInstance(getActivity()).getRequestQueue();

    }

    /**
     * Set all on click listeners here rather than in Activity
     */
    private void onClickListeners() {

        // Button Login
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hitLoginAPI();
            }
        });

    }


    /**
     * Validate form and Login Call API
     */
    public void hitLoginAPI() {

        // Retrieve data from views
        userEmail = binding.editLoginEmail.getText().toString();
        userPassword = binding.editLoginPassword.getText().toString();

        if (validateForm()) {

            // Create JsonObject for POST Method
            JSONObject userObject = new JSONObject();
            try {

                userObject.put("email", userEmail);
                userObject.put("password", userPassword);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest loginRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    Master.getLoginEndpoint(),
                    userObject,

                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i(LOG_TAG, "HTTP Response received for LoginRequest");
                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i(LOG_TAG, "HTTP Error occurred: " + error.toString());
                            Toast.makeText(getActivity(), "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }) {

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

                    parseJson(response);

                    return super.parseNetworkResponse(response);
                }
            };

            // Queue the request
            requestQueue.add(loginRequest);

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
            Boolean loginValidation;
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

                // Check if Login validation is successful
                loginValidation = resObject.getBoolean("validation");
                if (loginValidation) {

                    if (resObject.has("data")) {
                        data = resObject.getJSONObject("data");

                        // Retrieve user_id
                        userID = data.getString("user_id");

                        ParallemApp.saveUserId(userID);

                        // Save Team ID into SharedPref if exist
                        if (data.has("team_id")) {
                            ParallemApp.saveTeamId(data.getString("team_id"));
                        }

                        // Launch Dashboard Activity
                        Intent intent = new Intent(getActivity(), DashboardActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "It seems you entered wrong password!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            } else if (statusCode == 500) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Login Unsuccessful :(",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Validate form before Calling Login API
     *
     * @return Boolean validation
     */
    private Boolean validateForm() {

        // Clear all errors
        binding.editLoginEmailLayout.setError(null);
        binding.editLoginPasswordLayout.setError(null);

        Boolean validation = true;

        if (userEmail.isEmpty()) {
            binding.editLoginEmailLayout.setError("*Required");
            validation = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            binding.editLoginEmailLayout.setError("Input Valid Email");
            validation = false;
        }

        if (userPassword.isEmpty()) {
            binding.editLoginPasswordLayout.setError("*Required");
            validation = false;
        }

        return validation;

    }

}
