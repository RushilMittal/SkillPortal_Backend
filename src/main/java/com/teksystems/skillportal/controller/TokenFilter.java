package com.teksystems.skillportal.controller;


import com.teksystems.skillportal.helper.ConfigurationStrings;
import com.teksystems.skillportal.service.TokenValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.apache.log4j.Logger;


@CrossOrigin("/**")
public class TokenFilter extends GenericFilterBean {
    @Autowired
    private TokenValidationService tokenValidator;
    private static Logger logger = Logger.getLogger(TokenFilter.class);
    @Override
    public void doFilter(final ServletRequest req,
                         final ServletResponse res,
                         final FilterChain chain) throws IOException, ServletException{
        try{
            logger.info("Inside the doFilter");
            String typeOfRequest = ((HttpServletRequest) req).getMethod();
            System.out.println(typeOfRequest);
            if(typeOfRequest.equals("OPTIONS")){
                chain.doFilter(req,res);
            }else{
               try {
                    String authorizationHeader = ((HttpServletRequest) req).getHeader(ConfigurationStrings.AUTHORIZATION);
                    final String token = authorizationHeader.substring(7); // The part after "Bearer "
                    System.out.println(token);
                    boolean isTokenValid = false;
                    try {
                        tokenValidator = new TokenValidationService(token);
                        isTokenValid = tokenValidator.tokenValidate();
                        System.out.println("isvalidtoken" + isTokenValid);

                    }catch(Exception e){
                        logger.info("Invalid Authorization,Unable to Validate Authorization");
                        logger.error(e.getMessage());
                    }

                    if (isTokenValid) {
//                       Map<String,String[]> extraParams = new TreeMap<String, String[]>();
//                       extraParams.put("Content-Type", new String[]{"application/json"});
                       // HttpServletRequest modifiedRequest = new RequestWrapper((HttpServletRequest)req,extraParams);

                        System.out.println("isvalid called");
                        chain.doFilter(req, res);

                    } else {
                        ((HttpServletResponse) res).sendError(HttpServletResponse.SC_UNAUTHORIZED, ConfigurationStrings.INVALIDTOKEN);
                    }

                }catch (Exception e){
                    logger.info("Authorization Header Not Present");
                   logger.error(e.getMessage());
                }
            }

        }catch(Exception e) {
            logger.info("Some Error Occurred in Request");
            logger.error(e.getMessage());
        }
    }

}