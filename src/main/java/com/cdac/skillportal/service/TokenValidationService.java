package com.cdac.skillportal.service;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.cdac.skillportal.model.EmployeeDetails;
import com.cdac.skillportal.repository.AdminRoleRepository;
import com.cdac.skillportal.repository.EmployeeDetailsRepository;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

@Service
public class TokenValidationService {
    private static Logger logger = Logger.getLogger(TokenValidationService.class);

    private final String token;
    // Header

    // Payload
    protected final String name;

    @Autowired
    AdminService adminService;

    @Autowired
    private EmployeeDetailsRepository employeeDetailsRepository;

    @Autowired
    private AdminRoleRepository adminRoleRepository;

    public TokenValidationService() {
        token = null;
        name = null;
    }

    public TokenValidationService(String token) {
        this.token = token;
        String[] parts = token.split("\\.");


        // Payload
        // reserved, public, and private claims.
        String payloadStr = new String(Base64.getUrlDecoder().decode((parts[1])));

        JSONObject payload = new JSONObject(payloadStr);

        name = payload.getString("name");
    }


    public boolean verify() {
        boolean verified;
        try {
            verified = true;

        } catch (SignatureVerificationException | TokenExpiredException e) {
            verified = false;
            logger.error(e.getMessage());
        }

        return verified;
    }

    public String extractEmployeeId(HttpServletRequest req) {
        String email = null;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User userName = (User) authentication.getPrincipal();
            email = userName.getUsername();

//            String authorizationHeader = req.getHeader("Authorization");
//            final String tokenString = authorizationHeader.substring(7); // The part after "Bearer "
//
//            String[] parts = tokenString.split("\\.");
//            // Body Part of the token
//            String bodystr = new String(Base64.getUrlDecoder().decode((parts[1])));
//            JSONObject payload = new JSONObject(bodystr);
//            email = payload.getString("preferred_username");


        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return email;
    }

    public boolean tokenValidate() {
        boolean status = false;
        try {


            String[] moddedToken = token.split("\\.");

            String decodedBody = new String(Base64.getUrlDecoder().decode((moddedToken[1])));


            JSONObject jsonBody;

            jsonBody = new JSONObject(decodedBody);

            boolean isValidBody = validateBody(jsonBody);

            if (isValidBody) {
                status = true;
            }


        } catch (Exception e) {
            logger.error(e.getMessage());
            status = false;
        }

        return status;
    }


    private static boolean validateBody(JSONObject jsonObj) {
        boolean isValid = false;
        final String aud = "edb31c7a-1273-44e8-b0d0-50830aaede35";
        final String iss = "https://login.microsoftonline.com/371cb917-b098-4303-b878-c182ec8403ac/v2.0";
        final String tid = "371cb917-b098-4303-b878-c182ec8403ac";

        long currTime = System.currentTimeMillis() / 1000;

        long exp = (jsonObj.getLong("exp"));
        if ((exp > currTime) &&
                (jsonObj.get("aud").equals(aud)) &&
                (jsonObj.get("iss").equals(iss)) &&
                (jsonObj.get("tid").equals(tid))) {
            isValid = true;
        }
        return isValid;
    }

    public boolean validateAdminRole(HttpServletRequest request, HttpServletResponse response) {


        String validateToken = null;
        try {
            validateToken = request.getHeader("Authorization");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        if (validateToken != null) {
            try {
                //fetch the employee name fetch the employee details, check the admin roles.
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                User userName = (User) authentication.getPrincipal();
                String email = userName.getUsername();
                EmployeeDetails temp = employeeDetailsRepository.findByusername(email);
                if((adminRoleRepository.findByUserRole(temp.getJobTitle())!=null) ||(adminRoleRepository.findByUserRole(temp.getMail())!=null)){
                    return true;
                }else {

                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
                }

            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return false;
    }

}