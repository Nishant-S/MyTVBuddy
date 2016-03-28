package com.cts.mytvbuddy.handler;

import android.os.AsyncTask;
import android.util.Log;

import com.cts.mytvbuddy.R;
import com.cts.mytvbuddy.TvShowApp;
import com.cts.mytvbuddy.handler.interfaces.JsonParserInterface;
import com.cts.mytvbuddy.model.Episode;
import com.cts.mytvbuddy.model.EpisodeCollection;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


/**
 * Created by nishantranjansingh on 3/26/16.
 * JsonHandler is a Singleton class, who parse Json String to a model class using Gson library. It holds reference to the parsed
 * EpisodeCollection as well as Episode. It has registered observers with it to notify them once done with the job using AsyncTask.
 */
public class JsonHandler {
    private String TAG = JsonHandler.class.getSimpleName();
    private JsonParserInterface jsonParserListener;
    private static JsonHandler jsonHandler;
    private EpisodeCollection episodeCollection;
    private Episode episode;

    private JsonHandler() {

    }


    public JsonHandler setJsonParserListener(JsonParserInterface jsonParserInterface) {
        this.jsonParserListener = jsonParserInterface;
        return this;
    }

    public static JsonHandler getJsonHandler() {
        if (jsonHandler == null) {
            jsonHandler = new JsonHandler();
        }
        return jsonHandler;
    }

    /**
     * Parse JsonString to Episode
     * @param jsonString JsonString to parse
     */
    public void parseSelectedEpisode(String jsonString) {
        new JsonParser(JsonParser.PARSE_SELECTED_EPISODE).execute(jsonString);
    }

    /**
     * Parse JsonString to EpisodeCollection
     * @param jsonString JsonString to parse
     */
    public void parseEpisodeCollection(String jsonString) {
        new JsonParser(JsonParser.PARSE_EPISODE_COLLECTION).execute(jsonString);
    }


    public EpisodeCollection getEpisodeCollection() {
        return episodeCollection;
    }

    public Episode getEpisodeDetails() {
        return episode;
    }

    /**
     * JsonParser is an AsyncTask, which does parsing in background thread and returns result(true/false) in main thread.
     */
    class JsonParser extends AsyncTask<String, Void, Boolean> {
        int mode = -1;
        public static final int PARSE_EPISODE_COLLECTION = 1;
        public static final int PARSE_SELECTED_EPISODE = 2;

        public JsonParser(int mode) {
            this.mode = mode;
        }

        protected Boolean doInBackground(String... urls) {
            Boolean isParsedSuccess = false;
            Gson gson = new Gson();
            try {
                if (mode == PARSE_EPISODE_COLLECTION) {
                    episodeCollection = gson.fromJson(urls[0], EpisodeCollection.class);
                } else if (mode == PARSE_SELECTED_EPISODE) {
                    episode = gson.fromJson(urls[0], Episode.class);
                }
                isParsedSuccess = true;
            } catch (JsonSyntaxException exception) {
                Log.d(TAG, exception.toString());
                isParsedSuccess = false;
            } catch (Exception exception) {
                Log.d(TAG, exception.toString());
                isParsedSuccess = false;
            }
            return isParsedSuccess;
        }

        protected void onPostExecute(Boolean result) {
            if (result.booleanValue() == true) {
                jsonParserListener.onJsonParsingSuccess();
            } else {
                jsonParserListener.onJsonParsingFailure(TvShowApp.getInstance().getResources().getString(R.string.json_parse_error));
            }
        }

    }

}
