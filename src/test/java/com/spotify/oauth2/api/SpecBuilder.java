package com.spotify.oauth2.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static com.spotify.oauth2.api.Route.BASE_PATH;

public class SpecBuilder {

    static String access_token = "BQBxxZ8skZjudtsBiQ2LILT5Xi1AQwvm-q3GzrtyJh5A3vXd9gSb2csc_IcWRzfssDmW64nVzTbNn5Di6bcxxbyyzJ-kPAPDrRf-RJIYaDe7FwAYhNvLgNf5sWhyMVv0t9qfsixaYZ5NkrhqDUL-VnZ-e7qR-P4Hpq2tJuJSBoOp8xT4bc0kt9ZZF8SsmaD5AN7FDsyZgoWoDJUPkTRtIUv1TBZJvnuF4kWfTclifJ-pNo1obKHRizo8SB6RhrEvz7mVsPsCgOYqYFgz";

    public static RequestSpecification getRequestSpec()
    {
        return  new RequestSpecBuilder().
                setBaseUri(System.getProperty("BASE_URI")).
//                setBaseUri("https://api.spotify.com/").
                setBasePath(BASE_PATH).
                //addHeader("Authorization","Bearer "+access_token)
                setContentType(ContentType.JSON).
                log(LogDetail.ALL).build();

    }


    public static RequestSpecification getAccountRequestSpec()
    {
        return  new RequestSpecBuilder().
                setBaseUri(System.getProperty("ACCOUNT_BASE_URI")).
                //setBaseUri("https://accounts.spotify.com").
                setContentType(ContentType.URLENC).
                log(LogDetail.ALL).build();

    }

    public static ResponseSpecification getResponseSpec()
    {
        return new   ResponseSpecBuilder()
                .log(LogDetail.ALL).
                build();


    }
}
