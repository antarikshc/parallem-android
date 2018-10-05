package com.antarikshc.parallem.models.user;

public class Notification {

    private Integer typeId;
    private String message;
    private String teamId;

    public Notification(Integer typeId, String message, String teamId) {
        this.typeId = typeId;
        this.message = message;
        this.teamId = teamId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
}
