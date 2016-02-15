package de.hochrad.hochradapp.loader;

import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class WochenLadenTask extends AsyncTask<Void, Void, String[]> {
    WochenLadenTaskCallBack callBack;

    public WochenLadenTask(WochenLadenTaskCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected String[] doInBackground(Void... params) {
        String url = "http://www.gymnasium-hochrad.de/Vertretungsplan/Vertretungsplan_Internet/frames/navbar.htm";
        Connection connection = Jsoup.connect(url);
        Document doc;
        try {
            doc = connection.get();
            if (ParseUtilities.ParseWochenEinlesen(doc) == null) {
                return null;
            } else {
                return ParseUtilities.ParseWochenEinlesen(doc);
            }
        } catch (IOException e) {
            return null;
        }

    }

    protected void onPostExecute(String[] result) {
        callBack.WochenLaden(result);
    }
}
