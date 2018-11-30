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

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import de.hochrad.hochradapp.R;
import de.hochrad.hochradapp.activities.AppActivity;
import de.hochrad.hochradapp.hilfsfunktionen.FileWR;
import de.hochrad.hochradapp.hilfsfunktionen.Logic;
import de.hochrad.hochradapp.loader.ParseUtilities;

public class Service extends android.app.Service {

    Context context = this;
    FileWR fileWR;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        fileWR = new FileWR();
        Executors.newSingleThreadScheduledExecutor().schedule
                (new Runnable() {
                    public void run() {
                        if (fileWR.ladeFile(getFilesDir() + File.separator + "switch") == 2) {
                            String url = "http://www.gymnasium-hochrad.de/Vertretungsplan/Vertretungsplan_Internet/frames/navbar.htm";
                            Connection connection = Jsoup.connect(url);
                            Document docW;
                            try {
                                docW = connection.get();
                                String urlV = "http://www.gymnasium-hochrad.de/Vertretungsplan/Vertretungsplan_Internet/" +
                                        Logic.twoDigits(Logic.ToInteger(ParseUtilities.ParseWochennummerEinlesen(docW))) + "/w/w" + Logic.fiveDigits(fileWR.ladeFile(getFilesDir() + File.separator + "auswahl")) + ".htm";
                                Connection connectionV = Jsoup.connect(urlV);
                                Document docV;
                                docV = connectionV.get();

                                if (fileWR.ladeFile(getFilesDir() + File.separator + "vertretungsplanhash") != docV.text().hashCode()) {
                                    fileWR.writeFile(docV.text().hashCode(), getFilesDir() + File.separator + "vertretungsplanhash");
                                    Intent intent = new Intent(context, AppActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    PendingIntent pendingIntent = PendingIntent.getActivity(Service.this, 0, intent, 0);

                                    Notification notification = new Notification.Builder(Service.this)
                                            .setTicker("Vertretungplanupdate")
                                            .setSmallIcon(R.drawable.ic_view_list_black_24px)
                                            .setContentTitle(getString(R.string.app_name))
                                            .setContentText(getString(R.string.vertretungsplanupdate))
                                            .setContentIntent(pendingIntent)
                                            .getNotification();
                                    notification.flags = Notification.DEFAULT_ALL;
                                    notification.flags = Notification.FLAG_AUTO_CANCEL;
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                    notificationManager.notify(0, notification);
                                }
                            } catch (IOException ignored) {
                            }
                        }
                    }
                }, fileWR.ladeFile(getFilesDir() + File.separator + "servicezeit"), TimeUnit.HOURS);
    }
}
