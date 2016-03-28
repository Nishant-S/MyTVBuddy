package com.cts.mytvbuddy;

/**
 * Created by nishantranjansingh on 3/26/16.
 */
public class Constants {
    public static final String SERVER_URL = "http://www.omdbapi.com/";
    public static final String ALL_EPISODE_URL = SERVER_URL+"?t=Game%20of%20Thrones&Season=1";
    public static final String EPISODE_DETAIL_URL = SERVER_URL+"?i=%s&plot=short&r=json";
}
