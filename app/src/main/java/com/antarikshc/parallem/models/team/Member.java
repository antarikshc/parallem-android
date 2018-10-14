package com.antarikshc.parallem.models.team;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Member {

    @Expose
    @SerializedName("user")
    private String userId;

    @Expose
    private String role;

    @Expose
    @SerializedName("is_owner")
    private Boolean isOwner;

    public Member(String userId, String role, Boolean isOwner) {
        this.userId = userId;
        this.role = role;
        this.isOwner = isOwner;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getOwner() {
        return isOwner;
    }

    public void setOwner(Boolean owner) {
        isOwner = owner;
    }
}
