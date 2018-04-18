package com.teksystems.skillportal.service;
import javax.servlet.http.HttpServletRequest;



import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
public class TokenValidationService {


    public static String ExtractEmployeeId(HttpServletRequest httpRequest) {

        String header=httpRequest.getHeader("Authorization");
        String token=header.substring(7);
        String moddedToken[]=token.split("\\.");
        //String decodedHeader=decode(moddedToken[0]);
        String decodedBody=decode(moddedToken[1]);
        JSONParser parser = new JSONParser();
        //JSONObject jsonHeader = null;
        JSONObject jsonBody = null;
        try {
            //jsonHeader = (JSONObject) parser.parse(decodedHeader);
            jsonBody = (JSONObject) parser.parse(decodedBody);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String empId=(String) jsonBody.get("upn");
        return empId;
    }

    public static boolean tokenValidate(String token){
        System.out.println("Inside Validate");
        try {   String token1="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IkZTaW11RnJGTm9DMHNKWEdtdjEzbk5aY2VEYyIsImtpZCI6IkZTaW11RnJGTm9DMHNKWEdtdjEzbk5aY2VEYyJ9";
            String token2="eyJhdWQiOiJlZGIzMWM3YS0xMjczLTQ0ZTgtYjBkMC01MDgzMGFhZWRlMzUiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC8zNzFjYjkxNy1iMDk4LTQzMDMtYjg3OC1jMTgyZWM4NDAzYWMvIiwiaWF0IjoxNTIyOTMxMDQxLCJuYmYiOjE1MjI5MzEwNDEsImV4cCI6MTUyMjkzNDk0MSwiYWNyIjoiMSIsImFpbyI6IkFTUUEyLzhHQUFBQTRWUnFzdzQ5VHNWaTYxNG0xSUJDSnJhVHJmN1dsdkJGTUtZekVOd1dmc009IiwiYW1yIjpbInB3ZCJdLCJhcHBpZCI6ImVkYjMxYzdhLTEyNzMtNDRlOC1iMGQwLTUwODMwYWFlZGUzNSIsImFwcGlkYWNyIjoiMSIsImZhbWlseV9uYW1lIjoiU2hla2hhd2F0IiwiZ2l2ZW5fbmFtZSI6IkFiaGlzaGVrIiwiaXBhZGRyIjoiMTE1LjExMC4xOTIuMTQ2IiwibmFtZSI6IlNoZWtoYXdhdCwgQWJoaXNoZWsiLCJvaWQiOiJiMTA2MzAxZi1jOGM1LTQ1NGYtOTE3ZS1lMDQ2NjQ0ODI1NDMiLCJvbnByZW1fc2lkIjoiUy0xLTUtMjEtMjA3NjU5NzQ5Ni0xNTYzMjYxOTQ0LTEyNTY0MTAwNjEtNDM4MDY3Iiwic2NwIjoiVXNlci5SZWFkIiwic3ViIjoiTHBfTUZ6UndVcnJDc1pTMHVZRnpGWVdBa2lHZHdqV3lnYjFwSlVHTDBaVSIsInRpZCI6IjM3MWNiOTE3LWIwOTgtNDMwMy1iODc4LWMxODJlYzg0MDNhYyIsInVuaXF1ZV9uYW1lIjoiYXNoZWtoYXdhdEB0ZWtzeXN0ZW1zLmNvbSIsInVwbiI6ImFzaGVraGF3YXRAdGVrc3lzdGVtcy5jb20iLCJ1dGkiOiJLREl5cXBnNFdVeS01VjU3SlJzT0FBIiwidmVyIjoiMS4wIn0";
            String tokenExample="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IkZTaW11RnJGTm9DMHNKWEdtdjEzbk5aY2VEYyIsImtpZCI6IkZTaW11RnJGTm9DMHNKWEdtdjEzbk5aY2VEYyJ9.eyJhdWQiOiJlZGIzMWM3YS0xMjczLTQ0ZTgtYjBkMC01MDgzMGFhZWRlMzUiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC8zNzFjYjkxNy1iMDk4LTQzMDMtYjg3OC1jMTgyZWM4NDAzYWMvIiwiaWF0IjoxNTIyOTMxMDQxLCJuYmYiOjE1MjI5MzEwNDEsImV4cCI6MTUyMjkzNDk0MSwiYWNyIjoiMSIsImFpbyI6IkFTUUEyLzhHQUFBQTRWUnFzdzQ5VHNWaTYxNG0xSUJDSnJhVHJmN1dsdkJGTUtZekVOd1dmc009IiwiYW1yIjpbInB3ZCJdLCJhcHBpZCI6ImVkYjMxYzdhLTEyNzMtNDRlOC1iMGQwLTUwODMwYWFlZGUzNSIsImFwcGlkYWNyIjoiMSIsImZhbWlseV9uYW1lIjoiU2hla2hhd2F0IiwiZ2l2ZW5fbmFtZSI6IkFiaGlzaGVrIiwiaXBhZGRyIjoiMTE1LjExMC4xOTIuMTQ2IiwibmFtZSI6IlNoZWtoYXdhdCwgQWJoaXNoZWsiLCJvaWQiOiJiMTA2MzAxZi1jOGM1LTQ1NGYtOTE3ZS1lMDQ2NjQ0ODI1NDMiLCJvbnByZW1fc2lkIjoiUy0xLTUtMjEtMjA3NjU5NzQ5Ni0xNTYzMjYxOTQ0LTEyNTY0MTAwNjEtNDM4MDY3Iiwic2NwIjoiVXNlci5SZWFkIiwic3ViIjoiTHBfTUZ6UndVcnJDc1pTMHVZRnpGWVdBa2lHZHdqV3lnYjFwSlVHTDBaVSIsInRpZCI6IjM3MWNiOTE3LWIwOTgtNDMwMy1iODc4LWMxODJlYzg0MDNhYyIsInVuaXF1ZV9uYW1lIjoiYXNoZWtoYXdhdEB0ZWtzeXN0ZW1zLmNvbSIsInVwbiI6ImFzaGVraGF3YXRAdGVrc3lzdGVtcy5jb20iLCJ1dGkiOiJLREl5cXBnNFdVeS01VjU3SlJzT0FBIiwidmVyIjoiMS4wIn0.VLCHvaGBWdAOeKHOhhxt7XmbtN_H1lE3AZiUeULyLmtO1snm3BGBfvWka002ER5fIF3MYz9Ft6j65nbcgIb5-ce3nkhkg81_z1SUOrPmTyfLtws_SHrMkaHIO198EA3X-6-iXJPoUnov6kElBWX7Ps5mJ57OH4Y9nP3fg5G_xOOPNYoAAfqD9RbA4zLV3LA3DwdzOFroR2ebIm4PB3d9dfcedFndOZ0yDNRvsjhhG9Z3mW9oWG_TXyE4fXR10Trc8LwBIqHFCO3SOv8ll_X06XLicIFqqcg1yXC9vvHCoCLAnJHb9REE7SnG78zoJs2p4Cp4QsWgPyiDRGTvk0UqTQ";
            String moddedToken[]=token.split("\\.");
        /*
        for (String temp: moddedToken){
              System.out.println(temp);
           }
        */
            // tokenValidator("");
            String decodedHeader=decode(moddedToken[0]);
            String decodedBody=decode(moddedToken[1]);
            JSONParser parser = new JSONParser();
            JSONObject jsonHeader = null;
            JSONObject jsonBody = null;

            jsonHeader = (JSONObject) parser.parse(decodedHeader);
            jsonBody = (JSONObject) parser.parse(decodedBody);

            boolean isValidHeader=validateHeader(jsonHeader);
            boolean isValidBody=validateBody(jsonBody);
            System.out.println("Header Valid:"+isValidHeader);
            System.out.println("Body Valid:"+isValidBody);
            if(isValidHeader==true)
                if(isValidBody==true) {
                    return true;
                }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    public static String decode(String s) {
        return StringUtils.newStringUtf8(Base64.decodeBase64(s));
    }

    public static boolean validateHeader(JSONObject jsonObj) {
        boolean isValid=false;
        final String typ="JWT";
        final String alg="RS256";
        final String x5t="FSimuFrFNoC0sJXGmv13nNZceDc";
        final String kid="FSimuFrFNoC0sJXGmv13nNZceDc";
        if(jsonObj.get("typ").equals(typ))
        {System.out.println("typ Valid");
            if(jsonObj.get("alg").equals(alg))
            {System.out.println("alg Valid");
                if(jsonObj.get("x5t").equals(x5t))
                {System.out.println("x5t Valid");
                    if(jsonObj.get("kid").equals(kid))
                    {System.out.println("kid Valid");
                        isValid=true;}}}}
        return isValid;
    }

    public static boolean validateBody(JSONObject jsonObj) {
        boolean isValid=false;
        final String aud="edb31c7a-1273-44e8-b0d0-50830aaede35";
        final String iss="https://sts.windows.net/371cb917-b098-4303-b878-c182ec8403ac/";
        final String appid="edb31c7a-1273-44e8-b0d0-50830aaede35";
        final String sub="Lp_MFzRwUrrCsZS0uYFzFYWAkiGdwjWygb1pJUGL0ZU";
        long currTime = System.currentTimeMillis()/1000;
        long exp= (Long) jsonObj.get("exp");
        if(jsonObj.get("aud").equals(aud))
        {System.out.println("aud Valid");
            if(jsonObj.get("iss").equals(iss))
            {	System.out.println("iss Valid");
                // if(jsonObj.get("appid").equals(appid))
                //{   	System.out.println("appid Valid");
               // if(jsonObj.get("sub").equals(sub))
              //  {   	System.out.println("sub Valid");
                    if(exp>currTime)
                    {   	System.out.println("exp Valid");
                        isValid=true;}}}
        return isValid;


    }
}