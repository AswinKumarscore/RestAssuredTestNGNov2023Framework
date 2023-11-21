package com.spotify.oauth2.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;

import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;

public class TokenManagerOldClass {

    private static String access_token;
    private static Instant expiry_time;


    //logic to check expiry time of the token
    public static  String getToken()
    {

        System.out.println("ACCESS_TOKEN VALUE : "+access_token);

        try
        {
            if(access_token == null || Instant.now().isAfter(expiry_time))
            {
                System.out.println("Renewing token .... ");
                Response response = renewToken();
                access_token = response.path("access_token");
                int expiryDurationInSeconds = response.path("expires_in");
                expiry_time = Instant.now().plusSeconds(expiryDurationInSeconds - 300);

            }
            else
            {
                System.out.println("Token is good to use");
            }

        }
        catch (Exception e)
        {
            throw new RuntimeException("ABORT!!! Failed to get token");

        }
        return  access_token;


    }



    private static Response renewToken()
    {

        HashMap<String,String> formParams = new HashMap<String,String>();
        formParams.put("client_id","a38f7a9d38264821b7265b807ed6e85e");
        formParams.put("client_secret","01f3a19647254c64a0d460e8467c50da");
        formParams.put("refresh_token","AQAqxx3K-GOxTb2Fhw9G7Q79dyALTLUIEoq80YOVYopJm1QalbBc0r402tPUYCv-Z1EABfoHtTFQ4uK_qkrYUyPfdjbvwTrSI3sx80Z67I0iIdB-St5JvuecFAC2yk__5io");
        formParams.put("grant_type","refresh_token");

                Response response = given().
                        baseUri("https://accounts.spotify.com").
                        contentType(ContentType.URLENC).
                        formParams(formParams).
                when().post("/api/token").
                        then().spec(getResponseSpec()).extract().response();

                if(response.statusCode()!=200)
                {
                    throw new RuntimeException("ABORT!!! Renew Token Failed");
                }
                access_token = response.path("access_token");

//                return response.path("access_token");
                        return response;


    }
}
