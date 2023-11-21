package com.spotify.oauth2.tests;

import com.spotify.oauth2.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTestsOldClass {


    String access_token = "BQBqqwZbgY67jVrjyG7j0bX5Cu9R7HT-hQMwrcBz4L3G3LirV4jRSq_YlpEzK6Z6iqSOmMJPcB7apqwztb7Tzq3IptS-4snJ0zJG_6KxzKDRFy77i8p3qiChjSAH7kZ5V3wFui-sJcQSfK5lRNCkxr7G7VzU1zqx36al3srIURw11wdO8aDdGXmYC7S6CKpsPupLHGofLv-OIgLCAqdQ-dSYKffd-W7EDW15li6nJ5GwwExH-iMZY-Cz2AzyzNkEauYkFaZOyLIvcf2b";
    //Request and Response specification which is generic can put in RequestSpecbuilder
    //and responsespecbuilder


    @Test
    public void shouldBeAbleToCreateAPlaylist() {
        Playlist requestPlaylist = new Playlist().
                setName("Nov8 Playlist").
                setDescription("This new Playlist was created on Nov10,2023").
                setPublic(false);

        Response response = PlaylistApi.post(requestPlaylist);
        assertThat(response.statusCode(), equalTo(201));
        Playlist responsePlaylist = response.as(Playlist.class);

//Asserting using java object
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));


    }


    @Test
    public void shouldBeAbleToGetAPlaylist() {

        Playlist expectedPlaylist = new Playlist().
                setName("Nov8 Playlist-PUT2 on Nov11").
                setDescription("This new Playlist was created on Nov11,2023,UPDATE REQUEST 3 OR PUT REQUEST3").
                setPublic(false);

        Response response = PlaylistApi.get(DataLoader.getInstance().getGetPlaylistId());
        assertThat(response.statusCode(), equalTo(200));
        Playlist responsePlaylist = response.as(Playlist.class);


        assertThat(responsePlaylist.getName(), equalTo(expectedPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(expectedPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(expectedPlaylist.getPublic()));

    }


    @Test
    public void shouldBeAbleToUpdateAPlaylist() {
        Playlist updatePlaylistData = new Playlist().
                setName("Nov8 Playlist-PUT2 on Nov11").
                setDescription("This new Playlist was created on Nov11,2023,UPDATE REQUEST 3 OR PUT REQUEST3").
                setPublic(false);

        Response response = PlaylistApi.update(DataLoader.getInstance().getUpdatePlaylistId(), updatePlaylistData);
        assertThat(response.statusCode(), equalTo(200));


    }


    @Test
    public void shouldNotBeAbleToCreateAPlaylistWithoutName() {
        Playlist requestPlaylist = new Playlist().
                setName("").
                setDescription("This new Playlist was created on Nov10,2023").
                setPublic(false);

        Response response = PlaylistApi.post(requestPlaylist);
        assertThat(response.statusCode(), equalTo(400));

        Error error = response.as(Error.class);


//        Error error = given(getRequestSpec()).
//                body(requestPlaylist).
//                when().post("users/313apnwrwyjsh6xvzxlzpmd2dkj4/playlists").
//                then().spec(getResponseSpec()).
//                assertThat().statusCode(400).
//                extract().as(Error.class);

        assertThat(error.getError().getStatus(), equalTo(400));
        assertThat(error.getError().getMessage(), equalTo("Missing required field: name"));


    }


    @Test
    public void shouldNotBeAbleToCreateAPlaylistWithExpiredToken() {
        String invalid_token = "A1Q24563";
        Playlist requestPlaylist = new Playlist()
                .setName("Nov8 Playlist : 7pm ")
                .setDescription("This new Playlist was created on Nov10,2023 7pm ")
                .setPublic(false);


        Response response = PlaylistApi.post(requestPlaylist, invalid_token);
        assertThat(response.statusCode(), equalTo(401));

        Error error = response.as(Error.class);
        assertThat(error.getError().getStatus(), equalTo(401));
        assertThat(error.getError().getMessage(), equalTo("Invalid access token"));


//     Error error =     given().
//                baseUri("https://api.spotify.com/").
//                basePath("v1/").
//                header("Authorization","Bearer "+"A23456").
//                contentType(ContentType.JSON).
//                log().all().
//                body(requestPlaylist).
//                when().post("users/313apnwrwyjsh6xvzxlzpmd2dkj4/playlists").
//                then().spec(getResponseSpec()).
//                assertThat().statusCode(401).extract().as(Error.class);


    }
}
