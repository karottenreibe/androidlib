package be.rottenrei.android.lib.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import be.rottenrei.android.lib.R;

/**
 * Methods for handling exceptions in a uniform way.
 */
public class ExceptionUtils {

	public static void shouldNeverBeReached(Activity activity, Class<?> clazz) {
		Exception e = new IllegalStateException("impossible option selected");
		handleFatalExceptionWithMessage(e, activity, R.string.internal_error, clazz);
	}

	/** Logs the exception and displays the warning message to the user. */
	public static void handleExceptionWithMessage(Exception e, Context context, int message, Class<?> clazz) {
		logException(e, context, message, clazz);
		UIUtils.informUser(context, message);
	}

	/** Just logs the exception with the given message. */
	public static void logException(Exception e, Context context, int message, Class<?> clazz) {
		Log.e(clazz.getName(), context.getString(message), e);
	}

	/** Handles the exception and finishes the activity. */
	public static void handleFatalExceptionWithMessage(Exception e, Context context, int message, Class<?> clazz) {
		handleExceptionWithMessage(e, context, message, clazz);
		if (context instanceof Activity) {
			Activity activity = (Activity) context;
			activity.finish();
		}
	}

}
