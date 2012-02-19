package be.rottenrei.android.lib.model;

import java.sql.SQLException;

import com.j256.ormlite.support.ConnectionSource;

/**
 * Interface for versions of the database that can be upgraded.
 */
public interface IDatabaseVersion {

	public void upgrade(ConnectionSource source) throws SQLException;

	public IDatabaseVersion getPrevious();

	public int getVersion();

}
