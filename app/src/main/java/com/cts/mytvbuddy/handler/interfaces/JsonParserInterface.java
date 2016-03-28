package com.cts.mytvbuddy.handler.interfaces;

/**
 * Created by nishantranjansingh on 3/26/16.
 * Interface to nofify observer on Json parse response
 */
public interface JsonParserInterface {
    public void onJsonParsingSuccess();
    public void onJsonParsingFailure(String errorMessage);
}
