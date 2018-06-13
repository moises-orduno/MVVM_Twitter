package com.moisesorduno.twittermvvm.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
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
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.moisesorduno.twittermvvm.R;
import com.moisesorduno.twittermvvm.adapter.DateXAxisValueFormatter;
import com.moisesorduno.twittermvvm.common.CSVReader;
import com.moisesorduno.twittermvvm.model.Poll;
import com.moisesorduno.twittermvvm.viewmodel.StatisticsViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class StatisticsFragment extends Fragment implements java.util.Observer {

    public static final String TAG = StatisticsFragment.class.getSimpleName();


    private TextView mTextViewTitle;
    private LineChart mChart;
    private String mTitle;
    private int mFilename;
    private int [] mColors={Color.BLUE,Color.rgb(0, 153, 51),Color.rgb(255,155,102),Color.RED};

    public StatisticsFragment() {
        // Required empty public constructor
    }

    public static StatisticsFragment newInstance(Poll poll) {
        StatisticsFragment fragment = new StatisticsFragment();

        fragment.setTitle(poll.getName());
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


        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        mTextViewTitle = view.findViewById(R.id.tv_title);

        mChart = view.findViewById(R.id.chart);
        loadStatistics();

        return view;
    }

    private void loadStatistics() {

        mTextViewTitle.setText(String.format("%s%s", getContext().getString(R.string.tag_poll), mTitle));


        List<String[]> rows = new ArrayList<>();
        CSVReader csvReader = new CSVReader();
        try {
            rows = csvReader.readCSV(getResources().openRawResource(mFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] pollDates  = new String[rows.get(1).length];
        LineData lineData = new LineData();

        for (int j = 1; j < 5; j++) {

            List<Entry> entries = new ArrayList<>();



            for (int i = 2; i < rows.get(0).length; i++) {
                entries.add(new Entry(i, Float.valueOf(rows.get(j)[i])));
                pollDates[i] = rows.get(0)[i].substring(3);
            }
            LineDataSet dataSet = new LineDataSet(entries, rows.get(j)[0]); // add entries to dataset
            dataSet.setCircleColor(mColors[j-1]);
            dataSet.setColor(mColors[j-1]);
            lineData.addDataSet(dataSet);

        }



//
//        LineDataSet dataSet = new LineDataSet(entries, rows.get(1)[0]); // add entries to dataset
//        dataSet.setCircleColor(Color.BLACK);
//        dataSet.setColor(Color.BLACK);
//
//
//        LineData lineData = new LineData(dataSet);




        XAxis xAxis = mChart.getXAxis();
        xAxis.setValueFormatter(new DateXAxisValueFormatter(pollDates));
        mChart.setData(lineData);
        mChart.invalidate();

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

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setFilename(int filename) {
        mFilename = filename;
    }


}
