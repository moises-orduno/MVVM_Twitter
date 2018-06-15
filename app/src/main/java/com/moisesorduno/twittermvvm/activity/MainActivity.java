package com.moisesorduno.twittermvvm.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.moisesorduno.twittermvvm.R;
import com.moisesorduno.twittermvvm.fragment.GoogleFragment;
import com.moisesorduno.twittermvvm.fragment.ParentViewPagerFragment;
import com.moisesorduno.twittermvvm.fragment.TwitterFragment;

public class MainActivity extends AppCompatActivity  {

//    protected ActivityMainBinding activityMainBinding



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_statistics:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,new ParentViewPagerFragment(), ParentViewPagerFragment.TAG).commit();

                    return true;
                case R.id.navigation_twitter:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,new TwitterFragment(),TwitterFragment.TAG).commit();

                    return true;
                case R.id.navigation_notifications:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,new GoogleFragment(),GoogleFragment.TAG).commit();

                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,new ParentViewPagerFragment(), ParentViewPagerFragment.TAG).commit();

    }

}
