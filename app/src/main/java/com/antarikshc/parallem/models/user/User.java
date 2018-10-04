package com.antarikshc.parallem.models.user;

import com.antarikshc.parallem.models.Skill;
import com.antarikshc.parallem.models.team.Team;

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
    private ArrayList<Skill> skills;
    private ArrayList<Team> teams;
    private String password;

    public User(String _id, String name, String email, String mobileNumber, String headline, String location, String profileImage, ArrayList<Experience> experiences, ArrayList<Certification> certifications, ArrayList<UserProject> userProjects, ArrayList<Skill> skills, ArrayList<Team> teams, String password) {
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
        this.password = password;
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

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
