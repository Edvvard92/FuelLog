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

public class GetMPG extends AsyncTask<String, Void, String> {

    private WeakReference<TextView> mReturnMPG;
    public static final String logMPG = "https://www.fueleconomy.gov/ws/rest/ympg/shared/ympgVehicle/26425";

    GetMPG(TextView carText) {
        this.mReturnMPG = new WeakReference<>(carText);
    }


    @Override
    protected String doInBackground(String... strings) {
        return com.example.fuellog.NetworkUtils.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        String mpg;

        try {
            HashMap<String, String> data = parseXml(s);
            mpg = data.get("avgMpg");
            if (mpg != null) {
                mReturnMPG.get().setText(mpg);
            } else {
                mReturnMPG.get().setText(R.string.no_results);
            }

        } catch (Exception e) {
            mReturnMPG.get().setText(R.string.no_results);
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