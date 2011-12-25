package be.rottenrei.android.lib.test.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

abstract public class DatabaseTestBase extends GenericInstrumentationTestBase {

	protected SQLiteDatabase db;

	protected static class TestDatabaseOpenHelper extends SQLiteOpenHelper {

		public TestDatabaseOpenHelper(Context context, String name) {
			super(context, name, null, 0);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			clearDatabase(db);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			clearDatabase(db);
		}

		private void clearDatabase(SQLiteDatabase db) {
			// drop all tables
			db.execSQL("PRAGMA writable_schema = 1;" +
					"delete from sqlite_master where type = 'table';" +
					"PRAGMA writable_schema = 0;" +
					"VACUUM;" +
					"PRAGMA INTEGRITY_CHECK;");
		}

	}

	@Override
	public void setUp() {
		db = new TestDatabaseOpenHelper(getContext(), getDatabaseName()).getWritableDatabase();
	}

	abstract protected String getDatabaseName();

}
