package com.cts.mytvbuddy.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cts.mytvbuddy.Constants;
import com.cts.mytvbuddy.R;
import com.cts.mytvbuddy.handler.JsonHandler;
import com.cts.mytvbuddy.handler.NetworkHandler;
import com.cts.mytvbuddy.handler.interfaces.APIResponseInterface;
import com.cts.mytvbuddy.handler.interfaces.JsonParserInterface;
import com.cts.mytvbuddy.model.Episode;
import com.cts.mytvbuddy.model.EpisodeCollection;
import com.cts.mytvbuddy.ui.EpisodeSelectedListener;
import com.cts.mytvbuddy.ui.activities.TvShowActivity;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by nishantranjansingh on 3/25/16.
 * Fragment showing list of all Episodes returned from server. We are using ArrayAdapter and basic layout schema to show Strings
 * in List. first it gets Json string as callback after Api call, send this Json string to parser class and get the response back to
 * populate UI.
 */
public class EpisodeListFragment extends ListFragment implements APIResponseInterface, JsonParserInterface {
    private EpisodeSelectedListener mCallback;
    private EpisodeCollection episodeCollection;
    private JsonHandler jsonHandler;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (EpisodeSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement EpisodeSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setDividerHeight(7);
        jsonHandler = JsonHandler.getJsonHandler();
        jsonHandler.setJsonParserListener(this);
        ((TvShowActivity)getActivity()).setActionBarTitle(getResources().getString(R.string.app_title_home));
        NetworkHandler.getNetworkHandler().setApiListener(this).makeJsonObjReq(Constants.ALL_EPISODE_URL);
    }


    private void refreshUI(){
        ArrayAdapter<Episode> adapter = new ArrayAdapter<Episode>(getActivity(),
                android.R.layout.simple_list_item_1, episodeCollection.getEpisodeList());
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mCallback.onEpisodeSelected(position);
    }

    /**
     * We got the response back from server, so time to parse this data.
     * @param jsonObject JsonObject returned from server
     */
    @Override
    public void onAPICallSuccess(JSONObject jsonObject) {
        jsonHandler.parseEpisodeCollection(jsonObject.toString());
    }

    /**
     * Error during calling server, show the error message to user.
     * @param errorMessage
     */
    @Override
    public void onAPICallFailure(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    /**
     * Parsing done successfully and JsonHandler is holding the response. Get it from him and populate UI fields.
     */
    @Override
    public void onJsonParsingSuccess() {
        episodeCollection = jsonHandler.getEpisodeCollection();
        if(episodeCollection != null){
            List<Episode> episodeList = episodeCollection.getEpisodeList();
            if(episodeList != null && episodeList.size() > 0){
                refreshUI();
            }
        }
    }

    /**
     * Error during parsing Json, show the error message to user.
     * @param errorMessage
     */
    @Override
    public void onJsonParsingFailure(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

}
