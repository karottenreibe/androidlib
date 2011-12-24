package be.rottenrei.android.lib.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Utilities for UI interactions, e.g. showing toasts etc.
 */
public class UIUtils {

	public static void informUser(Context context, int message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public static void informUser(Context context, int message, CharSequence... messageArgs) {
		CharSequence messageString = TextUtils.expandTemplate(context.getString(message), messageArgs);
		Toast toast = Toast.makeText(context, messageString, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

}
