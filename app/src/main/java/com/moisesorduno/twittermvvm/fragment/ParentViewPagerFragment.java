package com.moisesorduno.twittermvvm.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moisesorduno.twittermvvm.R;
import com.moisesorduno.twittermvvm.model.Poll;

import java.util.ArrayList;
import java.util.List;

public class ParentViewPagerFragment extends Fragment {

    public static final int[] USERS_COLORS = {Color.parseColor("#990000"),
            Color.parseColor("#0000ff"), Color.parseColor("#33cc33"),
            Color.parseColor("#382115"), Color.parseColor("#adb7ad")};


    public static final String TAG = ParentViewPagerFragment.class.getName();
    private List<Poll> mPolls = new ArrayList<>();

    public static ParentViewPagerFragment newInstance() {
        return new ParentViewPagerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPolls.add(new Poll(getString(R.string.poll_bloomberg), R.raw.pollbloomberg, Poll.PollType.LINES));
        mPolls.add(new Poll(getString(R.string.poll_mitofsky), R.raw.mitofsky, Poll.PollType.LINES));
        mPolls.add(new Poll(getString(R.string.poll_coparmex_fundacionestepais), R.raw.coparmex_fundacionestepais, Poll.PollType.PIE));


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_parent_viewpager, container, false);

        ViewPager viewPager = root.findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));


        return root;
    }

    public class MyAdapter extends FragmentPagerAdapter {
        MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mPolls.size();
        }

        @Override
        public Fragment getItem(int position) {

            switch (mPolls.get(position).getPollType()) {
                case LINES:
                    return StatisticsLineFragment.newInstance(mPolls.get(position));
                case BAR:
                    return StatisticsBarFragment.newInstance(mPolls.get(position));
                case PIE:
                    return StatisticsPieFragment.newInstance(mPolls.get(position));

                default:
                    return StatisticsLineFragment.newInstance(mPolls.get(position));

            }

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Encuesta : " + mPolls.get(position).getName();
        }

    }
}
