package com.antarikshc.parallem.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public class ParallemApp extends Application {

    // Constants
    private static final String PARALLEM_SHARED_PREFS = "parallem_shared_prefs";
    private static final String USER_ID = "user_id";

    // Global params
    private static SharedPreferences sp;
    private static String userId;

    public ParallemApp() {
        super();
    }

    /**
     * Add userId into Shared Preferences
     *
     * @param userId Should not be null
     */
    public static void addUserId(@NonNull String userId) {

        // Null checks
        if (userId == null || sp == null) {
            return;
        }
        // Add token to shared preference
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(USER_ID, userId);
        editor.apply();

        // Save the token in class variable
        ParallemApp.userId = userId;

    }

    /**
     * Remove userId from Shared Preferences
     */
    public static void removeUserId() {

        if (sp == null) {
            return;
        }
        // Null the class variable
        userId = null;

        // Remove token from Shared preferences
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(USER_ID);
        editor.apply();

    }

    /**
     * Return the User ID from Shared Preferences
     *
     * @return String containing User ID, could be null
     */
    public static String getUserId() {
        return sp.getString(USER_ID, null);
    }

    /**
     * Method to check the validity of userId
     *
     * @return Boolean value indicating whether token exist or not
     */
    public static boolean isUserIdExist() {
        return userId != null && userId.length() > 0;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Get the Shared Preference
        sp = getSharedPreferences(PARALLEM_SHARED_PREFS, Context.MODE_PRIVATE);
        userId = sp.getString(USER_ID, null);

    }
}
