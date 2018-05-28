package com.teksystems.skillportal.service;

import com.teksystems.skillportal.model.AdminRoles;
import com.teksystems.skillportal.repository.AdminRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    AdminRoleRepository adminRoleRepository;

    public void addRoles(AdminRoles roleOrEmail){
        adminRoleRepository.save(roleOrEmail);
    }

    public List<AdminRoles> getAdminRoles(){

        List<AdminRoles> toReturn = adminRoleRepository.findAll();
        return toReturn;
    }
    public void deleteRole(String id){
        System.out.println(adminRoleRepository.findOne(id));
        adminRoleRepository.delete(id);
    }
}
