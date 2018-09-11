package com.teksystems.skillportal.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.teksystems.skillportal.helper.ConfigurationStrings;
import com.teksystems.skillportal.helper.HttpClientHelper;
import org.apache.log4j.Logger;
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
    private static Logger logger = Logger.getLogger(TokenValidationService.class);

    private final String token;
    // Header

    private final String kid;

    // Payload
    private final String issuer;

    protected final String name;


    @Autowired
    AdminService adminService;

    public TokenValidationService() {
        token = null;
        kid = null;
        issuer = null;

        name = null;

    }

    public TokenValidationService(String token) {
        this.token = token;
        String[] parts = token.split("\\.");
        // Header Part of the token
        String headerStr = new String(Base64.getUrlDecoder().decode((parts[0])));

        JSONObject header = new JSONObject(headerStr);


        kid = header.getString("kid");


        // Payload
        // reserved, public, and private claims.
        String payloadStr = new String(Base64.getUrlDecoder().decode((parts[1])));

        JSONObject payload = new JSONObject(payloadStr);


        issuer = payload.getString("iss");

        name = payload.getString("name");


    }

    private PublicKey loadPublicKey() throws IOException, CertificateException {
        String openIdConfigurationString = readUrl("https://login.microsoftonline.com/common/v2.0/.well-known/openid-configuration");


        JSONObject openidConfig = new JSONObject(openIdConfigurationString);


        String jwksUri = openidConfig.getString("jwks_uri");


        String jwkConfigStr = readUrl(jwksUri);


        JSONObject jwkConfig = new JSONObject(jwkConfigStr);


        JSONArray keys = jwkConfig.getJSONArray("keys");

        for (int i = 0; i < keys.length(); i++) {
            JSONObject key = keys.getJSONObject(i);

            String kidString = key.getString("kid");

            String x5c = key.getJSONArray("x5c").getString(0);


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

            CertificateFactory fact = CertificateFactory.getInstance("X.509");
            InputStream stream = new ByteArrayInputStream(keyStr.getBytes(StandardCharsets.US_ASCII));
            X509Certificate cer = (X509Certificate) fact.generateCertificate(stream);


            // get public key from certification
            PublicKey publicKey = cer.getPublicKey();


            if (this.kid.equals(kidString)) {
                return publicKey;
            }
        }
        return null;

    }


    //: cache content to file to prevent access internet everytime.
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

    public boolean verify() throws IOException, CertificateException {
        boolean verified = false;

        PublicKey publicKey = loadPublicKey();


        try {

            verified = true;

        } catch (SignatureVerificationException |TokenExpiredException  e) {
            verified = false;
            logger.error(e.getMessage());
        }



        return verified;
    }

    public String ExtractEmployeeId(HttpServletRequest req) {
        String email = null;
        try {

            String authorizationHeader = ((HttpServletRequest) req).getHeader("Authorization");
            final String tokenString = authorizationHeader.substring(7); // The part after "Bearer "

            String[] parts = tokenString.split("\\.");
            // Body Part of the token
            String bodystr = new String(Base64.getUrlDecoder().decode((parts[1])));
            JSONObject payload = new JSONObject(bodystr);
            email = payload.getString("preferred_username");


        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return email;
    }

    public boolean tokenValidate() {

        try {


            String moddedToken[] = token.split("\\.");

            String decodedBody = new String(Base64.getUrlDecoder().decode((moddedToken[1])));


            JSONObject jsonBody = null;

            jsonBody = new JSONObject(decodedBody);

            boolean isValidBody = validateBody(jsonBody);

            if (isValidBody ) {
                return true;
            }

            return false;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }

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
            validateToken =  request.getHeader("Token");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        if (validateToken != null) {
            try {


                URL url = new URL("https://graph.microsoft.com/v1.0/me");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");
                conn.setRequestProperty("Authorization", "Bearer " + validateToken);
                conn.setRequestProperty("Accept", "application/json");
                String goodRespStr = HttpClientHelper.getResponseStringFromConn(conn, true);
                int responseCode = conn.getResponseCode();

                if (responseCode == 200) {
                    JSONObject responseRecieved = HttpClientHelper.processGoodRespStr(responseCode, goodRespStr);
                    boolean toReturn = (adminService.IsAdmin(
                            responseRecieved.getJSONObject(ConfigurationStrings.RESPONSEMSG).getString("jobTitle")
                    ) ||
                            adminService.IsAdmin(
                                    responseRecieved.getJSONObject(ConfigurationStrings.RESPONSEMSG).getString("mail")
                            ));
                    return toReturn;

                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return false;
    }

}