package com.moisesorduno.twittermvvm.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateParser {

    public static String parseDate(Date date){
        return new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(date);

    }

    public static  Date formatTweetDateToDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.ENGLISH);
        sdf.setLenient(true);

        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }

}
