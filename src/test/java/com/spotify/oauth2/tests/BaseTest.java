package com.spotify.oauth2.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.lang.reflect.Method;
//-DBASE_URI="https://api.spotify.com" -DACCOUNT_BASE_URI="https://accounts.spotify.com"
public class BaseTest {

    @BeforeMethod
    public void beforeMethod(Method m)
    {
        System.out.println("STARTING TEST: "+m.getName());
        System.out.println("THREAD ID: "+Thread.currentThread().getId());
    }
}
