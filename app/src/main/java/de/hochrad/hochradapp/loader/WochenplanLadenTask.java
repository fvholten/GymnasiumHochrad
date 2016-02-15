package de.hochrad.hochradapp.loader;

import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

//TODO
public class WochenplanLadenTask extends AsyncTask<Void, Void, String[]> {

    WochenplanLadenTaskCallBack callBack;

    public WochenplanLadenTask(WochenplanLadenTaskCallBack callBack){
        this.callBack = callBack;
    }
    @Override
    protected String[] doInBackground(Void... params) {

        String url = "http://www.gymnasium-hochrad.de/wochenplan/";
        Connection connection = Jsoup.connect(url);
        Document doc;
        try {
            doc = connection.get();
        } catch (IOException e) {
            return null;
        }
//        ParseUtilities.ParseWochenplanEinlesen(doc);

        return null;
    }

    protected void onPostExecute(String[] result) {
        callBack.WochenplanLaden(result);
    }
}
