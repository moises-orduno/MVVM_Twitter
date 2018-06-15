package com.moisesorduno.twittermvvm.api;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.moisesorduno.twittermvvm.model.tweet.Tweet;
import com.moisesorduno.twittermvvm.model.tweet.TweetList;
import com.moisesorduno.twittermvvm.model.tweet.TwitterTokenType;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TwitterApi{

    private final static String CONSUMER_KEY = "fFexrye5XVDsvWRRitABiLxXb";

    private final static String CONSUMER_SECRET = "Ge6Su6tslxm0RJ40AiHfYmuQfCY48ktdlPqz1YWFjsSBrDOV8x";

    public static final String BEARER_TOKEN_CREDENTIALS = CONSUMER_KEY + ":" + CONSUMER_SECRET;

    public final static String TWITTER_HASHTAG_SEARCH_CODE = "/1.1/search/tweets.json";

    public final static String TWITTER_USER_SEARCH_CODE = "/1.1/statuses/user_timeline.json";

    public final static String TWITTER_USER_AMLO = "@lopezobrador_";

    public final static String TWITTER_USER_ANAYA = "@RicardoAnayaC";

    public final static String TWITTER_USER_MEADE = "@JoseAMeadeK";

    public final static String TWITTER_USER_BRONCO = "@JaimeRdzNL";

    //OkHttp
    public static final int CONNECT_TIMEOUT = 60;

    //amount of tweets
    private static final int TWEETS_LIMIT = 10;

    //amount of tweets
    private static final String TWEETS_MODE = "extended";

    private final static String TWITTER_SEARCH_URL = "https://api.twitter.com";


    private static volatile TwitterApi mApi;
    private final TwitterService mService;

    private TwitterApi() {

        mService = new Retrofit.Builder()
                .client(getClient())
                .baseUrl(TWITTER_SEARCH_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TwitterService.class);
    }

    private static OkHttpClient getClient() {
        OkHttpClient httpClient;
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(new StethoInterceptor())
//                .addInterceptor(new HeadersInterceptor(service))
                .addInterceptor(httpLoggingInterceptor)
                .readTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        return httpClient;
    }

    public static TwitterApi getInstance() {
        if (mApi == null) {
            synchronized (TwitterApi.class) {
                if (mApi == null) {
                    mApi = new TwitterApi();
                }
            }
        }
        return mApi;
    }

    public Single<TwitterTokenType> getToken(String authorization, String grantType) {
        return mService.getToken(authorization, grantType);

    }

    public Observable<TweetList> getTweetList(String accessToken, String hashtag) {
        return mService.getTweetList("Bearer " + accessToken, hashtag);

    }

    public Observable<TweetList> groupList(int groupId, String sort) {
        return null;
    }

    public Observable<List<Tweet>> getTweetListByUser(String accessToken, String screenName) {
        return mService.getTweetListByUser("Bearer " + accessToken, screenName,TWEETS_LIMIT,
                TWEETS_MODE);
    }

}
