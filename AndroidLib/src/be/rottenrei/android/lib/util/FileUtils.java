package be.rottenrei.android.lib.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.os.Environment;
import be.rottenrei.android.lib.R;

/**
 * Utilities for working with the Android external storage and {@link File}s in general.
 */
public class FileUtils {

	public static boolean isExternalStorageAvailable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean ensureExternalStorageIsAvailable(Context context) {
		if (isExternalStorageAvailable()) {
			return true;
		} else {
			UIUtils.informUser(context, R.string.error_no_external_storage);
			return false;
		}
	}

	public static boolean ensureFolderIsAvailable(Context context, File folder) {
		if (isFolderAvailable(folder)) {
			return true;
		} else {
			UIUtils.informUser(context, R.string.error_no_folder, folder.getAbsolutePath());
			return false;
		}
	}

	public static boolean isFolderAvailable(File folder) {
		return folder.exists() && folder.isDirectory();
	}

	/** Creates the directory structure above the file or warns the user that the operation failed. */
	public static boolean ensureFileIsReadyForWriting(File file, Context context) {
		if (!ensureExternalStorageIsAvailable(context)) {
			return false;
		}
		if (!isFileReadyForWriting(file)) {
			UIUtils.informUser(context, R.string.error_mkdirs_failed);
			return false;
		}
		return true;
	}

	public static boolean isFileReadyForWriting(File file) {
		if (!isExternalStorageAvailable()) {
			return false;
		}
		File directory = file.getParentFile();
		if (!directory.exists() && !directory.mkdirs()) {
			return false;
		}
		return true;
	}

	public static String readWholeFile(InputStream stream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		StringBuilder builder = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			builder.append(line);
			line = reader.readLine();
		}
		return builder.toString();
	}

}
