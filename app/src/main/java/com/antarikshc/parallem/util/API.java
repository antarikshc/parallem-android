package com.antarikshc.parallem.util;

import android.support.annotation.NonNull;

public class API {

    // Parallem API Base URL
    private static final String PARALLEM_BASE_API = "https://parallem.herokuapp.com/api";


    /**
     * Endpoints for general use throughout the app
     */
    public static String getfetchAllUsersEndpoint() {
        return PARALLEM_BASE_API + "/users";
    }

    public static String getUserById(@NonNull String userId) {
        return PARALLEM_BASE_API + "/users/" + userId;
    }

    public static String getExploreEndpoint() {
        return PARALLEM_BASE_API + "/explore";
    }

    public static String getWeeklyEndpoint() {
        return PARALLEM_BASE_API + "/weekly";
    }

    public static String getProfileImageUrl(@NonNull String imageId) {
        return PARALLEM_BASE_API + "/images/profile" + imageId + ".png";
    }

    /**
     * Endpoints to handle User Authentication- Login, Sign Up, Update Profile
     */
    public static String getLoginEndpoint() {
        return PARALLEM_BASE_API + "/users/login";
    }

    public static String getSignUpEndpoint() {
        return PARALLEM_BASE_API + "/users";
    }

    public static String getUpdateEndpoint(@NonNull String userId) {
        return PARALLEM_BASE_API + "/users/" + userId;
    }

    public static String getEmailCheckEndpoint() {
        return PARALLEM_BASE_API + "/users/check";
    }

    /**
     * Endpoints for Teams
     */
    public static String getTeamById(@NonNull String teamId) {
        return PARALLEM_BASE_API + "/teams/" + teamId;
    }

    public static String getTeamMembers(@NonNull String teamId) {
        return PARALLEM_BASE_API + "/teams/" + teamId + "/members";
    }

    /**
     * Endpoints for FCM and Collab requests
     */
    public static String getTokenEndpoint(@NonNull String userId) {
        return PARALLEM_BASE_API + "/users/" + userId + "/token";
    }

    public static String getCollabRequestEndpoint(@NonNull String userId) {
        return PARALLEM_BASE_API + "/users/" + userId + "/collab";
    }

    /**
     * Endpoint for getting list of all skills
     */
    public static String getSkillsEndpoint() {
        return PARALLEM_BASE_API + "/skills";
    }
}
