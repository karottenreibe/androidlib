package be.rottenrei.android.lib.test.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DebugUtils {

	/** Logs the names of all tables in the database at warning level. */
	public static void logTableNames(SQLiteDatabase db, String tag) {
		Cursor cursor = db.query("sqlite_master", new String[] { "key" }, "type = 'table'",
				null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			String name = cursor.getString(cursor.getColumnIndex("key"));
			Log.w(tag, "found table: " + name);
			cursor.moveToNext();
		}
	}

	public static void longOperation() {
		long v = -1;
		for (long i = 0; i < 100000000l; ++i) {
			v = v + 1;
		}
		Log.d("long.operation", "computed: " + v);
	}

}
