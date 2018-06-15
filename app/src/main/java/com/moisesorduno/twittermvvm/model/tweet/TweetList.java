package com.moisesorduno.twittermvvm.model.tweet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TweetList {

    @SerializedName("statuses")
    @Expose
    private List<Tweet> tweets = null;
    @SerializedName("search_metadata")
    @Expose
    private SearchMetadata searchMetadata;

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public SearchMetadata getSearchMetadata() {
        return searchMetadata;
    }

    public void setSearchMetadata(SearchMetadata searchMetadata) {
        this.searchMetadata = searchMetadata;
    }


}
