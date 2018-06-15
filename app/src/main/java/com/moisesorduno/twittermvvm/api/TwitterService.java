package com.moisesorduno.twittermvvm.api;

import com.moisesorduno.twittermvvm.model.tweet.Tweet;
import com.moisesorduno.twittermvvm.model.tweet.TweetList;
import com.moisesorduno.twittermvvm.model.tweet.TwitterTokenType;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TwitterService {



    @GET(TwitterApi.TWITTER_HASHTAG_SEARCH_CODE)
    Observable<TweetList> getTweetList(
            @Header("Authorization") String authorization, @Query("q") String hashtag);


    @GET(TwitterApi.TWITTER_HASHTAG_SEARCH_CODE)
    Observable<TweetList> groupList(@Path("id") int groupId, @Query("sort") String sort);

    @GET(TwitterApi.TWITTER_USER_SEARCH_CODE)
    Observable<List<Tweet>> getTweetListByUser(
            @Header("Authorization") String authorization, @Query("screen_name") String screenName,
            @Query("count") Integer count, @Query("tweet_mode") String extended);

    @FormUrlEncoded
    @POST("/oauth2/token")
    Single<TwitterTokenType> getToken(
            @Header("Authorization") String authorization,
            @Field("grant_type") String grantType);

}
