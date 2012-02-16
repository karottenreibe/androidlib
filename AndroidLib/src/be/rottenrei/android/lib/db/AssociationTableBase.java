package be.rottenrei.android.lib.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import be.rottenrei.android.lib.db.AssociationTableBase.Association;
import be.rottenrei.android.lib.db.TableCreateStatement.Type;

/**
 * Base class for association tables.
 */
public abstract class AssociationTableBase extends TableBase<Association> {

	public static String getCreateStatement(String tableName, ColumnNameHolder columnNameHolder) {
		return new TableCreateStatement(tableName)
		.col(columnNameHolder.keyColumn, Type.integer, false, "0")
		.col(columnNameHolder.valueColumn, Type.integer, false, "0")
		.end();
	}

	private final AssociationTableSerializer serializer;
	private final ColumnNameHolder columnNameHolder;

	public AssociationTableBase(SQLiteDatabase db, ColumnNameHolder columnNameHolder) {
		super(db);
		this.columnNameHolder = columnNameHolder;
		this.serializer = new AssociationTableSerializer();
	}

	@Override
	protected TableSerializer<Association> getSerializer() {
		return serializer;
	}

	@Override
	protected String[] getColumns() {
		return new String[] { KEY_ID, columnNameHolder.keyColumn, columnNameHolder.valueColumn };
	}

	private class AssociationTableSerializer implements TableBase.TableSerializer<Association> {

		@Override
		public ContentValues serialize(Association item) {
			ContentValues values = new ContentValues();
			values.put(columnNameHolder.keyColumn, item.getKey());
			values.put(columnNameHolder.valueColumn, item.getValue());
			return values;
		}

		@Override
		public Association deSerialize(Cursor cursor) throws DatabaseException {
			try {
				Long dbid = cursor.getLong(cursor.getColumnIndexOrThrow(KEY_ID));
				Long key = cursor.getLong(cursor.getColumnIndexOrThrow(columnNameHolder.keyColumn));
				Long value = cursor.getLong(cursor.getColumnIndexOrThrow(columnNameHolder.valueColumn));

				Association association = new Association();
				association.setDbId(dbid);
				association.setKey(key);
				association.setValue(value);
				return association;
			} catch (IllegalArgumentException e) {
				throw new DatabaseException(e);
			}
		}

	}

	public static class Association implements IModelType {

		private Long dbId;

		private long key;
		private long value;

		@Override
		public Long getDbId() {
			return dbId;
		}

		@Override
		public void setDbId(Long id) {
			dbId = id;
		}

		public long getKey() {
			return key;
		}

		public void setKey(long key) {
			this.key = key;
		}

		public long getValue() {
			return value;
		}

		public void setValue(long value) {
			this.value = value;
		}

	}

	public static class ColumnNameHolder {

		public final String keyColumn;
		public final String valueColumn;

		public ColumnNameHolder(String keyColumn, String valueColumn) {
			this.keyColumn = keyColumn;
			this.valueColumn = valueColumn;
		}

	}

}
