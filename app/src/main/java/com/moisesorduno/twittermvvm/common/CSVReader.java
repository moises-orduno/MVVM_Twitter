package com.moisesorduno.twittermvvm.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {


    private List<String[]> mRows = new ArrayList<>();


    public List<String[]> readCSV(InputStream is) throws IOException {
        InputStreamReader isr = new InputStreamReader(is);

        BufferedReader br = new BufferedReader(isr);
        String line;
        String csvSplitBy = ",";

        br.readLine();

        while ((line = br.readLine()) != null) {
            String[] row = line.split(csvSplitBy);
            mRows.add(row);
        }
        return mRows;
    }

}
