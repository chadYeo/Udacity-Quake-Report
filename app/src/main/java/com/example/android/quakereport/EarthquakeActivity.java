package com.example.android.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_main);

        // Create a fake list of earthquake locations.
        final ArrayList<Earthquake> earthquakes = new QueryUtils().extractEarthquakes();


        // Find a reference to the {@link ListView} in the layout
        final ListView earthquakeListView = (ListView) findViewById(R.id.list);

        EarthquakeAdapter adapter = new EarthquakeAdapter(this, earthquakes);

        earthquakeListView.setAdapter(adapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String url = earthquakes.get(position).getUrl();

                Uri uri = Uri.parse(url);

                showPage(uri);
            }
        });
    }

    public void showPage(Uri uri) {
        Intent pageIntent = new Intent(Intent.ACTION_VIEW);
        pageIntent.setData(uri);
        if (pageIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(pageIntent);
        }
    }
}
