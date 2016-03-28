package com.cts.mytvbuddy.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cts.mytvbuddy.Constants;
import com.cts.mytvbuddy.R;
import com.cts.mytvbuddy.handler.JsonHandler;
import com.cts.mytvbuddy.handler.NetworkHandler;
import com.cts.mytvbuddy.handler.interfaces.APIResponseInterface;
import com.cts.mytvbuddy.handler.interfaces.JsonParserInterface;
import com.cts.mytvbuddy.model.Episode;
import com.cts.mytvbuddy.ui.activities.TvShowActivity;

import org.json.JSONObject;


/**
 * Created by nishantranjansingh on 3/25/16.
 * EpisodeDetailFragment is fragment used to show detail of selected Episode; first it gets Json string as callback after Api call, send
 * this Json string to parser class and get the response back to populate UI.
 */
public class EpisodeDetailFragment extends Fragment implements APIResponseInterface, JsonParserInterface {
    public static final String SEELCTED_EPISODE_TITLE = "selected_episode_title";
    public static final String SEELCTED_EPISODE_ID = "selected_episode_id";
    private Episode selectedEpisode;
    private ProgressDialog pDialog;
    private TextView textViewYear, textViewRated, textViewReleased, textViewSeason, textViewEpisode, textViewRuntime,
        textViewImdb, textViewGenre, textViewDirector;
    private JsonHandler jsonHandler;
    private String title, imdbId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle=getArguments();
        title = bundle.getString(SEELCTED_EPISODE_TITLE);
        imdbId = bundle.getString(SEELCTED_EPISODE_ID);
        jsonHandler = JsonHandler.getJsonHandler();
        jsonHandler.setJsonParserListener(this);
        View view = inflater.inflate(R.layout.fragment_episode_detail,
                container, false);
        textViewYear = (TextView)view.findViewById(R.id.id_year);
        textViewRated = (TextView)view.findViewById(R.id.id_rated);
        textViewReleased = (TextView)view.findViewById(R.id.id_released);
        textViewSeason = (TextView)view.findViewById(R.id.id_season);
        textViewEpisode = (TextView)view.findViewById(R.id.id_episode);
        textViewRuntime = (TextView)view.findViewById(R.id.id_runtime);

        textViewImdb = (TextView)view.findViewById(R.id.id_imdb);
        textViewGenre = (TextView)view.findViewById(R.id.id_genre);
        textViewDirector = (TextView)view.findViewById(R.id.id_director);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((TvShowActivity)getActivity()).setActionBarTitle(title);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(getResources().getString(R.string.loading_str));
        pDialog.setCancelable(false);
        showProgressDialog();
        NetworkHandler.getNetworkHandler().setApiListener(this).makeJsonObjReq(String.format(Constants.EPISODE_DETAIL_URL, imdbId));
    }

    private void showProgressDialog() {
        if (pDialog != null && !pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog != null && pDialog.isShowing())
            pDialog.hide();
    }

    /**
     * We got the response back from server, so time to parse this data.
     * @param jsonObject JsonObject returned from server
     */
    @Override
    public void onAPICallSuccess(JSONObject jsonObject) {
        jsonHandler.parseSelectedEpisode(jsonObject.toString());
    }

    /**
     * Error during calling server, show the error message to user.
     * @param errorMessage
     */
    @Override
    public void onAPICallFailure(String errorMessage) {
        hideProgressDialog();
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    /**
     * Parsing done successfully and JsonHandler is holding the response. Get it from him and populate UI fields.
     */
    @Override
    public void onJsonParsingSuccess() {
        hideProgressDialog();
        selectedEpisode = jsonHandler.getEpisodeDetails();
        if(selectedEpisode != null){
            textViewYear.setText(selectedEpisode.getYear());
            textViewRated.setText(selectedEpisode.getRated());
            textViewReleased.setText(selectedEpisode.getReleased());
            textViewSeason.setText(selectedEpisode.getSeason());
            textViewEpisode.setText(selectedEpisode.getEpisode());
            textViewRuntime.setText(selectedEpisode.getRuntime());
            textViewImdb.setText(selectedEpisode.getImdbRating());
            textViewGenre.setText(selectedEpisode.getGenre());
            textViewDirector.setText(selectedEpisode.getDirector());
        }
    }

    /**
     * Error during parsing Json, show the error message to user.
     * @param errorMessage
     */
    @Override
    public void onJsonParsingFailure(String errorMessage) {
        hideProgressDialog();
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }
}
