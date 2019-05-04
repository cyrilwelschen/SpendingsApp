package com.example.cyrilwelschen.spendingsapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by cyril on 04.05.19.
 *
 */

public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                 int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget_layout);
        // Construct an Intent object includes web adresss.
        Intent intentFood = new Intent(context, ScrollingActivity.class);
        PendingIntent pendingIntentFood = PendingIntent.getActivity(context, 0, intentFood, 0);
        views.setOnClickPendingIntent(R.id.widget_button_food, pendingIntentFood);

        Intent intentShop= new Intent(context, SecondaryInputActivity.class);
        PendingIntent pendingIntentShop = PendingIntent.getActivity(context, 0, intentShop, 0);
        views.setOnClickPendingIntent(R.id.widget_button_food, pendingIntentShop);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

}
