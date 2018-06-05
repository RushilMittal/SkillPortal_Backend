package com.teksystems.skillportal.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.teksystems.skillportal.helper.HttpClientHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAKey;
import java.util.Base64;

@Service
public class TokenValidationService {

    private final String token;
    // Header

    private final String kid;

    // Payload
    private final String issuer;
    private final String aud;
    protected final String name;
    private final String uniqueName;

    @Autowired
    AdminService adminService;

    public TokenValidationService() {
        token = null;
        kid = null;
        issuer = null;
        aud = null;
        name = null;
        uniqueName = null;
    }

    public TokenValidationService(String token){
        this.token = token;
        String[] parts = token.split("\\.");
        // Header Part of the token
        String headerStr = new String(Base64.getUrlDecoder().decode((parts[0])));

        JSONObject header = new JSONObject(headerStr);
        // System.out.println("typ = " + header.getString("typ"));
        // System.out.println("alg = " + header.getString("alg"));
        // System.out.println("kid = " + header.getString("kid"));
        // System.out.println("---------------------------------");


        kid = header.getString("kid");


        // Payload
        // reserved, public, and private claims.
        String payloadStr = new String(Base64.getUrlDecoder().decode((parts[1])));

        JSONObject payload = new JSONObject(payloadStr);
        // System.out.println("aud = " + payload.getString("aud"));
        // System.out.println("iss = " + payload.getString("iss"));

        // System.out.println("name = " + payload.getString("name"));
        // System.out.println("unique_name = " + payload.getString("preferred_username"));

        // System.out.println("---------------------------------");

        issuer = payload.getString("iss");
        aud = payload.getString("aud");
        name = payload.getString("name");

        uniqueName = payload.getString("preferred_username");


    }
    private PublicKey loadPublicKey() throws IOException, CertificateException {
        String openIdConfigurationString = readUrl("https://login.microsoftonline.com/common/v2.0/.well-known/openid-configuration");

        // System.out.println(openIdConfigurationString);

        JSONObject openidConfig = new JSONObject(openIdConfigurationString);

        // System.out.println("---------------------------------");

        String jwksUri = openidConfig.getString("jwks_uri");

        // System.out.println(jwksUri);
        // System.out.println("---------------------------------");

        String jwkConfigStr = readUrl(jwksUri);

        // System.out.println(jwkConfigStr);

        JSONObject jwkConfig = new JSONObject(jwkConfigStr);
        // System.out.println("---------------------------------");

        JSONArray keys = jwkConfig.getJSONArray("keys");

        for (int i = 0; i < keys.length(); i++) {
            JSONObject key = keys.getJSONObject(i);

            String kid = key.getString("kid");
            String x5t = key.getString("x5t");
            String n = key.getString("n");
            String e = key.getString("e");
            String x5c = key.getJSONArray("x5c").getString(0);

            // System.out.println("kid: " + kid);
            // System.out.println("x5t: " + x5t);
            // System.out.println("n: " + n);
            // System.out.println("e: " + e);
            // System.out.println("x5c: " + x5c);

            String keyStr = "-----BEGIN CERTIFICATE-----\r\n";
            String tmp = x5c;
            while (tmp.length() > 0) {
                if (tmp.length() > 64) {
                    String x = tmp.substring(0, 64);
                    keyStr += x + "\r\n";
                    tmp = tmp.substring(64);
                } else {
                    keyStr += tmp + "\r\n";
                    tmp = "";
                }
            }
            keyStr += "-----END CERTIFICATE-----\r\n";
            // System.out.println(keyStr);
            CertificateFactory fact = CertificateFactory.getInstance("X.509");
            InputStream stream = new ByteArrayInputStream(keyStr.getBytes(StandardCharsets.US_ASCII));
            X509Certificate cer = (X509Certificate) fact.generateCertificate(stream);
            // System.out.println(cer);

            // get public key from certification
            PublicKey publicKey = cer.getPublicKey();
            // System.out.println(publicKey);

            if (this.kid.equals(kid)) {
                return publicKey;
            }
        }
        return null;

    }


    //TODO: cache content to file to prevent access internet everytime.
    private String readUrl(String url) throws IOException {
        URL addr = new URL(url);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(addr.openStream()))) {
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
        }
        return sb.toString();
    }

    public boolean verify() throws IOException, CertificateException{
        boolean verified = false;

        PublicKey publicKey = loadPublicKey();

        JWTVerifier verifier = JWT.require(Algorithm.RSA256((RSAKey) publicKey)).withIssuer(issuer).build();
        try {
            DecodedJWT jwt = verifier.verify(token);
            verified = true;
            // System.out.println("Verified");
        }catch(SignatureVerificationException e) {
            verified = false;
            e.printStackTrace();
        }catch(TokenExpiredException e){
            verified = false;
            e.printStackTrace();
        }catch(Exception e){
            verified = false;
            e.printStackTrace();
        }


        return verified;
    }

    public String ExtractEmployeeId(HttpServletRequest req) {
        String email =null;
        try {

            String authorizationHeader = ((HttpServletRequest) req).getHeader("Authorization");
            final String token = authorizationHeader.substring(7); // The part after "Bearer "

            String[] parts = token.split("\\.");
            // Body Part of the token
            String bodystr = new String(Base64.getUrlDecoder().decode((parts[1])));
            JSONObject payload = new JSONObject(bodystr);
            email = payload.getString("preferred_username");


        }catch(Exception e){
            e.printStackTrace();
        }
        return email;
    }

    public boolean tokenValidate(){
        // // System.out.println("Inside Validate");
        try {
//            boolean a = verify();
            boolean a = true;
            if(a) {
                String moddedToken[] = token.split("\\.");
                String decodedHeader = new String(Base64.getUrlDecoder().decode((moddedToken[0])));
                String decodedBody = new String(Base64.getUrlDecoder().decode((moddedToken[1])));

                JSONObject jsonHeader = null;
                JSONObject jsonBody = null;
                jsonHeader = new JSONObject(decodedHeader);
                jsonBody = new JSONObject(decodedBody);
//                boolean isValidHeader = validateHeader(jsonHeader);
                boolean isValidBody = validateBody(jsonBody);
//                // System.out.println("Header Valid:" + isValidHeader);
                // System.out.println("Body Valid:" + isValidBody);
                if (isValidBody == true) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    // Cannot be used as the algorithm and the encryption type are subject to be changed
    private static boolean validateHeader(JSONObject jsonObj) {
        boolean isValid=false;
        final String typ="JWT";
        final String alg="RS256";

        if(jsonObj.get("typ").equals(typ))
        {   // System.out.println("typ Valid");
            if(jsonObj.get("alg").equals(alg))
            {
                // System.out.println("alg Valid");
            }
        }
        return isValid;
    }

    private static boolean validateBody(JSONObject jsonObj) {
        boolean isValid=false;
        final String aud="edb31c7a-1273-44e8-b0d0-50830aaede35";
        final String iss="https://login.microsoftonline.com/371cb917-b098-4303-b878-c182ec8403ac/v2.0";
        final String tid = "371cb917-b098-4303-b878-c182ec8403ac";

        long currTime = System.currentTimeMillis()/1000;

        long exp= ( jsonObj.getLong("exp"));
        if(exp>currTime) {
            if (jsonObj.get("aud").equals(aud)) {
                // System.out.println("aud Valid");
                if (jsonObj.get("iss").equals(iss)) {
                    // System.out.println("iss Valid");
                    if (jsonObj.get("tid").equals(tid))
                        // System.out.println("exp Valid");
                        isValid = true;
                }
            }
        }
        return isValid;
    }

    public boolean validateAdminRole(HttpServletRequest request, HttpServletResponse response){
        System.out.println("inside the validateAdminrole");
        boolean isAdmin = false;
        String token = null;
        try{
            token =((HttpServletRequest)request).getHeader("Token");
        }catch(Exception e){
            //token not present in the admin call, fake call;
            e.printStackTrace();
        }
        System.out.println("token in admin"+token);
        if(token!=null) {
            try {

                System.out.println("token is" + token);
                URL url = new URL("https://graph.microsoft.com/v1.0/me");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");
                conn.setRequestProperty("Authorization", "Bearer " + token);
                conn.setRequestProperty("Accept", "application/json");
                String goodRespStr = HttpClientHelper.getResponseStringFromConn(conn, true);
                int responseCode = conn.getResponseCode();
                System.out.println(responseCode);
                if (responseCode == 200) {
                    JSONObject responseRecieved = HttpClientHelper.processGoodRespStr(responseCode, goodRespStr);
                    boolean toReturn = (adminService.IsAdmin(
                            responseRecieved.getJSONObject("responseMsg").getString("jobTitle")
                    ) ||
                            adminService.IsAdmin(
                                    responseRecieved.getJSONObject("responseMsg").getString("mail")
                            ));
                    return toReturn;

                } else {
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}