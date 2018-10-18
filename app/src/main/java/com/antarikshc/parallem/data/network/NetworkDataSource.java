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
import com.android.volley.toolbox.JsonObjectRequest;
import com.antarikshc.parallem.models.Skill;
import com.antarikshc.parallem.models.team.Team;
import com.antarikshc.parallem.models.user.User;
import com.antarikshc.parallem.util.Master;
import com.antarikshc.parallem.util.ParallemApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

public class NetworkDataSource {

    private static final String LOG_TAG = NetworkDataSource.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static NetworkDataSource sInstance;
    private final Context mContext;

    // Global params
    private RequestQueue mRequestQueue;
    private final MutableLiveData<User[]> retrievedUsers;
    private final MutableLiveData<User> userProfile;
    private final MutableLiveData<Skill[]> retrievedSkills;
    private final MutableLiveData<User> weeklyDev;
    private final MutableLiveData<Team> userTeam;
    private final Gson gson;

    private NetworkDataSource(Context context, RequestQueue requestQueue) {
        mContext = context;
        mRequestQueue = requestQueue;
        retrievedUsers = new MutableLiveData<User[]>();
        userProfile = new MutableLiveData<User>();
        retrievedSkills = new MutableLiveData<Skill[]>();
        weeklyDev = new MutableLiveData<User>();
        userTeam = new MutableLiveData<Team>();
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
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

    /**
     * Public method for Repository
     *
     * @return Single user profile
     */
    public LiveData<User> getProfileDetails() {
        fetchProfileDetails();
        return userProfile;
    }

    /**
     * Public method for Repository
     * @return Array of users for Explore recycler
     */
    public LiveData<User[]> getExploreUsers() {
        fetchExploreUsers();
        return retrievedUsers;
    }

    /**
     * Public method to return data in Repository
     *
     * @return List of all Skills
     */
    public LiveData<Skill[]> getAllSkills() {
        fetchAllSkills();
        return retrievedSkills;
    }

    /**
     * Public method to return data in Repository
     *
     * @return Profile of Top Weekly developer
     */
    public LiveData<User> getTopWeeklyDev() {
        fetchTopWeeklyDev();
        return weeklyDev;
    }

    /**
     * Public method to return data in Repository
     *
     * @return Profile of Top Weekly developer
     */
    public LiveData<Team> getUserTeam() {
        fetchUserTeam();
        return userTeam;
    }

    /**
     * Contains Volley request to fetch User from UserID
     */
    private void fetchProfileDetails() {

        // Get UserId from Shared preferences
        String userId = ParallemApp.getUserId();

        // Volley request to fetch single user (profile)
        JsonObjectRequest profileRequest = new JsonObjectRequest(
                Request.Method.GET,
                Master.getUserById(userId),
                null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(LOG_TAG, "HTTP Response received for profileRequest");
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

                try {
                    // Parse response data into String
                    String responseString = new String(response.data, "UTF-8");

                    // Create JSONObject from the responseString
                    JSONObject userObject = new JSONObject(responseString);

                    // Parse JSON Array
                    User user = gson.fromJson(userObject.toString(), User.class);

                    // Let the LiveData know that content has been updated
                    // This posts the update to the main thread
                    userProfile.postValue(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Store the response in cache
                cacheResponse(response);

                return super.parseNetworkResponse(response);
            }
        };

        // Queue the API Call
        mRequestQueue.add(profileRequest);

    }

    /**
     * Contains Volley request to fetch Users from /explore endpoint
     */
    private void fetchExploreUsers() {

        // Temporary - until Explore API is ready
        // Fetch all users from API
        JsonArrayRequest fetchExploreUsersRequest = new JsonArrayRequest(
                Request.Method.GET,
                Master.getExploreEndpoint(),
                null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i(LOG_TAG, "HTTP Response received for ExploreUsersRequest");
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
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {

                try {
                    // Parse response data into String
                    String responseString = new String(response.data, "UTF-8");
                    JSONArray usersArray = new JSONArray(responseString);

                    // Parse JSON Array
                    User[] users = gson.fromJson(usersArray.toString(), User[].class);

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
        mRequestQueue.add(fetchExploreUsersRequest);
    }

    /**
     * Contains Volley request to fetch Top weekly developer from /weekly endpoint
     */
    private void fetchTopWeeklyDev() {

        // Temporary - until Explore API is ready
        // Fetch all users from API
        JsonObjectRequest fetchWeeklyRequest = new JsonObjectRequest(
                Request.Method.GET,
                Master.getWeeklyEndpoint(),
                null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(LOG_TAG, "HTTP Response received for WeeklyRequest");
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

                try {
                    // Parse response data into String
                    String responseString = new String(response.data, "UTF-8");
                    JSONObject jsonObject = new JSONObject(responseString);

                    // Parse JSON Object into User Object
                    User user = gson.fromJson(jsonObject.toString(), User.class);

                    // Let the LiveData know that content has been updated
                    // This posts the update to the main thread
                    weeklyDev.postValue(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Store the response in cache
                cacheResponse(response);

                return super.parseNetworkResponse(response);
            }
        };

        // Queue the API Call
        mRequestQueue.add(fetchWeeklyRequest);
    }

    /**
     * Contains Volley request to fetch Team of the user from /teams/{team_id} endpoint
     */
    private void fetchUserTeam() {

        JsonObjectRequest userTeamRequest = new JsonObjectRequest(
                Request.Method.GET,
                Master.getTeamById(ParallemApp.getTeamId()),
                null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(LOG_TAG, "HTTP Response received for UserTeamRequest");
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

                try {
                    // Parse response data into String
                    String responseString = new String(response.data, "UTF-8");

                    // Create JSONObject from the responseString
                    JSONObject teamObject = new JSONObject(responseString);

                    // Parse JSON Array
                    Team team = gson.fromJson(teamObject.toString(), Team.class);

                    // Let the LiveData know that content has been updated
                    // This posts the update to the main thread
                    userTeam.postValue(team);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Store the response in cache
                cacheResponse(response);

                return super.parseNetworkResponse(response);
            }
        };

        // Queue the API Call
        mRequestQueue.add(userTeamRequest);

    }

    /**
     * Contains Volley request to fetch Skills from /skills endpoint
     */
    private void fetchAllSkills() {

        JsonArrayRequest fetchSkillsRequest = new JsonArrayRequest(
                Request.Method.GET,
                Master.getSkillsEndpoint(),
                null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i(LOG_TAG, "HTTP Response received for SkillsRequest");
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
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {

                try {
                    // Parse response data into String
                    String responseString = new String(response.data, "UTF-8");
                    JSONArray skillsArray = new JSONArray(responseString);

                    // Parse JSON Array
                    Skill[] skills = gson.fromJson(skillsArray.toString(), Skill[].class);

                    // Let the LiveData know that content has been updated
                    // This posts the update to the main thread
                    retrievedSkills.postValue(skills);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Store the response in cache
                cacheResponse(response);

                return super.parseNetworkResponse(response);
            }
        };

        // Queue the API Call
        mRequestQueue.add(fetchSkillsRequest);
    }

    /**
     * Helper method to cache the response received from Volley request
     *
     * @param response Pass the NetworkResponse that needs to be cached
     */
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
