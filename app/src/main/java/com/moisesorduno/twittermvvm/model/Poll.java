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

    public PollType getPollType() {
        return pollType;
    }

    public enum PollType {

        LINES,
        BAR ,
        PIE ,
        FALL ,



    }
}
