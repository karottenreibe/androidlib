package be.rottenrei.android.lib.db;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Provides access to basic table operations.
 */
public abstract class TableBase<ModelType extends IModelType> {

	public static final String KEY_ID = "_id";

	private final SQLiteDatabase db;

	public TableBase(SQLiteDatabase db) {
		this.db = db;
	}

	public void insert(ModelType object) throws DatabaseException {
		ContentValues values = getSerializer().serialize(object);
		long result = db.insert(getTableName(), null, values);
		if (result == -1) {
			throw new DatabaseException("Could not insert object into database: " + values.toString());
		} else {
			object.setDbId(result);
		}
	}

	/** Updates the object in the database or creates it if it doesn't exist. */
	public void update(ModelType object) throws DatabaseException {
		if (object.getDbId() == null) {
			insert(object);
		} else {
			ContentValues values = getSerializer().serialize(object);
			boolean success = db.update(getTableName(), values, KEY_ID + "=" + object.getDbId(), null) > 0;
			if (!success) {
				throw new DatabaseException("Could not update object in database: " + values.toString());
			}
		}
	}

	public void delete(ModelType object) throws DatabaseException {
		boolean success = db.delete(getTableName(), KEY_ID + "=" + object.getDbId(), null) > 0;
		if (!success) {
			throw new DatabaseException("Could not delete object from database: " + object.getDbId());
		}
	}

	public List<ModelType> getAll() throws DatabaseException {
		return getSelection(null, null);
	}

	protected List<ModelType> getSelection(String selection, String[] selectionArgs) throws DatabaseException {
		Cursor cursor = getCursor(selection, selectionArgs);
		List<ModelType> allItems = new LinkedList<ModelType>();
		TableSerializer<ModelType> serializer = getSerializer();

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ModelType item = serializer.deSerialize(cursor);
			allItems.add(item);
			cursor.moveToNext();
		}
		return allItems;
	}

	public Cursor getCursor(String selection, String[] selectionArgs) {
		return db.query(getTableName(), getColumns(), selection, selectionArgs, null, null, null);
	}

	public ModelType get(ModelType object) throws DatabaseException {
		Long dbId = object.getDbId();
		return get(dbId);
	}

	private ModelType get(Long dbId) throws DatabaseException {
		Cursor cursor = db.query(true, getTableName(), getColumns(), KEY_ID + "=" + dbId,
				null, null, null, null, null);
		if (cursor == null) {
			return null;
		}
		cursor.moveToFirst();
		return getSerializer().deSerialize(cursor);
	}

	abstract public TableSerializer<ModelType> getSerializer();

	abstract protected String[] getColumns();

	abstract protected String getTableName();

	/**
	 * Can serialize a ModelType object into a ContentValues object and
	 * deserialize a Cursor into a ModelType object.
	 */
	public static interface TableSerializer<ModelType extends IModelType> {

		public ContentValues serialize(ModelType type);

		/** @throws DatabaseException if the cursor contains invalid data. */
		public ModelType deSerialize(Cursor cursor) throws DatabaseException;

	}

}
