package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

    private static final String USGS_REQUEST_URL = "http://earthquake.usgs.gov/fdsnws/event/1/query";

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String minMagnitude = sharedPreferences.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));

        String orderBy = sharedPreferences.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));

        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "10");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);

        Log.i(LOG_TAG, "onCreateLoader is initiated");
        return new EarthquakeLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> data) {
        mEmptyTextView.setText(R.string.no_earthquakes);
        mProgressBar.setVisibility(View.GONE);

        mAdapter.clear();
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
