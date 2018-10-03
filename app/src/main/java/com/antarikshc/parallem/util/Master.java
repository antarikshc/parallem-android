package com.antarikshc.parallem.util;

public class Master {

    // Parallem API Base URL
    private static final String PARALLEM_BASE_API = "https://parallem.herokuapp.com/api";

    public static String getLoginEndpoint() {
        return PARALLEM_BASE_API + "/users/login";
    }

    public static String getSignUpEndpoint() {
        return PARALLEM_BASE_API + "/users";
    }

    public static String getEmailCheckEndpoint() {
        return PARALLEM_BASE_API + "/users/check";
    }

    public static String getfetchAllUsersEndpoint() {
        return PARALLEM_BASE_API + "/users";
    }

    public static String getExploreEndpoint() {
        return PARALLEM_BASE_API + "/explore";
    }

}
