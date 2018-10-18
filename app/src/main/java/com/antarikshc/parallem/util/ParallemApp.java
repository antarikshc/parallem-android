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
    private static final String FCM_TOKEN = "fcm_token";

    // Global params
    private static SharedPreferences sp;
    private static String userId;
    private static String teamId;
    private static String fcmToken;

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
        if (sp == null) {
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
        return sp.contains(USER_ID);
    }

    /**
     * Add teamId into Shared Preferences
     *
     * @param teamId Should not be null
     */
    public static void saveTeamId(@NonNull String teamId) {

        // Null checks
        if (sp == null) {
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
     * Method to check the validity of teamId
     *
     * @return Boolean value indicating whether token exist or not
     */
    public static boolean isTeamIdExist() {
        return sp.contains(TEAM_ID);
    }

    /**
     * Remove teamId from Shared Preferences
     */
    public static void removeTeamId() {

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
     * Add FCM Token into Shared Preferences
     *
     * @param fcmToken Should not be null
     */
    public static void saveFcmToken(@NonNull String fcmToken) {

        // Null checks
        if (sp == null) {
            return;
        }
        // Add fcmToken to shared preference
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(FCM_TOKEN, fcmToken);
        editor.apply();

        // Save the fcmToken in class variable
        ParallemApp.fcmToken = fcmToken;

    }

    /**
     * Method to check the validity of fcmToken
     *
     * @return Boolean value indicating whether token exist or not
     */
    public static boolean isFcmTokenExist() {
        return sp.contains(FCM_TOKEN);
    }

    /**
     * Clear all keys
     */
    public static void removeAll() {

        SharedPreferences.Editor editor = sp.edit();
        editor.remove(USER_ID);
        editor.remove(TEAM_ID);
        editor.remove(FCM_TOKEN);
        editor.apply();

    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Get the Shared Preference
        sp = getSharedPreferences(PARALLEM_SHARED_PREFS, Context.MODE_PRIVATE);
        userId = sp.getString(USER_ID, null);
        teamId = sp.getString(TEAM_ID, null);
        fcmToken = sp.getString(FCM_TOKEN, null);

    }

}
