package de.hochrad.hochradapp.loader;


import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class KlassenLadenTask extends AsyncTask<Void, Void, String[]> {

    KlassenLadenTaskCallBack callBack;
    int wochenauswahl;
    Document doc;

    public KlassenLadenTask(int wochenauswahl, KlassenLadenTaskCallBack callBack) {
        this.callBack = callBack;
        this.wochenauswahl = wochenauswahl;
    }

    @Override
    protected String[] doInBackground(Void... params) {
        if (!isCancelled()) {
            String url = "https://hochrad.de/idesk/plan/public.php/Vertretungsplan%20Schüler/55b3979bef1fa6b3/frames/navbar.htm";
            Connection connection = Jsoup.connect(url);

            try {
                doc = connection.get();
            } catch (IOException e) {
                return null;
            }
            return ParseUtilities.ParseKlassenEinlesen(doc);

        } else return new String[1];


    }

    protected void onPostExecute(String[] result) {
        callBack.KlassenLaden(wochenauswahl, result);
    }
}
