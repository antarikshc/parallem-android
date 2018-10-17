package com.antarikshc.parallem.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification {

    @Expose
    @SerializedName("type_id")
    private Integer typeId;

    @Expose
    private String message;

    @Expose
    @SerializedName("team_id")
    private String teamId;

    @Expose
    private Integer status;

    public Notification(Integer typeId, String message, String teamId, Integer status) {
        this.typeId = typeId;
        this.message = message;
        this.teamId = teamId;
        this.status = status;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
