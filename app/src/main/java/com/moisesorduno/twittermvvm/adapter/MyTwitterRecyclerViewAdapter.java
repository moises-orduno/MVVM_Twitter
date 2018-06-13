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
import com.moisesorduno.twittermvvm.model.Tweet;

import java.util.List;

import static com.moisesorduno.twittermvvm.api.TwitterApi.TWITTER_USER_AMLO;
import static com.moisesorduno.twittermvvm.api.TwitterApi.TWITTER_USER_ANAYA;
import static com.moisesorduno.twittermvvm.api.TwitterApi.TWITTER_USER_BRONCO;
import static com.moisesorduno.twittermvvm.api.TwitterApi.TWITTER_USER_MEADE;

public class MyTwitterRecyclerViewAdapter extends RecyclerView.Adapter<MyTwitterRecyclerViewAdapter.ViewHolder> {

    private final List<Tweet> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;


    public MyTwitterRecyclerViewAdapter(List<Tweet> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_tweet, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTextViewUser.setText(holder.mItem.getUser().getName());
        holder.mTextViewDate.setText(DateParser.parseDate(holder.mItem.getSimplifiedCreatedAt()));
        holder.mTextViewTweet.setText(holder.mItem.getFullText());

        switch (("@" + holder.mItem.getUser().getScreenName())) {
            case TWITTER_USER_AMLO:
                holder.mImageViewUser.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.pic_amlo));
                break;
            case TWITTER_USER_ANAYA:
                holder.mImageViewUser.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.pic_anaya));
                break;
            case TWITTER_USER_MEADE:
                holder.mImageViewUser.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.pic_meade));
                break;
            case TWITTER_USER_BRONCO:
                holder.mImageViewUser.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.pic_bronco));
                break;
        }

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
        private final TextView mTextViewUser;
        private final TextView mTextViewDate;
        private final TextView mTextViewTweet;
        private final ImageView mImageViewUser;
        public Tweet mItem;

        private ViewHolder(View view) {
            super(view);
            mView = view;
            mTextViewUser = view.findViewById(R.id.tv_user);
            mTextViewTweet = view.findViewById(R.id.tv_tweet);
            mTextViewDate = view.findViewById(R.id.tv_date);
            mImageViewUser = view.findViewById(R.id.iv_user);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextViewTweet.getText() + "'";
        }
    }


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Tweet item);
    }
}
