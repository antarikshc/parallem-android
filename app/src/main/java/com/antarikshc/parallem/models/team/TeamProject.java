package com.antarikshc.parallem.models.team;

import com.google.gson.annotations.Expose;

public class TeamProject {

    @Expose
    private String name;

    @Expose
    private Integer status;

    public TeamProject(String name, Integer status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
