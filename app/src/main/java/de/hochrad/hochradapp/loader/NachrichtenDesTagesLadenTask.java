package de.hochrad.hochradapp.loader;

import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

import de.hochrad.hochradapp.hilfsfunktionen.Logic;

public class NachrichtenDesTagesLadenTask extends AsyncTask<Void, Void, List<String>> {
    NachrichtenDesTagesLadenTaskCallBack callBack;
    Integer wochennummer;

    public NachrichtenDesTagesLadenTask(Integer wochennummer, NachrichtenDesTagesLadenTaskCallBack callBack) {
        this.wochennummer = wochennummer;
        this.callBack = callBack;
    }

    @Override
    protected List<String> doInBackground(Void... params) {

        String url = "http://www.gymnasium-hochrad.de/Vertretungsplan/Vertretungsplan_Internet/" + Logic.twoDigits(wochennummer) + "/w/w" + Logic.fiveDigits(1) + ".htm";
        Connection connection = Jsoup.connect(url);
        Document doc;
        try {
            doc = connection.get();
            if (ParseUtilities.ParseNachrichtenDesTages(doc) == null) {
                return null;
            }else {
                return ParseUtilities.ParseNachrichtenDesTages(doc);
            }
        } catch (IOException e) {
            return null;
        }
    }

    protected void onPostExecute(List<String> result) {
        callBack.NachrichtenDesTages(result);
    }
}
