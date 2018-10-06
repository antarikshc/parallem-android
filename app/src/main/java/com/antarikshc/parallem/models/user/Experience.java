package com.antarikshc.parallem.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Experience {

    @Expose
    private String name;

    @Expose
    private String desgn;

    @Expose
    @SerializedName("start_date")
    private String startDate;

    @Expose
    @SerializedName("end_date")
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
