package com.example.ngocphong.sqliteexample.sqlite;

import android.icu.util.Calendar;

import java.util.Date;

/**
 * Created by ngocphong on 08/12/2016.
 */
public class Run {
    private long mId;
    private Date mStartDate;

    public Run() {
        mId = -1;
        mStartDate = new Date();
    }
    public long getId() {
        return mId;
    }
    public void setId(long id) {
        mId = id;
    }

    public Date getStartDate() {
        return mStartDate;
    }
}
