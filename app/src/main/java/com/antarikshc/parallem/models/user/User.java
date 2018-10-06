package com.antarikshc.parallem.models.user;

import com.antarikshc.parallem.models.Skill;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    @Expose
    private String _id;

    @Expose
    private String name;

    @Expose
    private String email;

    @Expose
    @SerializedName("mobile")
    private String mobileNumber;

    @Expose
    private String headline;

    @Expose
    private String location;

    @Expose
    @SerializedName("profile_pic")
    private String profileImage;

    @Expose
    @SerializedName("work_exp")
    private List<Experience> experiences;

    @Expose
    private List<Certification> certifications;

    @Expose
    @SerializedName("user_projects")
    private List<UserProject> userProjects;

    @Expose
    private List<Skill> skills;

    @Expose
    private List<String> teams;

    @Expose
    @SerializedName("team_projects")
    private Integer teamProjectCount;

    @Expose
    private List<Notification> notifications;

    @Expose(deserialize = false, serialize = false)
    private Integer collabRequestCount;

    public User(String _id, String name, String email, String mobileNumber, String headline, String location, String profileImage, List<Experience> experiences, List<Certification> certifications, List<UserProject> userProjects, List<Skill> skills, List<String> teams, Integer teamProjectCount, List<Notification> notifications, Integer collabRequestCount) {
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

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public List<Certification> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<Certification> certifications) {
        this.certifications = certifications;
    }

    public List<UserProject> getUserProjects() {
        return userProjects;
    }

    public void setUserProjects(List<UserProject> userProjects) {
        this.userProjects = userProjects;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<String> getTeams() {
        return teams;
    }

    public void setTeams(List<String> teams) {
        this.teams = teams;
    }

    public Integer getTeamProjectCount() {
        return teamProjectCount;
    }

    public void setTeamProjectCount(Integer teamProjectCount) {
        this.teamProjectCount = teamProjectCount;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public Integer getCollabRequestCount() {
        return collabRequestCount;
    }

    public void setCollabRequestCount(Integer collabRequestCount) {
        this.collabRequestCount = collabRequestCount;
    }
}
