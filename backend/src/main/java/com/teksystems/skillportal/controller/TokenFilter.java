package com.teksystems.skillportal.controller;

import com.teksystems.skillportal.service.RequestWrapper;
import com.teksystems.skillportal.service.TokenValidationService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

@CrossOrigin("/**")
public class TokenFilter extends GenericFilterBean {

    private TokenValidationService tokenValidator;

    @Override
    public void doFilter(final ServletRequest req,
                         final ServletResponse res,
                         final FilterChain chain) throws IOException, ServletException , NullPointerException{


            logger.info("Inside the doFilter");
            String typeOfRequest = ((HttpServletRequest) req).getMethod();
            System.out.println(typeOfRequest);

            if (typeOfRequest.equals("OPTIONS")) {
                logger.info("options received");
                chain.doFilter(req, res);
            } else {
                try {
                    String authorizationHeader = ((HttpServletRequest) req).getHeader("Authorization");
                    final String token = authorizationHeader.substring(7); // The part after "Bearer "
                    System.out.println(token);
                    boolean isTokenValid = false;
                    try{
                        isTokenValid = TokenValidationService.tokenValidate(token);////                    } catch (Exception e) {


                        if (isTokenValid) {
                            Map<String, String[]> extraParams = new TreeMap<String, String[]>();
                            System.out.println("Before:" + ((HttpServletRequest) req).getHeader("Content-Type"));
                            String[] contentType = {"application/json"};
                            extraParams.put("Content-Type", contentType);

                            HttpServletRequest modifiedRequest = new RequestWrapper((HttpServletRequest) req, extraParams);

                            System.out.println("modified:" + ((HttpServletRequest) req).getHeader("Content-Type"));
                            chain.doFilter(req, res);
                        } else {
                            logger.info("Invalid Authorization,Unable to Validate Authorization");
                            ((HttpServletResponse) res).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Token");
                        }

                    } catch (Exception e) {
                        logger.info("Authorization Header Not Present");
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    logger.info("Some Error Occured in Request");
                    e.printStackTrace();
                }


            }
    }
}