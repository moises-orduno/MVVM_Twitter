package com.moisesorduno.twittermvvm.api;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.moisesorduno.twittermvvm.model.Tweet;
import com.moisesorduno.twittermvvm.model.TweetList;
import com.moisesorduno.twittermvvm.model.TwitterTokenType;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TwitterApi{

    public final static String CONSUMER_KEY = "fFexrye5XVDsvWRRitABiLxXb";

    public final static String CONSUMER_SECRET = "Ge6Su6tslxm0RJ40AiHfYmuQfCY48ktdlPqz1YWFjsSBrDOV8x";

    public final static String TWITTER_SEARCH_URL = "https://api.twitter.com";

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
//
//    @Subscribe
//    public void onGetToken(TwitterGetTokenEvent event) {
//        try {
//            mApi.getToken("Basic " + getBase64String(ApiConstants.BEARER_TOKEN_CREDENTIALS),
//                    "client_credentials").enqueue(new Callback<TwitterTokenType>() {
//                @Override
//                public void onResponse(Call<TwitterTokenType> call, Response<TwitterTokenType> response) {
//                    PrefsController.setAccessToken(TwitterSearchApplication.getAppContext(), response.body().accessToken);
//                    PrefsController.setTokenType(TwitterSearchApplication.getAppContext(), response.body().tokenType);
//                    mBus.post(new TwitterGetTokenEventOk());
//                }
//
//                @Override
//                public void onFailure(Call<TwitterTokenType> call, Throwable t) {
//                    Log.e(TAG, t.toString(), t);
//                    mBus.post(new TwitterGetTokenEventFailed());
//                }
//            });
//
//        } catch (UnsupportedEncodingException e) {
//            Log.e(TAG, e.toString(), e);
//        }
//    }
}
