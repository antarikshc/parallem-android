package com.antarikshc.parallem.models.user;

import java.util.ArrayList;

public class User {

    private String _id;
    private String name;
    private String email;
    private String mobileNumber;
    private String headline;
    private String location;
    private String profileImage;
    private ArrayList<Experience> experiences;
    private ArrayList<Certification> certifications;
    private ArrayList<UserProject> userProjects;
    private ArrayList<Integer> skills;
    private String teams;
    private Integer teamProjectCount;
    private ArrayList<Notification> notifications;
    private Integer collabRequestCount;

    public User(String _id, String name, String email, String mobileNumber, String headline, String location, String profileImage, ArrayList<Experience> experiences, ArrayList<Certification> certifications, ArrayList<UserProject> userProjects, ArrayList<Integer> skills, String teams, Integer teamProjectCount, ArrayList<Notification> notifications, Integer collabRequestCount) {
        this._id = _id;
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.headline = headline;
        this.location = location;
        this.profileImage = profileImage;
        this.experiences = experiences;
        this.certifications = certifications;
        this.userProjects = userProjects;
        this.skills = skills;
        this.teams = teams;
        this.teamProjectCount = teamProjectCount;
        this.notifications = notifications;
        this.collabRequestCount = collabRequestCount;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public ArrayList<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(ArrayList<Experience> experiences) {
        this.experiences = experiences;
    }

    public ArrayList<Certification> getCertifications() {
        return certifications;
    }

    public void setCertifications(ArrayList<Certification> certifications) {
        this.certifications = certifications;
    }

    public ArrayList<UserProject> getUserProjects() {
        return userProjects;
    }

    public void setUserProjects(ArrayList<UserProject> userProjects) {
        this.userProjects = userProjects;
    }

    public ArrayList<Integer> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Integer> skills) {
        this.skills = skills;
    }

    public String getTeams() {
        return teams;
    }

    public void setTeams(String teams) {
        this.teams = teams;
    }

    public Integer getTeamProjectCount() {
        return teamProjectCount;
    }

    public void setTeamProjectCount(Integer teamProjectCount) {
        this.teamProjectCount = teamProjectCount;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public Integer getCollabRequestCount() {
        return collabRequestCount;
    }

    public void setCollabRequestCount(Integer collabRequestCount) {
        this.collabRequestCount = collabRequestCount;
    }
}
