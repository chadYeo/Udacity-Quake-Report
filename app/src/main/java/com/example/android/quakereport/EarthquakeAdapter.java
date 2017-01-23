package com.example.android.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by ChadYeo on 1/22/17.
 */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Context context, List<Earthquake> objects) {
        super(context, 0, objects);
    }

    static class ViewHolder {
        TextView magTextView;
        TextView locationTextView;
        TextView dateTextVies;
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
        Double result = currentPosition.getMag();
        NumberFormat nf = NumberFormat.getNumberInstance();
        viewHolder.magTextView.setText(nf.format(result));

        viewHolder.locationTextView = (TextView) convertView.findViewById(R.id.location_textView);
        viewHolder.locationTextView.setText(currentPosition.getLocation());

        viewHolder.dateTextVies = (TextView) convertView.findViewById(R.id.date_textView);
        viewHolder.dateTextVies.setText(currentPosition.getDate());

        return convertView;
    }
}
