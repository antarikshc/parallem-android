package com.antarikshc.parallem.models.team;

import com.antarikshc.parallem.models.Skill;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Team {

    @Expose
    private String _id;

    @Expose
    private String name;

    @Expose
    private String desc;

    @Expose
    private Integer capacity;

    @Expose
    private List<Skill> skills;

    @Expose
    private List<Member> members;

    @Expose
    @SerializedName("projects")
    private List<TeamProject> teamProjects;

    public Team(String _id, String name, String desc, Integer capacity, ArrayList<Skill> skills, ArrayList<Member> members, ArrayList<TeamProject> teamProjects) {
        this._id = _id;
        this.name = name;
        this.desc = desc;
        this.capacity = capacity;
        this.skills = skills;
        this.members = members;
        this.teamProjects = teamProjects;
    }

    public String getId() {
        return _id;
    }

    public void setId(String teamId) {
        this._id = teamId;
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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public List<TeamProject> getTeamProjects() {
        return teamProjects;
    }

    public void setTeamProjects(List<TeamProject> teamProjects) {
        this.teamProjects = teamProjects;
    }
}
