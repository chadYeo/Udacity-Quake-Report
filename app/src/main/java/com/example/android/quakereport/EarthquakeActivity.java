package com.example.android.quakereport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_main);

        // Create a fake list of earthquake locations.
        ArrayList<Earthquake> earthquakes = new ArrayList<Earthquake>();
        earthquakes.add(new Earthquake(7.2, "San Francisco", "Feb 2, 2016"));
        earthquakes.add(new Earthquake(6.1, "Tokyo", "July 2, 2016"));
        earthquakes.add(new Earthquake(3.9, "Mexico City", "Nov 2, 2016"));
        earthquakes.add(new Earthquake(5.4, "Moscow", "May 2, 2016"));
        earthquakes.add(new Earthquake(2.8, "Rio de Janeiro", "Jan 2, 2016"));
        earthquakes.add(new Earthquake(1.6, "Paris", "Oct 2, 2016"));

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        EarthquakeAdapter adapter = new EarthquakeAdapter(this, earthquakes);

        earthquakeListView.setAdapter(adapter);
    }
}
