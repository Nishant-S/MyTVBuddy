package com.cts.mytvbuddy.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;

import com.cts.mytvbuddy.R;

import com.cts.mytvbuddy.handler.JsonHandler;
import com.cts.mytvbuddy.model.Episode;
import com.cts.mytvbuddy.ui.EpisodeSelectedListener;
import com.cts.mytvbuddy.ui.fragments.EpisodeDetailFragment;
import com.cts.mytvbuddy.ui.fragments.EpisodeListFragment;

/**
 * Created by nishantranjansingh on 3/26/16.
 * TvShowActivity is the only Activity in this project, which basically used to hold other 2 Fragments as well as
 * communicate with them via listener.
 */
public class TvShowActivity extends AppCompatActivity implements EpisodeSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                    new EpisodeListFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onEpisodeSelected(int selectedIndex) {
        Episode selectedEpisode = JsonHandler.getJsonHandler().getEpisodeCollection().getEpisodeList().get(selectedIndex);
        EpisodeDetailFragment detailFragment = new EpisodeDetailFragment();
        Bundle args = new Bundle();
        args.putString(EpisodeDetailFragment.SEELCTED_EPISODE_TITLE, selectedEpisode.getTitle());
        args.putString(EpisodeDetailFragment.SEELCTED_EPISODE_ID, selectedEpisode.getImdbID());
        detailFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null).commit();
    }


}
