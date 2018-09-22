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

}
