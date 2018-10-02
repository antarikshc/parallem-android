package com.antarikshc.parallem.models;

public class User {

    // This Object currently acting as a dummy

    private String _id;
    private String name;
    private String email;
    private String headline;
    private String location;

    public User(String _id, String name, String email, String headline, String location) {
        this._id = _id;
        this.name = name;
        this.email = email;
        this.headline = headline;
        this.location = location;
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
}
