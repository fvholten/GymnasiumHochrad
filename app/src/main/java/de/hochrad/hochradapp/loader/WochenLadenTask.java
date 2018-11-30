package de.hochrad.hochradapp.loader;

import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class WochenLadenTask extends AsyncTask<Void, Void, String[]> {
    WochenLadenTaskCallBack callBack;
    Document doc;

    public WochenLadenTask(WochenLadenTaskCallBack callBack) {
        this.callBack = callBack;
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
            return ParseUtilities.ParseWochenEinlesen(doc);
        } else return new String[1];
    }

    protected void onPostExecute(String[] result) {
        callBack.wochenLaden(result);
    }
}
