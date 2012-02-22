package be.rottenrei.android.lib.model;

import java.sql.SQLException;

import android.database.sqlite.SQLiteDatabase;

/**
 * Utils for upgrading databases.
 */
public class DatabaseUpgradeUtils {

	public static enum EType {
		INTEGER, TEXT
	}

	public static void addColumn(SQLiteDatabase database, String table, String column,
			EType type, boolean nullable, String defaultValue) throws SQLException {
		StringBuffer sb = new StringBuffer("alter table ");
		sb.append(table).append(" add column ").append(column).append(" ").append(type.name());
		if (!nullable) {
			sb.append(" not null");
		}
		sb.append(" default ").append(defaultValue).append(";");
		try {
			database.execSQL(sb.toString());
		} catch (android.database.SQLException e) {
			throw new SQLException(e);
		}
	}

}
