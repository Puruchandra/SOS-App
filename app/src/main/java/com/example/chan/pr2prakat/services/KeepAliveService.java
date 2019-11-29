package com.example.chan.pr2prakat.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class KeepAliveService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
