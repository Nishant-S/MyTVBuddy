package com.cts.mytvbuddy.handler;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cts.mytvbuddy.R;
import com.cts.mytvbuddy.TvShowApp;
import com.cts.mytvbuddy.handler.interfaces.APIResponseInterface;

import org.json.JSONObject;


/**
 * Created by nishantranjansingh on 3/26/16.
 * NetworkHandler is Singleton class, who does the networking operations using Volley library. It It has registered observers with it to
 * notify them once done with the job.
 */
public class NetworkHandler {

    private String TAG = NetworkHandler.class.getSimpleName();
    private static NetworkHandler networkHandler;
    private APIResponseInterface apiListener;

    private NetworkHandler() {

    }

    public NetworkHandler setApiListener(APIResponseInterface apiResponseInterface) {
        this.apiListener = apiResponseInterface;
        return this;
    }

    public static NetworkHandler getNetworkHandler() {
        if (networkHandler == null) {
            networkHandler = new NetworkHandler();
        }
        return networkHandler;
    }

    /**
     * It creates a JsonObjectRequest with GET method and passed Url, connect to server in separate thread using Volley and notify the
     * callback in main thread.
     * @param url Server Url
     */
    public void makeJsonObjReq(String url) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        apiListener.onAPICallSuccess(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                apiListener.onAPICallFailure(TvShowApp.getInstance().getResources().getString(R.string.network_error));
            }
        });

        // Adding request to request queue
        TvShowApp.getInstance().addToRequestQueue(jsonObjReq);
    }
}
