package com.moisesorduno.twittermvvm.fragment;

import android.os.Bundle;
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

public class ParentViewPagerFragment  extends Fragment {

    public static final String TAG = ParentViewPagerFragment.class.getName();
    private static List<Poll> mPolls= new ArrayList<>();

    public static ParentViewPagerFragment newInstance() {
        return new ParentViewPagerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPolls.add(new Poll("Bloomberg",R.raw.pollbloomberg,new Poll.PollType(Poll.PollType.BLOOMBERG)));
        mPolls.add(new Poll("Mitofsky",R.raw.mitofsky,new Poll.PollType(Poll.PollType.MITOFSKY)));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_parent_viewpager, container, false);

        ViewPager viewPager = root.findViewById(R.id.viewPager);
        /** Important: Must use the child FragmentManager or you will see side effects. */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));



        return root;
    }

    public static class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mPolls.size();
        }

        @Override
        public Fragment getItem(int position) {

            return StatisticsFragment.newInstance(mPolls.get(position));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Encuesta : " + mPolls.get(position).getName();
        }

    }
}
