package be.rottenrei.android.lib.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;
import be.rottenrei.android.lib.R;

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

	public static void confirm(Context context, int messageId, OnClickListener onConfirmedListener) {
		new AlertDialog.Builder(context)
		.setTitle(R.string.confirm)
		.setMessage(messageId)
		.setNegativeButton(R.string.cancel, null)
		.setPositiveButton(R.string.ok, onConfirmedListener)
		.show();
	}

}
