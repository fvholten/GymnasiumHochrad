package de.hochrad.hochradapp.loader;


import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import de.hochrad.hochradapp.domain.Vertretungsplan;
import de.hochrad.hochradapp.hilfsfunktionen.Logic;

public class VertretungsplanLadenTask extends AsyncTask<String, Void, Vertretungsplan> {

    VertretungsplanLadenTaskCallBack callBack;
    int wochennummer;
    public VertretungsplanLadenTask(int wochennummer, VertretungsplanLadenTaskCallBack callBack) {
        this.callBack = callBack;
        this.wochennummer = wochennummer;
    }
    @Override
    protected Vertretungsplan doInBackground(String... params) {
        Vertretungsplan vertretungsplan = new Vertretungsplan();
        String url = "http://www.gymnasium-hochrad.de/Vertretungsplan/Vertretungsplan_Internet/" + Logic.twoDigits(wochennummer) + "/w/w" + params[0] + ".htm";

        Connection connection = Jsoup.connect(url);
        Document doc;
        try {
            doc = connection.get();
            ParseUtilities.ParseVertretungsplan(doc, vertretungsplan);
            if (vertretungsplan == null) {
                return null;
            }else {
                return vertretungsplan;
            }
        } catch (IOException e) {
            return null;
        }

    }

    protected void onPostExecute(Vertretungsplan result) {
        callBack.VertretungsplanLaden(result);
    }
}
