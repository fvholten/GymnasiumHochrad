package de.hochrad.hochradapp.service;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompleted extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
            context.startService(new Intent(context, Service.class));
        }
    }
}
