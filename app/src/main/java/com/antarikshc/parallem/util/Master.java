package com.antarikshc.parallem.util;

public class Master {

    // Parallem API Base URL
    private static final String PARALLEM_BASE_API = "https://parallem.herokuapp.com/api";

    // Get Login API Endpoint
    public static String getLoginAPI() {
        return PARALLEM_BASE_API + "/users/login";
    }

}
