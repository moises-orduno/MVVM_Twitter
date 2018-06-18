package com.moisesorduno.twittermvvm.viewmodel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.airbnb.lottie.LottieAnimationView;
import com.moisesorduno.twittermvvm.api.GoogleQueryApi;
import com.moisesorduno.twittermvvm.api.TwitterApi;
import com.moisesorduno.twittermvvm.common.DateParser;
import com.moisesorduno.twittermvvm.common.PreferencesController;
import com.moisesorduno.twittermvvm.model.googlequery.GoogleItem;
import com.moisesorduno.twittermvvm.model.googlequery.GoogleQuery;
import com.moisesorduno.twittermvvm.model.tweet.Tweet;
import com.moisesorduno.twittermvvm.model.tweet.TwitterTokenType;

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

public class GoogleViewModel extends java.util.Observable {

    private final RecyclerView mRecyclerView;
    private final LottieAnimationView mAnimationView;
    private List<GoogleItem> mGoogleItemList;
    private GoogleQueryApi mApi;
    public static final String TAG = GoogleViewModel.class.getSimpleName();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    public GoogleViewModel(RecyclerView recyclerView, LottieAnimationView animationView) {
        mRecyclerView = recyclerView;
        mAnimationView = animationView;
        mApi = GoogleQueryApi.getInstance();
        mGoogleItemList = new ArrayList<>();

    }

    public void getGoogleItemsByQuery(String query) {

        showLoader(true);
        mApi.getGoogleQueryApi(query)
                .map(new Function<GoogleQuery, List<GoogleItem>>() {
                    @Override
                    public List<GoogleItem> apply(GoogleQuery googleQuery) throws Exception {
                        return googleQuery.getItems();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<GoogleItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<GoogleItem> googleItems) {
                        showLoader(false);
                        setGoogleItemsList(googleItems);
                        updateTweets();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                    }
                });


    }

    private void updateTweets() {
        setChanged();
        notifyObservers();
    }

    private void setGoogleItemsList(List<GoogleItem> tweets) {

        mGoogleItemList.addAll(tweets);

    }

    public List<GoogleItem> getGoogleItemsList() {
        return mGoogleItemList;
    }


    private void unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void reset() {
        unSubscribeFromObservable();
        compositeDisposable = null;
    }

    private void showLoader(boolean isLoading) {
        mRecyclerView.setEnabled(!isLoading);
        if (isLoading) {
            mAnimationView.playAnimation();
            mAnimationView.setVisibility(View.VISIBLE);
        } else {
            mAnimationView.pauseAnimation();
            mAnimationView.setVisibility(View.GONE);
        }
    }
}
