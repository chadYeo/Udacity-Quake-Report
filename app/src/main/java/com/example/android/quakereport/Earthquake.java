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

    public Double getMag() {
        return mMag;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getDate() {
        return mDate;
    }
}
