package com.moisesorduno.twittermvvm.viewmodel;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.moisesorduno.twittermvvm.api.TwitterApi;
import com.moisesorduno.twittermvvm.common.donwloaderrx.RxDownloader;
import com.moisesorduno.twittermvvm.model.Tweet;
import com.moisesorduno.twittermvvm.model.TweetList;
import com.moisesorduno.twittermvvm.model.TwitterTokenType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class StatisticsViewModel  extends java.util.Observable {

    private final ProgressBar mProgressBar;
    private List<Tweet> mTweetList;
    private Context mContext;
    private TwitterApi mApi;
    private TwitterTokenType mTwitterTokenType;
    public static final String TAG = StatisticsViewModel.class.getSimpleName();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static final String BLOOMBER_POLLS = "https://www.bloomberg.com/graphics/2018-mexican-election/live-data/gsheets/live/pollingavg.csv";


    public StatisticsViewModel(Context context, ProgressBar progressBar) {
        mContext = context;
        mApi = TwitterApi.getInstance();
        mTweetList = new ArrayList<>();


        mProgressBar = progressBar;
    }


    public void downloadStatistics(String screenName) {

        final String poll = "poll.csv";
        RxDownloader rxDownloader = new RxDownloader(mContext);
        rxDownloader.download(BLOOMBER_POLLS, poll, "text/csv", true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String filePath) {
                        Log.d(TAG, "onNext: "+filePath);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });


    }

    private void getTweets(String accessToken) {
        mApi.getTweetList(accessToken, "nasa")
                .map(new Function<TweetList, List<Tweet>>() {
                    @Override
                    public List<Tweet> apply(TweetList tweetList) throws Exception {
                        return tweetList.getTweets();
                    }
                })
                .flatMapIterable(new Function<List<Tweet>, Iterable<Tweet>>() {
                    @Override
                    public Iterable<Tweet> apply(List<Tweet> tweets) throws Exception {
                        return tweets;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Tweet>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Tweet tweet) {
                        Log.d(TAG, "onNext: ");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                        updateTweets();
                    }
                });
    }

    private void updateTweets() {
        setChanged();
        notifyObservers();
    }

    Comparator<Tweet> le = new Comparator<Tweet>() {
        @Override
        public int compare(Tweet o1, Tweet o2) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


            try {
                return (sdf.parse(o2.getCreatedAt()).getTime() > sdf.parse(o1.getCreatedAt()).getTime()) ? 1 : 0;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;
        }
    };

    private void setTwitterList(List<Tweet> tweets) {

        mTweetList.addAll(tweets);

        Collections.sort(mTweetList, new Comparator<Tweet>() {
            @Override
            public int compare(Tweet o1, Tweet o2) {
                if (o1.getCreatedAt() == null || o2.getCreatedAt() == null)
                    return 0;
                return dates(o2.getCreatedAt()).compareTo(dates(o1.getCreatedAt()));
            }
        });

        Log.d(TAG, "setTwitterList: " + mTweetList);
    }

    private Date dates(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.ENGLISH);
        sdf.setLenient(true);

        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<Tweet> getTweets() {
        return mTweetList;
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

        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
}
