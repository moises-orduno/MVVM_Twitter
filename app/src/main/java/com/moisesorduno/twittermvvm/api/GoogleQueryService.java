package com.moisesorduno.twittermvvm.api;


import com.moisesorduno.twittermvvm.model.googlequery.GoogleQuery;
import com.moisesorduno.twittermvvm.model.tweet.TweetList;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface GoogleQueryService {

    @GET(GoogleQueryApi.GOOGLE_CUSTOME_SEARCH)
    Single<GoogleQuery> getGoogleQuery(
            @Query("q") String query, @Query("cx") String searchEngine,
            @Query("key") String apiKey, @Query("searchType") String searchType);

}
