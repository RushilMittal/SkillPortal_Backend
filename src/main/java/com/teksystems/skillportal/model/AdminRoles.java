package com.teksystems.skillportal.model;

import org.springframework.data.annotation.Id;

public class AdminRoles {
    @Id
    String id;
    String userRole;

    public AdminRoles() {
    }

    public AdminRoles(String id, String userRole) {
        this.id = id;
        this.userRole = userRole;
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

    public void setId(String id) {
        this.id = id;
    }
}
