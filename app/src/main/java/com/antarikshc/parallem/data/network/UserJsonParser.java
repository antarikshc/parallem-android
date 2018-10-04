package com.antarikshc.parallem.data.network;

import com.antarikshc.parallem.models.user.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserJsonParser {

    // Constants
    private static final String USER_ID = "_id";
    private static final String USER_NAME = "name";
    private static final String USER_EMAIL = "email";
    private static final String USER_HEADLINE = "headline";
    private static final String USER_LOCATION = "location";

    public static User[] parseJson(JSONArray usersArray) throws JSONException {

        User[] fetchedUsers = new User[usersArray.length()];

        // Fields
        String _id = null;
        String name = null;
        String email = null;
        String headline = null;
        String location = null;

        if (usersArray.length() > 0) {
            for (int i = 0; i < usersArray.length(); i++) {

                JSONObject userObject = usersArray.getJSONObject(i);

                _id = userObject.getString(USER_ID);

                if (userObject.has(USER_NAME)) {
                    name = userObject.getString(USER_NAME);
                }

                if (userObject.has(USER_EMAIL)) {
                    email = userObject.getString(USER_EMAIL);
                }

                if (userObject.has(USER_HEADLINE)) {
                    headline = userObject.getString(USER_HEADLINE);
                }

                if (userObject.has(USER_LOCATION)) {
                    location = userObject.getString(USER_LOCATION);
                }

                //fetchedUsers[i] = new User(_id, name, email, headline, location);

            }
        }

        return fetchedUsers;

    }

}
