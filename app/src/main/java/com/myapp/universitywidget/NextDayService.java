package com.myapp.universitywidget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;

import org.json.JSONException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NextDayService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String date = intent.getStringExtra("currentDate");

        int[] allWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
        AppWidgetUpdater widgetUpdater = new AppWidgetUpdater(NextDayService.this);
        UniversityDao universityDao = new UniversityDao(NextDayService.this);
        Event[] nextDayEvents = universityDao.readNextDayEvents(date);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        try {
            widgetUpdater.updateWidget(allWidgetIds, nextDayEvents, dateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }
}
