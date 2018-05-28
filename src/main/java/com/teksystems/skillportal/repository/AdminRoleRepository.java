package com.teksystems.skillportal.repository;

import com.teksystems.skillportal.model.AdminRoles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRoleRepository extends MongoRepository<AdminRoles,String>{
    public AdminRoles findByUserRole(String userRole);


}
