package be.rottenrei.android.lib.model;

import java.sql.SQLException;
import java.util.Stack;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import be.rottenrei.android.lib.R;
import be.rottenrei.android.lib.util.ExceptionUtils;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

/**
 * Base class for DatabaseOpenHelpers.
 * 
 * Manages a context and provides automatic version upgrades.
 */
public abstract class DatabaseOpenHelperBase extends OrmLiteSqliteOpenHelper {

	private final Context context;
	private final IDatabaseVersion version;

	public DatabaseOpenHelperBase(Context context, String databaseName, IDatabaseVersion version) {
		super(context, databaseName, null, version.getVersion());
		this.context = context;
		this.version = version;
	}

	@Override
	public void onCreate(SQLiteDatabase database, ConnectionSource source) {
		Log.w(DatabaseOpenHelperBase.class.getName(), "Creating database");
		upgradeFrom(source, 0);
	}

	public void upgradeFrom(ConnectionSource source, int baseVersion) {
		Stack<IDatabaseVersion> versions = new Stack<IDatabaseVersion>();
		IDatabaseVersion current = version;
		while (current != null && current.getVersion() > baseVersion) {
			versions.push(current);
			current = current.getPrevious();
		}
		while (!versions.isEmpty()) {
			IDatabaseVersion version = versions.pop();
			try {
				version.upgrade(source);
			} catch (SQLException e) {
				ExceptionUtils.handleFatalExceptionWithMessage(e, context, R.string.no_database, DatabaseOpenHelperBase.class);
				return;
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource source, int oldVersion, int newVersion) {
		Log.w(DatabaseOpenHelperBase.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion);
		upgradeFrom(source, oldVersion);
		onCreate(database);
	}

	public Context getContext() {
		return context;
	}

}
