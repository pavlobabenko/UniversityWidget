package com.myapp.universitywidget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Base64;
import android.widget.RemoteViews;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class WidgetService extends Service {

    UniversityDao universityDao = new UniversityDao(this);

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (universityDao.isEmpty()) {
                    String jsonString = new FileDownloader().downloadFile();
                    try {
                        universityDao.insert(jsonString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Event[] todayEvents = universityDao.readTodayEvents();
                int[] allWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
                AppWidgetUpdater widgetUpdater = new AppWidgetUpdater(WidgetService.this);
                widgetUpdater.updateWidget(allWidgetIds, todayEvents, new Date());
                stopSelf();
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }
}
