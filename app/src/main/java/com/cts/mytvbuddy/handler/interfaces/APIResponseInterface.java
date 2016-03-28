package com.cts.mytvbuddy.handler.interfaces;

import org.json.JSONObject;

/**
 * Created by nishantranjansingh on 3/26/16.
 * Interface to nofify observer on api call response
 */
public interface APIResponseInterface {
    public void onAPICallSuccess(JSONObject jsonObject);
    public void onAPICallFailure(String errorMessage);
}
