package com.myapp.universitywidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class UniversityWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        ComponentName componentName = new ComponentName(context, UniversityWidget.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
        Intent intent = new Intent(context.getApplicationContext(),
                WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);
        context.startService(intent);
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}

