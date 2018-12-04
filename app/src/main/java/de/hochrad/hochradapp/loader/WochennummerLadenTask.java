package de.hochrad.hochradapp.loader;


import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import de.hochrad.hochradapp.hilfsfunktionen.Logic;

public class WochennummerLadenTask extends AsyncTask<Void, Void, Integer> {
    private WochennummerLadenTaskCallBack callBack;
    private int wochenauswahl, klassenauswahl;
    private boolean mitklassenauswahl;

    public WochennummerLadenTask(boolean mitklassenauswahl, int klassenauswahl, int wochenauswahl, WochennummerLadenTaskCallBack callBack) {
        this.callBack = callBack;
        this.wochenauswahl = wochenauswahl;
        this.klassenauswahl = klassenauswahl;
        this.mitklassenauswahl = mitklassenauswahl;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        if (!isCancelled()) {
            String url = "https://hochrad.de/idesk/plan/public.php/Vertretungsplan%20Schüler/55b3979bef1fa6b3/frames/navbar.htm";
            Connection connection = Jsoup.connect(url);
            Document doc;
            try {
                doc = connection.get();
            } catch (IOException e) {
                return null;
            }
            return Logic.ToInteger(ParseUtilities.ParseWochennummerEinlesen(doc));
        } else return 0;
    }


    protected void onPostExecute(Integer result) {
        callBack.loadWeekNumber(mitklassenauswahl, klassenauswahl, wochenauswahl, result);
    }
}
