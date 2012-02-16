package be.rottenrei.android.lib.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


/**
 * Provides utility methods to database version classes.
 */
abstract public class DatabaseVersionBase implements IDatabaseVersion {

	protected boolean columnExists(SQLiteDatabase db, String table, String column) {
		Cursor cursor = db.rawQuery("pragma table_info(" + table + ");", null);
		if (cursor == null) {
			return false;
		}
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			String name = cursor.getString(cursor.getColumnIndex("key"));
			if (column.equals(name)) {
				return true;
			}
			cursor.moveToNext();
		}
		return false;
	}

	protected boolean tableExists(SQLiteDatabase db, String tableName) {
		Cursor cursor = db.query("sqlite_master", new String[] { "key" }, "type = 'table'", null, null, null, null);
		if (cursor == null) {
			return false;
		}
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			String name = cursor.getString(cursor.getColumnIndex("key"));
			if (tableName.equals(name)) {
				return true;
			}
			cursor.moveToNext();
		}
		return false;
	}

}
