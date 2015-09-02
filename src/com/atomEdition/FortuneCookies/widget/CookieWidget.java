package com.atomEdition.FortuneCookies.widget;

import android.app.Application;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import com.atomEdition.FortuneCookies.CookiesActivity;
import com.atomEdition.FortuneCookies.R;
import com.atomEdition.FortuneCookies.Utils;
import com.atomEdition.FortuneCookies.model.Prophecy;
import com.atomEdition.FortuneCookies.services.ProphecyUtils;

/**
 * Created by FruityDevil on 27.01.2015.
 */
public class CookieWidget extends AppWidgetProvider {

    public static String ACTION_WIDGET_RECEIVER = "ActionReceiverWidget";

    private static void updateWidget(Context context, AppWidgetManager appWidgetManager,
                                     SharedPreferences sharedPreferences, int widgetId) {
        RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        if (ProphecyUtils.isProphecyAlreadyGot(context)) {
            int size = sharedPreferences.getInt(Utils.PREFERENCES_LIST_SIZE, 0);
            Prophecy prophecy = new Prophecy(sharedPreferences.getString(Utils.PREFERENCES_LIST_ELEMENT + (size - 1), ""));
            widgetView.setTextViewText(R.id.text_widget, prophecy.getProphecy());
            int paperId = context.getResources().getIdentifier(Utils.PROPHECY_NAME + prophecy.getProphecyType(), "drawable", context.getPackageName());
            widgetView.setImageViewResource(R.id.image_widget, paperId);
        } else {
            widgetView.setTextViewText(R.id.text_widget, "");
            widgetView.setImageViewResource(R.id.image_widget, R.drawable.widget_image_idle);
        }
        appWidgetManager.updateAppWidget(widgetId, widgetView);
    }

    public static Intent updateWidget(Context appContext, Application application) {
        Intent intent = new Intent(appContext, CookieWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(application).getAppWidgetIds(new ComponentName(application, CookieWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        return intent;
    }

    @Override
    public void onEnabled(Context context){
        super.onEnabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Utils.PREFERENCES_NAME, Context.MODE_PRIVATE);
        for (int id : appWidgetIds) {
            updateWidget(context, appWidgetManager, sharedPreferences, id);
        }
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        Intent intent = new Intent(context, CookieWidget.class);
        intent.setAction(ACTION_WIDGET_RECEIVER);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if(ACTION_WIDGET_RECEIVER.equals(action)){
            intent.setAction(null);
            Intent intentMenu = new Intent(context, CookiesActivity.class);
            intentMenu.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentMenu);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

}
