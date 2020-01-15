package com.cdac.skillportal.repository;

import com.cdac.skillportal.model.AdminRoles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminRoleRepository extends MongoRepository<AdminRoles, String> {
    public AdminRoles findByUserRole(String userRole);

}
