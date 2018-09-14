package com.teksystems.skillportal.controller;


import com.teksystems.skillportal.helper.ConfigurationStrings;
import com.teksystems.skillportal.service.TokenValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


@CrossOrigin("/**")
public class TokenFilter extends GenericFilterBean {
    @Autowired
    private TokenValidationService tokenValidator;

    private static Logger logger = Logger.getLogger(TokenFilter.class);

    @Override
    public void doFilter(final ServletRequest req,
                         final ServletResponse res,
                         final FilterChain chain) {
        try {
            logger.info("Inside the doFilter");
            String typeOfRequest = ((HttpServletRequest) req).getMethod();

            if (typeOfRequest.equals("OPTIONS")) {
                chain.doFilter(req, res);
            } else {
                String authorizationHeader = ((HttpServletRequest) req).getHeader(ConfigurationStrings.AUTHORIZATION);
                final String token = authorizationHeader.substring(7); // The part after "Bearer "

                boolean isTokenValid;
                tokenValidator = new TokenValidationService(token);
                isTokenValid = tokenValidator.tokenValidate();
                if (isTokenValid) {
                    chain.doFilter(req, res);
                } else {
                    ((HttpServletResponse) res).sendError(HttpServletResponse.SC_UNAUTHORIZED, ConfigurationStrings.INVALIDTOKEN);
                }
            }
        } catch (NullPointerException e) {
            logger.info("Authorization not present");
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.info("Some Error Occurred in Request");
            logger.error(e.getMessage());
        }
    }

}