package com.antarikshc.parallem.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Skill {

    @Expose
    @SerializedName("skill_id")
    private Integer skillId;

    @Expose(serialize = false)
    private String name;

    public Skill(Integer skillId, String name) {
        this.skillId = skillId;
        this.name = name;
    }

    public Integer getSkillId() {
        return skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
