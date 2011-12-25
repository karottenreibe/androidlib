package be.rottenrei.android.lib.db;

/**
 * Creates an SQL string that creates a table.
 */
public class TableCreateStatement {

	private final StringBuilder builder;

	public TableCreateStatement(String tableName) {
		builder = new StringBuilder("create table if not exists ")
		.append(tableName).append(" (")
		.append(TableBase.KEY_ID).append(" integer primary key autoincrement");
	}

	public String end() {
		builder.append(");");
		return builder.toString();
	}

	public TableCreateStatement col(String columnName, Type type, boolean nullable, String defaultValue) {
		builder.append(", ");
		builder.append(columnName).append(" ").append(type.toString());
		if (!nullable) {
			builder.append(" not null");
		}
		builder.append(" default " + defaultValue);
		return this;
	}

	public enum Type {
		text,
		integer,
	}

}
