package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChadYeo on 1/23/17.
 */
public class QueryUtils {

    static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public QueryUtils() {
    }

    // Returns new URL object from the given String URL
    private static URL createURL(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    // Make an HTTP request to the given URL and return a String as the response.
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(100000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    // Converting Stream to String
    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    // JSON Response Parsing: Return a list of objects that has been built up from parsing the given JSON response.
    private static List<Earthquake> extractFeatureFromJson(String earthquakeJSON) {
        if (TextUtils.isEmpty(earthquakeJSON)) {
            return null;
        }

        List<Earthquake> earthquakes = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);
            JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");

            for (int i=0; i<earthquakeArray.length(); i++) {
                JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);
                JSONObject properties = currentEarthquake.getJSONObject("properties");
                double magnitude = properties.getDouble("mag");
                String location = properties.getString("place");
                long time = properties.getLong("time");
                String url = properties.getString("url");

                Earthquake earthquake = new Earthquake(magnitude, location, time, url);

                earthquakes.add(earthquake);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON result", e);
        }
        return earthquakes;
    }

    // fetchEarthquakeData() helper method that ties all the steps together
    public static List<Earthquake> fetchEarthquakeData(String requestUrl) {
        URL url = createURL(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request", e);
        }

        List<Earthquake> earthquakes = extractFeatureFromJson(jsonResponse);

        Log.i(LOG_TAG, "fetchEarthquakeData is initiated");

        return earthquakes;
    }
}
