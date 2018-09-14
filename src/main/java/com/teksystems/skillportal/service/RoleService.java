package com.teksystems.skillportal.service;

import com.mongodb.MongoException;
import com.teksystems.skillportal.model.AdminRoles;
import com.teksystems.skillportal.repository.AdminRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    AdminRoleRepository adminRoleRepository;

    public void addRoles(AdminRoles roleOrEmail) throws MongoException {
        adminRoleRepository.save(roleOrEmail);
    }

    public List<AdminRoles> getAdminRoles() throws MongoException {

        return adminRoleRepository.findAll();

    }

    public void deleteRole(String id) throws MongoException {

        adminRoleRepository.delete(id);
    }
}
