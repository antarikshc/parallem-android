package com.antarikshc.parallem.ui.authentication;

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

import com.android.volley.NetworkResponse;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.antarikshc.parallem.R;
import com.antarikshc.parallem.databinding.ActivityLoginBinding;
import com.antarikshc.parallem.util.Master;
import com.antarikshc.parallem.util.ParallemApp;
import com.antarikshc.parallem.util.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    // Global params
    private ActivityLoginBinding binding;
    private String userEmail;
    private String userPassword;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        // Request focus on EditText on Startup
        binding.editLoginEmail.requestFocus();

        // Get Singleton Volley Request Queue Instance
        requestQueue = VolleySingleton.getInstance(this).getRequestQueue();

    }

    /**
     * OnClick Button Login - Validate form and Call API
     *
     * @param view : Button Login
     */
    public void hitLoginAPI(View view) {

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
                    Method.POST,
                    Master.getLoginEndpoint(),
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
                            Toast.makeText(LoginActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }) {

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

                    Log.i(LOG_TAG, "Volley Parsing network response");

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

                        // Create toast with user_id
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "Login Successful\n" + userID,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                        ParallemApp.addUserId(userID);
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "It seems you entered wrong password!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            } else if (statusCode == 500) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "Login Unsuccessful",
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
