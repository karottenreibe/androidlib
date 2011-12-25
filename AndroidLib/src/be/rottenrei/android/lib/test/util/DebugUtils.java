package be.rottenrei.android.lib.test.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DebugUtils {

	/** Logs the names of all tables in the database at warning level. */
	public void logTableNames(SQLiteDatabase db, String tag) {
		Cursor cursor = db.query("sqlite_master", new String[] { "name" }, "type = 'table'",
				null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			String name = cursor.getString(cursor.getColumnIndex("name"));
			Log.w(tag, "found table: " + name);
			cursor.moveToNext();
		}
	}

}
