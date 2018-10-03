package com.antarikshc.parallem.data.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.antarikshc.parallem.models.User;
import com.antarikshc.parallem.util.Master;

import org.json.JSONArray;

public class NetworkDataSource {

    private static final String LOG_TAG = NetworkDataSource.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static NetworkDataSource sInstance;
    private final Context mContext;

    private final MutableLiveData<User[]> retrievedUsers;
    // Global params
    private RequestQueue mRequestQueue;

    private NetworkDataSource(Context context, RequestQueue requestQueue) {
        mContext = context;
        mRequestQueue = requestQueue;
        retrievedUsers = new MutableLiveData<User[]>();
    }

    /**
     * Get the singleton for this class
     */
    public static NetworkDataSource getInstance(Context context, RequestQueue requestQueue) {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new NetworkDataSource(
                        context.getApplicationContext(),
                        requestQueue);
                Log.d(LOG_TAG, "Made new network data source");
            }
        }

        return sInstance;
    }

    public LiveData<User[]> getUsers() {
        fetchUsers();
        return retrievedUsers;
    }

    private void fetchUsers() {

        // Temporary - until Explore API is ready
        // Fetch all users from API
        JsonArrayRequest fetchUserRequest = new JsonArrayRequest(
                Request.Method.GET,
                Master.getExploreEndpoint(),
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i(LOG_TAG, "Volley response received");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(LOG_TAG, "Volley Error occurred: " + error);
                    }
                }
        ) {
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {

                Log.i(LOG_TAG, "Parsing network response");

                try {
                    // Parse response data into String
                    String responseString = new String(response.data, "UTF-8");
                    JSONArray usersArray = new JSONArray(responseString);

                    // Parse JSON Array
                    User[] users = UserJsonParser.parseJson(usersArray);

                    // Let the LiveData know that content has been updated
                    // This posts the update to the main thread
                    retrievedUsers.postValue(users);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return super.parseNetworkResponse(response);
            }
        };

        // Queue the API Call
        mRequestQueue.add(fetchUserRequest);
    }


}
