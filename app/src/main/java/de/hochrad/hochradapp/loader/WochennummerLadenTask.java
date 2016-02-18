package de.hochrad.hochradapp.loader;


import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import de.hochrad.hochradapp.hilfsfunktionen.Logic;

public class WochennummerLadenTask extends AsyncTask<Void, Void, Integer> {
    private WochennummerLadenTaskCallBack callBack;
    int wochenauswahl, klassenauswahl;
    boolean mitklassenauswahl;
    Document doc;

    public WochennummerLadenTask(boolean mitklassenauswahl, int klassenauswahl, int wochenauswahl, WochennummerLadenTaskCallBack callBack) {
        this.callBack = callBack;
        this.wochenauswahl = wochenauswahl;
        this.klassenauswahl = klassenauswahl;
        this.mitklassenauswahl = mitklassenauswahl;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        if (!isCancelled()) {
            String url = "http://www.gymnasium-hochrad.de/Vertretungsplan/Vertretungsplan_Internet/frames/navbar.htm";
            Connection connection = Jsoup.connect(url);
            try {
                doc = connection.get();
            } catch (IOException e) {
                return null;
            }
            return Logic.ToInteger(ParseUtilities.ParseWochennummerEinlesen(doc));
        } else return 0;
    }


    protected void onPostExecute(Integer result) {
        callBack.WochennummerLaden(mitklassenauswahl, klassenauswahl, wochenauswahl, result);
    }
}
