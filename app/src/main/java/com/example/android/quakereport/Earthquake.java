package com.example.android.quakereport;

/**
 * Created by ChadYeo on 1/22/17.
 */
public class Earthquake {
    private Double mMag;
    private String mLocation;
    private long mTimeInMilliseconds;
    private String mUrl;

    public Earthquake(double mag, String location, long timeInMilliseconds, String url) {
        mMag = mag;
        mLocation = location;
        mTimeInMilliseconds = timeInMilliseconds;
        mUrl = url;
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

    public String getUrl() {
        return mUrl;
    }
}
