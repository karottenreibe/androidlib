package be.rottenrei.android.lib.model;

import java.sql.SQLException;

import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.support.ConnectionSource;

/**
 * Handles database creation and upgrades.
 */
public interface IMigrationManager {

	public Class<?>[] getModelClasses();
	public void populateNewDatabase(SQLiteDatabase database, ConnectionSource source) throws SQLException;
	public void upgradeFrom(SQLiteDatabase database, ConnectionSource source, int baseVersion) throws SQLException;

}
