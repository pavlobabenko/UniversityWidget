package com.myapp.universitywidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.widget.RemoteViews;

public class AppWidgetUpdater {

    private AppWidgetManager appWidgetManager;
    private RemoteViews remoteViews;

    AppWidgetUpdater(Context context) {
        appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
        remoteViews = new RemoteViews(context.getPackageName(), R.layout.university_widget);
    }

    void updateWidget(int[] allWidgetIds, Event[] todayEvents) {
        for (int widgetId : allWidgetIds) {
            for (int i = 1; i <= todayEvents.length - 1; i++) {
                if (todayEvents[i] != null) {
                    remoteViews.setTextViewText(getTextViewAuditory(i), todayEvents[i].auditory);
                    remoteViews.setTextViewText(getTextViewBrief(i), todayEvents[i].brief);
                    remoteViews.setImageViewResource(getImageView(i), getType(todayEvents[i].type));
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
        if (index == 20) {
            return R.drawable.lab;
        }
        return 0;
    }


}
