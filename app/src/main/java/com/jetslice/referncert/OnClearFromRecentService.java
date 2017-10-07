package com.jetslice.referncert;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by shubham on 7/10/17.
 */

public class OnClearFromRecentService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("ClearFromRecentService", "Service Started");
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("ClearFromRecentService", "Service Destroyed");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {

        //TODO place your destroy notification code here.notification should not display if the file is already downloaded in storage.Paas normal intents if necessary


        Log.e("ClearFromRecentService", "END");
        //Code here
        stopSelf();
    }
}
