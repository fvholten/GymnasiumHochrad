package de.hochrad.hochradapp.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import de.hochrad.hochradapp.R;
import de.hochrad.hochradapp.activites.startseite.MainActivity;
import de.hochrad.hochradapp.hilfsfunktionen.Logic;
import de.hochrad.hochradapp.hilfsfunktionen.Optionen;
import de.hochrad.hochradapp.loader.ParseUtilities;
//TODO

public class Service extends android.app.Service {

    Context context = this;
    Optionen optionen, optionen2;
    int hashcode;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        optionen = new Optionen(context, "hashcode");
        optionen2 = new Optionen(context, "auswahl");
        Executors.newSingleThreadScheduledExecutor().schedule
                (new Runnable() {
                    public void run() {
                        String url = "http://www.gymnasium-hochrad.de/Vertretungsplan/Vertretungsplan_Internet/frames/navbar.htm";
                        Connection connection = Jsoup.connect(url);
                        Document docW;
                        try {
                            docW = connection.get();
                            if (Logic.ToInteger(ParseUtilities.ParseWochennummerEinlesen(docW)) == null) {
                                hashcode = optionen.getFile();
                            } else {
                                String urlV = "http://www.gymnasium-hochrad.de/Vertretungsplan/Vertretungsplan_Internet/" +
                                        Logic.twoDigits(Logic.ToInteger(ParseUtilities.ParseWochennummerEinlesen(docW))) + "/w/w" + Logic.fiveDigits(optionen2.getFile()) + ".htm";
                                Connection connectionV = Jsoup.connect(urlV);
                                Document docV;
                                docV = connectionV.get();
                                hashcode = docV.text().hashCode();
                                //TODO ICH SPEICHERE DENN HASH UND BEKOMME IRGENDEINE ANDERE ZAHL ZURÜCK
                                optionen.putFile(hashcode, "hashcode");
                                int i = optionen.getFile();
                                if (i != hashcode) {

                                    Intent intent = new Intent(context, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    PendingIntent pendingIntent = PendingIntent.getActivity(Service.this, 0, intent, 0);

                                    Notification notification = new Notification.Builder(Service.this)
                                            .setTicker("Vertretungplanupdate")
                                            .setSmallIcon(R.drawable.vertretungsalart)
                                            .setContentTitle(getString(R.string.vertretungsplanupdate))
                                            .setContentText(getString(R.string.vertretungsplanupdatet))
                                            .setContentIntent(pendingIntent).getNotification();
                                    notification.flags = Notification.DEFAULT_ALL;
                                    notification.flags = Notification.FLAG_AUTO_CANCEL;
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                    notificationManager.notify(0, notification);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, 20, TimeUnit.DAYS);
    }

}
