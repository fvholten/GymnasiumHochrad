package de.hochrad.hochradapp.loader;

import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hochrad.hochradapp.hilfsfunktionen.Logic;

public class NachrichtenDesTagesLadenTask extends AsyncTask<Void, Void, List<String>> {
    NachrichtenDesTagesLadenTaskCallBack callBack;
    Integer wochennummer;
    Document doc;

    public NachrichtenDesTagesLadenTask(Integer wochennummer, NachrichtenDesTagesLadenTaskCallBack callBack) {
        this.wochennummer = wochennummer;
        this.callBack = callBack;
    }

    @Override
    protected List<String> doInBackground(Void... params) {
        if (!isCancelled()) {
            String url = "http://www.gymnasium-hochrad.de/Vertretungsplan/Vertretungsplan_Internet/" + Logic.twoDigits(wochennummer) + "/w/w" + Logic.fiveDigits(1) + ".htm";
            Connection connection = Jsoup.connect(url);
            try {
                doc = connection.get();
            } catch (IOException e) {
                return null;
            }
            return ParseUtilities.ParseNachrichtenDesTages(doc);
        } else {
            List<String> dummy = new ArrayList<>();
            dummy.add("0");
            return dummy;
        }
    }

    protected void onPostExecute(List<String> result) {
        callBack.NachrichtenDesTages(result);
    }
}
