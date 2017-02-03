package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ChadYeo on 1/22/17.
 */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Context context, List<Earthquake> objects) {
        super(context, 0, objects);
    }

    private List<Earthquake> earthquakes = new ArrayList<Earthquake>();

    static class ViewHolder {
        TextView magTextView;
        TextView nearbyTextView;
        TextView locationTextView;
        TextView dateTextView;
        TextView hourTextView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_info, parent, false);
        }

        Earthquake currentPosition = getItem(position);

        ViewHolder viewHolder = new ViewHolder();

        viewHolder.magTextView = (TextView) convertView.findViewById(R.id.mag_textView);
        Double mag = currentPosition.getMag();
        DecimalFormat df = new DecimalFormat("0.0");
        viewHolder.magTextView.setText(df.format(mag));

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magCircle = (GradientDrawable) viewHolder.magTextView.getBackground();

        int magColor = getMagnitudeColor(currentPosition.getMag());

        magCircle.setColor(magColor);

        String originalLocation = currentPosition.getLocation();
        String nearbyText;
        String locationText;

        if (originalLocation.contains(" of ")) {
            String[] parts = originalLocation.split(" of ");
            nearbyText = parts[0] + " of ";
            locationText = parts[1];
        } else {
            nearbyText = "Near by ";
            locationText = originalLocation;
        }

        viewHolder.nearbyTextView = (TextView) convertView.findViewById(R.id.nearby_textView);
        viewHolder.nearbyTextView.setText(nearbyText);

        viewHolder.locationTextView = (TextView) convertView.findViewById(R.id.location_textView);
        viewHolder.locationTextView.setText(locationText);

        viewHolder.dateTextView = (TextView) convertView.findViewById(R.id.date_textView);
        Date dateView = new Date(currentPosition.getTimeInMilliseconds());
        String formattedDate = formatDate(dateView);
        viewHolder.dateTextView.setText(formattedDate);

        viewHolder.hourTextView = (TextView) convertView.findViewById(R.id.hour_textView);
        String formattedTime = formatTime(dateView);
        viewHolder.hourTextView.setText(formattedTime);

        return convertView;
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        return dateFormat.format(dateObject);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }

        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

    public void setEarthquakes(List<Earthquake> data) {
        earthquakes.addAll(data);
        notifyDataSetChanged();
    }
}
