package com.spotify.oauth2.api;

import com.spotify.oauth2.pojo.Playlist;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.oauth2.api.SpecBuilder.*;
import static io.restassured.RestAssured.given;

public class RestResource {





    //Searialized response is sent back
    //de-serialized body(POJO) is sent as request body for POST request
    public static Response post(String path,String token ,Object requestPlaylist) {
        return given(getRequestSpec()).
                    body(requestPlaylist).
                    header("Authorization", "Bearer " + token).
                when().
                    post(path).
                then().
                    spec(getResponseSpec()).
                extract().
                response();
    }

    public static Response postAccount(HashMap<String,String> formParams)
    {
        return given(getAccountRequestSpec()).
                formParams(formParams).
                when().post("/api/token").
                then().spec(getResponseSpec()).
                extract().
                response();
    }


    public static Response get(String path,String token) {
        return given(getRequestSpec()).
                    header("Authorization", "Bearer " + token).
                when().
                    get(path).
                then().
                    spec(getResponseSpec()).
                    assertThat().statusCode(200).
                    extract().
                    response();
    }

    public static Response update(String path,String token,Object requestPlaylist) {
        Response res = given(getRequestSpec()).
                                header("Authorization", "Bearer " + token).
                                body(requestPlaylist).
                        when().
                                put(path).
                        then().
                                spec(getResponseSpec()).
                                assertThat().
                                extract().response();
        return res;

    }
}
