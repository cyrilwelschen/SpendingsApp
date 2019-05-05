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

        // todo: somehow this doesn't work any more, maybe error is in SecondaryInputActivity.java
        Intent intentFood= new Intent(context, SecondaryInputActivity.class);
        intentFood.putExtra("CATEGORY", "Komisionen");
        PendingIntent pendingIntentFood = PendingIntent.getActivity(context, 0, intentFood, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentShop= new Intent(context, SecondaryInputActivity.class);
        intentShop.putExtra("CATEGORY", "Shopping");
        PendingIntent pendingIntentShop = PendingIntent.getActivity(context, 0, intentShop, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentNice= new Intent(context, SecondaryInputActivity.class);
        intentNice.putExtra("CATEGORY", "NiceToHave");
        PendingIntent pendingIntentNice = PendingIntent.getActivity(context, 0, intentNice, 0);

        Intent intentBank= new Intent(context, SecondaryInputActivity.class);
        intentBank.putExtra("CATEGORY", "Rechnung");
        PendingIntent pendingIntentBank = PendingIntent.getActivity(context, 0, intentBank, 0);

        views.setOnClickPendingIntent(R.id.widget_button_food, pendingIntentFood);
        views.setOnClickPendingIntent(R.id.widget_button_shop, pendingIntentShop);
        views.setOnClickPendingIntent(R.id.widget_button_nice, pendingIntentNice);
        views.setOnClickPendingIntent(R.id.widget_button_bank, pendingIntentBank);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

}
