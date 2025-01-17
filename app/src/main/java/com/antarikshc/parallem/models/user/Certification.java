package com.antarikshc.parallem.models.user;

import com.google.gson.annotations.Expose;

public class Certification {

    @Expose
    private String name;

    @Expose
    private String authority;

    public Certification(String name, String authority) {
        this.name = name;
        this.authority = authority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
