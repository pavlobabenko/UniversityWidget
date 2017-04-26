package com.myapp.universitywidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.util.Date;

public class AppWidgetUpdater {

    private AppWidgetManager appWidgetManager;
    private RemoteViews remoteViews;
    Context context;

    AppWidgetUpdater(Context context) {
        this.context = context;
        appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
        remoteViews = new RemoteViews(context.getPackageName(), R.layout.university_widget);
    }

    void updateWidget(int[] allWidgetIds, Event[] todayEvents, Date date) {
        Intent intent = new Intent(context.getApplicationContext(), UniversityWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.textView1, pendingIntent);


        Intent intentNextDay = new Intent(context.getApplicationContext(), NextDayService.class);
        intentNextDay.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        String nextDay = dateFormat.format(new Date(date.getTime()+(1000*60*60*24)));
        intentNextDay.putExtra("currentDate", nextDay);

        PendingIntent pendingIntentNextDay = PendingIntent.getService(context,
                0, intentNextDay, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.textView6, pendingIntentNextDay);

        for (int widgetId : allWidgetIds) {
            for (int i = 1; i <= todayEvents.length - 1; i++) {
                if (todayEvents[i] != null) {
                    System.out.println(todayEvents[i].getType());
                    remoteViews.setTextViewText(getTextViewAuditory(i), todayEvents[i].getAuditory());
                    remoteViews.setTextViewText(getTextViewBrief(i), todayEvents[i].getBrief());
                    remoteViews.setImageViewResource(getImageView(i), getType(todayEvents[i].getType()));
                } else {
                    remoteViews.setTextViewText(getTextViewAuditory(i), "");
                    remoteViews.setTextViewText(getTextViewBrief(i), "");
                    remoteViews.setImageViewResource(getImageView(i), R.drawable.empty);
                }
            }
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    private int getTextViewAuditory(int index) {
        if (index == 1) {
            return R.id.auditory1;
        }
        if (index == 2) {
            return R.id.auditory2;
        }
        if (index == 3) {
            return R.id.auditory3;
        }
        if (index == 4) {
            return R.id.auditory4;
        }
        if (index == 5) {
            return R.id.auditory5;
        }
        if (index == 6) {
            return R.id.auditory6;
        }
        return 0;
    }

    private int getTextViewBrief(int index) {
        if (index == 1) {
            return R.id.brief1;
        }
        if (index == 2) {
            return R.id.brief2;
        }
        if (index == 3) {
            return R.id.brief3;
        }
        if (index == 4) {
            return R.id.brief4;
        }
        if (index == 5) {
            return R.id.brief5;
        }
        if (index == 6) {
            return R.id.brief6;
        }
        return 0;
    }

    private int getImageView(int index) {
        if (index == 1) {
            return R.id.imageView1;
        }
        if (index == 2) {
            return R.id.imageView2;
        }
        if (index == 3) {
            return R.id.imageView3;
        }
        if (index == 4) {
            return R.id.imageView4;
        }
        if (index == 5) {
            return R.id.imageView5;
        }
        if (index == 6) {
            return R.id.imageView6;
        }
        return 0;
    }

    private int getType(int index) {
        if (index == 0) {
            return R.drawable.lecture;
        }
        if (index == 10) {
            return R.drawable.practice;
        }
        if (index == 20|| index==22||index == 21) {
            return R.drawable.lab;
        }
        return 0;
    }


}
