package com.antarikshc.parallem.data.network;

import android.util.Log;

import com.antarikshc.parallem.models.user.Certification;
import com.antarikshc.parallem.models.user.Experience;
import com.antarikshc.parallem.models.user.Notification;
import com.antarikshc.parallem.models.user.User;
import com.antarikshc.parallem.models.user.UserProject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserJsonParser {

    private static final String LOG_TAG = UserJsonParser.class.getSimpleName();

    // Constants
    private static final String USER_ID = "_id";
    private static final String USER_NAME = "name";
    private static final String USER_EMAIL = "email";
    private static final String USER_MOBILE_NUMBER = "mobile";
    private static final String USER_HEADLINE = "headline";
    private static final String USER_LOCATION = "location";
    private static final String USER_PROFILE_PIC = "profile_pic";

    private static final String USER_WORK_EXPERIENCE = "work_exp";
    private static final String EXP_NAME = "name";
    private static final String EXP_DESIGNATION = "desgn";
    private static final String EXP_START_DATE = "start_date";
    private static final String EXP_END_DATE = "end_date";

    private static final String USER_CERTIFICATIONS = "certifications";
    private static final String CERT_NAME = "name";
    private static final String CERT_AUTHORITY = "authority";

    private static final String USER_PROJECTS = "user_projects";
    private static final String PROJECT_NAME = "name";
    private static final String PROJECT_DESC = "desc";

    private static final String USER_SKILLS = "skills";
    private static final String SKILL_ID = "skill_id";

    private static final String USER_TEAM_PROJECTS = "team_projects";
    private static final String USER_TEAMS = "teams";

    private static final String USER_NOTIFICATIONS = "notifications";
    private static final String NOTIFY_TYPE_ID = "type_id";
    private static final String NOTIFY_MESSAGE = "message";
    private static final String NOTIFY_TEAM_ID = "team_id";

    public static User[] parseUserArray(JSONArray usersArray) throws JSONException {

        // Create Users array
        User[] fetchedUsers = new User[usersArray.length()];

        if (usersArray.length() > 0) {
            for (int i = 0; i < usersArray.length(); i++) {

                JSONObject userObject = usersArray.getJSONObject(i);
                fetchedUsers[i] = parseSingleUser(userObject);

            }
        }

        return fetchedUsers;

    }

    public static User parseSingleUser(JSONObject userObject) throws JSONException {

        // Fields
        String _id = null;
        String name = null;
        String email = null;
        String mobileNumber = null;
        String headline = null;
        String location = null;
        String profileImg = null;

        // Experience Array
        ArrayList<Experience> experience = new ArrayList<Experience>();
        String expName = null;
        String expDesgn = null;
        String expStartDate = null;
        String expEndDate = null;

        // Certifications Array
        ArrayList<Certification> certification = new ArrayList<Certification>();
        String certName = null;
        String certAuthority = null;

        // User Project Array
        ArrayList<UserProject> userProject = new ArrayList<UserProject>();
        String userProjectName = null;
        String userProjectDesc = null;

        Integer teamProjectCount = null;
        ArrayList<Integer> skills = new ArrayList<Integer>();
        String teamId = null;

        // Notifications Array
        ArrayList<Notification> notification = new ArrayList<Notification>();
        Integer notifyTypeId = null;
        String notifyMessage = null;
        String notifyTeamId = null;

        Integer collabRequestCount = 0;

        // Start JSON Parsing here

        _id = userObject.getString(USER_ID);

        if (userObject.has(USER_NAME)) {
            name = userObject.getString(USER_NAME);
        }

        if (userObject.has(USER_EMAIL)) {
            email = userObject.getString(USER_EMAIL);
        }

        if (userObject.has(USER_MOBILE_NUMBER)) {
            mobileNumber = userObject.getString(USER_MOBILE_NUMBER);
        }

        if (userObject.has(USER_HEADLINE)) {
            headline = userObject.getString(USER_HEADLINE);
        }

        if (userObject.has(USER_LOCATION)) {
            location = userObject.getString(USER_LOCATION);
        }

        if (userObject.has(USER_PROFILE_PIC)) {
            profileImg = userObject.getString(USER_PROFILE_PIC);
        }

        // Experience Array
        if (userObject.has(USER_WORK_EXPERIENCE)) {
            JSONArray expArray = userObject.getJSONArray(USER_WORK_EXPERIENCE);

            for (int i = 0; i < expArray.length(); i++) {

                JSONObject expObject = expArray.getJSONObject(i);

                if (expObject.has(EXP_NAME)) {
                    expName = expObject.getString(EXP_NAME);
                }

                if (expObject.has(EXP_DESIGNATION)) {
                    expDesgn = expObject.getString(EXP_DESIGNATION);
                }

                if (expObject.has(EXP_START_DATE)) {
                    expStartDate = expObject.getString(EXP_START_DATE);
                }

                if (expObject.has(EXP_END_DATE)) {
                    expEndDate = expObject.getString(EXP_END_DATE);
                }

                experience.add(new Experience(expName, expDesgn, expStartDate, expEndDate));

            }
        }

        // Certification object
        if (userObject.has(USER_CERTIFICATIONS)) {
            JSONArray certArray = userObject.getJSONArray(USER_CERTIFICATIONS);

            for (int i = 0; i < certArray.length(); i++) {

                JSONObject certObject = certArray.getJSONObject(i);

                if (certObject.has(CERT_NAME)) {
                    certName = certObject.getString(CERT_NAME);
                }

                if (certObject.has(CERT_AUTHORITY)) {
                    certAuthority = certObject.getString(CERT_AUTHORITY);
                }

                certification.add(new Certification(certName, certAuthority));
            }
        }

        // User Project object
        if (userObject.has(USER_PROJECTS)) {

            JSONArray projectArray = userObject.getJSONArray(USER_PROJECTS);

            for (int i = 0; i < projectArray.length(); i++) {

                JSONObject projectObject = projectArray.getJSONObject(i);

                if (projectObject.has(PROJECT_NAME)) {
                    userProjectName = projectObject.getString(PROJECT_NAME);
                }

                if (projectObject.has(PROJECT_DESC)) {
                    userProjectDesc = projectObject.getString(PROJECT_DESC);
                }

                userProject.add(new UserProject(userProjectName, userProjectDesc));
            }
        }

        if (userObject.has(USER_TEAM_PROJECTS)) {
            teamProjectCount = userObject.getInt(USER_TEAM_PROJECTS);
        }

        if (userObject.has(USER_SKILLS)) {
            JSONArray skillsArray = userObject.getJSONArray(USER_SKILLS);

            for (int i = 0; i < skillsArray.length(); i++) {
                skills.add((skillsArray.getJSONObject(0)).getInt(SKILL_ID));

            }

            if (userObject.has(USER_TEAMS)) {
                JSONArray teamsArray = userObject.getJSONArray(USER_TEAMS);

                if (teamsArray.length() > 0) {
                    teamId = teamsArray.getString(0);
                }
            }
        }

        // Notification Object
        if (userObject.has(USER_NOTIFICATIONS)) {

            JSONArray notifyArray = userObject.getJSONArray(USER_NOTIFICATIONS);

            for (int i = 0; i < notifyArray.length(); i++) {

                JSONObject notifyObject = notifyArray.getJSONObject(i);

                if (notifyObject.has(NOTIFY_TYPE_ID)) {
                    notifyTypeId = notifyObject.getInt(NOTIFY_TYPE_ID);
                }

                if (notifyObject.has(NOTIFY_MESSAGE)) {
                    notifyMessage = notifyObject.getString(NOTIFY_MESSAGE);
                }

                if (notifyObject.has(NOTIFY_TEAM_ID)) {
                    notifyTeamId = notifyObject.getString(NOTIFY_TEAM_ID);
                }

                collabRequestCount++;

                notification.add(new Notification(notifyTypeId, notifyMessage, notifyTeamId));
            }
        }

        Log.i(LOG_TAG, "JSON response parsed for 1 user");

        return new User(
                _id, name, email, mobileNumber, headline, location, profileImg,
                experience, certification, userProject, skills, teamId, teamProjectCount, notification, collabRequestCount
        );

    }

}