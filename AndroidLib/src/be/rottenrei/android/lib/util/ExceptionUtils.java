package be.rottenrei.android.lib.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import be.rottenrei.android.lib.R;

/**
 * Methods for handling exceptions in a uniform way.
 */
public class ExceptionUtils {

	/** The throwable can be null. */
	public static void shouldNeverBeReached(Activity activity, Class<?> clazz, Throwable cause) {
		if (cause == null) {
			cause = new IllegalStateException("An impossible state has been reached");
		}
		handleFatalExceptionWithMessage(cause, activity, R.string.internal_error, clazz);
	}

	/** Logs the exception and displays the warning message to the user. */
	public static void handleExceptionWithMessage(Throwable cause, Context context, int message, Class<?> clazz) {
		logException(cause, context, message, clazz);
		UIUtils.informUser(context, message);
	}

	/** Just logs the exception with the given message. */
	public static void logException(Throwable cause, Context context, int message, Class<?> clazz) {
		Log.e(clazz.getName(), context.getString(message), cause);
	}

	/** Handles the exception and finishes the activity if possible. */
	public static void handleFatalExceptionWithMessage(Throwable cause, Context context, int message, Class<?> clazz) {
		handleExceptionWithMessage(cause, context, message, clazz);
		if (context instanceof Activity) {
			Activity activity = (Activity) context;
			activity.finish();
		}
	}

}
