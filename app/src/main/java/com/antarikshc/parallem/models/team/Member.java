package com.antarikshc.parallem.models.team;

public class Member {

    private String userId;
    private String role;
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