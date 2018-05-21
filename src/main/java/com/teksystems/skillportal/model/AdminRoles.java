package com.teksystems.skillportal.model;

import org.springframework.data.annotation.Id;

public class AdminRoles {
    @Id
    String id;
    String userRole;

    public AdminRoles() {
    }

    public String getId() {
        return id;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
