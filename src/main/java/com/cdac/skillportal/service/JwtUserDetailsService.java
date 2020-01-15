package com.cdac.skillportal.service;

import com.cdac.skillportal.model.EmployeeDetails;
import com.cdac.skillportal.repository.EmployeeDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private EmployeeDetailsRepository employeeDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        EmployeeDetails employeeDetails = employeeDetailsRepository.findByusername(username);
        if (employeeDetails == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(employeeDetails.getUsername(), employeeDetails.getPassword(),
                new ArrayList<>());
    }

    public EmployeeDetails save(EmployeeDetails employeeDetails){
        employeeDetails.setPassword(bcryptEncoder.encode(employeeDetails.getPassword()));
        return employeeDetailsRepository.save(employeeDetails);
    }

    public EmployeeDetails getEmployeeDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userName = (User) authentication.getPrincipal();
        String email = userName.getUsername();
        return employeeDetailsRepository.findByusername(email);
    }


}
