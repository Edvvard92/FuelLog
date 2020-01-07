package com.example.fuellog;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.lang.ref.WeakReference;
import java.util.HashMap;

public class GetEmissions extends AsyncTask<String, Void, String> {

    private WeakReference<TextView> mReturnID, mReturnMPG;

    GetEmissions(TextView carText) {

        this.mReturnID = new WeakReference<>(carText);
        this.mReturnMPG = new WeakReference<>(carText);
    }


    @Override
    protected String doInBackground(String... strings) {
        return com.example.fuellog.NetworkUtilsFuel.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        String emissions;


        try {
            HashMap<String, String> data = parseXml(s);
            emissions = data.get("score");
            if (emissions != null) {

                mReturnMPG.get().setText(emissions);
            } else {
                mReturnID.get().setText(R.string.no_results);
                mReturnMPG.get().setText("");
            }

        } catch (Exception e) {
            mReturnID.get().setText(R.string.no_results);
            mReturnMPG.get().setText("");
            e.printStackTrace();



        }

    }


    public HashMap<String, String> parseXml(String xml) {
        XmlPullParserFactory factory;
        String tagName = "";
        String text = "";
        HashMap<String, String> hm = new HashMap<String, String>();

        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            StringReader sr = new StringReader(xml);
            xpp.setInput(sr);
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.TEXT) {
                    text = xpp.getText(); //Pulling out node text
                } else if (eventType == XmlPullParser.END_TAG) {
                    tagName = xpp.getName();

                    hm.put(tagName, text);

                    text = ""; //Reset text for the next node
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("Exception attribute", e + "+" + tagName);
        }

        return hm;
    }
}