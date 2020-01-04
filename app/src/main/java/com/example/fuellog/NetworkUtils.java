package com.example.fuellog;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.fuellog.GetCar.logLink;

public class NetworkUtils {

    private static final String LOG_TAG =
            NetworkUtils.class.getSimpleName();

    // Base URL for Vehicle API.
    public static final String CAR_BASE_URL = logLink;
    // Parameter to specify XML return data.
    private static final String FORMAT = "format";

    static String getBookInfo(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String carXMLString = null;

        try {
            Uri builtURI = Uri.parse(CAR_BASE_URL).buildUpon()
                    .appendQueryParameter(FORMAT, "XML")
                    .build();

            URL requestURL = new URL(builtURI.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            InputStream inputStream = urlConnection.getInputStream();


            reader = new BufferedReader(new InputStreamReader(inputStream));


            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);

                builder.append("\n");
            }

            if (builder.length() == 0) {

                return null;
            }

            carXMLString = builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(LOG_TAG, carXMLString);
        return carXMLString;
    }
}
