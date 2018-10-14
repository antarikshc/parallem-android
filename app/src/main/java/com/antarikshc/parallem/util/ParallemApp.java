package com.antarikshc.parallem.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public class ParallemApp extends Application {

    // Constants
    private static final String PARALLEM_SHARED_PREFS = "parallem_shared_prefs";
    private static final String USER_ID = "user_id";
    private static final String TEAM_ID = "team_id";

    // Global params
    private static SharedPreferences sp;
    private static String userId;
    private static String teamId;

    public ParallemApp() {
        super();
    }

    /**
     * Add userId into Shared Preferences
     *
     * @param userId Should not be null
     */
    public static void saveUserId(@NonNull String userId) {

        // Null checks
        if (userId == null || sp == null) {
            return;
        }
        // Add UserId to shared preference
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(USER_ID, userId);
        editor.apply();

        // Save the UserId in class variable
        ParallemApp.userId = userId;

    }

    /**
     * Method to check the validity of userId
     *
     * @return Boolean value indicating whether token exist or not
     */
    public static boolean isUserIdExist() {
        return !userId.isEmpty() && userId.length() > 0;
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
     * Add teamId into Shared Preferences
     *
     * @param teamId Should not be null
     */
    public static void saveTeamId(@NonNull String teamId) {

        // Null checks
        if (teamId == null || sp == null) {
            return;
        }
        // Add teamId to shared preference
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(TEAM_ID, teamId);
        editor.apply();

        // Save the teamId in class variable
        ParallemApp.teamId = teamId;

    }

    /**
     * Remove teamId from Shared Preferences
     */
    public static void removeTeanId() {

        if (sp == null) {
            return;
        }
        // Null the class variable
        teamId = null;

        // Remove token from Shared preferences
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(TEAM_ID);
        editor.apply();

    }

    /**
     * Return the Team ID from Shared Preferences
     *
     * @return String containing Team ID, could be null
     */
    public static String getTeamId() {
        return sp.getString(TEAM_ID, null);
    }

    /**
     * Method to check the validity of teamId
     *
     * @return Boolean value indicating whether token exist or not
     */
    public static boolean isTeamIdExist() {
        return !teamId.isEmpty() && teamId.length() > 0;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Get the Shared Preference
        sp = getSharedPreferences(PARALLEM_SHARED_PREFS, Context.MODE_PRIVATE);
        userId = sp.getString(USER_ID, null);
        teamId = sp.getString(TEAM_ID, null);

    }

}
