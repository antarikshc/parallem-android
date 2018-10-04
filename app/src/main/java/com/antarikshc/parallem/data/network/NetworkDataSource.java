package com.antarikshc.parallem.data.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.antarikshc.parallem.models.user.User;
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

                // Store the response in cache
                cacheResponse(response);

                return super.parseNetworkResponse(response);
            }
        };

        // Queue the API Call
        mRequestQueue.add(fetchUserRequest);
    }

    private void cacheResponse(NetworkResponse response) {
        try {
            Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
            if (cacheEntry == null) {
                cacheEntry = new Cache.Entry();
            }
            final long cacheHitButRefreshed = 2 * 60 * 1000; // in 2 minutes cache will be hit, but also refreshed on background
            final long cacheExpired = 48 * 60 * 60 * 1000; // in 48 hours this cache entry expires completely
            long now = System.currentTimeMillis();
            final long softExpire = now + cacheHitButRefreshed;
            final long ttl = now + cacheExpired;
            cacheEntry.data = response.data;
            cacheEntry.softTtl = softExpire;
            cacheEntry.ttl = ttl;
            String headerValue;
            headerValue = response.headers.get("Date");
            if (headerValue != null) {
                cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
            }
            headerValue = response.headers.get("Last-Modified");
            if (headerValue != null) {
                cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
            }
            cacheEntry.responseHeaders = response.headers;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
