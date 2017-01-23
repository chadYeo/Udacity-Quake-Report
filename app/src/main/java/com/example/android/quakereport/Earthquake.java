package com.example.android.quakereport;

/**
 * Created by ChadYeo on 1/22/17.
 */
public class Earthquake {
    private Double mMag;
    private String mLocation;
    private String mDate;

    public Earthquake(double mag, String location, String date) {
        mMag = mag;
        mLocation = location;
        mDate = date;
    }

    public Double getmMag() {
        return mMag;
    }

    public String getmLocation() {
        return mLocation;
    }

    public String getmDate() {
        return mDate;
    }
}
