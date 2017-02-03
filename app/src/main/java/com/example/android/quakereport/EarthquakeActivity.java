package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Earthquake>>{

    private ProgressBar mProgressBar;
    private EarthquakeAdapter mAdapter;
    private TextView mEmptyTextView;

    private ListView mEarthquakeListView;
    private static final String LOG_TAG = Earthquake.class.getName();

    private static final int EARTHQUAKE_LOADER_ID = 1;

    private static final String USGS_REQUEST_URL = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_main);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.isIndeterminate();
        mEarthquakeListView = (ListView) findViewById(R.id.list);
        mEmptyTextView = (TextView) findViewById(R.id.empty_list_view);

        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        mEarthquakeListView.setAdapter(mAdapter);
        mEarthquakeListView.setEmptyView(mEmptyTextView);

        mEarthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Earthquake currentEarthquake = mAdapter.getItem(position);

                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                startActivity(websiteIntent);
            }
        });

        ConnectivityManager cm = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
            Log.i(LOG_TAG, "loaderManger.initLoader is initiated");
        } else {
            mProgressBar.setVisibility(View.GONE);
            mEmptyTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        Log.i(LOG_TAG, "onCreateLoader is initiated");
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> data) {
        mEmptyTextView.setText(R.string.no_earthquakes);
        mProgressBar.setVisibility(View.GONE);

        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
        Log.i(LOG_TAG, "onLoadFinished is initiated");
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        mAdapter.clear();
        Log.i(LOG_TAG, "onLoaderReset is initiated");
    }
}
