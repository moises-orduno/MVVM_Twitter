package com.moisesorduno.twittermvvm.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.moisesorduno.twittermvvm.R;
import com.moisesorduno.twittermvvm.common.CSVReader;
import com.moisesorduno.twittermvvm.model.Poll;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static com.moisesorduno.twittermvvm.fragment.ParentViewPagerFragment.USERS_COLORS;

public class StatisticsBarFragment extends Fragment implements java.util.Observer {

    public static final String TAG = StatisticsBarFragment.class.getSimpleName();

    private BarChart mBarChart;
    private int mFilename;

    public StatisticsBarFragment() {
        // Required empty public constructor
    }

    public static StatisticsBarFragment newInstance(Poll poll) {
        StatisticsBarFragment fragment = new StatisticsBarFragment();

        fragment.setFilename(poll.getFilename());
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

        View view = inflater.inflate(R.layout.fragment_statistics_bar, container, false);

        mBarChart = view.findViewById(R.id.chart);
        loadStatistics();

        return view;
    }

    private void loadStatistics() {

        List<String[]> rows = new ArrayList<>();
        CSVReader csvReader = new CSVReader();
        try {
            rows = csvReader.readCSV(getResources().openRawResource(mFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        buildLineData(mBarChart);

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

    public void buildLineData(BarChart barChart) {


        List<BarEntry> entries = new ArrayList<>();


        for (int i = 0; i < 4; i++) {

            entries.add(new BarEntry(i, i));


        }


        List<IBarDataSet> dataSets  = new ArrayList<>();

        List<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(110.000f, 1); // Jan
        valueSet1.add(v1e1);


//        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
//        BarEntry v2e1 = new BarEntry(150.000f, 2); // Jan
//        valueSet2.add(v2e1);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
//        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Brand 2");
//        barDataSet2.setColors(Color.rgb(0, 155, 0));

        dataSets.add(barDataSet1);
//        dataSets.add(barDataSet2);

        List<String> labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");


        BarData data = new BarData(dataSets);
//        data.addDataSet(dataset);
//        data.addDataSet(dataset);
        barChart.setData(data);
//        XAxis xAxis = chart.getXAxis();
//        xAxis.setValueFormatter(new DateXAxisValueFormatter(pollDates));
//        chart.setData(lineData);
        barChart.invalidate();

    }

    @Override
    public void update(Observable o, Object arg) {

    }


    public void setFilename(int filename) {
        mFilename = filename;
    }


}
