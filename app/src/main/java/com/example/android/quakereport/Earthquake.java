package com.example.android.quakereport;

/**
 * Created by ChadYeo on 1/22/17.
 */
public class Earthquake {
    private Double mMag;
    private String mLocation;
    private long mTimeInMilliseconds;

    public Earthquake(double mag, String location, long timeInMilliseconds) {
        mMag = mag;
        mLocation = location;
        mTimeInMilliseconds = timeInMilliseconds;
    }

    public Double getMag() {
        return mMag;
    }

    public String getLocation() {
        return mLocation;
    }

    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }
}
