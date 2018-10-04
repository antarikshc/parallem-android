package com.antarikshc.parallem.models.team;

import com.antarikshc.parallem.models.Skill;

import java.util.ArrayList;

public class Team {

    private String name;
    private String desc;
    private Integer capacity;
    private ArrayList<Skill> skills;
    private ArrayList<Member> members;
    private ArrayList<TeamProject> teamProjects;

    public Team(String name, String desc, Integer capacity, ArrayList<Skill> skills, ArrayList<Member> members, ArrayList<TeamProject> teamProjects) {
        this.name = name;
        this.desc = desc;
        this.capacity = capacity;
        this.skills = skills;
        this.members = members;
        this.teamProjects = teamProjects;
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

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Member> members) {
        this.members = members;
    }

    public ArrayList<TeamProject> getTeamProjects() {
        return teamProjects;
    }

    public void setTeamProjects(ArrayList<TeamProject> teamProjects) {
        this.teamProjects = teamProjects;
    }
}
