package com.antarikshc.parallem.models.user;

public class Experience {

    private String name;
    private String desgn;
    private String startDate;
    private String endDate;

    public Experience(String name, String desgn, String startDate, String endDate) {
        this.name = name;
        this.desgn = desgn;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesgn() {
        return desgn;
    }

    public void setDesgn(String desgn) {
        this.desgn = desgn;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
