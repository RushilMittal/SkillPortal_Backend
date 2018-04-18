package com.teksystems.skillportal.controller;

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

@CrossOrigin("/**")
public class TokenFilter extends GenericFilterBean {

    private TokenValidationService tokenValidator;

    @Override
    public void doFilter(final ServletRequest req,
                         final ServletResponse res,
                         final FilterChain chain) throws IOException, ServletException {
        // HttpServletRequest request = (HttpServletRequest) req;
        // HttpServletResponse httpResponse=(HttpServletResponse) request;
//        final Enumeration<String> optionsHeader = ((HttpServletRequest) req).getHeaderNames();
//        while (optionsHeader.hasMoreElements()) {
//            String key = (String) optionsHeader.nextElement();
//            String value = ((HttpServletRequest) req).getHeader(key);
//            System.out.println(key + ":");
//            System.out.println(value + "\n");
//        }
        String acrHeader = "Test Header";
        try {

            if (!(((HttpServletRequest) req).getHeader("access-control-request-headers").toString().equals(null))) {
                acrHeader = ((HttpServletRequest) req).getHeader("access-control-request-headers");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ACR HEADER:" + acrHeader.toString());
        if (!(acrHeader.toString().equals("authorization"))) {
            final String authHeader = ((HttpServletRequest) req).getHeader("Authorization");
//        System.out.println(authHeader.toString());
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                //   throw new ServletException("Missing or invalid Authorization header.");
            }

            final String token = authHeader.substring(7); // The part after "Bearer "

            System.out.println(token);
            boolean isTokenValid = false;
            try {

                isTokenValid = TokenValidationService.tokenValidate(token);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
            System.out.println(isTokenValid);
            if (isTokenValid) {
                chain.doFilter(req, res);

            } else {
                ((HttpServletResponse) res).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Token");
            }
        } else {
            chain.doFilter(req, res);
        }


    }

}