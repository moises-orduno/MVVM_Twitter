package com.moisesorduno.twittermvvm.api;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.moisesorduno.twittermvvm.model.googlequery.GoogleQuery;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class GoogleQueryApi {

    public final static String GOOGLE_CUSTOME_SEARCH = "/customsearch/v1";

    public final static String GOOGLE_QUERY = "memes+debates+mexico";

    private final static String GOOGLE_SEARCH_URL = "https://www.googleapis.com";

    private final static String GOOGLE_API_KEY = "AIzaSyDmtZ2PQy2bzihmuw0PBMois1GTGF7TxRU";

    private final static String GOOGLE_SEARCH_TYPE = "image";

    private final static String MEME_SEARCH_ENGINE = "001419079092240543192:sivxvvj6xrm";

    private static volatile GoogleQueryApi mApi;
    private final GoogleQueryService mService;

    private GoogleQueryApi() {

        mService = new Retrofit.Builder()
                .client(getClient())
                .baseUrl(GOOGLE_SEARCH_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GoogleQueryService.class);
    }

    private static OkHttpClient getClient() {
        OkHttpClient httpClient;
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient = new OkHttpClient.Builder()
                .connectTimeout(TwitterApi.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(new StethoInterceptor())
//                .addInterceptor(new HeadersInterceptor(service))
                .addInterceptor(httpLoggingInterceptor)
                .readTimeout(TwitterApi.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        return httpClient;
    }

    public static GoogleQueryApi getInstance() {
        if (mApi == null) {
            synchronized (GoogleQueryApi.class) {
                if (mApi == null) {
                    mApi = new GoogleQueryApi();
                }
            }
        }
        return mApi;
    }

    public Single<GoogleQuery> getGoogleQueryApi(String query) {
        return mService.getGoogleQuery(query,MEME_SEARCH_ENGINE, GOOGLE_API_KEY,GOOGLE_SEARCH_TYPE);

    }

}
