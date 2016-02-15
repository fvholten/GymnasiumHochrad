package de.hochrad.hochradapp.loader;


import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import de.hochrad.hochradapp.hilfsfunktionen.Logic;

public class WochennummerLadenTask extends AsyncTask<Void, Void, Integer>{
    private WochennummerLadenTaskCallBack callBack;
    int wochenauswahl, klassenauswahl;
    boolean mitklassenauswahl;
    public WochennummerLadenTask(boolean mitklassenauswahl, int klassenauswahl, int wochenauswahl, WochennummerLadenTaskCallBack callBack) {
        this.callBack = callBack;
        this.wochenauswahl = wochenauswahl;
        this.klassenauswahl = klassenauswahl;
        this.mitklassenauswahl = mitklassenauswahl;
    }
    @Override
    protected Integer doInBackground(Void... params) {
        String url = "http://www.gymnasium-hochrad.de/Vertretungsplan/Vertretungsplan_Internet/frames/navbar.htm";
        Connection connection = Jsoup.connect(url);
        Document doc;
        try {
            doc = connection.get();
            if (Logic.ToInteger(ParseUtilities.ParseWochennummerEinlesen(doc)) == null) {
                return null;
            }else {
                return Logic.ToInteger(ParseUtilities.ParseWochennummerEinlesen(doc));
            }
        } catch (IOException e) {
            return null;
        }
    }

    protected void onPostExecute(Integer result) {
        callBack.WochennummerLaden(mitklassenauswahl, klassenauswahl, wochenauswahl, result);
    }
}
