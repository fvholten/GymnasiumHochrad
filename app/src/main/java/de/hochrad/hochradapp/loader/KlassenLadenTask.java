package de.hochrad.hochradapp.loader;


import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class KlassenLadenTask extends AsyncTask<Void, Void, String[]>{

    KlassenLadenTaskCallBack callBack;
    int wochenauswahl;

    public KlassenLadenTask (int wochenauswahl, KlassenLadenTaskCallBack callBack) {
        this.callBack = callBack;
        this.wochenauswahl = wochenauswahl;
    }

    @Override
    protected String[] doInBackground(Void... params) {
        String url = "http://www.gymnasium-hochrad.de/Vertretungsplan/Vertretungsplan_Internet/frames/navbar.htm";

        Connection connection = Jsoup.connect(url);
        Document doc;
        try {
            doc = connection.get();
            if (ParseUtilities.ParseKlassenEinlesen(doc) == null) {
                return null;
            }else {
                return ParseUtilities.ParseKlassenEinlesen(doc);
            }
        } catch (IOException e) {
            return null;
        }

    }

    protected void onPostExecute(String[] result) {
        callBack.KlassenLaden(wochenauswahl, result);
    }
}
