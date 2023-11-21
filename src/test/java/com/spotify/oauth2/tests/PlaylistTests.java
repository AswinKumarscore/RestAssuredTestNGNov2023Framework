package com.spotify.oauth2.tests;
import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;
//import com.sun.source.tree.AssertTree;
import groovy.util.logging.Log;
import io.qameta.allure.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.spotify.oauth2.api.SpecBuilder.getRequestSpec;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;



@Epic("Spotify Oauth 2.0")
@Feature("Playlist API")
public class PlaylistTests extends  BaseTest{
//    public class PlaylistTests {


    public Playlist playlistBuilder(String name,String description,boolean _public)
    {
        return new  Playlist().
            setName(name).
            setDescription(description).
            setPublic(_public);


    }



    //Request and Response specification which is generic can put in RequestSpecbuilder
    //and responsespecbuilder


    @Story("Create a playlist story")
    @Description("USER MUST BE ABLE TO CREATE A PLAYLIST")
    @Link("https://example.org")
    @Test(description = "should be able to create a playlist")
    public void shouldBeAbleToCreateAPlaylist() {
        Playlist requestPlaylist = playlistBuilder("Nov18 Playlist","This new Playlist was created on Nov18,2023",false);


        Response response = PlaylistApi.post(requestPlaylist);
        //assertThat(response.statusCode(), equalTo(201));
        assertThat(response.statusCode(),equalTo(StatusCode.CODE_201.getCode()));
        Playlist responsePlaylist = response.as(Playlist.class);

//Asserting using java object
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));


    }


    @Story("Get a playlist story")
    @Description("User must be able to fetch a playlist")
    @Link("https://example.org")
    @Test(description = "should be able to GET a playlist")
    public void shouldBeAbleToGetAPlaylist() {
        Playlist expectedPlaylist = playlistBuilder("Nov8 Playlist-PUT2 on Nov11","This new Playlist was created on Nov11,2023,UPDATE REQUEST 3 OR PUT REQUEST3",false);



        Response response = PlaylistApi.get(DataLoader.getInstance().getGetPlaylistId());
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_200.getCode()));
        Playlist responsePlaylist = response.as(Playlist.class);


        assertThat(responsePlaylist.getName(), equalTo(expectedPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(expectedPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(expectedPlaylist.getPublic()));

    }


    @Story("Update a playlist story")
    @Link("https://example.org")
    @Description("User must be able to update a playlist")
    @Test(description = "should be able to UPDATE a playlist")
    public void shouldBeAbleToUpdateAPlaylist() {
        Playlist updatePlaylistData = new Playlist().
                setName("Nov8 Playlist-PUT2 on Nov11").
                setDescription("This new Playlist was created on Nov11,2023,UPDATE REQUEST 3 OR PUT REQUEST3").
                setPublic(false);

        Response response = PlaylistApi.update(DataLoader.getInstance().getUpdatePlaylistId(), updatePlaylistData);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_200.getCode()));


    }


    @Story("Create a playlist story")
    @Link("https://example.org")
    @Description("User must not be able to create a playlist without Name")
    @Test(description = "should NOT  be able to create a playlist WITHOUT NAME")
    public void shouldNotBeAbleToCreateAPlaylistWithoutName() {
        Playlist requestPlaylist = new Playlist().
                setName("").
                setDescription("This new Playlist was created on Nov10,2023").
                setPublic(false);

        Response response = PlaylistApi.post(requestPlaylist);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_400.getCode()));

        Error error = response.as(Error.class);


//        Error error = given(getRequestSpec()).
//                body(requestPlaylist).
//                when().post("users/313apnwrwyjsh6xvzxlzpmd2dkj4/playlists").
//                then().spec(getResponseSpec()).
//                assertThat().statusCode(400).
//                extract().as(Error.class);

        assertThat(error.getError().getStatus(), equalTo(400));
        assertThat(error.getError().getMessage(), equalTo("Missing required field: name"));
        assertThat(error.getError().getMessage(), equalTo(StatusCode.CODE_400.getMsg()));


    }


    @Story("Create a playlist story")
    @Description("User should not be able to create a playlist with expired token")
    @Link("https://example.org")
    @Test(description = "should NOT BE  be able to create a playlist WITH EXPIRED TOKEN")
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
        assertThat(error.getError().getMessage(), equalTo(StatusCode.CODE_401.getMsg()));


        StatusCode.CODE_401.getMsg();

        //StatusCode.CODE_401;

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
