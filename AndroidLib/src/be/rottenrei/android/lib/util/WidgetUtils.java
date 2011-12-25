package be.rottenrei.android.lib.util;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/**
 * Utility functions for widgets.
 */
public class WidgetUtils {

	public static void forceUpdate(Context context, Class<? extends AppWidgetProvider> widgetClazz) {
		Intent intent = new Intent(context, widgetClazz);
		intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
		ComponentName name = new ComponentName(context, widgetClazz);
		int [] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(name);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
		context.sendBroadcast(intent);
	}

}
