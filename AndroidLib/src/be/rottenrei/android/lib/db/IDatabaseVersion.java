package be.rottenrei.android.lib.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Interface for versions of the database that can be upgraded.
 */
public interface IDatabaseVersion {

	public void upgrade(SQLiteDatabase db);

	public IDatabaseVersion getPrevious();

	public int getVersion();

	boolean willPerformUpgrade(SQLiteDatabase db);

}
