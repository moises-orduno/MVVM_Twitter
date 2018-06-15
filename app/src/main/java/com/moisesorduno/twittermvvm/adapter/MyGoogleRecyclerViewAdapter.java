package com.moisesorduno.twittermvvm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moisesorduno.twittermvvm.R;
import com.moisesorduno.twittermvvm.common.DateParser;
import com.moisesorduno.twittermvvm.model.googlequery.GoogleItem;
import com.moisesorduno.twittermvvm.model.tweet.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.moisesorduno.twittermvvm.api.TwitterApi.TWITTER_USER_AMLO;
import static com.moisesorduno.twittermvvm.api.TwitterApi.TWITTER_USER_ANAYA;
import static com.moisesorduno.twittermvvm.api.TwitterApi.TWITTER_USER_BRONCO;
import static com.moisesorduno.twittermvvm.api.TwitterApi.TWITTER_USER_MEADE;

public class MyGoogleRecyclerViewAdapter extends RecyclerView.Adapter<MyGoogleRecyclerViewAdapter.ViewHolder> {

    private final List<GoogleItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;


    public MyGoogleRecyclerViewAdapter(List<GoogleItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_google, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTextViewGoogleItem.setText(holder.mItem.getTitle());
        holder.mTextViewGoogleItemDisplayLink.setText(holder.mItem.getDisplayLink());

        Picasso.get().load(holder.mItem.getLink()).into(holder.mImageViewGoogleImage);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private final TextView mTextViewGoogleItem;
        private final TextView mTextViewGoogleItemDisplayLink;
        private final ImageView mImageViewGoogleImage;
        public GoogleItem mItem;

        private ViewHolder(View view) {
            super(view);
            mView = view;
            mTextViewGoogleItem = view.findViewById(R.id.tv_google_item);
            mImageViewGoogleImage = view.findViewById(R.id.iv_google_image);
            mTextViewGoogleItemDisplayLink = view.findViewById(R.id.tv_google_item_display_link);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextViewGoogleItem.getText() + "'";
        }
    }


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(GoogleItem item);
    }
}
