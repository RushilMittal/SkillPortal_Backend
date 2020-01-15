package com.cdac.skillportal.service;

import com.cdac.skillportal.model.AdminRoles;
import com.mongodb.MongoException;
import com.cdac.skillportal.repository.AdminRoleRepository;
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
