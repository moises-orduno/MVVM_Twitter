package com.moisesorduno.twittermvvm.viewmodel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.moisesorduno.twittermvvm.api.TwitterApi;
import com.moisesorduno.twittermvvm.common.DateParser;
import com.moisesorduno.twittermvvm.common.PreferencesController;
import com.moisesorduno.twittermvvm.model.Tweet;
import com.moisesorduno.twittermvvm.model.TwitterTokenType;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class TweetViewModel extends java.util.Observable {

    private final RecyclerView mRecyclerView;
    private final ProgressBar mProgressBar;
    private List<Tweet> mTweetList;
    private Context mContext;
    private TwitterApi mApi;
    public static final String TAG = TweetViewModel.class.getSimpleName();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String mScreenName;


    public TweetViewModel(Context context, RecyclerView recyclerView, ProgressBar progressBar) {
        mContext = context;
        mRecyclerView = recyclerView;
        mProgressBar = progressBar;
        mApi = TwitterApi.getInstance();
        mTweetList = new ArrayList<>();

    }

    public void getTweetsFromScreenName(String screenName) {

        mScreenName = screenName;

        String accessToken = PreferencesController.getAccessToken(mContext);

        showLoader(true);

        if (accessToken.isEmpty()) {

            showLoader(true);
            try {
                mApi.getToken("Basic " + getBase64String(TwitterApi.BEARER_TOKEN_CREDENTIALS),
                        "client_credentials")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<TwitterTokenType>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onSuccess(TwitterTokenType twitterTokenType) {
                                PreferencesController.setAccessToken(mContext, twitterTokenType.getAccessToken());
                                showLoader(false);
                                getTweetsByScreenName(mScreenName);
                            }


                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "onError: ");
                                showLoader(false);
                            }


                        });
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }else  getTweetsByScreenName(mScreenName);






    }

    private void getTweetsByScreenName(String screenName) {

        String accessToken = PreferencesController.getAccessToken(mContext);

        Observable<List<Tweet>> observable;

        if (screenName.equals("")) {
            observable = Observable.merge(
                    mApi.getTweetListByUser(accessToken, TwitterApi.TWITTER_USER_AMLO),
                    mApi.getTweetListByUser(accessToken, TwitterApi.TWITTER_USER_ANAYA),
                    mApi.getTweetListByUser(accessToken, TwitterApi.TWITTER_USER_BRONCO),
                    mApi.getTweetListByUser(accessToken, TwitterApi.TWITTER_USER_MEADE)
            );
        } else {
            mTweetList = new ArrayList<>();
            observable = mApi.getTweetListByUser(accessToken, screenName);
        }


        showLoader(true);

        observable
                .flatMapIterable(new Function<List<Tweet>, Iterable<Tweet>>() {
                    @Override
                    public Iterable<Tweet> apply(List<Tweet> tweets) throws Exception {
                        return tweets;
                    }
                })
                .map(new Function<Tweet, Tweet>() {
                    @Override
                    public Tweet apply(Tweet tweet) throws Exception {
                        tweet.setSimplifiedCreatedAt(DateParser.formatTweetDateToDate(tweet.getCreatedAt()));
                        return tweet;
                    }
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Tweet>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Tweet> tweets) {
                        Log.d(TAG, "onNext: ");
                        setTwitterList(tweets);
                        updateTweets();
                        showLoader(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showLoader(false);
                    }
                });

    }

    private void updateTweets() {
        setChanged();
        notifyObservers();
    }

    private void setTwitterList(List<Tweet> tweets) {

        mTweetList.addAll(tweets);

        Collections.sort(mTweetList, new Comparator<Tweet>() {
            @Override
            public int compare(Tweet o1, Tweet o2) {
                if (o1.getCreatedAt() == null || o2.getCreatedAt() == null)
                    return 0;
                return DateParser.formatTweetDateToDate(o2.getCreatedAt()).compareTo(DateParser.formatTweetDateToDate(o1.getCreatedAt()));
            }
        });

    }

    public List<Tweet> getTweets() {
        return mTweetList;
    }

    private static String getBase64String(String value) throws UnsupportedEncodingException {
        return Base64.encodeToString(value.getBytes("UTF-8"), Base64.NO_WRAP);
    }

    private void unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void reset() {
        unSubscribeFromObservable();
        compositeDisposable = null;
        mContext = null;
    }

    private void showLoader(boolean isLoading) {
        mRecyclerView.setEnabled(!isLoading);
        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
}
