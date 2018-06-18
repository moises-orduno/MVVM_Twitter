package com.moisesorduno.twittermvvm.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.airbnb.lottie.LottieAnimationView;
import com.moisesorduno.twittermvvm.R;
import com.moisesorduno.twittermvvm.adapter.MyGoogleRecyclerViewAdapter;

import com.moisesorduno.twittermvvm.api.GoogleQueryApi;
import com.moisesorduno.twittermvvm.model.googlequery.GoogleItem;
import com.moisesorduno.twittermvvm.viewmodel.GoogleViewModel;

import java.util.List;
import java.util.Observable;

public class GoogleFragment extends Fragment implements java.util.Observer, MyGoogleRecyclerViewAdapter.OnListFragmentInteractionListener {

    private RecyclerView mRecyclerView;
    public static final String TAG = GoogleFragment.class.getSimpleName();
    private GoogleViewModel mGoogleViewModel;

    private LottieAnimationView mAnimationView;


    public GoogleFragment() {
    }

    @SuppressWarnings("unused")
    public static GoogleFragment newInstance() {
        GoogleFragment fragment = new GoogleFragment();
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

        mGoogleViewModel.getGoogleItemsByQuery(GoogleQueryApi.GOOGLE_QUERY_PRESIDENTS);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_google, container, false);
        init(view);
        mGoogleViewModel = new GoogleViewModel(mRecyclerView ,mAnimationView);
        setUpObserver(mGoogleViewModel);
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
        mGoogleViewModel.reset();
    }

    @Override
    public void update(Observable o, Object arg) {

        mGoogleViewModel = (GoogleViewModel) o;
        Log.d(TAG, "update: ");

        setGoogleItems(mGoogleViewModel.getGoogleItemsList());

    }

    private void init(View view) {
        // Set the adapter
        mRecyclerView = view.findViewById(R.id.rv_tweet);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAnimationView = view.findViewById(R.id.animation_view);
        mAnimationView.setAnimation(R.raw.spinner_into_confirmation);

    }

    private void setGoogleItems(List<GoogleItem> items) {
        mRecyclerView.setAdapter(new MyGoogleRecyclerViewAdapter(items, this));

    }

    @Override
    public void onListFragmentInteraction(GoogleItem item) {

        if (getContext() == null) return;
        String urlString = item.getImage().getContextLink();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        try {
            getContext().startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            // Chrome browser presumably not installed so allow user to choose instead
            intent.setPackage(null);
            getContext().startActivity(intent);
        }
    }
}
