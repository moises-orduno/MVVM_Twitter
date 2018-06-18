package com.moisesorduno.twittermvvm.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateParser {

    public static String parseDate(Date date){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(date);

    }

    public static  String formatTweetDateToDate(String date) {

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.ENGLISH);
        sdf.setLenient(true);

        Date tweetDate=null;
        try {
            tweetDate= sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(tweetDate);
        cal2.setTime(new Date());
        boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);

        if(sameDay){
           return new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(tweetDate);
        }else{
            return new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(tweetDate);
        }

    }

}
