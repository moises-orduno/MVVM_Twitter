package com.moisesorduno.twittermvvm.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.moisesorduno.twittermvvm.R;
import com.moisesorduno.twittermvvm.adapter.DateXAxisValueFormatter;
import com.moisesorduno.twittermvvm.common.CSVReader;
import com.moisesorduno.twittermvvm.model.Poll;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static com.moisesorduno.twittermvvm.fragment.ParentViewPagerFragment.USERS_COLORS;

public class StatisticsLineFragment extends Fragment implements java.util.Observer {

    public static final String TAG = StatisticsLineFragment.class.getSimpleName();


    private LineChart mChart;
    private int mFilename;

    public StatisticsLineFragment() {
        // Required empty public constructor
    }

    public static StatisticsLineFragment newInstance(Poll poll) {
        StatisticsLineFragment fragment = new StatisticsLineFragment();

        fragment.setFilename(poll.getFilename());
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_statistics_line, container, false);

        mChart = view.findViewById(R.id.chart);
        loadStatistics();

        return view;
    }

    private void loadStatistics() {

        if(mFilename==0)return;

        List<String[]> rows = new ArrayList<>();
        CSVReader csvReader = new CSVReader();
        try {
            rows = csvReader.readCSV(getResources().openRawResource(mFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        buildLineData(rows);

    }


    @Override
    public void onResume() {
        super.onResume();
//        mStatisticsViewModel.downloadStatistics("");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public void buildLineData(List<String[]> rows) {

        String[] pollDates  = new String[rows.get(1).length];
        LineData lineData = new LineData();

        for (int j = 1; j < 5; j++) {

            List<Entry> entries = new ArrayList<>();



            for (int i = 2; i < rows.get(0).length; i++) {
                entries.add(new Entry(i, Float.valueOf(rows.get(j)[i])));
                pollDates[i] = rows.get(0)[i].substring(3);
            }
            LineDataSet dataSet = new LineDataSet(entries, rows.get(j)[0]); // add entries to dataset
            dataSet.setCircleColor(USERS_COLORS[j-1]);
            dataSet.setColor(USERS_COLORS[j-1]);
            lineData.addDataSet(dataSet);

        }


        XAxis xAxis = mChart.getXAxis();
        xAxis.setValueFormatter(new DateXAxisValueFormatter(pollDates));
        mChart.setData(lineData);
        mChart.invalidate();

    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public void setFilename(int filename) {
        mFilename = filename;
    }


}
