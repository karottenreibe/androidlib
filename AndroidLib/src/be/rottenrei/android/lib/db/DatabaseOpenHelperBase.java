package be.rottenrei.android.lib.db;

import java.util.Stack;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Base class for database open helpers.
 */
public abstract class DatabaseOpenHelperBase extends SQLiteOpenHelper {

	private final IDatabaseVersion currentVersion;

	public DatabaseOpenHelperBase(Context context, String name, IDatabaseVersion version) {
		super(context, name, null, version.getVersion());
		this.currentVersion = version;
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		Log.w(DatabaseOpenHelperBase.class.getName(), "Creating database");
		upgradeFrom(database, 0);
	}

	private void upgradeFrom(SQLiteDatabase database, int baseVersion) {
		Stack<IDatabaseVersion> versions = new Stack<IDatabaseVersion>();
		IDatabaseVersion current = currentVersion;
		while (current != null && current.getVersion() > baseVersion) {
			versions.push(current);
			current = current.getPrevious();
		}
		while (!versions.isEmpty()) {
			IDatabaseVersion version = versions.pop();
			if (version.willPerformUpgrade(database)) {
				version.upgrade(database);
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(DatabaseOpenHelperBase.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion);
		upgradeFrom(database, oldVersion);
		onCreate(database);
	}

}
