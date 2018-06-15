package com.moisesorduno.twittermvvm.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.airbnb.lottie.LottieAnimationView;
import com.moisesorduno.twittermvvm.R;
import com.moisesorduno.twittermvvm.adapter.MyTwitterRecyclerViewAdapter;
import com.moisesorduno.twittermvvm.model.tweet.Tweet;
import com.moisesorduno.twittermvvm.viewmodel.TweetViewModel;

import java.util.List;
import java.util.Observable;

public class TwitterFragment extends Fragment implements java.util.Observer,MyTwitterRecyclerViewAdapter.OnListFragmentInteractionListener {

    //    private  userActivityBinding;

    private RecyclerView mRecyclerView;
    public static final String TAG = TwitterFragment.class.getSimpleName();
    private TweetViewModel mTweetViewModel;
    private LottieAnimationView mAnimationView;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TwitterFragment() {
    }

    @SuppressWarnings("unused")
    public static TwitterFragment newInstance(int columnCount) {
        TwitterFragment fragment = new TwitterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();

        mTweetViewModel.getTweetsFromScreenName("");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_twitter, container, false);
        init(view);
        mTweetViewModel = new TweetViewModel(getContext(),mRecyclerView,mAnimationView);

        setUpObserver(mTweetViewModel);
        return view;
    }

    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mTweetViewModel.reset();
    }

    @Override
    public void update(Observable o, Object arg) {

        TweetViewModel userViewModel = (TweetViewModel) o;
        setTwitters(userViewModel.getTweets());


    }

    @Override
    public void onListFragmentInteraction(Tweet item) {
        mTweetViewModel.getTweetsFromScreenName(item.getUser().getScreenName());
    }


    private void init(View view) {
        // Set the adapter
        mRecyclerView = view.findViewById(R.id.rv_tweet);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAnimationView = view.findViewById(R.id.animation_view);
        mAnimationView.setAnimation(R.raw.spinner_into_confirmation);


    }

    private void setTwitters(List<Tweet> items) {
        mRecyclerView.setAdapter(new MyTwitterRecyclerViewAdapter(items, this));

    }

}
