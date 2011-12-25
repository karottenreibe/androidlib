package be.rottenrei.android.lib.test.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

abstract public class DatabaseTestBase extends GenericInstrumentationTestBase {

	protected SQLiteDatabase db;

	protected class TestDatabaseOpenHelper extends SQLiteOpenHelper {

		public TestDatabaseOpenHelper(Context context, String name) {
			super(context, name, null, 1);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// nothing to do
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// nothing to do
		}


	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		db = new TestDatabaseOpenHelper(getContext(), getDatabaseName()).getWritableDatabase();
		// drop all tables
		for (String tableName : getTableNames()) {
			db.execSQL("drop table if exists " + tableName + ";");
		}
	}

	abstract protected String getDatabaseName();

	abstract protected String[] getTableNames();

}
