package com.teksystems.skillportal.service;

import com.mongodb.MongoException;
import com.teksystems.skillportal.model.AdminRoles;
import com.teksystems.skillportal.repository.AdminRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminRoleService {
    @Autowired
    AdminRoleRepository adminRoleRepository;

    //method to validate whether the role is present or not in the list
    public boolean IsAdmin(String role) throws MongoException {
        AdminRoles adminRoles = adminRoleRepository.findByUserRole(role);
        if(!((adminRoles == null) || (adminRoles.getUserRole().isEmpty())))
            return true;
        return false;
    }
    public void addRoles(){
        AdminRoles admin = new AdminRoles();
        admin.setUserRole("HR");
        adminRoleRepository.save(admin);
    }
}

