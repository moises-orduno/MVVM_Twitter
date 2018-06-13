package com.moisesorduno.twittermvvm.common;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.moisesorduno.twittermvvm.adapter.DateXAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class GraphBuilder {

    private int [] mColors={Color.BLUE,Color.rgb(0, 153, 51),Color.rgb(255,155,102),Color.RED};


    public void buildLineData(List<String[]> rows,LineChart chart) {

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


        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new DateXAxisValueFormatter(pollDates));
        chart.setData(lineData);
        chart.invalidate();

    }

}
