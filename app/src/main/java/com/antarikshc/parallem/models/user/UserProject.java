package com.antarikshc.parallem.models.user;

import com.google.gson.annotations.Expose;

public class UserProject {

    @Expose
    private String name;

    @Expose
    private String desc;

    public UserProject(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
