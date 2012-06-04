package be.rottenrei.android.lib.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.text.TextUtils;
import be.rottenrei.android.lib.R;
import be.rottenrei.android.lib.util.ExceptionUtils;

/**
 * Base class that handles the progress dialog and exceptions.
 */
public abstract class ProgressTaskBase<Params, Result> extends AsyncTask<Params, String, Result> {

	protected ProgressDialog dialog;
	protected Exception exception = null;
	protected Integer exceptionMessage = null;
	protected Activity activity;

	public static <P, T extends ProgressTaskBase<P, ?>> void createAndRun(T task, Activity activity, P... params) {
		task.setActivity(activity);
		task.execute(params);
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
		dialog = new ProgressDialog(activity);
	}

	protected String getString(int messageId, CharSequence... params) {
		return TextUtils.expandTemplate(activity.getText(messageId), params).toString();
	}

	@Override
	protected void onPreExecute() {
		dialog.setMessage(getString(R.string.progress_preparing));
		dialog.show();
	}

	@Override
	protected void onProgressUpdate(String... values) {
		dialog.setMessage(values[0]);
	}

	protected boolean exception(Exception e, int messageId) {
		exception = e;
		exceptionMessage = messageId;
		return false;
	}

	@Override
	protected void onPostExecute(Result result) {
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
		if (exception != null) {
			ExceptionUtils.handleExceptionWithMessage(exception, activity, exceptionMessage);
			return;
		}
	}

}
