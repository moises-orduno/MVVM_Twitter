package com.moisesorduno.twittermvvm.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moisesorduno.twittermvvm.R;
import com.moisesorduno.twittermvvm.common.DateParser;
import com.moisesorduno.twittermvvm.model.Tweet;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class MyTwitterRecyclerViewAdapter  extends RecyclerView.Adapter<MyTwitterRecyclerViewAdapter.ViewHolder> {

    private final List<Tweet> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyTwitterRecyclerViewAdapter(List<Tweet> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_tweet, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTextViewUser.setText(String.format("%s %s", holder.mItem.getUser().getName(), DateParser.parseDate(holder.mItem.getSimplifiedCreatedAt())));
        holder.mTextViewTweet.setText(holder.mItem.getFullText());

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
        private final TextView mTextViewTweet;
        public Tweet mItem;

        private ViewHolder(View view) {
            super(view);
            mView = view;
            mTextViewUser = view.findViewById(R.id.tv_user);
            mTextViewTweet = view.findViewById(R.id.tv_tweet);
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
