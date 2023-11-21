package com.spotify.oauth2.applicationApi;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.api.TokenManager;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.ConfigLoader;
import io.restassured.response.Response;



import static com.spotify.oauth2.api.Route.PLAYLISTS;
import static com.spotify.oauth2.api.Route.USERS;
import static com.spotify.oauth2.api.SpecBuilder.getRequestSpec;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;

public class PlaylistApi {
    //static String access_token = "BQC3mkg5qHbZCZmMx_I3LsgLeysEfkeyYie_sTuxnI4OVBrmmWr713c6T0EXdnMrrPzc6cUYtHY6wu1S3FUszEe9nFNfagW8J70fBQQWMyt5SdWJCgL6KYCaQYudhvMFDggo3rRU2o3UZB2WUugPhP-Bok5RSBKvWRnxeBQWAZmAGtN7tPOPquab9XW3yG1cxHCJy6VzBYlEjgqMscoywbABrjGYd7BiTnisYm_RugeZ6JfntLi8C0iYA3jp2i3NMDsimEJkt0sme9QL";
    //Searialized response is sent back
    //de-serialized body(POJO) is sent as request body for POST request
    public static Response post(Playlist requestPlaylist) {
        //return RestResource.post("users/313apnwrwyjsh6xvzxlzpmd2dkj4/playlists", TokenManager.getToken(),requestPlaylist);
        return RestResource.post("users/"+ConfigLoader.getInstance().getUser()+"/playlists", TokenManager.getToken(),requestPlaylist);
    }
    /**
     * Method to create a playlist with expired token
     * @param requestPlaylist
     * @param token
     * @return
     */
    public static Response post(Playlist requestPlaylist, String token) {
        return  RestResource.post(USERS+"/"+ ConfigLoader.getInstance().getUser() +PLAYLISTS,token,requestPlaylist);
    }
    public static Response get(String playlistId) {
        return RestResource.get("playlists/" + playlistId,TokenManager.getToken());
    }
    public static Response update(String playlistId, Playlist requestPlaylist) {
        return  RestResource.update("playlists/" + playlistId,TokenManager.getToken(),requestPlaylist);
    }
}
