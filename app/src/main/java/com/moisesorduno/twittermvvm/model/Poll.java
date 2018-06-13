package com.moisesorduno.twittermvvm.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Poll {

    private String name;
    private int filename;
    private PollType pollType;

    public String getName() {
        return name;
    }

    public Poll(String name, int filename, PollType pollType) {
        this.name = name;
        this.filename = filename;
        this.pollType = pollType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFilename() {
        return filename;
    }

    public void setFilename(int filename) {
        this.filename = filename;
    }

    public static class PollType {

        public static final int BLOOMBERG = 0;
        public static final int MITOFSKY = 1;
        private static final int SUMMER = 2;
        private static final int FALL = 3;


        public PollType(@Season int season) {
            System.out.println("Season :" + season);
        }

        @IntDef({BLOOMBERG, MITOFSKY, SUMMER, FALL})
        @Retention(RetentionPolicy.SOURCE)
        public @interface Season {
        }

    }
}
