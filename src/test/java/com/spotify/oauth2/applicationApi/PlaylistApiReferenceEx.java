package com.spotify.oauth2.applicationApi;

import com.spotify.oauth2.pojo.Playlist;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.SpecBuilder.getRequestSpec;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;

public class PlaylistApiReferenceEx {


    static String access_token = "BQBqqwZbgY67jVrjyG7j0bX5Cu9R7HT-hQMwrcBz4L3G3LirV4jRSq_YlpEzK6Z6iqSOmMJPcB7apqwztb7Tzq3IptS-4snJ0zJG_6KxzKDRFy77i8p3qiChjSAH7kZ5V3wFui-sJcQSfK5lRNCkxr7G7VzU1zqx36al3srIURw11wdO8aDdGXmYC7S6CKpsPupLHGofLv-OIgLCAqdQ-dSYKffd-W7EDW15li6nJ5GwwExH-iMZY-Cz2AzyzNkEauYkFaZOyLIvcf2b";


    //Searialized response is sent back
    //de-serialized body(POJO) is sent as request body for POST request
    public static Response post(Playlist requestPlaylist) {
        return given(getRequestSpec()).
                    body(requestPlaylist).
                    header("Authorization", "Bearer " + access_token).
                when().
                    post("users/313apnwrwyjsh6xvzxlzpmd2dkj4/playlists").
                then().
                    spec(getResponseSpec()).
                extract().
                response();
    }

    /**
     * Method to create a playlist with expired token
     * @param requestPlaylist
     * @param token
     * @return
     */
    public static Response post(Playlist requestPlaylist, String token) {
        return given(getRequestSpec()).
                    body(requestPlaylist).
                    header("Authorization", "Bearer " + token).
                when().
                    post("users/313apnwrwyjsh6xvzxlzpmd2dkj4/playlists").
                then().
                    spec(getResponseSpec()).
                    extract().
                    response();
    }

    public static Response get(String playlistId) {
        return given(getRequestSpec()).
                header("Authorization", "Bearer " + access_token).
                when().get("playlists/" + playlistId).
                then().spec(getResponseSpec()).
                assertThat().statusCode(200).
                extract().
                response();
    }

    public static Response update(String playlistId, Playlist requestPlaylist) {
        Response res = given(getRequestSpec()).
                header("Authorization", "Bearer " + access_token).
                body(requestPlaylist).
                when().put("playlists/" + playlistId).
                then().spec(getResponseSpec()).
                assertThat().
                extract().response();
        return res;

    }
}
