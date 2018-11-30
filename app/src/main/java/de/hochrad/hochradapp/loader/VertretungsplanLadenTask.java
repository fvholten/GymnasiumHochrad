package de.hochrad.hochradapp.loader;


import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import de.hochrad.hochradapp.domain.Vertretung;
import de.hochrad.hochradapp.domain.Vertretungsplan;
import de.hochrad.hochradapp.hilfsfunktionen.Logic;

public class VertretungsplanLadenTask extends AsyncTask<String, Void, Vertretungsplan> {

    VertretungsplanLadenTaskCallBack callBack;
    int wochennummer;
    int hashcode;
    Document doc;

    public VertretungsplanLadenTask(int wochennummer, VertretungsplanLadenTaskCallBack callBack) {
        this.callBack = callBack;
        this.wochennummer = wochennummer;
    }
    @Override
    protected Vertretungsplan doInBackground(String... params) {
        Vertretungsplan vertretungsplan = new Vertretungsplan();
        if (!isCancelled()) {
            String url = "https://hochrad.de/idesk/plan/public.php/Vertretungsplan%20Schüler/55b3979bef1fa6b3/" + Logic.twoDigits(wochennummer) + "/w/w" + params[0] + ".htm";
            Connection connection = Jsoup.connect(url);
            try {
                doc = connection.get();
            } catch (IOException e) {
                return null;
            }
            //FÜR DEN SERVICE
            hashcode = doc.text().hashCode();
            ParseUtilities.ParseVertretungsplan(doc, vertretungsplan);
            return vertretungsplan;
        } else {
            Vertretung vertretung = new Vertretung();
            vertretung.Wochentag = ParseUtilities.ToWochentag("error");
            vertretung.Stunde = ParseUtilities.ToStunde("error");
            vertretung.Art = ParseUtilities.ToArt("error");
            vertretung.Fach = ParseUtilities.ToFach("error");
            vertretung.Raum = ParseUtilities.ToRaum("error");
            //vertretung.statt_Fach = ParseUtilities.ToFach("error");
            //vertretung.statt_Raum = ParseUtilities.ToRaum("error");
            vertretung.Informationen = ParseUtilities.ToInformation("error");
            vertretung.Klasse = ParseUtilities.ToKlasse("error");
            vertretungsplan.Hinzufügen(vertretung);
            return vertretungsplan;
        }
    }
    protected void onPostExecute(Vertretungsplan result) {
        callBack.vertretungsplanLaden(hashcode, result);
    }
}
