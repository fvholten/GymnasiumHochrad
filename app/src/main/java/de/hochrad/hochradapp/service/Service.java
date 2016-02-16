package de.hochrad.hochradapp.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import de.hochrad.hochradapp.R;
import de.hochrad.hochradapp.activites.startseite.MainActivity;
//TODO

public class Service extends android.app.Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//    Context context = this;
//    boolean x;
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        if (x) {
//            String x = "BADUM!!!";
//        } else {
//            Executors.newSingleThreadScheduledExecutor().schedule
//                    (new Runnable() {
//                        public void run() {
//                            Intent intent = new Intent(context, MainActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                            PendingIntent pendingIntent = PendingIntent.getActivity(Service.this, 0, intent, 0);
//
//                            Notification notification = new Notification.Builder(Service.this)
//                                    .setTicker("Vertretungplanupdate")
//                                    .setSmallIcon(R.drawable.vertretungsalart)
//                                    .setContentTitle(getString(R.string.vertretungsplanupdate))
//                                    .setContentText(getString(R.string.vertretungsplanupdatet))
//                                    .setContentIntent(pendingIntent).getNotification();
//                            notification.flags = Notification.DEFAULT_ALL;
//                            notification.flags = Notification.FLAG_AUTO_CANCEL;
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                            notificationManager.notify(0, notification);
//                        }
//                    }, 1, TimeUnit.HOURS);
//        }
//    }
}
