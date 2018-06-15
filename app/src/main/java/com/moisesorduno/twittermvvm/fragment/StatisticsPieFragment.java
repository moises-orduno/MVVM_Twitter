package com.moisesorduno.twittermvvm.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.MPPointF;
import com.moisesorduno.twittermvvm.R;
import com.moisesorduno.twittermvvm.common.CSVReader;
import com.moisesorduno.twittermvvm.model.Poll;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static com.moisesorduno.twittermvvm.fragment.ParentViewPagerFragment.USERS_COLORS;

public class StatisticsPieFragment extends Fragment implements java.util.Observer {

    public static final String TAG = StatisticsPieFragment.class.getSimpleName();

    private PieChart mPieChart;
    private int mFilename;


    public StatisticsPieFragment() {
        // Required empty public constructor
    }

    public static StatisticsPieFragment newInstance(Poll poll) {
        StatisticsPieFragment fragment = new StatisticsPieFragment();

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

        View view = inflater.inflate(R.layout.fragment_statistics_pie, container, false);

        mPieChart = view.findViewById(R.id.chart);

        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);

        mPieChart.setDragDecelerationFrictionCoef(0.1f);

//        mPieChart.setCenterTextTypeface(mTfLight);
        mPieChart.setCenterText("She's my little rock and roll");

        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColor(Color.WHITE);

        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);

        mPieChart.setHoleRadius(58f);

        mPieChart.setDrawCenterText(true);

        mPieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mPieChart.setRotationEnabled(false);
        mPieChart.setHighlightPerTapEnabled(false);



        loadStatistics();

        return view;
    }

    private void setData(List<String[]> rows) {

        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 1; i < rows.size() ; i++) {
            entries.add(new PieEntry(Float.parseFloat(rows.get(i)[1]),
                    rows.get(i)[0]));
        }

        PieDataSet dataSet = new PieDataSet(entries, getString(R.string.tag_presidential_results));

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);



        dataSet.setColors(USERS_COLORS);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
//        data.setValueTypeface(mTfLight);
        mPieChart.setData(data);

        // undo all highlights
        mPieChart.highlightValues(null);

        mPieChart.invalidate();
    }

    private void loadStatistics() {

        List<String[]> rows = new ArrayList<>();
        CSVReader csvReader = new CSVReader();
        try {
            rows = csvReader.readCSV(getResources().openRawResource(mFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setData(rows);
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



    @Override
    public void update(Observable o, Object arg) {

    }


    public void setFilename(int filename) {
        mFilename = filename;
    }


}
