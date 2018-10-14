package com.antarikshc.parallem.models.team;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Member {

    @Expose
    @SerializedName("user")
    private String userId;

    @Expose
    private String name;

    @Expose
    private String role;

    @Expose
    @SerializedName("profile_pic")
    private String profileImage;

    @Expose
    @SerializedName("is_owner")
    private Boolean isOwner;

    public Member(String userId, String name, String role, String profileImage, Boolean isOwner) {
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.profileImage = profileImage;
        this.isOwner = isOwner;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Boolean getOwner() {
        return isOwner;
    }

    public void setOwner(Boolean owner) {
        isOwner = owner;
    }
}
