package com.cts.mytvbuddy.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nishantranjansingh on 3/26/16.
 * Model class to hold collection of Episode list.
 */
public class EpisodeCollection {

    @SerializedName("Title")
    public String title;

    @SerializedName("Season")
    public String season;

    @SerializedName("Episodes")
    public List<Episode> episodeList;

    public List<Episode> getEpisodeList() {
        return episodeList;
    }

    public void setEpisodeList(List<Episode> episodeList) {
        this.episodeList = episodeList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

}
