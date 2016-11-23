package com.reddit.networkservice;

import com.reddit.jsonmodels.RedditFeeds;

import retrofit.Call;
import retrofit.http.GET;


/**
 * Created by surajmuralidharagupta on 11/20/16.
 */
public interface FeedsService {
    @GET(".json")
    Call<RedditFeeds> getRedditFeeds();
}

