package com.moisesorduno.twittermvvm.model;

public class SearchTweetsEvent {


    private final String hashtag;
    private final String twitterToken;

    public SearchTweetsEvent(String twitterToken, String hashtag) {
        this.hashtag = hashtag;
        this.twitterToken = twitterToken;
    }

    public String getTwitterToken() {
        return twitterToken;
    }

    public String getHashtag() {
        return hashtag;
    }

}
