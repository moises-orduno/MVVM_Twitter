package com.moisesorduno.twittermvvm.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moisesorduno.twittermvvm.R;
import com.moisesorduno.twittermvvm.common.CSVReader;
import com.moisesorduno.twittermvvm.viewmodel.StatisticsViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class StatisticsFragment extends Fragment implements java.util.Observer{
    public static final String TAG = StatisticsFragment.class.getSimpleName();


    private OnFragmentInteractionListener mListener;
    private StatisticsViewModel mStatisticsViewModel;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    public static StatisticsFragment newInstance(String param1, String param2) {
        StatisticsFragment fragment = new StatisticsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        List<String[]> rows = new ArrayList<>();
        CSVReader csvReader = new CSVReader();
        try {
            rows = csvReader.readCSV(getResources().openRawResource(R.raw.pollingavg));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < rows.size(); i++) {
            Log.d(TAG, String.format("row %s: %s, %s, %s", i, rows.get(i)[0], rows.get(i)[1], rows.get(i)[2]));
        }

        mStatisticsViewModel=new StatisticsViewModel(getContext(),null);
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        mStatisticsViewModel.downloadStatistics("");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void update(Observable o, Object arg) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
